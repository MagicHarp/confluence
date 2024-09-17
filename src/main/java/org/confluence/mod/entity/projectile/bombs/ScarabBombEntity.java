package org.confluence.mod.entity.projectile.bombs;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class ScarabBombEntity extends StickyBombEntity {
    private Vec3 facingDir = new Vec3(0, -1, 0);

    public ScarabBombEntity(EntityType<? extends ScarabBombEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ScarabBombEntity(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.SCARAB_BOMB_ENTITY.get(), pX, pY, pZ, pLevel);
    }

    public ScarabBombEntity(LivingEntity pShooter) {
        super(ModEntities.SCARAB_BOMB_ENTITY.get(), pShooter);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.SCARAB_BOMB.get();
    }

    @Override
    protected void explodeFunction() {
        Vec3 blastPos = getEyePosition();
        Vec3 step = facingDir.normalize().scale(3);
        float upperLimit = ModBlocks.getObsidianBasedExplosionResistance(100);
        ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList = new ObjectArrayList<>();
        for (int i = 0; i < 13; i++) {
            if (i % 3 == 0) {
                Explosion explosion = level().explode(this, blastPos.x(), blastPos.y(), blastPos.z(), blastPower, Level.ExplosionInteraction.MOB);
                Vec3 end = blastPos.add(step);
                BlockPos.betweenClosedStream(new AABB(blastPos, end)).forEach(blockPos -> {
                    if (!level().isLoaded(blockPos)) return;
                    BlockState blockState = level().getBlockState(blockPos);
                    if (blockState.getExplosionResistance(level(), blockPos, explosion) < upperLimit) {
                        level().getProfiler().push("explosion_blocks");
                        if (blockState.canDropFromExplosion(level(), blockPos, explosion)) {
                            if (level() instanceof ServerLevel serverLevel) {
                                BlockEntity blockentity = blockState.hasBlockEntity() ? level().getBlockEntity(blockPos) : null;
                                LootParams.Builder lootparams$builder = new LootParams.Builder(serverLevel)
                                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos))
                                        .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                                        .withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockentity)
                                        .withOptionalParameter(LootContextParams.THIS_ENTITY, this);
                                blockState.spawnAfterBreak(serverLevel, blockPos, ItemStack.EMPTY, getOwner() instanceof Player);
                                blockState.getDrops(lootparams$builder).forEach((itemStack) -> addBlockDrops(objectArrayList, itemStack, blockPos));
                            }
                        }
                        blockState.onBlockExploded(level(), blockPos, explosion);
                        level().getProfiler().pop();
                    }
                });
                blastPos = end;
            }
        }
        for (Pair<ItemStack, BlockPos> pair : objectArrayList) {
            Block.popResource(level(), pair.getSecond(), pair.getFirst());
        }
    }

    private static void addBlockDrops(ObjectArrayList<Pair<ItemStack, BlockPos>> pDropPositionArray, ItemStack pStack, BlockPos pPos) {
        int i = pDropPositionArray.size();
        for (int j = 0; j < i; ++j) {
            Pair<ItemStack, BlockPos> pair = pDropPositionArray.get(j);
            ItemStack itemstack = pair.getFirst();
            if (ItemEntity.areMergable(itemstack, pStack)) {
                ItemStack itemStack = ItemEntity.merge(itemstack, pStack, 16);
                pDropPositionArray.set(j, Pair.of(itemStack, pair.getSecond()));
                if (pStack.isEmpty()) return;
            }
        }
        pDropPositionArray.add(Pair.of(pStack, pPos));
    }

    @Override
    public void tick() {
        super.tick();
        if (getOwner() != null) {
            this.facingDir = getEyePosition().subtract(getOwner().getEyePosition());
            float[] yawPitch = ModUtils.dirToRot(facingDir);
            // 将Yaw和Pitch调整至45的倍数（还原原作.jpg）
            for (int i = 0; i < 2; i++) {
                // 先+360防止取余运算对负数犯病
                yawPitch[i] += 360f;
                float remainder = yawPitch[i] % 45f;
                yawPitch[i] -= remainder;
                if (remainder > 22.5f)
                    yawPitch[i] += 45f;
            }
            this.facingDir = ModUtils.rotToDir(yawPitch[0], yawPitch[1]);
        }
        ModUtils.updateEntityRotation(this, facingDir);
    }
}
