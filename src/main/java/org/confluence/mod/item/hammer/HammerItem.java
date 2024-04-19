package org.confluence.mod.item.hammer;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class HammerItem extends DiggerItem {
    public HammerItem(Tier tier, float rawDamage, float rawSpeed) {
        this(tier, rawDamage, rawSpeed, new Properties());
    }

    public HammerItem(Tier tier, float rawDamage, float rawSpeed, Properties properties) {
        super(rawDamage - tier.getAttackDamageBonus() - 1, rawSpeed - 4, tier, BlockTags.WALLS, properties);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entity) {
        if (!level.isClientSide) {
            int destroyCount = 1;
            if (entity instanceof Player player){
                BlockHitResult picked = (BlockHitResult) player.pick(10,1.0F, true);
                boolean xOff = true, yOff = true, zOff = true;
                switch (picked.getDirection()){
                    case NORTH, SOUTH -> zOff = false;
                    case WEST, EAST -> xOff = false;
                    default -> yOff = false;
                }
                destroyCount += iteForBlocks(level, player, pos, xOff, yOff, zOff, state.getDestroySpeed(level, pos) * 1.5F);
            }
            if (state.getDestroySpeed(level, pos) != 0.0F) {
                stack.hurtAndBreak(destroyCount, entity, (entity1) -> entity1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return true;
    }

    /**Scan 3*1*3 blocks related to the given pos. */
    public static int iteForBlocks(Level level, Player player, BlockPos pos, boolean xOff, boolean yOff, boolean zOff, float speedOff){
        int innerDestroyed = 0;
        for (int d1 = -1; d1 <= 1; d1++){
            for (int d2 = -1; d2 <= 1; d2++){
                if (d1 == 0 && d2 == 0) continue;
                if (applyBlockDestroy(level, xOff? pos.offset(d1, yOff?d2:0, zOff?d2:0) : pos.offset(0, d1, d2), player, speedOff)) innerDestroyed++;
            }
        }
        return innerDestroyed;
    }

    /**If the target block can hardly be break, skip it.
     * @param pos The current producing block's pos(Target pos).
     * @param speedOff Related block's destroy speed * 1.5 (satisfied range).
     * @return TRUE, if the block has been broke, otherwise return FALSE.*/
    public static boolean applyBlockDestroy(@NotNull Level level, BlockPos pos, Player player, float speedOff){
        BlockState targetState = level.getBlockState(pos);
        float targetSpeed = targetState.getDestroySpeed(level, pos);
        boolean flag1 = targetState.canHarvestBlock(level, pos, player);
        boolean flag2 = speedOff > 0 ? targetSpeed >= 0 && speedOff >= targetSpeed : targetSpeed >= speedOff;
        boolean flag3 = player.getAbilities().instabuild;
        if (flag1 && flag2 || flag3){
            level.destroyBlock(pos, flag1, player);
            return true;
        }
        return false;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }
}
