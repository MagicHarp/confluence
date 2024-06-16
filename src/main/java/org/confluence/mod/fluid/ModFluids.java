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
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
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

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.fluid.ShimmerEntityTransmutationEvent.addEntity;
import static org.confluence.mod.fluid.ShimmerItemTransmutationEvent.addItem;
import static org.confluence.mod.item.curio.CurioItems.*;

public final class ModFluids {
    public static FluidTriple HONEY;
    public static FluidTriple SHIMMER;

    public static void initialize() {
        HONEY = FluidTriple.builder(new ResourceLocation(MODID, "honey"))
            .properties(FluidType.Properties.create()
                .density(2000)
                .canSwim(false)
                .viscosity(3000)
                .motionScale(0.003)
                .canExtinguish(true)
                .supportsBoating(true)
                .rarity(ModRarity.YELLOW)
                .fallDistanceModifier(0.2F)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH))
            .customClient(new IClientFluidTypeExtensions() {
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
            .flowing(properties -> new ForgeFlowingFluid.Flowing(properties) {
                @Override
                protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluidIn, Direction direction) {
                    return !isSame(fluidIn);
                }
            })
            .build();

        SHIMMER = FluidTriple.builder(new ResourceLocation(MODID, "shimmer"))
            .properties(FluidType.Properties.create()
                .density(800)
                .lightLevel(7)
                .viscosity(800)
                .canSwim(false)
                .motionScale(0.02)
                .canExtinguish(true)
                .supportsBoating(true)
                .rarity(ModRarity.PURPLE)
                .fallDistanceModifier(0.0F)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH))
            .customClient(new IClientFluidTypeExtensions() {
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
            .flowing(properties -> new ForgeFlowingFluid.Flowing(properties) {
                @Override
                protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluidIn, Direction direction) {
                    return !isSame(fluidIn);
                }
            })
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
        addItem(ItemTags.WOOL, Items.WHITE_WOOL, 1);
        addItem(ItemTags.WOOL_CARPETS, Items.WHITE_CARPET, 1);
        // 饰品转化
        addItem(SHINY_RED_BALLOON.get(), BALLOON_PUFFERFISH.get());
        addItem(BALLOON_PUFFERFISH.get(), SHINY_RED_BALLOON.get());
        addItem(MAGMA_STONE.get(), LAVA_CHARM.get());
        addItem(LAVA_CHARM.get(), MAGMA_STONE.get());
        addItem(SEXTANT.get(), WEATHER_RADIO.get());
        addItem(WEATHER_RADIO.get(), FISHERMANS_POCKET_GUIDE.get());
        addItem(FISHERMANS_POCKET_GUIDE.get(), SEXTANT.get());
        addItem(BEZOAR.get(), ADHESIVE_BANDAGE.get());
        addItem(ADHESIVE_BANDAGE.get(), BEZOAR.get());
        addItem(ARMOR_POLISH.get(), VITAMINS.get());
        addItem(VITAMINS.get(), ARMOR_POLISH.get());
        addItem(POCKET_MIRROR.get(), BLINDFOLD.get());
        addItem(BLINDFOLD.get(), POCKET_MIRROR.get());
        addItem(FAST_CLOCK.get(), TRIFOLD_MAP.get());
        addItem(TRIFOLD_MAP.get(), FAST_CLOCK.get());
        addItem(NAZAR.get(), MEGAPHONE.get());
        addItem(MEGAPHONE.get(), NAZAR.get());
        addItem(HIGH_TEST_FISHING_LINE.get(), ANGLER_EARRING.get());
        addItem(ANGLER_EARRING.get(), TACKLE_BOX.get());
        addItem(TACKLE_BOX.get(), HIGH_TEST_FISHING_LINE.get());
        // 匣子转化
        // <---注释后面记得打空格
        // 宝石转化
        addItem(Materials.TOPAZ.get(), Materials.ANOTHER_AMETHYST.get());
        addItem(Materials.SAPPHIRE.get(), Materials.TOPAZ.get());
        // 锭到矿的转化
        addItem(Materials.TITANIUM_INGOT.get(), Materials.RAW_TITANIUM.get());
        addItem(Materials.ADAMANTITE_INGOT.get(), Materials.RAW_ADAMANTITE.get());
        addItem(Materials.ORICHALCUM_INGOT.get(), Materials.RAW_ORICHALCUM.get());
        addItem(Materials.MITHRIL_INGOT.get(), Materials.RAW_MITHRIL.get());
        addItem(Materials.PALLADIUM_INGOT.get(), Materials.RAW_PALLADIUM.get());
        addItem(Materials.COBALT_INGOT.get(), Materials.RAW_COBALT.get());
        addItem(Materials.HELLSTONE_INGOT.get(), Materials.PRIMORDIAL_HELLSTONE_INGOT.get());
        addItem(Materials.PRIMORDIAL_HELLSTONE_INGOT.get(), Materials.RAW_HELLSTONE.get());
        addItem(Materials.ANOTHER_CRIMSON_INGOT.get(), Materials.RAW_ANOTHER_CRIMSON.get());
        addItem(Materials.EBONY_INGOT.get(), Materials.RAW_EBONY.get());
        addItem(Materials.METEORITE_INGOT.get(), Materials.RAW_METEORITE.get());
        addItem(Materials.PLATINUM_INGOT.get(), Materials.RAW_PLATINUM.get());
        addItem(Materials.TUNGSTEN_INGOT.get(), Materials.RAW_TUNGSTEN.get());
        addItem(Materials.SILVER_INGOT.get(), Materials.RAW_SILVER.get());
        addItem(Materials.LEAD_INGOT.get(), Materials.RAW_LEAD.get());
        addItem(Materials.TIN_INGOT.get(), Materials.RAW_TIN.get());
        // 矿的下级转化（陨石，魔矿，猩红矿不参与这一过程）
        addItem(Materials.RAW_TITANIUM.get(), Materials.RAW_ADAMANTITE.get());
        addItem(Materials.RAW_ADAMANTITE.get(), Materials.RAW_ORICHALCUM.get());
        addItem(Materials.RAW_ORICHALCUM.get(), Materials.RAW_MITHRIL.get());
        addItem(Materials.RAW_PALLADIUM.get(), Materials.RAW_COBALT.get());
        addItem(Materials.RAW_COBALT.get(), Materials.RAW_PLATINUM.get());
        addItem(Materials.RAW_PLATINUM.get(), Materials.RAW_TUNGSTEN.get());
        addItem(Materials.RAW_TUNGSTEN.get(), Materials.RAW_SILVER.get());
        addItem(Materials.RAW_SILVER.get(), Materials.RAW_LEAD.get());
        addItem(Materials.RAW_LEAD.get(), Materials.RAW_TIN.get());

        addItem(Items.WATER_BUCKET, Items.LAVA_BUCKET);
        addItem(Items.LAVA_BUCKET, ModItems.HONEY_BUCKET.get());
        addItem(ModItems.HONEY_BUCKET.get(), Items.WATER_BUCKET);

        addItem(Materials.LIFE_CRYSTAL.get(), ModItems.VITAL_CRYSTAL.get());
        addItem(Materials.MANA_STAR.get(), ModItems.ARCANE_CRYSTAL.get());
        addItem(Materials.LIFE_FRUIT.get(), ModItems.AEGIS_APPLE.get());
        addItem(ModTags.Items.FRUIT, ModItems.AMBROSIA.get(), 1);
        addItem(Baits.GOLD_WORM.get(), ModItems.GUMMY_WORM.get());
        addItem(Materials.PINK_PEARL.get(), ModItems.GALAXY_PEARL.get());

        addEntity(EntityType.VILLAGER, EntityType.WITCH);
    }
}
