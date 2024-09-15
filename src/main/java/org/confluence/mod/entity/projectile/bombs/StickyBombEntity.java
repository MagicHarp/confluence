package org.confluence.mod.entity.projectile.bombs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class StickyBombEntity extends BaseBombEntity {
    BlockPos stickBlock = null;

    public StickyBombEntity(EntityType<? extends StickyBombEntity> pEntityType, Player player, Level pLevel) {
        super(pEntityType, player, pLevel);
        super.bounceFactor = 0f;
    }

    public StickyBombEntity(EntityType<? extends StickyBombEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        super.bounceFactor = 0f;
    }

    public StickyBombEntity(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
        super.bounceFactor = 0f;
    }

    public StickyBombEntity(Level pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
        super.bounceFactor = 0f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.STICKY_BOMB.get();
    }

    @Override
    protected void blockHitCallBack(BlockHitResult blockHitResult) {
        super.blockHitCallBack(blockHitResult);
        // 取消重力
        if (this.stickBlock == null) {
            gravityOn = false;
        }
        this.stickBlock = blockHitResult.getBlockPos();
        // 停止移动
        this.setDeltaMovement(Vec3.ZERO);
        // 传送到碰撞位置
        Vec3 collPos = blockHitResult.getLocation();
        this.teleportTo( collPos.x(), collPos.y(), collPos.z() );
    }

    @Override
    public void tick() {
        super.tick();
        // 黏附方块消失后恢复重力
        if (this.stickBlock != null && this.level().getBlockState(stickBlock).canBeReplaced()) {
            gravityOn = true;
            this.stickBlock = null;
        }
    }
}
