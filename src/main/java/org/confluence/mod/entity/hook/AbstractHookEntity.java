package org.confluence.mod.entity.hook;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.item.hook.AbstractHookItem;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.IntFunction;

public abstract class AbstractHookEntity extends Projectile {
    private static final EntityDataAccessor<Integer> DATA_HOOK_STATE = SynchedEntityData.defineId(AbstractHookEntity.class, EntityDataSerializers.INT);
    private final float hookRangeSqr;
    private final AbstractHookItem.HookType hookType;

    public AbstractHookEntity(EntityType<? extends AbstractHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
        this.hookRangeSqr = 0.0F;
        this.hookType = null;
    }

    public AbstractHookEntity(EntityType<? extends AbstractHookEntity> entityType, AbstractHookItem item, Player player, Level level) {
        super(entityType, level);
        this.hookRangeSqr = item.getHookRange() * item.getHookRange();
        this.hookType = item.getHookType();
        setOwner(player);
        setNoGravity(true);
        setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_HOOK_STATE, 0);
    }

    public HookState getHookState() {
        return HookState.byId(entityData.get(DATA_HOOK_STATE));
    }

    public void setHookState(HookState state) {
        entityData.set(DATA_HOOK_STATE, state.id);
    }

    @Override
    public void tick() {
        super.tick();
        Entity owner = getOwner();
        if (owner == null || owner.isRemoved()) {
            discard();
            return;
        }
        move(MoverType.SELF, getDeltaMovement());
        Level level = level();
        if (level.isClientSide) return;
        Optional<ItemStack> hook = CuriosUtils.getSlot((LivingEntity) owner, "hook", 0);
        if (hook.isEmpty()) {
            discard();
            return;
        }
        ItemStack itemStack = hook.get();
        HookState hookState = getHookState();
        if (hookState == HookState.PUSH) {
            if (distanceToSqr(owner) > hookRangeSqr) {
                setHookState(HookState.POP);
                return;
            }
            if (ProjectileUtil.getHitResultOnMoveVector(this, entity -> false).getType() != HitResult.Type.BLOCK) {
                return;
            }
            setDeltaMovement(Vec3.ZERO);
            setHookState(HookState.HOOKED);
            if (hookType == AbstractHookItem.HookType.INDIVIDUAL && itemStack.getOrCreateTag().get("hooks") instanceof ListTag list) {
                list.forEach(tag -> {
                    if (tag instanceof CompoundTag tag1 && level.getEntity(tag1.getInt("id")) instanceof AbstractHookEntity hookEntity && hookEntity != this) {
                        hookEntity.setHookState(HookState.POP);
                    }
                });
            }
        } else if (hookState == HookState.POP) {
            addDeltaMovement(owner.position().subtract(getX(), getY(), getZ()).normalize().scale(0.05));
            if (distanceToSqr(owner) < 4.0) discard();
        }
    }

    public enum HookState implements StringRepresentable {
        PUSH(0, "push"), // 发射
        POP(1, "pop"), // 收回
        HOOKED(2, "hooked"); // 抓住

        public static final Codec<HookState> CODEC = StringRepresentable.fromEnum(HookState::values);
        private static final IntFunction<HookState> BY_ID = ByIdMap.continuous(HookState::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        final int id;
        private final String name;

        HookState(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public static HookState byId(int pId) {
            return BY_ID.apply(pId);
        }

        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
