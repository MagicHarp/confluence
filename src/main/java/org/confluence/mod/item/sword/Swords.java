package org.confluence.mod.item.sword;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Swords implements EnumRegister<SwordItem> {
    COPPER_SHORT_SWORD("copper_short_sword", () -> new ShortSwordItem(ModTiers.COPPER, 2, 3)),
    TIN_SHORT_SWORD("tin_short_sword", () -> new ShortSwordItem(ModTiers.TIN, 3, 3)),
    LEAD_SHORT_SWORD("lead_short_sword", () -> new ShortSwordItem(ModTiers.LEAD, 3, 3)),
    SILVER_SHORT_SWORD("silver_short_sword", () -> new ShortSwordItem(ModTiers.SILVER, 4, 3)),
    TUNGSTEN_SHORT_SWORD("tungsten_short_sword", () -> new ShortSwordItem(ModTiers.TUNGSTEN, 4, 3)),
    GOLDEN_SHORT_SWORD("golden_short_sword", () -> new ShortSwordItem(ModTiers.GOLD, 5, 3)),
    PLATINUM_SHORT_SWORD("platinum_short_sword", () -> new ShortSwordItem(ModTiers.PLATINUM, 5, 3)),

    CACTUS_SWORD("cactus_sword", () -> new BoardSwordItem(ModTiers.CACTUS, 4, 1.6F)),
    COPPER_BOARD_SWORD("copper_board_sword", () -> new BoardSwordItem(ModTiers.COPPER, 4, 1.6F)),
    TIN_BOARD_SWORD("tin_board_sword", () -> new BoardSwordItem(ModTiers.TIN, 4, 1.6F)),
    LEAD_BOARD_SWORD("lead_board_sword", () -> new BoardSwordItem(ModTiers.LEAD, 5, 1.6F)),
    SILVER_BOARD_SWORD("silver_board_sword", () -> new BoardSwordItem(ModTiers.SILVER, 5, 1.6F)),
    TUNGSTEN_BOARD_SWORD("tungsten_board_sword", () -> new BoardSwordItem(ModTiers.TUNGSTEN, 6, 1.6F)),
    GOLDEN_BOARD_SWORD("golden_board_sword", () -> new BoardSwordItem(ModTiers.GOLD, 7, 1.6F)),
    PLATINUM_BOARD_SWORD("platinum_board_sword", () -> new BoardSwordItem(ModTiers.PLATINUM, 7, 1.6F)),

    LIGHTS_BANE("lights_bane", () -> new BloodButchereSword(ModTiers.TITANIUM, 5, -0.1F, new Item.Properties().rarity(ModRarity.BLUE))), //TODO 魔光剑
    BLOOD_BUTCHERER("blood_butchere", () -> new BloodButchereSword(ModTiers.TITANIUM, 7, -2.7F, new Item.Properties().rarity(ModRarity.BLUE))), //TODO 血腥屠刀 （给予被伤害的单位流血效果）蓄力才触发

    RED_LIGHT_SABER("red_light_saber", LightSaber.Red::new),
    ORANGE_LIGHT_SABER("orange_light_saber", LightSaber.Orange::new),
    YELLOW_LIGHT_SABER("yellow_light_saber", LightSaber.Yellow::new),
    GREEN_LIGHT_SABER("green_light_saber", LightSaber.Green::new),
    BLUE_LIGHT_SABER("blue_light_saber", LightSaber.Blue::new),
    PURPLE_LIGHT_SABER("purple_light_saber", LightSaber.Purple::new),
    WHITE_LIGHT_SABER("white_light_saber", LightSaber.White::new),

    ICE_BLADE("ice_blade", () -> new IceBladeSwordItem(ModTiers.TITANIUM, 5, -0.1F, new Item.Properties().rarity(ModRarity.BLUE))),
    STARFURY("starfury", () -> new IceBladeSwordItem(ModTiers.TITANIUM, 6, -0.1F, new Item.Properties().rarity(ModRarity.GREEN))),  //TODO 星怒
    ENCHANTED_SWORD("enchanted_sword", () -> new EnchantedSwordItem(ModTiers.TITANIUM, 7, -0.2F, new Item.Properties().rarity(ModRarity.ORANGE))),


    TERRAGRIM("terragrim", TerragrimItem::new),
    // 其他剑
    CANDY_CANE_SWORD("candy_cane_sword", () -> new BoardSwordItem(ModTiers.CANDY, 5, 1.6F)),
    BREATHING_REED("breathing_reed", BreathingReed::new),
    UMBRELLA("umbrella", UmbrellaItem::new),
    TRAGIC_UMBRELLA("tragic_umbrella", UmbrellaItem::new),
    FALCON_BLADE("falcon_blade", () -> new ReversalBoardSwordItem(ModTiers.TITANIUM, 3, -1.45F, new Item.Properties().rarity(ModRarity.BLUE))),
    ZOMBIE_ARM("zombie_arm", () -> new RegularBroadSwordItem(ModTiers.TITANIUM, 3, -1.4F, new Item.Properties().rarity(ModRarity.WHITE))),
    MANDIBLE_BLADE("mandible_blade", () -> new RegularBroadSwordItem(ModTiers.TITANIUM, 4, -1.4F, new Item.Properties().rarity(ModRarity.GREEN))),
    BONE_SWORD("bone_sword", () -> new ReversalBoardSwordItem(ModTiers.TITANIUM, 5, -0.2F, new Item.Properties().rarity(ModRarity.ORANGE))),
    BAT_BAT("bat_bat", BatBatItem::new),
    PURPLE_CLUBBERFISH("purple_clubberfish", () -> new BigRegularBroadSwordItem(ModTiers.TITANIUM, 10, -3.5F, new Item.Properties().rarity(ModRarity.BLUE))),
    STYLISH_SCISSORS("stylish_scissors", () -> new RegularBroadSwordItem(ModTiers.TITANIUM, 3, -1.2F, new Item.Properties().rarity(ModRarity.GREEN))),
    EXOTIC_SCIMITAR("exotic_scimitar", () -> new ReversalBoardSwordItem(ModTiers.TITANIUM, 5, -0.3F, new Item.Properties().rarity(ModRarity.GREEN))),
    KATANA("katana",() -> new BigRegularBroadSwordItem(ModTiers.TITANIUM, 4, -0.3F, new Item.Properties().rarity(ModRarity.BLUE))),
    CROWBAR("crowbar", CrowbarItem::new);

    private final RegistryObject<SwordItem> value;

    Swords(String id, Supplier<SwordItem> sword) {
        this.value = ModItems.ITEMS.register(id, sword);
    }

    public static void init() {
    }

    @Override
    public RegistryObject<SwordItem> getValue() {
        return value;
    }
}
