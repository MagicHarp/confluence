package org.confluence.mod.block.furniture.chair;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.model.block.WhitePlasticChairBlockModel;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.misc.ModRarity;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class WhitePlasticChairBlock extends Block implements CustomModel, CustomItemModel {
    public WhitePlasticChairBlock() {
        super(BlockBehaviour.Properties.of());
    }
    public static class Entity extends BlockEntity implements GeoBlockEntity {
        private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.WHITE_PLASTIC_CHAIR_ENTITY.get(), pPos, pBlockState);
        }

        @Override
        public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

        @Override
        public AnimatableInstanceCache getAnimatableInstanceCache() {
            return CACHE;
        }
    }
    public static class Item extends BlockItem implements GeoItem {
        private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

        public Item(WhitePlasticChairBlock pBlock) {
            super(pBlock, new Properties().rarity(ModRarity.BLUE));
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                private GeoItemRenderer<Item> renderer;

                @Override
                public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    if (renderer == null) {
                        this.renderer = new GeoItemRenderer<>(new GeoModel<>() {
                            @Override
                            public ResourceLocation getModelResource(Item animatable) {
                                return WhitePlasticChairBlockModel.MODEL;
                            }

                            @Override
                            public ResourceLocation getTextureResource(Item animatable) {
                                return WhitePlasticChairBlockModel.TEXTURE;
                            }

                            @Override
                            public ResourceLocation getAnimationResource(Item animatable) {
                                return null;
                            }
                        });
                    }
                    return renderer;
                }
            });
        }

        @Override
        public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

        @Override
        public AnimatableInstanceCache getAnimatableInstanceCache() {
            return CACHE;
        }
    }
}
