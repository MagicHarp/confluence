package org.confluence.mod.common.init.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.entity.fishing.CurioFishingHook;
import org.confluence.mod.common.entity.projectile.bombs.*;
import org.confluence.mod.common.init.block.ModBlocks;
import org.confluence.mod.common.item.common.BombItem;
import org.confluence.mod.common.item.common.LifeCrystal;
import org.confluence.mod.common.item.common.LifeFruit;
import org.confluence.mod.common.item.curio.fishing.FishingBobber;
import org.confluence.mod.common.item.mana.ManaStar;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Confluence.MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(Confluence.MODID);

    public static final DeferredItem<ManaStar> MANA_STAR = ITEMS.register("mana_star", ManaStar::new);
    public static final DeferredItem<LifeCrystal> LIFE_CRYSTAL = ITEMS.register("life_crystal", LifeCrystal::new);
    public static final DeferredItem<LifeFruit> LIFE_FRUIT = ITEMS.register("life_fruit", LifeFruit::new);

    public static final DeferredItem<BombItem> BOMB = ITEMS.register("bomb", () -> new BombItem(BaseBombEntity::new));
    public static final DeferredItem<BombItem> BOUNCY_BOMB = ITEMS.register("bouncy_bomb", () -> new BombItem(BouncyBombEntity::new));
    public static final DeferredItem<BombItem> STICKY_BOMB = ITEMS.register("sticky_bomb", () -> new BombItem(StickyBombEntity::new));
    public static final DeferredItem<BombItem> BOMB_FISH = ITEMS.register("bomb_fish", () -> new BombItem(BombFishEntity::new)); // todo 炸弹鱼
    public static final DeferredItem<BombItem> SCARAB_BOMB = ITEMS.register("scarab_bomb", () -> new BombItem(ScarabBombEntity::new));

    public static final DeferredItem<Item> COPPER_COIN = ITEMS.register("copper_coin", () -> new BlockItem(ModBlocks.COPPER_COIN_PILE.get(), new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final DeferredItem<Item> SILVER_COIN = ITEMS.register("silver_coin", () -> new BlockItem(ModBlocks.SILVER_COIN_PILE.get(), new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final DeferredItem<Item> GOLDEN_COIN = ITEMS.register("golden_coin", () -> new BlockItem(ModBlocks.GOLDEN_COIN_PILE.get(), new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final DeferredItem<Item> PLATINUM_COIN = ITEMS.register("platinum_coin", () -> new BlockItem(ModBlocks.PLATINUM_COIN_PILE.get(), new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final DeferredItem<FishingBobber> FISHING_BOBBER = ITEMS.register("fishing_bobber", () -> new FishingBobber(CurioFishingHook.Variant.COMMON)), // 钓鱼浮标
            GLOWING_FISHING_BOBBER = ITEMS.register("glowing_fishing_bobber", () -> new FishingBobber(CurioFishingHook.Variant.GLOWING)), // 发光钓鱼浮标
            LAVA_MOSS_FISHING_BOBBER = ITEMS.register("lava_moss_fishing_bobber", () -> new FishingBobber(CurioFishingHook.Variant.LAVA)), // 熔岩苔藓钓鱼浮标
            HELIUM_MOSS_FISHING_BOBBER = ITEMS.register("helium_moss_fishing_bobber", () -> new FishingBobber(CurioFishingHook.Variant.HELIUM)), // 氦苔藓钓鱼浮标
            NEON_MOSS_FISHING_BOBBER = ITEMS.register("neon_moss_fishing_bobber", () -> new FishingBobber(CurioFishingHook.Variant.NEON)), // 氖苔藓钓鱼浮标
            ARGON_MOSS_FISHING_BOBBER = ITEMS.register("argon_moss_fishing_bobber", () -> new FishingBobber(CurioFishingHook.Variant.ARGON)), // 氩苔藓钓鱼浮标
            KRYPTON_MOSS_FISHING_BOBBER = ITEMS.register("krypton_moss_fishing_bobber", () -> new FishingBobber(CurioFishingHook.Variant.KRYPTON)), // 氪苔藓钓鱼浮标
            XENON_MOSS_FISHING_BOBBER = ITEMS.register("xenon_moss_fishing_bobber", () -> new FishingBobber(CurioFishingHook.Variant.XENON)); // 氙苔藓钓鱼浮标

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
        ArrowItems.ARROWS.register(eventBus);
        BaitItems.BAITS.register(eventBus);
        BowItems.BOWS.register(eventBus);
        FishingPoleItems.POLES.register(eventBus);
        IconItems.ICONS.register(eventBus);
        MaterialItems.MATERIALS.register(eventBus);
        SwordItems.SWORDS.register(eventBus);
        TerraPotions.POTIONS.register(eventBus);
        QuestedFishes.FISHES.register(eventBus);
    }
}
