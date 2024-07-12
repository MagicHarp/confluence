package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class PanicNecklace extends BaseCurioItem {
    public static final UUID SPEED_UUID = UUID.fromString("E939EBB6-41D9-4B5D-9778-A019B820D7A8");
    private static ImmutableMultimap<Attribute, AttributeModifier> SPEED;

    public PanicNecklace() {
        super();
    }

    public PanicNecklace(Rarity rarity) {
        super(rarity);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.isClientSide) return;
        CompoundTag nbt = stack.getOrCreateTag();
        long lastHurt = nbt.getLong("lastHurt");
        if (lastHurt == 0) return;
        if (level.getGameTime() - lastHurt > 100) {
            nbt.putLong("lastHurt", 0);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (SPEED == null) {
            SPEED = ImmutableMultimap.of(
                Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Panic Necklace", ModConfigs.PANIC_NECKLACE_MOVEMENT.get(), AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return stack.getOrCreateTag().getLong("lastHurt") == 0 ? EMPTY_ATTRIBUTE : SPEED;
    }

    public static void apply(LivingEntity living) {
        CuriosUtils.findCurio(living, CurioItems.PANIC_NECKLACE.get())
            .ifPresent(itemStack -> itemStack.getOrCreateTag().putLong("lastHurt", living.level().getGameTime()));
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.panic_necklace.info"),
            Component.translatable("item.confluence.panic_necklace.info2")
        };
    }
}
