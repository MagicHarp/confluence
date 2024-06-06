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
import org.confluence.mod.entity.fishing.BloodyFishingHook;
import org.confluence.mod.misc.ModRarity;

import java.util.UUID;

public class ChumCaster extends AbstractFishingPole {
    public static final UUID LUCK_UUID = UUID.fromString("9D53F775-C8BF-68DD-B98C-5311BD7E4A7F");
    private static final ImmutableMultimap<Attribute, AttributeModifier> LUCK = ImmutableMultimap.of(
        Attributes.LUCK, new AttributeModifier(LUCK_UUID, "Chum Caster", 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public ChumCaster() {
        super(new Properties().rarity(ModRarity.GREEN).durability(384));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) return LUCK;
        return EMPTY;
    }

    @Override
    protected FishingHook getHook(ItemStack itemStack, Player player, Level level, int luckBonus, int speedBonus) {
        return new BloodyFishingHook(player, level, luckBonus, speedBonus);
    }
}
