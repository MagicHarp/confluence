package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class ObsidianWaterWalkingBoots extends BaseCurioItem implements IFluidWalk, IFireImmune {
    public ObsidianWaterWalkingBoots() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public boolean canStandOn(FluidState fluidState) {
        return fluidState.is(Fluids.WATER) || fluidState.is(Fluids.LAVA);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), IFluidWalk.class);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IFluidWalk.WATER);
        list.add(IFireImmune.TOOLTIP);
    }
}
