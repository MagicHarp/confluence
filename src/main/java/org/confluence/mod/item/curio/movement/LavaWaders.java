package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.ILavaHurtReduce;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class LavaWaders extends BaseCurioItem implements IFireImmune, ILavaImmune, ILavaHurtReduce, IFluidWalk {
    public LavaWaders() {
        super(ModRarity.LIME);
    }

    @Override
    public boolean canStandOn(FluidState fluidState) {
        return fluidState.is(ModTags.ALL_FLUID_WALK);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), IFluidWalk.class);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IFluidWalk.ALL_FLUID);
        list.add(Component.translatable("item.confluence.lava_waders.tooltip2"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.lava_waders.info"),
            Component.translatable("item.confluence.lava_waders.info2"),
            Component.translatable("item.confluence.lava_waders.info3"),
            Component.translatable("item.confluence.lava_waders.info4"),
            Component.translatable("item.confluence.lava_waders.info5"),
            Component.translatable("item.confluence.lava_waders.info6")
        };
    }
}
