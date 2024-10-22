package org.confluence.mod.common.init.item;

import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.component.ModRarity;
import org.confluence.mod.common.item.bow.BaseArrowItem;

public class Arrows {
    public static final DeferredRegister.Items ARROWS = DeferredRegister.createItems(Confluence.MODID);

    public static final DeferredItem<BaseArrowItem> FLAMING_ARROW = ARROWS.register("flaming_arrow", () -> new BaseArrowItem(ModRarity.WHITE));
    public static final DeferredItem<BaseArrowItem> UNHOLY_ARROW = ARROWS.register("unholy_arrow", () -> new  BaseArrowItem(ModRarity.BLUE));
    public static final DeferredItem<BaseArrowItem> JESTERS_ARROW = ARROWS.register("jesters_arrow", () -> new  BaseArrowItem(ModRarity.BLUE));
    public static final DeferredItem<BaseArrowItem> HELLFIRE_ARROW = ARROWS.register("hellfire_arrow", () -> new  BaseArrowItem(ModRarity.GREEN));
    public static final DeferredItem<BaseArrowItem> FROSTBURN_ARROW = ARROWS.register("frostburn_arrow", () -> new  BaseArrowItem(ModRarity.WHITE));
    public static final DeferredItem<BaseArrowItem> BONE_ARROW = ARROWS.register("bone_arrow", () -> new  BaseArrowItem(ModRarity.WHITE));
    public static final DeferredItem<BaseArrowItem> SHIMMER_ARROW = ARROWS.register("shimmer_arrow", () -> new BaseArrowItem(ModRarity.WHITE));
}
