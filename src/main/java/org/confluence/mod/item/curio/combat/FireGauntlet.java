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
import net.minecraftforge.common.ForgeMod;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class FireGauntlet extends BaseCurioItem implements IFireAttack, IContinueSwing {
    public static final UUID DAMAGE_UUID = UUID.fromString("27D54C06-2AE7-C9F2-60D8-AC4FF55F7B9F");
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("AF3F478D-0E82-B1D5-6A1C-1714BCA73525");
    public static final UUID KNOCK_BACK_UUID = UUID.fromString("AAFC960E-43F6-B0D4-859C-9C198E60D057");
    public static final UUID DISTANCE_UUID = UUID.fromString("4EC038D4-318C-EEC7-C7E8-29E614E8572E");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Mechanical Glove", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Mechanical Glove", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ATTACK_KNOCKBACK, new AttributeModifier(KNOCK_BACK_UUID, "Mechanical Glove", 1, AttributeModifier.Operation.MULTIPLY_TOTAL),
        ForgeMod.ENTITY_REACH.get(), new AttributeModifier(DISTANCE_UUID, "Mechanical Glove", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public FireGauntlet() {
        super(ModRarity.LIME);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.fire_gauntlet.tooltip2"));
    }
}
