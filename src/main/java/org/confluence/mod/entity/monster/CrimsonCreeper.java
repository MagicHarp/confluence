package org.confluence.mod.entity.monster;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.confluence.mod.entity.ModEntities;

public class CrimsonCreeper extends Creeper {
    private int oldSwell;
    private int swell;
    private int maxSwell = 30;

    public CrimsonCreeper(EntityType<? extends Creeper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 0.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.MAX_HEALTH, 100.0);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }

            int $$0 = this.getSwellDir();
            if ($$0 > 0 && this.swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += $$0;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explodeCreeper();
            }
        }
        super.tick();
    }

    private void explodeCreeper() {
        if (!this.level().isClientSide) {
            int $$0 = this.isPowered() ? 2 : 1;
            this.dead = true;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.2F * $$0, Level.ExplosionInteraction.NONE);
            System.out.println("ok, it is custom exp!");
            int number = (random.nextInt(2, 4)) * $$0;
            for (int i = 0; i < number; i++){
                //summon
                BloodCrawler crawler = new BloodCrawler(ModEntities.BLOOD_CRAWLER.get(), level());
                crawler.setPos(this.getX(), this.getY(), this.getZ());
                level().addFreshEntity(crawler);
            }
            this.discard();
        }
    }
    @Override
    public float getSwelling(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }
}
