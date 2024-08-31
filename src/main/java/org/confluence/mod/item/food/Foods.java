package org.confluence.mod.item.food;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Foods implements EnumRegister<Item> {
    //常规食物
    COOKED_SHRIMP("cooked_shrimp", () -> new BaseFoodItem(FoodType.MEDIUM)),//熟虾
    ESCARGOT("escargot", () -> new BaseFoodItem(FoodType.MEDIUM)),//法式蜗牛
    FROGGLE_BUNWICH("froggle_bunwich", () -> new BaseFoodItem(FoodType.MEDIUM)),//面包夹田鸡
    GOLDEN_DELIGHT("golden_delight", () -> new BaseFoodItem(FoodType.GOLDEN_CARP)),//金美味
    GRILLED_SQUIRREL("grilled_squirrel", () -> new BaseFoodItem(FoodType.MEAT)),//松鼠尾
    LOBSTER_TAIL("lobster_tail", () -> new BaseFoodItem(FoodType.SENIOR)),//龙虾尾
    MONSTER_LASAGNA("monster_lasagna", () -> new BaseFoodItem(FoodType.SENIOR)),//怪物千层面
    SASHIMI("sashimi", () -> new BaseFoodItem(FoodType.MEDIUM)),//生鱼片
    ROASTED_BIRD("roasted_bird", () -> new BaseFoodItem(FoodType.MEAT)),//烤鸟腿
    ROASTED_DUCK("roasted_duck", () -> new BaseFoodItem(FoodType.MEAT)),//鸭肉
    SAUTEED_FROG_LEGS("sauteed_frog_legs", () -> new BaseFoodItem(FoodType.MEAT)),//爆炒青蛙腿
    SEAFOOD_DINNER("seafood_dinner", () -> new BaseFoodItem(FoodType.SENIOR)),//海鲜大餐
    BACON("bacon", () -> new BaseFoodItem(FoodType.SENIOR)),//培根
    BANANA_SPLIT("banana_split", () -> new BaseFoodItem(FoodType.SENIOR)),//香蕉船
    BBQ_RIBS("bbq_ribs", () -> new BaseFoodItem(FoodType.SENIOR)),//炭烧排骨
    BURGER("burger", () -> new BaseFoodItem(FoodType.SENIOR)),//汉堡
    CHICKEN_NUGGET("chicken_nugget", () -> new BaseFoodItem(FoodType.SENIOR)),//鸡块
    CHOCOLATE_CHIP_COOKIE("chocolate_chip_cookie", () -> new BaseFoodItem(FoodType.SENIOR)),//巧克力大曲奇
    FRIED_EGG("fried_egg", () -> new BaseFoodItem(FoodType.MEAT)),//煎蛋
    FRIES("fries", () -> new BaseFoodItem(FoodType.SENIOR)),//薯条
    HOTDOG("hotdog", () -> new BaseFoodItem(FoodType.SENIOR)),//热狗
    PIZZA("pizza", () -> new BaseFoodItem(FoodType.SENIOR)),//披萨
    POTATO_CHIPS("potato_chips", () -> new BaseFoodItem(FoodType.SENIOR)),//薯片
    SHRIMP_PO_BOY("shrimp_po_boy", () -> new BaseFoodItem(FoodType.SENIOR)),//鲨宝男孩
    SHUCKED_OYSTER("shucked_oyster", () -> new BaseFoodItem(FoodType.LOW)),//去壳牡蛎
    SPAGHETTI("spaghetti", () -> new BaseFoodItem(FoodType.SENIOR)),//意大利面
    SURPER_STEAK("surper_steak", () -> new BaseFoodItem(FoodType.SENIOR)),//超大肉排
    CHRISTMAS_PUDDING("christmas_pudding", () -> new BaseFoodItem(FoodType.SENIOR)),//圣诞布丁
    GINGERBREAD_COOKIE("gingerbread_cookie", () -> new BaseFoodItem(FoodType.SENIOR)),//姜饼人
    SUGAR_COOKIE("sugar_cookie", () -> new BaseFoodItem(FoodType.SENIOR)),//糖曲奇
    MARSHMALLOW("marshmallow", () -> new BaseFoodItem(FoodType.LOW)),//棉花糖
    COOKED_MARSHMALLOW("cooked_marshmallow", () -> new BaseFoodItem(FoodType.MEDIUM)),//烤棉花糖
    PAD_THAI("pad_thai", () -> new BaseFoodItem(FoodType.SENIOR)),//泰式炒河粉
    FISH_AND_MUSHROOM_SOUP("fish_and_mushroom_soup", () -> new BottleFoodItem(FoodType.MEDIUM)),//鱼菇汤
    FRUIT_SALAD("fruit_salad", () -> new BottleFoodItem(FoodType.HIGH)),//水果沙拉
    GRUB_SOUP("grub_soup", () -> new BottleFoodItem(FoodType.HIGH)),//蛆虫汤
    NACHOS("nachos", () -> new BottleFoodItem(FoodType.HIGH)),//一碗玉米粒
    PHO("pho", () -> new BottleFoodItem(FoodType.MEDIUM)),//河粉
    CRUDE_CLOUD_BREAD("crude_cloud_bread", () -> new BaseFoodItem(FoodType.LOW)),// 粗制云朵面包
    CLOUD_BREAD("cloud_bread", () -> new BaseFoodItem(FoodType.CLOUD_BREAD)),// 云朵面包
    // 水果
    APRICOT("apricot", () -> new BaseFoodItem(FoodType.LOW)),
    BANANA("banana", () -> new BaseFoodItem(FoodType.LOW)),
    CHERRY("cherry", () -> new BaseFoodItem(FoodType.LOW)),
    COCONUT("coconut", () -> new BaseFoodItem(FoodType.LOW)),
    DRAGON_FRUIT("dragon_fruit", () -> new BaseFoodItem(FoodType.LOW)),
    GRAPE_FRUIT("grape_fruit", () -> new BaseFoodItem(FoodType.LOW)),
    LEMON("lemon", () -> new BaseFoodItem(FoodType.LOW)),
    MANGO("mango", () -> new BaseFoodItem(FoodType.LOW)),
    PEACH("peach", () -> new BaseFoodItem(FoodType.LOW)),
    PINEAPPLE("pineapple", () -> new BaseFoodItem(FoodType.LOW)),
    PLUM("plum", () -> new BaseFoodItem(FoodType.LOW)),
    GRAPE("grape", () -> new BaseFoodItem(FoodType.SENIOR)),//葡萄
    SPICY_PEPPER("spicy_pepper", () -> new BaseFoodItem(FoodType.LOW)),
    STAR_FRUIT("star_fruit", () -> new BaseFoodItem(FoodType.LOW)),
    POMEGRANATE("pomegranate", () -> new BaseFoodItem(FoodType.LOW)),
    RAMBUTAN("rambutan", () -> new BaseFoodItem(FoodType.LOW)),
    BLOOD_ORANGE("blood_orange", () -> new BaseFoodItem(FoodType.LOW)),
    ELDERBERRY("elderberry", () -> new BaseFoodItem(FoodType.LOW)),
    BLACKCURRANT("blackcurrant", () -> new BaseFoodItem(FoodType.LOW)),
    //饮料
    FRUIT_JUICE("fruit_juice", () -> new BottleFoodItem(FoodType.MEDIUM)),//混合果汁
    APPLE_JUICE("apple_juice", () -> new BottleFoodItem(FoodType.MEDIUM)),

    FROZEN_BANANA_DAIQUIRI("frozen_banana_daiquiri", () -> new BottleFoodItem(FoodType.MEDIUM)),//香蕉圣代
    GRAPE_JUICE("grape_juice", () -> new BottleFoodItem(FoodType.GOLDEN_CARP)),//葡萄汁
    LEMONADE("lemonade", () -> new BottleFoodItem(FoodType.MEDIUM)),//柠檬水
    PEACH_SANGRIA("peach_sangria", () -> new BottleFoodItem(FoodType.SENIOR)),//桃子桑格利亚汽酒
    PIÑA_COLADA("pina_colada", () -> new BottleFoodItem(FoodType.MEDIUM)),//皮尼亚·科拉达
    PRISMATIC_PUNCH("prismatic_punch", () -> new BottleFoodItem(FoodType.SENIOR)),//味蕾冲击者
    TROPICAL_SMOOTHIE("tropical_smoothie", () -> new BottleFoodItem(FoodType.SENIOR)),//热带冰沙
    SMOOTHIE_OF_DARKNESS("smoothie_of_darkness", () -> new BottleFoodItem(FoodType.MEDIUM)),//黑暗奶昔
    BLOODY_MOSCATO("bloody_moscato", () -> new BottleFoodItem(FoodType.MEDIUM)),
    CREAM_SODA("cream_soda", () -> new BottleFoodItem(FoodType.SENIOR)),//奶油苏打
    ICE_CREAM("ice_cream", () -> new BottleFoodItem(FoodType.SENIOR)),//冰淇淋
    MILKSHAKE("milkshake", () -> new BottleFoodItem(FoodType.SENIOR)),//奶昔
    //不掉落瓶子的饮料
    JOJA_COLA("joja_cola", () -> new BottleFoodItem(FoodType.LOW)),//乔家可乐
    CARTON_OF_MILK("carton_of_milk", () -> new BaseFoodItem(FoodType.SENIOR)),//卡通牛奶
    TEACUP("teacup", () -> new BottleFoodItem(FoodType.LOW)),//一小杯茶
    COFFEE("coffee", () -> new BottleFoodItem(FoodType.SENIOR)),//咖啡

    SAKE("sake", () -> new BottleFoodItem(FoodType.SENIOR)),//清酒

    //鱼
    SEA_BASS("sea_bass", () -> new BaseFoodItem(FoodType.FISH)),
    ATLANTIC_COD("atlantic_cod", () -> new BaseFoodItem(FoodType.FISH)),
    FROSTY_MINNOW("frosty_minnow", () -> new BaseFoodItem(FoodType.FISH)),
    PISCES_FIN_COD("pisces_fin_cod", () -> new BaseFoodItem(FoodType.FISH)),
    PARTIAL_MOUTH_FISH("partial_mouth_fish", () -> new BaseFoodItem(FoodType.FISH)),
    ROCK_LOBSTER("rock_lobster", () -> new BaseFoodItem(FoodType.FISH)),
    SHRIMP("shrimp", () -> new BaseFoodItem(FoodType.FISH)),
    TR_SALMON("tr_salmon", () -> new BaseFoodItem(FoodType.FISH)),
    TUNA("tuna", () -> new BaseFoodItem(FoodType.FISH)),
    RED_SNAPPER("red_snapper", () -> new BaseFoodItem(FoodType.FISH)),
    TROUT("trout", () -> new BaseFoodItem(FoodType.FISH)),
    ARMORED_CAVE_FISH("armored_cave_fish", () -> new BaseFoodItem(FoodType.FISH)),
    MIRROR_FISH("mirror_fish", () -> new BaseFoodItem(FoodType.FISH)),
    STINKY_FISH("stinky_fish", () -> new BaseFoodItem(FoodType.FISH)),
    NEON_GREASE_CARP("neon_grease_carp", () -> new BaseFoodItem(FoodType.FISH)),
    DAMSEL_FISH("damsel_fish", () -> new BaseFoodItem(FoodType.FISH)),
    EBONY_KOI("ebony_koi", () -> new BaseFoodItem(FoodType.FISH)),
    SCARLET_TIGER_FISH("scarlet_tiger_fish", () -> new BaseFoodItem(FoodType.FISH)),
    BLOODY_PIRANHAS("bloody_piranhas", () -> new BaseFoodItem(FoodType.FISH)),
    PRINCESS_FISH("princess_fish", () -> new BaseFoodItem(FoodType.FISH)),
    COLORFUL_MINERAL_FISH("colorful_mineral_fish", () -> new BaseFoodItem(FoodType.FISH)),
    CHAOS_FISH("chaos_fish", () -> new BaseFoodItem(FoodType.FISH)),
    MOTTLED_OILFISH("mottled_oilfish", () -> new BaseFoodItem(FoodType.FISH)),
    GOLDEN_CARP("golden_carp", () -> new BaseFoodItem(FoodType.GOLDEN_CARP)),
    OBSIDIAN_FISH("obsidian_fish", () -> new FireproofFoodItem(FoodType.FISH)),
    FLASHFIN_KOI("flashfin_koi", () -> new FireproofFoodItem(FoodType.FISH)),

    //节日特有
    ZONGZI("zongzi", () -> new FireproofFoodItem(FoodType.LOW)),

    ;
    private final RegistryObject<Item> value;

    Foods(String id, Supplier<Item> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {}

}
