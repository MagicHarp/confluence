package org.confluence.mod.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.confluence.mod.Confluence;

public final class ModDamageTypes {
    public static final ResourceKey<DamageType> FALLING_STAR = register("falling_star");
    public static final ResourceKey<DamageType> ACID_VENOM = register("acid_venom");
    public static final ResourceKey<DamageType> CURSED_INFERNO = register("cursed_inferno");
    public static final ResourceKey<DamageType> FROST_BURN = register("frost_burn");
    public static final ResourceKey<DamageType> STAR_CLOAK = register("star_cloak");
    public static final ResourceKey<DamageType> BOULDER = register("boulder");

    private static ResourceKey<DamageType> register(String id) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Confluence.MODID, id));
    }

    public static DamageSource of(Level level, ResourceKey<DamageType> key) {
        return of(level, key, null, null);
    }

    public static DamageSource of(Level level, ResourceKey<DamageType> key, Entity causing) {
        return of(level, key, causing, causing);
    }

    public static DamageSource of(Level level, ResourceKey<DamageType> key, Entity causing, Entity direct) {
        return new DamageSource(level.registryAccess().registry(Registries.DAMAGE_TYPE).orElseThrow().getHolderOrThrow(key), causing, direct);
    }
}
