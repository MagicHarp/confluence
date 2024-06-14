package org.confluence.mod.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
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
import org.confluence.mod.item.food.Foods;
import org.confluence.mod.item.gun.AmmoItems;
import org.confluence.mod.item.gun.GunItems;
import org.confluence.mod.item.hammer.HammerAxes;
import org.confluence.mod.item.hammer.Hammers;
import org.confluence.mod.item.hook.Hooks;
import org.confluence.mod.item.loot.ChristmasGiftsItem;
import org.confluence.mod.item.loot.ClamItem;
import org.confluence.mod.item.mana.ManaWeapons;
import org.confluence.mod.item.pickaxe.Pickaxes;
import org.confluence.mod.item.potion.TerraPotions;
import org.confluence.mod.item.sword.Swords;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModSounds;


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
    public static final RegistryObject<Item> COPPER_COIN = ITEMS.register("copper_coin", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<Item> SILVER_COIN = ITEMS.register("silver_coin", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<Item> GOLDEN_COIN = ITEMS.register("golden_coin", () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> PLATINUM_COIN = ITEMS.register("platinum_coin", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));
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
    public static final RegistryObject<ThrowingKnivesItem> THROWING_KNIVES = ITEMS.register("throwing_knives", ThrowingKnivesItem::new); // todo 飞刀
    public static final RegistryObject<Item> GLOW_STICK = ITEMS.register("glow_stick", BaseItem::new); // todo 荧光棒
    public static final RegistryObject<Item> STICKY_GLOW_STICK = ITEMS.register("sticky_glow_stick", BaseItem::new); // todo 粘性荧光棒
    public static final RegistryObject<Item> BOMB = ITEMS.register("bomb", BaseItem::new); // todo 炸弹
    public static final RegistryObject<Item> SCARAB_BOMB = ITEMS.register("scarab_bomb", BaseItem::new); // todo 甲虫炸弹
    public static final RegistryObject<RecordItem> ALPHA = ITEMS.register("alpha", () -> new ExpertRecordItem(0, ModSounds.ALPHA, 12060));
    public static final RegistryObject<PlayerAbilityItem> VITAL_CRYSTAL = ITEMS.register("vital_crystal", () -> new PlayerAbilityItem(PlayerAbility::isVitalCrystalUsed, PlayerAbility::setVitalCrystalUsed));
    public static final RegistryObject<ManaStorageItem> ARCANE_CRYSTAL = ITEMS.register("arcane_crystal", () -> new ManaStorageItem(ManaStorage::isArcaneCrystalUsed, ManaStorage::setArcaneCrystalUsed));
    public static final RegistryObject<PlayerAbilityItem> AEGIS_APPLE = ITEMS.register("aegis_apple", PlayerAbilityItem.AegisApple::new);
    public static final RegistryObject<PlayerAbilityItem> AMBROSIA = ITEMS.register("ambrosia", () -> new PlayerAbilityItem(PlayerAbility::isAmbrosiaUsed, PlayerAbility::setAmbrosiaUsed));
    public static final RegistryObject<PlayerAbilityItem> GUMMY_WORM = ITEMS.register("gummy_worm", () -> new PlayerAbilityItem(PlayerAbility::isGummyWormUsed, PlayerAbility::setGummyWormUsed));
    public static final RegistryObject<PlayerAbilityItem> GALAXY_PEARL = ITEMS.register("galaxy_pearl", PlayerAbilityItem.GalaxyPearl::new);

    public static void register(IEventBus bus) {
        ModTiers.register();
        IconItem.Icons.init();
        Materials.init();
        Swords.init();
        Axes.init();
        Pickaxes.init();
        Hammers.init();
        HammerAxes.init();
        Gels.init();
        ManaWeapons.init();
        TerraPotions.init();
        SpawnEggs.init();
        Armors.init();
        CurioItems.init();
        QuestedFishes.init();
        Foods.init();
        FishingPoles.init();
        Hooks.init();
        Baits.init();
        AmmoItems.init();
        GunItems.init();
        ITEMS.register(bus);
    }
}
