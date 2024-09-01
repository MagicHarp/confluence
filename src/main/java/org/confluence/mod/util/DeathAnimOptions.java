package org.confluence.mod.util;

import net.minecraft.world.entity.Entity;

import java.util.function.Consumer;

// TODO: 不够灵活，待重构
public interface DeathAnimOptions {
    default float[] getRotations(){
        return new float[]{30, 270, 30};
    }

    default boolean isNoModelGravity(){
        return false;
    }
    default double getExtraGravity(){
        return 0;
    }
    default float[] getBloodColor(){
        return new float[]{0.7f, 0, 0};
    }

    /** @return 是否已经添加过粒子，不用再添加默认粒子了 */
    default boolean makeParticle(Entity entity){
        return false;
    }

    default DeathAnimOptions bloodColor(float r, float g, float b){
        return new Delegate(DeathAnimOptions.this) {
            @Override
            public float[] getBloodColor(){
                return new float[]{r, g, b};
            }
        };
    }

    default DeathAnimOptions customParticle(Consumer<Entity> particle){
        return new Delegate(DeathAnimOptions.this){
            @Override
            public boolean makeParticle(Entity entity){
                particle.accept(entity);
                return true;
            }
        };
    }

    class Delegate implements DeathAnimOptions{
        private final DeathAnimOptions delegate;
        public Delegate(DeathAnimOptions delegate){
            this.delegate = delegate;
        }
        @Override
        public float[] getRotations(){
            return delegate.getRotations();
        }

        @Override
        public boolean isNoModelGravity(){
            return delegate.isNoModelGravity();
        }

        @Override
        public double getExtraGravity(){
            return delegate.getExtraGravity();
        }

        @Override
        public float[] getBloodColor(){
            return delegate.getBloodColor();
        }

        @Override
        public boolean makeParticle(Entity e){
            return delegate.makeParticle(e);
        }
    }

    enum Builtin implements DeathAnimOptions{
        DEFAULT(new float[]{30, 270, 30}, false,0),
        NO_GRAVITY(new float[]{30, 270, 30}, true,0),
        LOW_SPIN(new float[]{30, 30, 30}, false,0),
        NO_GRAVITY_LOW_SPIN(new float[]{30, 30, 30}, true,0),
        NO_XZ_SPIN_NO_GRAVITY(new float[]{0, 270, 0}, true, 0),
        EXTRA_FALL(new float[]{30, 270, 30}, false, 0.08);

        private final boolean noModelGravity;
        private final double extraGravity;
        private final float[] rot;

        Builtin(float[] rot, boolean noModelGravity, double extraGravity){
            this.rot = rot;
            this.noModelGravity = noModelGravity;
            this.extraGravity = extraGravity;
        }
        @Override
        public float[] getRotations(){
            return rot;
        }

        @Override
        public boolean isNoModelGravity(){
            return noModelGravity;
        }

        @Override
        public double getExtraGravity(){
            return extraGravity;
        }

    }
}
