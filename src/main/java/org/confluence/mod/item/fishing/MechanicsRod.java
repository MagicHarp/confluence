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
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.entity.fishing.BaseFishingHook;
import org.confluence.mod.misc.ModRarity;

import java.util.UUID;

public class MechanicsRod extends AbstractFishingPole implements CustomName {
    public static final UUID LUCK_UUID = UUID.fromString("1072ACCB-514C-8D50-B844-4608ADEC43A9");
    private static final ImmutableMultimap<Attribute, AttributeModifier> LUCK = ImmutableMultimap.of(
        Attributes.LUCK, new AttributeModifier(LUCK_UUID, "Mechanic's Rod", 0.35, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public MechanicsRod() {
        super(new Properties().rarity(ModRarity.GREEN).durability(384));
    }

    @Override
    public String getGenName() {
        return "Mechanic's Rod";
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) return LUCK;
        return EMPTY;
    }

    @Override
    protected FishingHook getHook(ItemStack itemStack, Player player, Level level, int luckBonus, int speedBonus) {
        return new BaseFishingHook(player, level, luckBonus, speedBonus, BaseFishingHook.Variant.MECHANICS);
    }
}
