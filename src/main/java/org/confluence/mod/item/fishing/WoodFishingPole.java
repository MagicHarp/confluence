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

public class WoodFishingPole extends AbstractFishingPole {
    public static final UUID LUCK_UUID = UUID.fromString("00526142-0004-ADE9-DA9A-652A62E0EE7E");
    private static final ImmutableMultimap<Attribute, AttributeModifier> LUCK = ImmutableMultimap.of(
        Attributes.LUCK, new AttributeModifier(LUCK_UUID, "WoodFishing Pole", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public WoodFishingPole() {
        super(new Properties().rarity(ModRarity.WHITE).durability(64));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) return LUCK;
        return ImmutableMultimap.of();
    }

    @Override
    protected FishingHook getHook(ItemStack itemStack, Player player, Level level, int luckBonus, int speedBonus) {
        return new BaseFishingHook(player, level, luckBonus, speedBonus, BaseFishingHook.Variant.WOOD);
    }
}
