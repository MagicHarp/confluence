package org.confluence.mod.common.init.item;

import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.item.food.BaseFoodItem;
import org.confluence.mod.common.item.food.FoodType;

import static net.minecraft.world.item.Items.BOWL;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public class FoodItems {
    public static final DeferredRegister.Items FOOD = DeferredRegister.createItems(Confluence.MODID);
    //常规食物
    public static final DeferredItem<BaseFoodItem> COOKED_SHRIMP = FOOD.register("cooked_shrimp", () -> new BaseFoodItem(FoodType.MEDIUM));
    public static final DeferredItem<BaseFoodItem> ESCARGOT = FOOD.register("escargot", () -> new BaseFoodItem(FoodType.MEDIUM)); //法式蜗牛
    public static final DeferredItem<BaseFoodItem> FROGGLE_BUNWICH = FOOD.register("froggle_bunwich", () -> new BaseFoodItem(FoodType.MEDIUM)); //面包夹田鸡
    public static final DeferredItem<BaseFoodItem> GOLDEN_DELIGHT = FOOD.register("golden_delight", () -> new BaseFoodItem(FoodType.GOLDEN_CARP)); //金美味
    public static final DeferredItem<BaseFoodItem> GRILLED_SQUIRREL = FOOD.register("grilled_squirrel", () -> new BaseFoodItem(FoodType.MEAT)); //松鼠尾
    public static final DeferredItem<BaseFoodItem> LOBSTER_TAIL = FOOD.register("lobster_tail", () -> new BaseFoodItem(FoodType.SENIOR)); //龙虾尾
    public static final DeferredItem<BaseFoodItem> MONSTER_LASAGNA = FOOD.register("monster_lasagna", () -> new BaseFoodItem(FoodType.SENIOR)); //怪物千层面
    public static final DeferredItem<BaseFoodItem> SASHIMI = FOOD.register("sashimi", () -> new BaseFoodItem(FoodType.MEDIUM)); //生鱼片
    public static final DeferredItem<BaseFoodItem> ROASTED_BIRD = FOOD.register("roasted_bird", () -> new BaseFoodItem(FoodType.MEAT)); //烤鸟腿
    public static final DeferredItem<BaseFoodItem> ROASTED_DUCK= FOOD.register("roasted_duck", () -> new BaseFoodItem(FoodType.MEAT)); //鸭肉
    public static final DeferredItem<BaseFoodItem> SAUTEED_FROG_LEGS= FOOD.register("sauteed_frog_legs", () -> new BaseFoodItem(FoodType.MEAT)); //爆炒青蛙腿
    public static final DeferredItem<BaseFoodItem> SEAFOOD_DINNER = FOOD.register("seafood_dinner", () -> new BaseFoodItem(FoodType.SENIOR)); //海鲜大餐
    public static final DeferredItem<BaseFoodItem> BACON = FOOD.register("bacon", () -> new BaseFoodItem(FoodType.SENIOR)); //培根
    public static final DeferredItem<BaseFoodItem> BANANA_SPLIT = FOOD.register("banana_split", () -> new BaseFoodItem(FoodType.SENIOR)); //香蕉船
    public static final DeferredItem<BaseFoodItem> BBQ_RIBS = FOOD.register("bbq_ribs", () -> new BaseFoodItem(FoodType.SENIOR)); //炭烧排骨
    public static final DeferredItem<BaseFoodItem> BURGER = FOOD.register("burger", () -> new BaseFoodItem(FoodType.SENIOR)); //汉堡
    public static final DeferredItem<BaseFoodItem> CHICKEN_NUGGET = FOOD.register("chicken_nugget", () -> new BaseFoodItem(FoodType.SENIOR)); //鸡块
    public static final DeferredItem<BaseFoodItem> CHOCOLATE_CHIP_COOKIE = FOOD.register("chocolate_chip_cookie", () -> new BaseFoodItem(FoodType.SENIOR)); //巧克力大曲奇
    public static final DeferredItem<BaseFoodItem> FRIED_EGG = FOOD.register("fried_egg", () -> new BaseFoodItem(FoodType.MEAT)); //煎蛋
    public static final DeferredItem<BaseFoodItem> FRIES = FOOD.register("fries", () -> new BaseFoodItem(FoodType.SENIOR)); //薯条
    public static final DeferredItem<BaseFoodItem> HOTDOG = FOOD.register("hotdog", () -> new BaseFoodItem(FoodType.SENIOR)); //热狗
    public static final DeferredItem<BaseFoodItem> PIZZA = FOOD.register("pizza", () -> new BaseFoodItem(FoodType.SENIOR)); //披萨
    public static final DeferredItem<BaseFoodItem> SHRIMP_PO_BOY = FOOD.register("shrimp_po_boy", () -> new BaseFoodItem(FoodType.SENIOR)); //鲨宝男孩
    public static final DeferredItem<BaseFoodItem> SHUCKED_OYSTER = FOOD.register("shucked_oyster", () -> new BaseFoodItem(FoodType.LOW)); //去壳牡蛎
    public static final DeferredItem<BaseFoodItem> SPAGHETTI = FOOD.register("spaghetti", () -> new BaseFoodItem(FoodType.SENIOR)); //意大利面
    public static final DeferredItem<BaseFoodItem> SURPER_STEAK = FOOD.register("surper_steak", () -> new BaseFoodItem(FoodType.SENIOR)); //超大肉排
    public static final DeferredItem<BaseFoodItem> CHRISTMAS_PUDDING = FOOD.register("christmas_pudding", () -> new BaseFoodItem(FoodType.SENIOR)); //圣诞布丁
    public static final DeferredItem<BaseFoodItem> GINGERBREAD_COOKIE = FOOD.register("gingerbread_cookie", () -> new BaseFoodItem(FoodType.SENIOR)); //姜饼人
    public static final DeferredItem<BaseFoodItem> SUGAR_COOKIE = FOOD.register("sugar_cookie", () -> new BaseFoodItem(FoodType.SENIOR)); //糖曲奇
    public static final DeferredItem<BaseFoodItem> MARSHMALLOW = FOOD.register("marshmallow", () -> new BaseFoodItem(FoodType.LOW)); //棉花糖
    public static final DeferredItem<BaseFoodItem> COOKED_MARSHMALLOW = FOOD.register("cooked_marshmallow", () -> new BaseFoodItem(FoodType.MEDIUM)); //烤棉花糖
    public static final DeferredItem<BaseFoodItem> PAD_THAI = FOOD.register("pad_thai", () -> new BaseFoodItem(FoodType.SENIOR)); //泰式炒河粉
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> FISH_AND_MUSHROOM_SOUP = FOOD.register("fish_and_mushroom_soup", () -> new BaseFoodItem.ContainerFoodItem(FoodType.MEDIUM, BOWL)); //鱼菇汤
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> FRUIT_SALAD = FOOD.register("fruit_salad", () -> new BaseFoodItem.ContainerFoodItem(FoodType.HIGH, GLASS_BOTTLE)); //水果沙拉
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> GRUB_SOUP = FOOD.register("grub_soup", () -> new BaseFoodItem.ContainerFoodItem(FoodType.HIGH, BOWL)); //蛆虫汤
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> NACHOS = FOOD.register("nachos", () -> new BaseFoodItem.ContainerFoodItem(FoodType.HIGH, BOWL)); //一碗玉米粒
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> PHO = FOOD.register("pho", () -> new BaseFoodItem.ContainerFoodItem(FoodType.MEDIUM, BOWL)); //河粉
    public static final DeferredItem<BaseFoodItem> CLOUD_DOUGH = FOOD.register("cloud_dough", () -> new BaseFoodItem(FoodType.LOW)); // 粗制云朵面包
    public static final DeferredItem<BaseFoodItem> CLOUD_BREAD = FOOD.register("cloud_bread", () -> new BaseFoodItem(FoodType.CLOUD_BREAD)); // 云朵面包
    public static final DeferredItem<BaseFoodItem> FLUTTERING_LAMB_CHOPS = FOOD.register("fluttering_lamb_chops", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> COOKED_FLUTTERING_LAMB_CHOPS = FOOD.register("cooked_fluttering_lamb_chops", () -> new BaseFoodItem(FoodType.SENIOR));
    // 水果
    public static final DeferredItem<BaseFoodItem> APRICOT = FOOD.register("apricot", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> BANANA = FOOD.register("banana", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> CHERRY = FOOD.register("cherry", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> COCONUT = FOOD.register("coconut", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> DRAGON_FRUIT = FOOD.register("dragon_fruit", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> GRAPE_FRUIT = FOOD.register("grape_fruit", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> LEMON = FOOD.register("lemon", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> MANGO = FOOD.register("mango", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> PEACH = FOOD.register("peach", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> PINEAPPLE = FOOD.register("pineapple", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> PLUM = FOOD.register("plum", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> GRAPE = FOOD.register("grape", () -> new BaseFoodItem(FoodType.SENIOR));//葡萄
    public static final DeferredItem<BaseFoodItem> SPICY_PEPPER = FOOD.register("spicy_pepper", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> STAR_FRUIT = FOOD.register("star_fruit", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> POMEGRANATE = FOOD.register("pomegranate", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> RAMBUTAN = FOOD.register("rambutan", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> BLOOD_ORANGE = FOOD.register("blood_orange", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> ELDERBERRY = FOOD.register("elderberry", () -> new BaseFoodItem(FoodType.LOW));
    public static final DeferredItem<BaseFoodItem> BLACKCURRANT = FOOD.register("blackcurrant", () -> new BaseFoodItem(FoodType.LOW));
    //饮料
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> FRUIT_JUICE = FOOD.register("fruit_juice", () -> new BaseFoodItem.ContainerFoodItem(FoodType.MEDIUM, GLASS_BOTTLE)); //混合果汁
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> APPLE_JUICE = FOOD.register("apple_juice", () -> new BaseFoodItem.ContainerFoodItem(FoodType.MEDIUM, GLASS_BOTTLE));
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> FROZEN_BANANA_DAIQUIRI = FOOD.register("frozen_banana_daiquiri", () -> new BaseFoodItem.ContainerFoodItem(FoodType.MEDIUM, GLASS_BOTTLE)); //香蕉圣代
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> GRAPE_JUICE = FOOD.register("grape_juice", () -> new BaseFoodItem.ContainerFoodItem(FoodType.GOLDEN_CARP, GLASS_BOTTLE)); //葡萄汁
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> LEMONADE = FOOD.register("lemonade", () -> new BaseFoodItem.ContainerFoodItem(FoodType.MEDIUM, GLASS_BOTTLE)); //柠檬水
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> PEACH_SANGRIA = FOOD.register("peach_sangria", () -> new BaseFoodItem.ContainerFoodItem(FoodType.SENIOR, GLASS_BOTTLE)); //桃子桑格利亚汽酒
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> PIÑA_COLADA = FOOD.register("pina_colada", () -> new BaseFoodItem.ContainerFoodItem(FoodType.MEDIUM, GLASS_BOTTLE)); //皮尼亚·科拉达
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> PRISMATIC_PUNCH = FOOD.register("prismatic_punch", () -> new BaseFoodItem.ContainerFoodItem(FoodType.SENIOR, GLASS_BOTTLE)); //味蕾冲击者
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> TROPICAL_SMOOTHIE = FOOD.register("tropical_smoothie", () -> new BaseFoodItem.ContainerFoodItem(FoodType.SENIOR, GLASS_BOTTLE)); //热带冰沙
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> SMOOTHIE_OF_DARKNESS = FOOD.register("smoothie_of_darkness", () -> new BaseFoodItem.ContainerFoodItem(FoodType.MEDIUM, GLASS_BOTTLE)); //黑暗奶昔
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> BLOODY_MOSCATO = FOOD.register("bloody_moscato", () -> new BaseFoodItem.ContainerFoodItem(FoodType.MEDIUM, GLASS_BOTTLE));
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> CREAM_SODA = FOOD.register("cream_soda", () -> new BaseFoodItem.ContainerFoodItem(FoodType.SENIOR, GLASS_BOTTLE)); //奶油苏打
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> ICE_CREAM = FOOD.register("ice_cream", () -> new BaseFoodItem.ContainerFoodItem(FoodType.SENIOR, GLASS_BOTTLE)); //冰淇淋
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> MILKSHAKE = FOOD.register("milkshake", () -> new BaseFoodItem.ContainerFoodItem(FoodType.SENIOR, GLASS_BOTTLE)); //奶昔
    //不掉落瓶子的饮料
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> JOJA_COLA = FOOD.register("joja_cola", () -> new BaseFoodItem.ContainerFoodItem(FoodType.LOW, GLASS_BOTTLE)); //乔家可乐
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> CARTON_OF_MILK = FOOD.register("carton_of_milk", () -> new BaseFoodItem.ContainerFoodItem(FoodType.SENIOR, GLASS_BOTTLE)); //卡通牛奶
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> TEACUP = FOOD.register("teacup", () -> new BaseFoodItem.ContainerFoodItem(FoodType.LOW, GLASS_BOTTLE)); //一小杯茶
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> COFFEE = FOOD.register("coffee", () -> new BaseFoodItem.ContainerFoodItem(FoodType.SENIOR, GLASS_BOTTLE)); //咖啡
    public static final DeferredItem<BaseFoodItem.ContainerFoodItem> SAKE = FOOD.register("sake", () -> new BaseFoodItem.ContainerFoodItem(FoodType.SENIOR, GLASS_BOTTLE)); //清酒
    //鱼
    public static final DeferredItem<BaseFoodItem> SEA_BASS = FOOD.register("sea_bass", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> ATLANTIC_COD = FOOD.register("atlantic_cod", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> FROSTY_MINNOW = FOOD.register("frosty_minnow", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> PISCES_FIN_COD = FOOD.register("pisces_fin_cod", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> PARTIAL_MOUTH_FISH = FOOD.register("partial_mouth_fish", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> ROCK_LOBSTER = FOOD.register("rock_lobster", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> SHRIMP = FOOD.register("shrimp", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> TR_SALMON = FOOD.register("tr_salmon", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> TUNA = FOOD.register("tuna", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> RED_SNAPPER = FOOD.register("red_snapper", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> TROUT = FOOD.register("trout", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> ARMORED_CAVE_FISH = FOOD.register("armored_cave_fish", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> MIRROR_FISH = FOOD.register("mirror_fish", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> STINKY_FISH = FOOD.register("stinky_fish", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> NEON_GREASE_CARP = FOOD.register("neon_grease_carp", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> DAMSEL_FISH = FOOD.register("damsel_fish", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> EBONY_KOI = FOOD.register("ebony_koi", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> SCARLET_TIGER_FISH = FOOD.register("scarlet_tiger_fish", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> BLOODY_PIRANHAS = FOOD.register("bloody_piranhas", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> PRINCESS_FISH = FOOD.register("princess_fish", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> COLORFUL_MINERAL_FISH = FOOD.register("colorful_mineral_fish", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> CHAOS_FISH = FOOD.register("chaos_fish", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> MOTTLED_OILFISH = FOOD.register("mottled_oilfish", () -> new BaseFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem> GOLDEN_CARP = FOOD.register("golden_carp", () -> new BaseFoodItem(FoodType.GOLDEN_CARP));
    public static final DeferredItem<BaseFoodItem.FireproofFoodItem> OBSIDIAN_FISH = FOOD.register("obsidian_fish", () -> new BaseFoodItem.FireproofFoodItem(FoodType.FISH));
    public static final DeferredItem<BaseFoodItem.FireproofFoodItem> FLASHFIN_KOI = FOOD.register("flashfin_koi", () -> new BaseFoodItem.FireproofFoodItem(FoodType.FISH));
    //节日特有
    public static final DeferredItem<BaseFoodItem.FireproofFoodItem > ZONGZI = FOOD.register("zongzi", () -> new BaseFoodItem.FireproofFoodItem(FoodType.LOW));

    public static final DeferredItem<BaseFoodItem.FireproofFoodItem> HONEY_MOONCAKES = FOOD.register("honey_mooncakes", () -> new BaseFoodItem.FireproofFoodItem(FoodType.MEDIUM));
    public static final DeferredItem<BaseFoodItem.FireproofFoodItem> HONEY_MOONCAKES_CHUNKS = FOOD.register("honey_mooncakes_chunks", () -> new BaseFoodItem.FireproofFoodItem(FoodType.MOONCAKES));
    public static final DeferredItem<BaseFoodItem.FireproofFoodItem> EGG_YOLK_MOONCAKES = FOOD.register("egg_yolk_mooncakes", () -> new BaseFoodItem.FireproofFoodItem(FoodType.MEDIUM));
    public static final DeferredItem<BaseFoodItem.FireproofFoodItem> EGG_YOLK_MOONCAKES_CHUNKS = FOOD.register("egg_yolk_mooncakes_chunks", () -> new BaseFoodItem.FireproofFoodItem(FoodType.MEDIUM));

    public static final DeferredItem<BaseFoodItem.FireproofFoodItem> LONGEVITY_NOODLES = FOOD.register("longevity_noodles", () -> new BaseFoodItem.FireproofFoodItem(FoodType.LOW));
}
