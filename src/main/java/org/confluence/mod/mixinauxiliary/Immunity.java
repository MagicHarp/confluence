package org.confluence.mod.mixinauxiliary;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import static net.minecraft.world.damagesource.DamageTypes.*;

public interface Immunity {
    enum Types {
        /** 静态无敌帧，以类而不是对象区分不同的伤害，比如魔刺，多个魔刺弹幕叠在一起伤害频率也不会变快 */
        STATIC,
        /** 局部无敌帧，以对象区分不同的伤害，比如召唤物，多个同种召唤物同时击中不会骗伤 */
        LOCAL
    }

    // 有几个是由实体造成的伤害，其实不用写进来，实体默认用局部无敌帧且时长为0，即使用静态无敌帧也会按EntityType区分
    Object2IntMap<ResourceKey<DamageType>> durationsForTypes = new Object2IntOpenHashMap<>(new ImmutableMap.Builder<ResourceKey<DamageType>, Integer>()
        .put(PLAYER_ATTACK, 0)
        .put(ARROW, 0)
        .put(TRIDENT, 0)
        .put(MOB_PROJECTILE, 0)
        .put(FIREWORKS, 0)
        .put(FIREBALL, 0)
        .put(UNATTRIBUTED_FIREBALL, 0)
        .put(WITHER_SKULL, 0)
        .put(EXPLOSION, 0)
        .put(PLAYER_EXPLOSION, 0).build());

    HolderLookup.Provider damageTypeLookup = createLookup();

    private static HolderLookup.Provider createLookup(){
        RegistryAccess.Frozen registryAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        return new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, DamageTypes::bootstrap).build(registryAccess);
    }


    Types confluence$getImmunityType();

    default int confluence$getImmunityDuration(){
        return 0;
    }

    default int confluence$getImmunityDuration(RegistryAccess registry){
        if(!(getSelf() instanceof DamageType damageType)){
            return confluence$getImmunityDuration();
        }
        // 从DamageType实例拿对应的key
        ResourceKey<DamageType> key = registry.registryOrThrow(Registries.DAMAGE_TYPE).getResourceKey(damageType).orElse(null);
        // 优先查表
        if(durationsForTypes.containsKey(key)){
            return durationsForTypes.getInt(key);
        }
        // 然后如果是原版type就返回10
        if(key != null){
            if(damageTypeLookup.lookupOrThrow(Registries.DAMAGE_TYPE).get(key).isPresent()){
                return 10;
            }
        }
        return confluence$getImmunityDuration();
    }

    /**
     * 用来抑制上面if的警告<br>
     * 不用SelfGetter是因为和EntityMixin冲突
     */
    default Object getSelf(){
        return this;
    }
}
