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
            float speedOff = state.getDestroySpeed(level, pos) * 0.7F;

            if (entity instanceof Player player){
                BlockHitResult picked = (BlockHitResult) player.pick(10,1.0F, true);

                boolean xOff = true, yOff = true, zOff = true;
                switch (picked.getDirection()){
                    case DOWN, UP -> yOff = false;
                    case NORTH, SOUTH -> zOff = false;
                    case WEST, EAST -> xOff = false;
                }

                BlockPos[] posL = new BlockPos[8];
                int count = 0;
                for (int d1 = -1; d1 <= 1; d1++){
                    for (int d2 = -1; d2 <= 1; d2++){
                        if (d1 == 0 && d2 == 0) continue;
                        if (xOff) posL[count] = pos.offset(d1, yOff?d2:0, zOff?d2:0);
                        else posL[count] = pos.offset(0, d1, d2);
                        count++;
                    }
                }
                for (BlockPos pos1 : posL){
                    BlockState targetState = level.getBlockState(pos1);
                    boolean flag1 = targetState.canHarvestBlock(level, pos, player);
                    boolean flag2 = targetState.getDestroySpeed(level, pos) > speedOff;
                    boolean flag3 = player.getAbilities().instabuild;
                    if (flag1 || flag2 || flag3){
                        level.destroyBlock(pos1, flag1, player);
                        destroyCount++;
                    }
                }
            }
            if (state.getDestroySpeed(level, pos) != 0.0F) {
                stack.hurtAndBreak(destroyCount, entity, (entity1) -> entity1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return true;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }
}
