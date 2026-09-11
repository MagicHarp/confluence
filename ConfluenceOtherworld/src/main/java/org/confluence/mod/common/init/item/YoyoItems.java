package org.confluence.mod.common.init.item;

import net.minecraft.world.item.Item;
import org.confluence.lib.common.component.ModRarity;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.item.yoyo.CascadeYoyoItem;
import org.confluence.mod.common.item.yoyo.HiveFiveYoyoItem;
import org.confluence.mod.common.item.yoyo.YoyoDefinition;
import org.confluence.mod.common.item.yoyo.YoyoItem;
import org.mesdag.portlib.registries.PortDeferredItem;
import org.mesdag.portlib.registries.PortItemRegistration;
import org.mesdag.portlib.registries.PortRegisterHandler;

public class YoyoItems {
    public static void init() {}

    public static final PortItemRegistration ITEMS = PortRegisterHandler.item(Confluence.MODID);

    public static final PortDeferredItem<YoyoItem> AMAZON = register("amazon", ModRarity.ORANGE, 4.5F, 14, 0xFFC896, 8);
    public static final PortDeferredItem<YoyoItem> ARTERY = register("artery", ModRarity.BLUE, 4.2F, 13, 0x9696FF, 6);
    public static final PortDeferredItem<YoyoItem> CASCADE = ITEMS.register("cascade", () -> new CascadeYoyoItem(ModRarity.ORANGE, YoyoDefinition.of(5.5F, 15, 0xFFC896, 13)));
    public static final PortDeferredItem<YoyoItem> CODE_1 = register("code_1", ModRarity.GREEN, 4.8F, 14, 0x96FF96, 9);
    public static final PortDeferredItem<YoyoItem> HIVE_FIVE = ITEMS.register("hive_five", () -> new HiveFiveYoyoItem(ModRarity.ORANGE, YoyoDefinition.of(5.2F, 14, 0xFFC896, 8)));
    public static final PortDeferredItem<YoyoItem> MALAISE = register("malaise", ModRarity.BLUE, 3.8F, 12, 0x9696FF, 7);
    public static final PortDeferredItem<YoyoItem> RALLY = register("rally", ModRarity.BLUE, 3.5F, 10, 0x9696FF, 5);
    public static final PortDeferredItem<YoyoItem> VALOR = register("valor", ModRarity.ORANGE, 5.7F, 15, 0xFFC896, 11);
    public static final PortDeferredItem<YoyoItem> WOODEN_YOYO = register("wooden_yoyo", ModRarity.WHITE, 1.5F, 8, 0x00FF00, 3);

    private static PortDeferredItem<YoyoItem> register(String name, ModRarity rarity, float damage, float range, int stringColor, float lifetime) {
        return ITEMS.register(name, () -> new YoyoItem(new Item.Properties().unbreakable(), rarity, YoyoDefinition.of(damage, range, stringColor, lifetime)));
    }
}
