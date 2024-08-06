package org.confluence.mod.entity.hook;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;
import org.confluence.mod.util.CuriosUtils;

import java.util.Optional;

public class AntiGravityHookEntity extends AbstractHookEntity {
    public AntiGravityHookEntity(EntityType<AntiGravityHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public AntiGravityHookEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.ANTI_GRAVITY_HOOK.get(), item, player, level);
    }
    @Override
    protected void onHooked(BlockHitResult hitResult, ItemStack itemStack) {
        BlockPos blockPos = hitResult.getBlockPos();
        level().gameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Context.of(this, level().getBlockState(blockPos)));
        setDeltaMovement(Vec3.ZERO);
        setHookState(HookState.HOOKED);
        this.hasImpulse = true;
    }
    @Override
    public void tick(){
        super.tick();
        Entity owner = getOwner();
        if (owner == null || owner.isRemoved()) {
            discard();
            return;
        }

        Vec3 motion = getDeltaMovement();
        double x = getX() + motion.x;
        double y = getY() + motion.y;
        double z = getZ() + motion.z;
        setPos(x, y, z);

        HookState hookState = getHookState();


        if (hookState == HookState.HOOKED) {
            Vec3 speed = owner.getDeltaMovement();
            owner.setDeltaMovement(speed.x, 0.0, speed.z);
            owner.fallDistance = 0.0F;
            return;
        }

        if (level().isClientSide) return;
        Optional<ItemStack> hook = CuriosUtils.getSlot((LivingEntity) owner, "hook", 0);
        if (hook.isEmpty()) {
            discard();
            return;
        }

        if (hookState == HookState.POP) {
            if (distanceToSqr(owner) < 2.0) {
                discard();
                return;
            } else if (distanceToSqr(owner) > 1500.0) {
                setDeltaMovement(getDeltaMovement().scale(0.85).add(owner.position().subtract(position()).normalize().scale(4)));
            } else {
                setDeltaMovement(getDeltaMovement().scale(0.85).add(owner.position().subtract(position()).normalize().scale(0.5)));
            }
            return;
        }

        if (distanceToSqr(owner) > hookRangeSqr) {
            setHookState(HookState.POP);
            return;
        }

        if (hookState == HookState.PUSH) {
            if (distanceToSqr(owner) < 4) {
                setDeltaMovement(getDeltaMovement().scale(1.3));
            }
            Vec3 pos = position();
            Vec3 nextPos = pos.add(motion);

            BlockHitResult hitResult = level().clip(new ClipContext(pos, nextPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                Vec3 hitPos = hitResult.getLocation();

                double adjustX = hitPos.x - pos.x;
                double adjustY = hitPos.y - pos.y;
                double adjustZ = hitPos.z - pos.z;

                double newX = pos.x + adjustX * 0.8;
                double newY = pos.y + adjustY * 0.8;
                double newZ = pos.z + adjustZ * 0.8;

                if (hitResult.isInside()) {
                    setPos(getX() - adjustX, getY() - adjustY, getZ() - adjustZ);
                } else {
                    setPos(newX, newY, newZ);
                }
                onHitBlock(hitResult);
                onHooked(hitResult, hook.get());
            }
        }
    }
}
