package org.confluence.mod.item.curio.construction;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class ArchitectGizmoPack extends BaseCurioItem implements IRightClickSubtractor {
    public ArchitectGizmoPack() {
        super(ModRarity.PINK);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ExtendoGrip.getOrCreateAttributes();
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.architect_gizmo_pack.info"),
            Component.translatable("item.confluence.architect_gizmo_pack.info2")
        };
    }
}
