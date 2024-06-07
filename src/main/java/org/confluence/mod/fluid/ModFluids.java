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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.fishing.Baits;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Collections;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.fluid.ShimmerTransmutationEvent.add;
import static org.confluence.mod.item.curio.CurioItems.*;

public final class ModFluids {
    public static FluidTriple HONEY;
    public static FluidTriple SHIMMER;

    public static void initialize() {
        HONEY = FluidTriple.builder(new ResourceLocation(MODID, "honey"))
            .properties(FluidType.Properties.create()
                .density(2000)
                .viscosity(3000)
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

        SHIMMER = FluidTriple.builder(new ResourceLocation(MODID, "shimmer"))
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
            .bucket(() -> Items.AIR)
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
        // 饰品转化
        add(SHINY_RED_BALLOON.get(), BALLOON_PUFFERFISH.get());
        add(BALLOON_PUFFERFISH.get(), SHINY_RED_BALLOON.get());
        add(MAGMA_STONE.get(), LAVA_CHARM.get());
        add(LAVA_CHARM.get(), MAGMA_STONE.get());
        add(SEXTANT.get(), WEATHER_RADIO.get());
        add(WEATHER_RADIO.get(), FISHERMANS_POCKET_GUIDE.get());
        add(FISHERMANS_POCKET_GUIDE.get(), SEXTANT.get());
        add(BEZOAR.get(), ADHESIVE_BANDAGE.get());
        add(ADHESIVE_BANDAGE.get(), BEZOAR.get());
        add(ARMOR_POLISH.get(), VITAMINS.get());
        add(VITAMINS.get(), ARMOR_POLISH.get());
        add(POCKET_MIRROR.get(), BLINDFOLD.get());
        add(BLINDFOLD.get(), POCKET_MIRROR.get());
        add(FAST_CLOCK.get(), TRIFOLD_MAP.get());
        add(TRIFOLD_MAP.get(), FAST_CLOCK.get());
        add(NAZAR.get(), MEGAPHONE.get());
        add(MEGAPHONE.get(), NAZAR.get());
        add(HIGH_TEST_FISHING_LINE.get(), ANGLER_EARRING.get());
        add(ANGLER_EARRING.get(), TACKLE_BOX.get());
        add(TACKLE_BOX.get(), HIGH_TEST_FISHING_LINE.get());
        // 匣子转化
        // 宝石转化
        add(Materials.TOPAZ.get(), Materials.ANOTHER_AMETHYST.get());
        add(Materials.SAPPHIRE.get(), Materials.TOPAZ.get());
        // 锭到矿的转化
        add(Materials.TITANIUM_INGOT.get(), Materials.RAW_TITANIUM.get());
        add(Materials.ADAMANTITE_INGOT.get(), Materials.RAW_ADAMANTITE.get());
        add(Materials.ORICHALCUM_INGOT.get(), Materials.RAW_ORICHALCUM.get());
        add(Materials.MITHRIL_INGOT.get(), Materials.RAW_MITHRIL.get());
        add(Materials.PALLADIUM_INGOT.get(), Materials.RAW_PALLADIUM.get());
        add(Materials.COBALT_INGOT.get(), Materials.RAW_COBALT.get());
        add(Materials.HELLSTONE_INGOT.get(), Materials.PRIMORDIAL_HELLSTONE_INGOT.get());
        add(Materials.PRIMORDIAL_HELLSTONE_INGOT.get(), Materials.RAW_HELLSTONE.get());
        add(Materials.ANOTHER_CRIMSON_INGOT.get(), Materials.RAW_ANOTHER_CRIMSON.get());
        add(Materials.EBONY_INGOT.get(), Materials.RAW_EBONY.get());
        add(Materials.METEORITE_INGOT.get(), Materials.RAW_METEORITE.get());
        add(Materials.PLATINUM_INGOT.get(), Materials.RAW_PLATINUM.get());
        add(Materials.TUNGSTEN_INGOT.get(), Materials.RAW_TUNGSTEN.get());
        add(Materials.SILVER_INGOT.get(), Materials.RAW_SILVER.get());
        add(Materials.LEAD_INGOT.get(), Materials.RAW_LEAD.get());
        add(Materials.TIN_INGOT.get(), Materials.RAW_TIN.get());
        // 矿的下级转化（陨石，魔矿，猩红矿不参与这一过程）
        add(Materials.RAW_TITANIUM.get(), Materials.RAW_ADAMANTITE.get());
        add(Materials.RAW_ADAMANTITE.get(), Materials.RAW_ORICHALCUM.get());
        add(Materials.RAW_ORICHALCUM.get(), Materials.RAW_MITHRIL.get());
        add(Materials.RAW_PALLADIUM.get(), Materials.RAW_COBALT.get());
        add(Materials.RAW_COBALT.get(), Materials.RAW_PLATINUM.get());
        add(Materials.RAW_PLATINUM.get(), Materials.RAW_TUNGSTEN.get());
        add(Materials.RAW_TUNGSTEN.get(), Materials.RAW_SILVER.get());
        add(Materials.RAW_SILVER.get(), Materials.RAW_LEAD.get());
        add(Materials.RAW_LEAD.get(), Materials.RAW_TIN.get());

        add(Items.WATER_BUCKET, Items.LAVA_BUCKET);
        add(Items.LAVA_BUCKET, ModItems.HONEY_BUCKET.get());
        add(ModItems.HONEY_BUCKET.get(), Items.WATER_BUCKET);

        add(Materials.LIFE_CRYSTAL.get(), ModItems.VITAL_CRYSTAL.get());
        add(Materials.MANA_STAR.get(), ModItems.ARCANE_CRYSTAL.get());
        add(Materials.LIFE_FRUIT.get(), ModItems.AEGIS_APPLE.get());
        add(ModTags.Items.FRUIT, Collections.singletonList(new ItemStack(ModItems.AMBROSIA.get())), 1);
        add(Baits.GOLD_WORM.get(), ModItems.GUMMY_WORM.get());
        add(Materials.PINK_PEARL.get(), ModItems.GALAXY_PEARL.get());
        // <---注释后面记得打空格
    }
}
