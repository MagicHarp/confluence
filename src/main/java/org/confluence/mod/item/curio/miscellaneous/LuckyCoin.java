package org.confluence.mod.item.curio.miscellaneous;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class LuckyCoin extends BaseCurioItem {
    public static final UUID LUCKY_UUID = UUID.fromString("C136140E-2C12-14F2-ED0D-A6848D4F4EED");
    public static final ImmutableMultimap<Attribute, AttributeModifier> LUCKY = ImmutableMultimap.of(
        Attributes.LUCK, new AttributeModifier(LUCKY_UUID, "Lucky Coin", 0.05, AttributeModifier.Operation.ADDITION)
    );

    public LuckyCoin() {
        super(ModRarity.PINK);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return LUCKY;
    }

    public static void apply(Player player, Entity target) {
        if (!ModConfigs.dropsMoney) return;
        RandomSource randomSource = player.getRandom();
        if (randomSource.nextFloat() < 0.2F && CuriosUtils.hasCurio(player, LuckyCoin.class)) {
            Item item;
            float a = randomSource.nextFloat();
            if (a < 0.01F) {
                item = ModItems.GOLDEN_COIN.get();
            } else if (a < 0.099F) {
                item = ModItems.SILVER_COIN.get();
            } else {
                item = ModItems.COPPER_COIN.get();
            }
            Containers.dropItemStack(player.level(), target.getX(), target.getY(), target.getZ(), new ItemStack(item, randomSource.nextInt(1, 3)));
        }
    }
}
