package org.confluence.mod.item.hook;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.entity.hook.MimicHookEntity;
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
    WEB_SLINGER("web_slinger", WebSlingerItem::new),
    SKELETRON_HAND("skeletron_hand", SkeletronHandItem::new),
    SLIME_HOOK("slime_hook", SlimeHookItem::new),
    FISH_HOOK("fish_hook", FishHookItem::new),
    IVY_WHIP("ivy_whip", IvyWhipItem::new),
    BAT_HOOK("bat_hook", BatHookItem::new),
    CANDY_CANE_HOOK("candy_cane_hook", CandyCaneHookItem::new),
    // 困难模式
    DUAL_HOOK("dual_hook", DualHookItem::new),
    HOOK_OF_DISSONANCE("hook_of_dissonance", HookOfDissonanceItem::new),
    THORN_HOOK("thorn_hook", ThornHookItem::new),
    ILLUMINANT_HOOK("illuminant_hook", () -> new MimicHookItem(MimicHookEntity.Variant.ILLUMINANT)),
    WORM_HOOK("worm_hook", () -> new MimicHookItem(MimicHookEntity.Variant.WORM)),
    TENDON_HOOK("tendon_hook", () -> new MimicHookItem(MimicHookEntity.Variant.TENDON)),
    // todo ANTI_GRAVITY_HOOK("anti_gravity_hook", AntiGravityHookItem::new),
    SPOOKY_HOOK("spooky_hook", SpookyHookItem::new),
    CHRISTMAS_HOOK("christmas_hook", ChristmasHookItem::new),
    LUNAR_HOOK("lunar_hook", LunarHookItem::new),
    /* todo 静止钩 */;

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
