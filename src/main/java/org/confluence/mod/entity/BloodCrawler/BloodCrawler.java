package org.confluence.mod.entity.BloodCrawler;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class BloodCrawler extends Spider implements GeoEntity {

    private static final int ATTACK_DAMAGE = 11;
    private static final int MAX_HEALTH = 23;
    private static final int DEFENSE = 2;

    public BloodCrawler(EntityType<? extends Spider> type, Level level) {
        super(type, level);
        this.setHealth(MAX_HEALTH);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Spider.createMobAttributes()
            .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE)  // 攻击力
            .add(Attributes.MAX_HEALTH, MAX_HEALTH)        // 生命值
            .add(Attributes.ARMOR, DEFENSE)                 // 防御值
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);     // 击退抗性
    }

    /**
     * @param controllers The object to register your controller instances to
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    /**
     * @return
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }
}
