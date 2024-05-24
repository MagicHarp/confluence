package org.confluence.mod.item.hook;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Hooks implements EnumRegister<AbstractHookItem> {
    GRAPPLING_HOOK("grappling_hook", GrapplingHookItem::new),
    AMETHYST_HOOK("amethyst_hook", AmethystHookItem::new),
    TOPAZ_HOOK("topaz_hook", TopazHookItem::new),
    SAPPHIRE_HOOK("sapphire_hook", SapphireHookItem::new),
    EMERALD_HOOK("emerald_hook", EmeraldHookItem::new),
    RUBY_HOOK("ruby_hook", RubyHookItem::new),
    AMBER_HOOK("amber_hook", AmberHookItem::new),
    DIAMOND_HOOK("diamond_hook", DiamondHookItem::new),
    WEB_SLINGER("web_slinger", WebSlingerItem::new), // 没有模型,使用就崩
    SKELETRON_HAND("skeletron_hand", SkeletronHandItem::new), // 没有模型,使用就崩
    ;

    private final RegistryObject<AbstractHookItem> value;

    Hooks(String id, Supplier<AbstractHookItem> supplier) {
        this.value = ModItems.ITEMS.register(id, supplier);
    }

    @Override
    public RegistryObject<AbstractHookItem> getValue() {
        return value;
    }

    public static void init() {}

    public static void acceptTag(IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> tag) {
        for (Hooks hooks : values()) tag.add(hooks.get());
    }
}
