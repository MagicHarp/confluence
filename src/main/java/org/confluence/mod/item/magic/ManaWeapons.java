package org.confluence.mod.item.magic;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.projectile.bullet.*;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.BaseItem;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum ManaWeapons implements EnumRegister<Item> {
    WOND_OF_SPARKING("wond_of_sparking", () -> new StaffItem(SparkBulletEntity::new)),
    WOND_OF_FROSTING("wond_of_frosting", () -> new StaffItem(FrostBulletEntity::new)),
    RUBY_STAFF("ruby_staff", () -> new StaffItem(RubyBulletEntity::new)),
    AMBER_STAFF("amber_staff", () -> new StaffItem(AmberBulletEntity::new)),
    TOPAZ_STAFF("topaz_staff", () -> new StaffItem(TopazBulletEntity::new)),
    EMERALD_STAFF("emerald_staff", () -> new StaffItem(EmeraldBulletEntity::new)),
    SAPPHIRE_STAFF("sapphire_staff", () -> new StaffItem(SapphireBulletEntity::new)),
    AMETHYST_STAFF("amethyst_staff", () -> new StaffItem(AmethystBulletEntity::new)),
    DIAMOND_STAFF("diamond_staff", () -> new StaffItem(DiamondBulletEntity::new)),
    AQUA_SCEPTER("aqua_scepter", BaseItem::new),//海蓝权杖
    THUNDER_ZAPPER("thunder_zapper", BaseItem::new),//雷霆法杖
    VILETHORN("vilethorn", BaseItem::new),//魔刺
    WEATHER_PAIN("weather_pain", BaseItem::new),//天候棒
    MAGIC_MISSILE("magic_missile", BaseItem::new),//魔法导弹
    FLOWER_OF_FIRE("flower_of_fire", BaseItem::new),//火之花
    FLAMELASH("flamelash", BaseItem::new),//烈焰火鞭
    //肉后
    SKY_FRACTURE("sky_fracture", BaseItem::new),//裂天剑
    CRYSTAL_SERPENT("crystal_serpent", BaseItem::new),//水晶蛇
    FROST_STAFF("frost_staff", BaseItem::new),//寒霜法杖
    FLOWER_OF_FROST("flower_of_frost", BaseItem::new),//寒霜之花
    CRYSTAL_VILE_SHARD("crystal_vile_shard", BaseItem::new),//魔晶碎块
    LIFE_DRAIN("life_drain", BaseItem::new),//夺命杖
    METEOR_STAFF("meteor_staff", BaseItem::new),//流星法杖
    POISON_STAFF("poison_staff", BaseItem::new),//毒液法杖
    RAINBOW_ROD("rainbow_rod", BaseItem::new),//彩虹法杖
    UNHOLY_TRIDENT("unholy_trident", BaseItem::new),//邪恶三叉戟
    TOME_OF_INFINITE_WISDOM(" tome_of_infinite_wisdom", BaseItem::new),//无限智慧巨著
    NETTLE_BURST("nettle_burst", BaseItem::new),//爆裂藤曼
    SHADOWBEAM_STAFF("shadowbeam_staff", BaseItem::new),//暗影束法杖
    INFERNO_FORK("inferno_fork", BaseItem::new),//狱火叉
    VENOM_STAFF("venom_staff", BaseItem::new),//毒液法杖
    BAT_SCEPTER("bat_scepter", BaseItem::new),//蝙蝠权杖
    BLIZZARD_STAFF("blizzard_staff", BaseItem::new),//暴雪法杖
    SPECTRE_STAFF("spectre_staff", BaseItem::new),//幽灵法杖
    RESONANCE_SCEPTER("resonance_scepter", BaseItem::new),//共鸣权杖
    STAFF_OF_EARTH("staff_of_earth", BaseItem::new),//大地法杖
    RAZORPINE(" razorpine", BaseItem::new),//剃刀松
    BETSYS_WRATH("betsys_wrath", BaseItem::new),//双足翼龙怒气


;
    private final RegistryObject<Item> value;

    ManaWeapons(String id, Supplier<Item> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering mana weapons");
    }
}
