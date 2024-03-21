package org.confluence.mod.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import org.confluence.mod.Confluence;

public class ConfluenceDamageTypes {
    public static final ResourceKey<DamageType> FALLING_STAR = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Confluence.MODID, "falling_star"));

    public static DamageSource of(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registry(Registries.DAMAGE_TYPE).orElseThrow().getHolderOrThrow(key));
    }
}
