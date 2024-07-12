package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class WarriorEmblem extends BaseCurioItem {
    public static final UUID DAMAGE_UUID = UUID.fromString("595F6B45-6487-A65A-29C9-D00096A3D7AE");
    private static ImmutableMultimap<Attribute, AttributeModifier> DAMAGE;

    public WarriorEmblem() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (DAMAGE == null) {
            DAMAGE = ImmutableMultimap.of(
                Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Warrior Emblem", ModConfigs.WARRIOR_EMBLEM_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return DAMAGE;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.warrior_emblem.info"),
            Component.translatable("item.confluence.warrior_emblem.info2")
        };
    }
}
