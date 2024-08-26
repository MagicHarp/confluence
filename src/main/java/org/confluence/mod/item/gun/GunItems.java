package org.confluence.mod.item.gun;

import net.minecraftforge.registries.RegistryObject;

import static org.confluence.mod.item.ModItems.ITEMS;

public final class GunItems {
    public static final RegistryObject<HandGunItem> HANDGUN = ITEMS.register("handgun", HandGunItem::new);
    public static final RegistryObject<MusketGunItem> MUSKET = ITEMS.register("musket", MusketGunItem::new);
    public static final RegistryObject<BoomstickItem> BOOMSTICK = ITEMS.register("boomstick", BoomstickItem::new);
    public static final RegistryObject<FlintlockPistolItem> FLINTLOCKPISTOL = ITEMS.register("flintlockpistol", FlintlockPistolItem::new);
    public static final RegistryObject<FlareGunItem> FLAREGUN = ITEMS.register("flaregun", FlareGunItem::new);
    public static final RegistryObject<MinisharkItem> MINISHARK = ITEMS.register("minishark", MinisharkItem::new);
    public static final RegistryObject<ShotgunItem> SHOTGUN = ITEMS.register("shotgun", ShotgunItem::new);
    public static final RegistryObject<TacticalShotgunItem> TACTICALSHOTGUN = ITEMS.register("tacticalshotgun", TacticalShotgunItem::new);
    public static final RegistryObject<UZIItem> UZI = ITEMS.register("uzi", UZIItem::new);
    public static final RegistryObject<RevolverItem> REVOLVER = ITEMS.register("revolver", RevolverItem::new);
    public static final RegistryObject<PhoenixBlasterItem> PHOENIXBLASTER = ITEMS.register("phoenixblaster", PhoenixBlasterItem::new);
    public static final RegistryObject<OnyxBlasterItem> ONYXBLASTER = ITEMS.register("onyxblaster", OnyxBlasterItem::new);
    public static final RegistryObject<SniperRifleItem> SNIPERRIFLE = ITEMS.register("sniperrifle", SniperRifleItem::new);
    public static final RegistryObject<SlimeGunItem> SLIMEGUN = ITEMS.register("slimegun", SlimeGunItem::new);
    public static final RegistryObject<SnowballCannonItem> SNOWBALLCANNON = ITEMS.register("snowballcannon", SnowballCannonItem::new);
    public static final RegistryObject<StarCannonItem> STARCANNON = ITEMS.register("starcannon", StarCannonItem::new);
    public static final RegistryObject<BlowpipeItem> BLOWPIPE = ITEMS.register("blowpipe", BlowpipeItem::new);
    public static final RegistryObject<BlowgunItem> BLOWGUN = ITEMS.register("blowgun", BlowgunItem::new);

    public static void init() {}
}
