package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.util.EnumRegister;

public enum QuestedFishes implements EnumRegister<Item> {
    AMANITA_FUNGIFIN("amanita_fungifin"), // 发光蘑菇鱼
    ANGELFISH("angelfish"), // 天使鱼
    BATFISH("batfish"), // 蝙蝠鱼
    BLOODY_MANOWAR("bloody_manowar"), // 血腥战神
    BONEFISH("bonefish"), // 骨鱼
    BUMBLEBEE_TUNA("bumblebee_tuna"), // 大黄蜂金枪鱼
    BUNNYFISH("bunnyfish"), // 兔兔鱼
    CAP_TUNABEARD("cap_tunabeard"), // 金枪鱼船长
    INFECTED_SCABBARDFISH("infected_scabbardfish"), // 染病鞘鱼
    JEWELFISH("jewelfish"), // 宝石鱼
    MIRAGE_FISH("mirage_fish"), // 混沌鱼
    MUDFISH("mudfish"), // 泥鱼
    MUTANT_FLINXFIN("mutant_flinxfin"), // 突变弗林鱼
    CLOUDFISH("cloudfish"), // 云朵鱼
    ANOTHER_CLOWNFISH("another_clownfish"), // 异域小丑鱼
    DEMONIC_HELLFISH("demonic_hellfish"), // 地狱恶魔鱼
    DERPFISH("derpfish"), // 跳跳鱼
    ICHORFISH("ichorfish"), // 灵液鱼
    SLIMEFISH("slimefish"), // 史莱姆鱼
    ZOMBIE_FISH("zombie_fish"), // 僵尸鱼
    TROPICAL_BARRACUDA("tropical_barracuda"), // 热带梭鱼

    UNICORN_FISH("unicorn_fish"), // 独角兽鱼
    WYVERNTAIL("wyverntail"), // 飞龙尾
    DIRTFISH("dirtfish"), // 土鱼
    CURSEDFISH("cursedfish"), // 诅咒鱼

    DYNAMITE_FISH("dynamite_fish"), // 雷管鱼
    FALLEN_STARFISH("fallen_starfish"), // 坠落星鱼
    EATER_OF_PLANKTON("eater_of_plankton"), // 浮游噬鱼

    THE_FISH_OF_CTHULHU("the_fish_of_cthulhu"), // 克苏鲁之鱼

    FISHRON("fishron"), // 猪龙鱼
    GUIDE_VOODOO_FISH("guide_voodoo_fish"), // 向导巫毒鱼
    HARPYFISH("harpyfish"), // 鸟妖鱼
    FISHOTRON("fishotron"), // 骷髅王鱼
    SCORPIO_FISH("scorpio_fish"), // 毒蝎鱼
    HUNGERFISH("hungerfish"), // 饥饿鱼
    CATFISH("catfish"), // 猫猫鱼

    PENGFISH("pengfish"), // 企鹅鱼
    PIXIEFISH("pixiefish"), // 妖精鱼
    SCARAB_FISH("scarab_fish"), // 甲虫鱼


    SPIDERFISH("spiderfish"), // 蜘蛛鱼

    TUNDRA_TROUT("tundra_trout"), // 苔原鳟鱼

    ;


    private final RegistryObject<Item> value;

    QuestedFishes(String id) {
        this.value = ModItems.ITEMS.register(id, () -> new Item(new Item.Properties().rarity(ModRarity.QUEST)));
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering quested fishes");
    }
}
