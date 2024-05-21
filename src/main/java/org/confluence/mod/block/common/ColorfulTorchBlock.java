package org.confluence.mod.block.common;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ColorfulTorchBlock extends TorchBlock {
    private final ColorPointLight.Template torchColor;

    public ColorfulTorchBlock(float radius, float r, float g, float b) {
        super(BlockBehaviour.Properties.copy(Blocks.TORCH), ParticleTypes.FLAME);
        this.torchColor = new ColorPointLight.Template(radius, r, g, b, 1.0F);
    }

    public ColorfulTorchBlock(ColorPointLight.Template torchColor) {
        super(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel(state -> 15), ParticleTypes.FLAME);
        this.torchColor = torchColor;
    }

    public ColorPointLight.Template getColor() {
        return torchColor;
    }
}
