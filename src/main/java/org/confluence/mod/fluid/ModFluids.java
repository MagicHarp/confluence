package org.confluence.mod.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.fluid.ShimmerTransformEvent.add;
import static org.confluence.mod.item.curio.CurioItems.BALLOON_PUFFERFISH;
import static org.confluence.mod.item.curio.CurioItems.SHINY_RED_BALLOON;

public final class ModFluids {
    public static FluidTriple HONEY;
    public static FluidTriple SHIMMER;

    public static void initialize() {
        HONEY = FluidBuilder.builder(new ResourceLocation(MODID, "honey"))
            .properties(FluidType.Properties.create()
                .density(1400)
                .viscosity(2000)
                .motionScale(0.0084)
                .canExtinguish(true)
                .supportsBoating(true)
                .rarity(ModRarity.YELLOW)
                .fallDistanceModifier(0.2F)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH))
            .client(new IClientFluidTypeExtensions() {
                private static final ResourceLocation STILL = new ResourceLocation(MODID, "block/fluid/honey_still");
                private static final ResourceLocation FLOWING = new ResourceLocation(MODID, "block/fluid/honey_flowing");
                private static final Vector3f FOG_COLOR = new Vector3f(1.0F, 1.0F, 0.0F);

                @Override
                public ResourceLocation getStillTexture() {
                    return STILL;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return FLOWING;
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                    return FOG_COLOR;
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                    RenderSystem.setShaderFogStart(0.125F);
                    RenderSystem.setShaderFogEnd(5.0F);
                }
            })
            .block(ModBlocks.HONEY)
            .bucket(ModItems.HONEY_BUCKET)
            .flowing((properties -> new ForgeFlowingFluid.Flowing(properties) {
                @Override
                protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluidIn, Direction direction) {
                    return !isSame(fluidIn);
                }
            }))
            .build();

        SHIMMER = FluidBuilder.builder(new ResourceLocation(MODID, "shimmer"))
            .properties(FluidType.Properties.create()
                .density(800)
                .lightLevel(7)
                .viscosity(800)
                .motionScale(0.0147)
                .canExtinguish(true)
                .supportsBoating(true)
                .rarity(ModRarity.PURPLE)
                .fallDistanceModifier(0.0F)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH))
            .client(new IClientFluidTypeExtensions() {
                private static final ResourceLocation STILL = new ResourceLocation(MODID, "block/fluid/shimmer_still");
                private static final ResourceLocation FLOWING = new ResourceLocation(MODID, "block/fluid/shimmer_flowing");
                private static final Vector3f FOG_COLOR = new Vector3f(1.0F, 0.5882F, 1.0F);

                @Override
                public ResourceLocation getStillTexture() {
                    return STILL;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return FLOWING;
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                    return FOG_COLOR;
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                    RenderSystem.setShaderFogStart(0.125F);
                    RenderSystem.setShaderFogEnd(10.0F);
                }
            })
            .block(ModBlocks.SHIMMER)
            .bucket(ModItems.SHIMMER_BUCKET)
            .build();
    }

    public static void registerInteraction() {
        FluidInteractionRegistry.addInteraction(HONEY.fluidType().get(), new FluidInteractionRegistry.InteractionInformation(
            (level, currentPos, relativePos, currentState) -> currentState.isSource() && level.getFluidState(relativePos).getFluidType() == ForgeMod.WATER_TYPE.get(),
            Blocks.HONEY_BLOCK.defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(HONEY.fluidType().get(), new FluidInteractionRegistry.InteractionInformation(
            (level, currentPos, relativePos, currentState) -> currentState.isSource() && level.getFluidState(relativePos).getFluidType() == ForgeMod.LAVA_TYPE.get(),
            ModBlocks.CRISPY_HONEY_BLOCK.get().defaultBlockState()
        ));
    }

    public static void registerShimmerTransform() {
        add(SHINY_RED_BALLOON.get(), BALLOON_PUFFERFISH.get());
        add(BALLOON_PUFFERFISH.get(), SHINY_RED_BALLOON.get());
    }
}
