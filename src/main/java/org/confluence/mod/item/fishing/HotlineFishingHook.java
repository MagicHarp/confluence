package org.confluence.mod.item.fishing;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;

import java.util.UUID;

public class HotlineFishingHook extends AbstractFishingPole {
    public static final UUID LUCK_UUID = UUID.fromString("F3C1B9F6-4A0B-199B-C2D2-35DB33213D50");
    private static final ImmutableMultimap<Attribute, AttributeModifier> LUCK = ImmutableMultimap.of(
        Attributes.LUCK, new AttributeModifier(LUCK_UUID, "Hotline Fishing Hook", 0.45, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public HotlineFishingHook() {
        super(new Properties().rarity(ModRarity.ORANGE).fireResistant().durability(512));
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 4;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) return LUCK;
        return EMPTY;
    }

    @Override
    protected FishingHook getHook(ItemStack itemStack, Player player, Level level, int luckBonus, int speedBonus) {
        return new org.confluence.mod.entity.fishing.HotlineFishingHook(player, level, luckBonus, speedBonus);
    }
}
