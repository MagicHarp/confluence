package org.confluence.mod.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.Torches;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.fishing.Baits;
import org.confluence.mod.item.sword.Swords;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.fluid.ShimmerEntityTransmutationEvent.addEntity;
import static org.confluence.mod.fluid.ShimmerItemTransmutationEvent.addItem;
import static org.confluence.mod.fluid.ShimmerItemTransmutationEvent.blackList;
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
                .motionScale(0.0003)
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
            .build();
    }

    public static void registerInteraction() {
        FluidInteractionRegistry.addInteraction(HONEY.fluidType().get(), new FluidInteractionRegistry.InteractionInformation(
            ForgeMod.WATER_TYPE.get(), fluidState -> fluidState.isSource() ? Blocks.HONEY_BLOCK.defaultBlockState() : ModBlocks.THIN_HONEY_BLOCK.get().defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(HONEY.fluidType().get(), new FluidInteractionRegistry.InteractionInformation(
            ForgeMod.LAVA_TYPE.get(), fluidState -> fluidState.isSource() ? ModBlocks.CRISPY_HONEY_BLOCK.get().defaultBlockState() : ModBlocks.LOOSE_HONEY_BLOCK.get().defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(SHIMMER.fluidType().get(), new FluidInteractionRegistry.InteractionInformation(
            ForgeMod.WATER_TYPE.get(), fluidState -> fluidState.isSource() ? ModBlocks.AETHERIUM_BLOCK.get().defaultBlockState() : ModBlocks.DARK_AETHERIUM_BLOCK.get().defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(SHIMMER.fluidType().get(), new FluidInteractionRegistry.InteractionInformation(
            ForgeMod.LAVA_TYPE.get(), fluidState -> fluidState.isSource() ? ModBlocks.AETHERIUM_BLOCK.get().defaultBlockState() : ModBlocks.DARK_AETHERIUM_BLOCK.get().defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(SHIMMER.fluidType().get(), new FluidInteractionRegistry.InteractionInformation(
            HONEY.fluidType().get(), fluidState -> fluidState.isSource() ? ModBlocks.AETHERIUM_BLOCK.get().defaultBlockState() : ModBlocks.DARK_AETHERIUM_BLOCK.get().defaultBlockState()
        ));
    }

    public static void registerShimmerTransform() {
        // 黑名单
        blackList(ItemTags.STAIRS);
        // 顶替
        addItem(ItemTags.WOOL, Items.WHITE_WOOL, 1);
        addItem(ItemTags.WOOL_CARPETS, Items.WHITE_CARPET, 1);
        addItem(Items.CRAFTING_TABLE, Items.OAK_PLANKS, 4);
        // 饰品转化
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
        // 火把转化
        addItem(Torches.RED_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.ORANGE_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.YELLOW_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.GREEN_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.BLUE_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.WHITE_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.PURPLE_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.ICE_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.PINK_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.BONE_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.ULTRABRIGHT_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.DEMON_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.CURSED_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.ICHOR_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.RAINBOW_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.DESERT_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.CORAL_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.CORRUPT_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.CRIMSON_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.HALLOWED_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.JUNGLE_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        addItem(Torches.MUSHROOM_TORCH.item.get(), Torches.AETHER_TORCH.item.get());
        // 匣子转化
        // 宝石转化
        addItem(Materials.TOPAZ.get(), Materials.TR_AMETHYST.get());
        addItem(Materials.SAPPHIRE.get(), Materials.TOPAZ.get());
        addItem(Items.EMERALD, Materials.SAPPHIRE.get());
        addItem(Materials.RUBY.get(), Items.EMERALD);
        addItem(Items.DIAMOND, Materials.RUBY.get());
        addItem(Materials.TR_AMETHYST.get(), Items.DIRT);
        // 锭到矿的转化
        addItem(Materials.TITANIUM_INGOT.get(), Materials.RAW_TITANIUM.get());
        addItem(Materials.ADAMANTITE_INGOT.get(), Materials.RAW_ADAMANTITE.get());
        addItem(Materials.ORICHALCUM_INGOT.get(), Materials.RAW_ORICHALCUM.get());
        addItem(Materials.MITHRIL_INGOT.get(), Materials.RAW_MITHRIL.get());
        addItem(Materials.PALLADIUM_INGOT.get(), Materials.RAW_PALLADIUM.get());
        addItem(Materials.COBALT_INGOT.get(), Materials.RAW_COBALT.get());
        addItem(Materials.HELLSTONE_INGOT.get(), Materials.PRIMORDIAL_HELLSTONE_INGOT.get());
        addItem(Materials.PRIMORDIAL_HELLSTONE_INGOT.get(), Materials.RAW_HELLSTONE.get());
        addItem(Materials.TR_CRIMSON_INGOT.get(), Materials.RAW_TR_CRIMSON.get());
        addItem(Materials.EBONY_INGOT.get(), Materials.RAW_EBONY.get());
        addItem(Materials.METEORITE_INGOT.get(), Materials.RAW_METEORITE.get());
        addItem(Materials.PLATINUM_INGOT.get(), Materials.RAW_PLATINUM.get());
        addItem(Items.GOLD_INGOT, Items.RAW_GOLD);
        addItem(Materials.TUNGSTEN_INGOT.get(), Materials.RAW_TUNGSTEN.get());
        addItem(Materials.SILVER_INGOT.get(), Materials.RAW_SILVER.get());
        addItem(Items.IRON_INGOT, Items.RAW_IRON);
        addItem(Materials.LEAD_INGOT.get(), Materials.RAW_LEAD.get());
        addItem(Materials.TIN_INGOT.get(), Materials.RAW_TIN.get());
        addItem(Items.COPPER_INGOT, Items.RAW_COPPER);
        addItem(Items.RAW_COPPER, Items.COBBLESTONE);
        addItem(Items.COBBLESTONE, Items.DIRT);
        // 矿的下级转化（陨石，魔矿，猩红矿不参与这一过程）
        addItem(Materials.RAW_TITANIUM.get(), Materials.RAW_ADAMANTITE.get());
        addItem(Materials.RAW_ADAMANTITE.get(), Materials.RAW_ORICHALCUM.get());
        addItem(Materials.RAW_ORICHALCUM.get(), Materials.RAW_MITHRIL.get());
        addItem(Materials.RAW_PALLADIUM.get(), Materials.RAW_COBALT.get());
        addItem(Materials.RAW_COBALT.get(), Materials.RAW_PLATINUM.get());
        addItem(Materials.RAW_PLATINUM.get(), Items.RAW_GOLD);
        addItem(Items.RAW_GOLD, Materials.RAW_TUNGSTEN.get());
        addItem(Materials.RAW_TUNGSTEN.get(), Materials.RAW_SILVER.get());
        addItem(Materials.RAW_SILVER.get(), Materials.RAW_LEAD.get());
        addItem(Materials.RAW_LEAD.get(), Items.RAW_IRON);
        addItem(Items.RAW_IRON, Materials.RAW_TIN.get());
        addItem(Materials.RAW_TIN.get(), Items.RAW_COPPER);

        addItem(Items.WATER_BUCKET, Items.LAVA_BUCKET);
        addItem(Items.LAVA_BUCKET, ModItems.HONEY_BUCKET.get());
        addItem(ModItems.HONEY_BUCKET.get(), Items.WATER_BUCKET);
        addItem(ModItems.WHOOPIE_CUSHION.get(), Swords.ZOMBIE_ARM.get());

        addItem(Materials.LIFE_CRYSTAL.get(), ModItems.VITAL_CRYSTAL.get());
        addItem(Materials.MANA_STAR.get(), ModItems.ARCANE_CRYSTAL.get());
        addItem(Materials.LIFE_FRUIT.get(), ModItems.AEGIS_APPLE.get());
        addItem(ModTags.Items.FRUIT, ModItems.AMBROSIA.get(), 1);
        addItem(Baits.GOLD_WORM.get(), ModItems.GUMMY_WORM.get());
        addItem(Materials.PINK_PEARL.get(), ModItems.GALAXY_PEARL.get());

        addEntity(EntityType.WITCH, EntityType.VILLAGER);
        addEntity(entity -> {
            EntityType<?> entityType = entity.getType();
            return entityType == EntityType.PIGLIN ||
                entityType == EntityType.PIGLIN_BRUTE ||
                entityType == EntityType.ZOMBIFIED_PIGLIN ||
                entityType == EntityType.CREEPER;
        }, EntityType.PIG);
        addEntity(entity -> entity instanceof AbstractSkeleton || entity.getType() == EntityType.ZOMBIE, EntityType.SKELETON);
        addEntity(entity -> entity instanceof AbstractHorse, EntityType.HORSE);
        addEntity(EntityType.VEX, EntityType.ALLAY);
        addEntity(entity -> entity instanceof Creeper creeper && creeper.isPowered(), EntityType.CREEPER);
        addEntity(EntityType.MOOSHROOM, EntityType.COW);
    }
}
