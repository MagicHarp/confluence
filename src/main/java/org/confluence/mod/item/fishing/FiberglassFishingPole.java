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

public class FiberglassFishingPole extends AbstractFishingPole {
    public static final UUID LUCK_UUID = UUID.fromString("BF6DC104-5657-6705-C2D9-93CA81EABB79");
    private static final ImmutableMultimap<Attribute, AttributeModifier> LUCK = ImmutableMultimap.of(
        Attributes.LUCK, new AttributeModifier(LUCK_UUID, "Fiberglass Fishing Pole", 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public FiberglassFishingPole() {
        super(new Properties().rarity(ModRarity.GREEN).durability(384));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) return LUCK;
        return EMPTY;
    }

    @Override
    protected FishingHook getHook(ItemStack itemStack, Player player, Level level, int luckBonus, int speedBonus) {
        return new BaseFishingHook(player, level, luckBonus, speedBonus, BaseFishingHook.Variant.FIBERGLASS);
    }
}
