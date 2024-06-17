package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicCuffs extends BandOfStarpower {
    public MagicCuffs() {
        super(ModRarity.GREEN);
    }

    public MagicCuffs(Rarity rarity) {
        super(rarity);
    }

    public static void apply(LivingEntity living, DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.DROWN) || damageSource.is(ModTags.HARMFUL_EFFECT)) return;
        if (CuriosUtils.hasCurio(living, CurioItems.MAGIC_CUFFS.get())) {
            living.getCapability(ManaProvider.CAPABILITY)
                .ifPresent(manaStorage -> manaStorage.receiveMana(() -> (int) amount));
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.magic_cuffs.tooltip"));
        list.add(Component.translatable("item.confluence.magic_cuffs.tooltip2"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.magic_cuffs.info"),
            Component.translatable("item.confluence.magic_cuffs.info2"),
            Component.translatable("item.confluence.magic_cuffs.info3"),
            Component.translatable("item.confluence.magic_cuffs.info4")
        };
    }
}
