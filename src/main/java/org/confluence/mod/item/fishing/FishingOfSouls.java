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
import org.confluence.mod.entity.fishing.BaseFishingHook;
import org.confluence.mod.misc.ModRarity;

import java.util.UUID;

public class FishingOfSouls extends AbstractFishingPole {
    public static final UUID LUCK_UUID = UUID.fromString("F6C9D343-03C4-AB75-4441-D4E46450B17B");
    private static final ImmutableMultimap<Attribute, AttributeModifier> LUCK = ImmutableMultimap.of(
        Attributes.LUCK, new AttributeModifier(LUCK_UUID, "Fishing Of Souls", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public FishingOfSouls() {
        super(new Properties().rarity(ModRarity.BLUE).durability(256));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) return LUCK;
        return ImmutableMultimap.of();
    }

    @Override
    protected FishingHook getHook(ItemStack itemStack, Player player, Level level, int luckBonus, int speedBonus) {
        return new BaseFishingHook(player, level, luckBonus, speedBonus, BaseFishingHook.Variant.SOULS);
    }
}
