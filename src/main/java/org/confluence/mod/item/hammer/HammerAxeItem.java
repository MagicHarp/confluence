package org.confluence.mod.item.hammer;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class HammerAxeItem extends DiggerItem {
    public HammerAxeItem(Tier tier, float rawDamage, float rawSpeed) {
        this(tier, rawDamage, rawSpeed, new Properties());
    }

    public HammerAxeItem(Tier tier, float rawDamage, float rawSpeed, Properties properties) {
        super(rawDamage - tier.getAttackDamageBonus() - 1, rawSpeed - 4, tier, BlockTags.WALLS, properties);
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack itemStack, @NotNull BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_WITH_AXE) ? super.getDestroySpeed(itemStack, blockState) : speed;
    }

    @Override
    public boolean isCorrectToolForDrops(@NotNull ItemStack stack, @NotNull BlockState state) {
        return (state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.WALLS)) && net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(getTier(), state);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }
}
