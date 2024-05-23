package org.confluence.mod.item.hook;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Hooks implements EnumRegister<AbstractHookItem> {
    GRAPPLING("grappling_hook", GrapplingHookItem::new);

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
