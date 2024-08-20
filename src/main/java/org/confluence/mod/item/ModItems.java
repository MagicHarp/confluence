package org.confluence.mod.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.capability.ability.PlayerAbility;
import org.confluence.mod.capability.mana.ManaStorage;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.item.armor.Armors;
import org.confluence.mod.item.axe.Axes;
import org.confluence.mod.item.common.*;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.fishing.Baits;
import org.confluence.mod.item.fishing.FishingPoles;
import org.confluence.mod.item.fishing.QuestedFishes;
import org.confluence.mod.item.flail.FlailItems;
import org.confluence.mod.item.food.Foods;
import org.confluence.mod.item.gun.AmmoItems;
import org.confluence.mod.item.gun.GunItems;
import org.confluence.mod.item.hammer.HammerAxes;
import org.confluence.mod.item.hammer.Hammers;
import org.confluence.mod.item.hook.Hooks;
import org.confluence.mod.item.loot.*;
import org.confluence.mod.item.mana.ManaWeapons;
import org.confluence.mod.item.pickaxe.Pickaxes;
import org.confluence.mod.item.potion.TerraPotions;
import org.confluence.mod.item.sword.Swords;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModSoundEvents;


@SuppressWarnings("unused")
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Confluence.MODID);

    public static final RegistryObject<MagicMirror> ICE_MIRROR = ITEMS.register("ice_mirror", MagicMirror::new);
    public static final RegistryObject<MagicMirror> MAGIC_MIRROR = ITEMS.register("magic_mirror", MagicMirror::new);
    public static final RegistryObject<CellPhone> CELL_PHONE = ITEMS.register("cell_phone", CellPhone::new);
    public static final RegistryObject<BaseItem> SHURIKEN = ITEMS.register("shuriken", BaseItem::new); // todo 手里剑
    public static final RegistryObject<BaseItem> GRENADE = ITEMS.register("grenade", BaseItem::new); // todo 手雷
    public static final RegistryObject<ClamItem> CLAM = ITEMS.register("clam", ClamItem::new);
    public static final RegistryObject<WhoopieCushionItem> WHOOPIE_CUSHION = ITEMS.register("whoopie_cushion", WhoopieCushionItem::new);
    public static final RegistryObject<ChristmasGiftsItem> CHRISTMAS_GIFT = ITEMS.register("christmas_gift", ChristmasGiftsItem::new);
    public static final RegistryObject<CanOfWormsItem> CAN_OF_WORMS = ITEMS.register("can_of_worms", CanOfWormsItem::new);
    public static final RegistryObject<HerbBagItem> HERB_BAG = ITEMS.register("herb_bag", HerbBagItem::new);
    public static final RegistryObject<RedEnvelopeItem> RED_ENVELOPE = ITEMS.register("red_envelope", RedEnvelopeItem::new);
    public static final RegistryObject<Item> COPPER_COIN = ITEMS.register("copper_coin", () -> new BlockItem(ModBlocks.COPPER_COIN_PILE.get(), new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<Item> SILVER_COIN = ITEMS.register("silver_coin", () -> new BlockItem(ModBlocks.SILVER_COIN_PILE.get(), new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<Item> GOLDEN_COIN = ITEMS.register("golden_coin", () -> new BlockItem(ModBlocks.GOLDEN_COIN_PILE.get(), new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> PLATINUM_COIN = ITEMS.register("platinum_coin", () -> new BlockItem(ModBlocks.PLATINUM_COIN_PILE.get(), new Item.Properties().rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<SuspiciousLookingEye> SUSPICIOUS_LOOKING_EYE = ITEMS.register("suspicious_looking_eye", SuspiciousLookingEye::new);

    public static final RegistryObject<ExpertTestItem> EXPERT_TEST_ITEM = ITEMS.register("expert_test_item", ExpertTestItem::new);
    public static final RegistryObject<MasterTestItem> MASTER_TEST_ITEM = ITEMS.register("master_test_item", MasterTestItem::new);
    public static final RegistryObject<HoneyBucketItem> HONEY_BUCKET = ITEMS.register("honey_bucket", HoneyBucketItem::new);
    public static final RegistryObject<BottomlessBucketItem> BOTTOMLESS_WATER_BUCKET = ITEMS.register("bottomless_water_bucket", () -> new BottomlessBucketItem(() -> Fluids.WATER, ModRarity.LIME));
    public static final RegistryObject<BottomlessBucketItem> BOTTOMLESS_LAVA_BUCKET = ITEMS.register("bottomless_lava_bucket", () -> new BottomlessBucketItem(() -> Fluids.LAVA, ModRarity.LIME));
    public static final RegistryObject<BottomlessBucketItem> BOTTOMLESS_HONEY_BUCKET = ITEMS.register("bottomless_honey_bucket", () -> new BottomlessBucketItem(ModFluids.HONEY.fluid(), ModRarity.LIME));
    public static final RegistryObject<BottomlessBucketItem> BOTTOMLESS_SHIMMER_BUCKET = ITEMS.register("bottomless_shimmer_bucket", () -> new BottomlessBucketItem(ModFluids.SHIMMER.fluid(), ModRarity.RED));
    public static final RegistryObject<Item> STAR = ITEMS.register("star", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_CAKE = ITEMS.register("soul_cake", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUGAR_PLUM = ITEMS.register("sugar_plum", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HEART = ITEMS.register("heart", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CANDY_APPLE = ITEMS.register("candy_apple", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane", () -> new Item(new Item.Properties()));
    public static final RegistryObject<ThrowingKnivesItem> THROWING_KNIVES = ITEMS.register("throwing_knives", ThrowingKnivesItem::new);
    public static final RegistryObject<Item> GLOW_STICK = ITEMS.register("glow_stick", BaseItem::new); // todo 荧光棒
    public static final RegistryObject<Item> STICKY_GLOW_STICK = ITEMS.register("sticky_glow_stick", BaseItem::new); // todo 粘性荧光棒
    public static final RegistryObject<Item> BOMB = ITEMS.register("bomb", Bomb::new); // todo 炸弹
    public static final RegistryObject<Item> BOUNCY_BOMB = ITEMS.register("bouncy_bomb", BombBouncy::new); // todo 弹力炸弹
    public static final RegistryObject<Item> STICKY_BOMB = ITEMS.register("sticky_bomb", BombSticky::new); // todo 粘性炸弹
    public static final RegistryObject<Item> SCARAB_BOMB = ITEMS.register("scarab_bomb", BombScarab::new); // todo 甲虫炸弹
    public static final RegistryObject<RecordItem> ALPHA = ITEMS.register("alpha", () -> new ExpertRecordItem(0, ModSoundEvents.ALPHA, 12060));
    public static final RegistryObject<PlayerAbilityItem> VITAL_CRYSTAL = ITEMS.register("vital_crystal", () -> new PlayerAbilityItem(PlayerAbility::isVitalCrystalUsed, PlayerAbility::setVitalCrystalUsed));
    public static final RegistryObject<ManaStorageItem> ARCANE_CRYSTAL = ITEMS.register("arcane_crystal", () -> new ManaStorageItem(ManaStorage::isArcaneCrystalUsed, ManaStorage::setArcaneCrystalUsed));
    public static final RegistryObject<PlayerAbilityItem> AEGIS_APPLE = ITEMS.register("aegis_apple", PlayerAbilityItem.AegisApple::new);
    public static final RegistryObject<PlayerAbilityItem> AMBROSIA = ITEMS.register("ambrosia", () -> new PlayerAbilityItem(PlayerAbility::isAmbrosiaUsed, PlayerAbility::setAmbrosiaUsed));
    public static final RegistryObject<PlayerAbilityItem> GUMMY_WORM = ITEMS.register("gummy_worm", () -> new PlayerAbilityItem(PlayerAbility::isGummyWormUsed, PlayerAbility::setGummyWormUsed));
    public static final RegistryObject<PlayerAbilityItem> GALAXY_PEARL = ITEMS.register("galaxy_pearl", PlayerAbilityItem.GalaxyPearl::new);
    public static final RegistryObject<WrenchItem> RED_WRENCH = ITEMS.register("red_wrench", () -> new WrenchItem(0xFF0000));
    public static final RegistryObject<WrenchItem> GREEN_WRENCH = ITEMS.register("green_wrench", () -> new WrenchItem(0x00FF00));
    public static final RegistryObject<WrenchItem> BLUE_WRENCH = ITEMS.register("blue_wrench", () -> new WrenchItem(0x0000FF));
    public static final RegistryObject<WrenchItem> YELLOW_WRENCH = ITEMS.register("yellow_wrench", () -> new WrenchItem(0xFFFF00));
    public static final RegistryObject<WireCutterItem> WIRE_CUTTER = ITEMS.register("wire_cutter", WireCutterItem::new);
    // 草药种子
    public static final RegistryObject<Item> WATERLEAF_SEED = ITEMS.register("waterleaf_seed", () -> new HerbSeedItem(ModBlocks.WATERLEAF.get()));
    public static final RegistryObject<Item> FLAMEFLOWERS_SEED = ITEMS.register("flameflowers_seed", () -> new HerbSeedItem(ModBlocks.FLAMEFLOWERS.get(), new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> MOONSHINE_GRASS_SEED = ITEMS.register("moonshine_grass_seed", () -> new HerbSeedItem(ModBlocks.MOONSHINE_GRASS.get()));
    public static final RegistryObject<Item> SHINE_ROOT_SEED = ITEMS.register("shine_root_seed", () -> new HerbSeedItem(ModBlocks.SHINE_ROOT.get()));
    public static final RegistryObject<Item> SHIVERINGTHORNS_SEED = ITEMS.register("shiveringthorns_seed", () -> new HerbSeedItem(ModBlocks.SHIVERINGTHORNS.get()));
    public static final RegistryObject<Item> SUNFLOWERS_SEED = ITEMS.register("sunflowers_seed", () -> new HerbSeedItem(ModBlocks.SUNFLOWERS.get()));
    public static final RegistryObject<Item> DEATHWEED_SEED = ITEMS.register("deathweed_seed", () -> new HerbSeedItem(ModBlocks.DEATHWEED.get()));
    // 草药
    public static final RegistryObject<Item> WATERLEAF = ITEMS.register("waterleaf", HerbItem::new);
    public static final RegistryObject<Item> FLAMEFLOWERS = ITEMS.register("flameflowers", () -> new HerbItem(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> MOONSHINE_GRASS = ITEMS.register("moonshine_grass", HerbItem::new);
    public static final RegistryObject<Item> SHINE_ROOT = ITEMS.register("shine_root", HerbItem::new);
    public static final RegistryObject<Item> SHIVERINGTHORNS = ITEMS.register("shiveringthorns", HerbItem::new);
    public static final RegistryObject<Item> SUNFLOWERS = ITEMS.register("sunflowers", HerbItem::new);
    public static final RegistryObject<Item> DEATHWEED = ITEMS.register("deathweed", HerbItem::new);
    // 蘑菇
    public static final RegistryObject<Item> ANOTHER_CRIMSON_MUSHROOM = ITEMS.register("another_crimson_mushroom", () -> new MushroomItem(ModBlocks.ANOTHER_CRIMSON_MUSHROOM.get(), 0.0F));
    public static final RegistryObject<Item> EBONY_MUSHROOM = ITEMS.register("ebony_mushroom", () -> new MushroomItem(ModBlocks.EBONY_MUSHROOM.get(), 0.0F));
    // TODO: 发光蘑菇可以放置，但此蘑菇非彼蘑菇
    public static final RegistryObject<Item> GLOWING_MUSHROOM = ITEMS.register("glowing_mushroom", () -> new MushroomItem(ModBlocks.GLOWING_MUSHROOM.get(), 0.0F));
    public static final RegistryObject<Item> LIFE_MUSHROOM = ITEMS.register("life_mushroom", () -> new MushroomItem(ModBlocks.LIFE_MUSHROOM.get(), 6.0F));
    public static final RegistryObject<Item> JUNGLE_SPORE = ITEMS.register("jungle_spore", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GOLDEN_KEY = ITEMS.register("golden_key", () -> new Item(new Item.Properties().rarity(ModRarity.WHITE)));
    public static final RegistryObject<Item> SHADOW_KEY = ITEMS.register("shadow_key", () -> new Item(new Item.Properties().stacksTo(1).rarity(ModRarity.WHITE)));
    public static final RegistryObject<Item> DEMON_HEART = ITEMS.register("demon_heart", DemonHeart::new);


    public static void register(IEventBus bus) {
        ModTiers.register();
        IconItem.Icons.init();
        Materials.init();
        Swords.init();
        Axes.init();
        Pickaxes.init();
        Hammers.init();
        HammerAxes.init();
        ManaWeapons.init();
        TerraPotions.init();
        SpawnEggs.init();
        Armors.init();
        CurioItems.initialize();
        QuestedFishes.init();
        Foods.init();
        FishingPoles.init();
        Hooks.init();
        Baits.init();
        AmmoItems.init();
        GunItems.init();
        FlailItems.init();
        ITEMS.register(bus);
    }
}
