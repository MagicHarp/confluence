package org.confluence.mod.item.common;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.capability.mana.ManaStorage;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class ManaStorageItem extends Item {
    private final Function<ManaStorage, Boolean> toCheck;
    private final Consumer<ManaStorage> toEnable;

    public ManaStorageItem(Function<ManaStorage, Boolean> toCheck, Consumer<ManaStorage> toEnable) {
        super(new Properties().rarity(ModRarity.LIGHT_PURPLE).fireResistant());
        this.toCheck = toCheck;
        this.toEnable = toEnable;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.SPYGLASS;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 15;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide) {
            pLivingEntity.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
                if (!toCheck.apply(manaStorage)) {
                    pStack.shrink(1);
                    toEnable.accept(manaStorage);
                    // todo sound
                }
            });
        }
        return pStack;
    }
}
