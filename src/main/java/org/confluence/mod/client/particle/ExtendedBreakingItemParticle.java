package org.confluence.mod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ExtendedBreakingItemParticle extends BreakingItemParticle {
    public ExtendedBreakingItemParticle(ClientLevel level, double x, double y, double z, Item item) {
        super(level, x, y, z, new ItemStack(item));
    }

    @OnlyIn(Dist.CLIENT)
    public static class SlimeBallProvider implements ParticleProvider<SimpleParticleType> {
        private final Item item;

        public SlimeBallProvider(Item item) {
            this.item = item;
        }

        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double mx, double my, double mz) {
            ExtendedBreakingItemParticle particle = new ExtendedBreakingItemParticle(level, x, y, z, item);
            particle.setColor((float) mx, (float) my, (float) mz);
            return particle;
        }
    }
}
