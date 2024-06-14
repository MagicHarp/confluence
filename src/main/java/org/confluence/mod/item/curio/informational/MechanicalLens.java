package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.curio.IFunctionCouldEnable;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class MechanicalLens extends AbstractInfoCurio implements CustomModel, IFunctionCouldEnable {
    public static final byte INDEX = 12;

    public MechanicalLens() {
        super(ModRarity.ORANGE);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) cycleEnable(pPlayer.getItemInHand(pUsedHand));
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.mechanical_lens.info"),
                Component.translatable("item.confluence.mechanical_lens.info2")
        };
    }
}
