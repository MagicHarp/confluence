package org.confluence.mod.block.functional;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.model.block.ExtractinatorBlockModel;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.misc.ModLootTables;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class ExtractinatorBlock extends HorizontalDirectionalBlock implements EntityBlock, CustomModel, CustomItemModel {
    public static final EnumProperty<ExtractinatorPart> PART = EnumProperty.create("part", ExtractinatorPart.class);
    private static final VoxelShape BASE_SHAPE_SOUTH = box(3.0, 0.0, 3.0, 16.0, 16.0, 13.0);
    private static final VoxelShape BASE_SHAPE_WEST = box(3.0, 0.0, 3.0, 13.0, 16.0, 16.0);
    private static final VoxelShape BASE_SHAPE_NORTH = box(0.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape BASE_SHAPE_EAST = box(3.0, 0.0, 0.0, 13.0, 16.0, 13.0);
    private static final VoxelShape RIGHT_SHAPE_SOUTH = box(0.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape RIGHT_SHAPE_WEST = box(3.0, 0.0, 0.0, 13.0, 16.0, 13.0);
    private static final VoxelShape RIGHT_SHAPE_NORTH = box(3.0, 0.0, 3.0, 16.0, 16.0, 13.0);
    private static final VoxelShape RIGHT_SHAPE_EAST = box(3.0, 0.0, 3.0, 13.0, 16.0, 16.0);
    private static final VoxelShape[] BASE_SHAPES = new VoxelShape[]{BASE_SHAPE_SOUTH, BASE_SHAPE_WEST, BASE_SHAPE_NORTH, BASE_SHAPE_EAST};
    private static final VoxelShape[] RIGHT_SHAPES = new VoxelShape[]{RIGHT_SHAPE_SOUTH, RIGHT_SHAPE_WEST, RIGHT_SHAPE_NORTH, RIGHT_SHAPE_EAST};

    public ExtractinatorBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(stateDefinition.any().setValue(PART, ExtractinatorPart.BASE).setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        int index = pState.getValue(FACING).get2DDataValue();
        return pState.getValue(PART) == ExtractinatorPart.BASE ? BASE_SHAPES[index] : RIGHT_SHAPES[index];
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide) {
            BlockPos relativePos = pPos.relative(getConnectedDirection(pState));
            pLevel.setBlockAndUpdate(relativePos, defaultBlockState().setValue(PART, ExtractinatorPart.RIGHT).setValue(FACING, pState.getValue(FACING)));
        }
    }

    /**
     * 获取与该方块相连的多方块的相对方向
     * <p>
     * 注：是以玩家视角看向的相对方向
     *
     * @param blockState 该方块的方块状态
     * @return 相对方向
     */
    public static Direction getConnectedDirection(BlockState blockState) {
        Direction facing = blockState.getValue(FACING);
        return switch (blockState.getValue(PART)) {
            case BASE -> facing.getCounterClockWise(); // 获取其相对右边
            case RIGHT -> facing.getClockWise(); // 获取其相对左边
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockState blockState = defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
        BlockPos relativePos = pContext.getClickedPos().relative(getConnectedDirection(blockState));
        return level.getBlockState(relativePos).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(relativePos) ? blockState : null;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        pLevel.setBlockAndUpdate(pPos.relative(getConnectedDirection(pState)), Blocks.AIR.defaultBlockState());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PART, FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ExtractinatorBlock.Entity(pPos, pState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pLevel instanceof ServerLevel level) {
            ItemStack item = pPlayer.getItemInHand(pHand);
            ResourceLocation path;
            Block block;
            if (item.is(ModTags.Items.DESERT_FOSSIL)) {
                path = ModLootTables.EXTRACT_DESERT_FOSSIL;
                block = ModBlocks.DESERT_FOSSIL.get();
            } else if (item.is(ModTags.Items.GRAVEL)) {
                path = ModLootTables.EXTRACT_GRAVEL;
                block = Blocks.GRAVEL;
            } else if (item.is(ModTags.Items.JUNK)) {
                path = ModLootTables.EXTRACT_JUNK;
                block = Blocks.LILY_PAD;
            } else if (item.is(ModTags.Items.SLUSH)) {
                path = ModLootTables.EXTRACT_SLUSH;
                block = ModBlocks.SLUSH.get();
            } else if (item.is(ModTags.Items.MARINE_GRAVEL)) {
                path = ModLootTables.EXTRACT_MARINE_GRAVEL;
                block = ModBlocks.MARINE_GRAVEL.get();
            } else {
                return InteractionResult.PASS;
            }

            level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, block.defaultBlockState()),
                pPos.getX() + 0.5F,
                pPos.getY() + 0.75F,
                pPos.getZ() + 0.5F,
                100, 0F, 0.0625F, 0F, 0.25F);

            LootTable lootTable = level.getServer().getLootData().getLootTable(path);
            LootParams lootparams = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, pPlayer.position())
                .withParameter(LootContextParams.THIS_ENTITY, pPlayer)
                .create(LootContextParamSets.GIFT);
            for (ItemStack loot : lootTable.getRandomItems(lootparams)) {
                ModUtils.createItemEntity(loot, pPos.getX() + 0.5, pPos.getY() + 1.0, pPos.getZ() + 0.5, level);
            }

            pPlayer.getItemInHand(pHand).setCount(item.getCount() - 1);
        }
        return InteractionResult.SUCCESS;
    }

    public static class Entity extends BlockEntity implements GeoBlockEntity {
        private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.EXTRACTINATOR_ENTITY.get(), pPos, pBlockState);
        }

        @Override
        public AABB getRenderBoundingBox() { // 使其能在屏幕外渲染
            return INFINITE_EXTENT_AABB;
        }

        @Override
        public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
            controllers.add(new AnimationController<>(this, "controller", state ->
                state.setAndContinue(RawAnimation.begin().thenLoop("default")))
            );
        }

        @Override
        public AnimatableInstanceCache getAnimatableInstanceCache() {
            return CACHE;
        }
    }

    public static class Item extends BlockItem implements GeoItem {
        private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

        public Item(ExtractinatorBlock pBlock) {
            super(pBlock, new Properties().rarity(ModRarity.WHITE).stacksTo(1));
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
                                return ExtractinatorBlockModel.MODEL;
                            }

                            @Override
                            public ResourceLocation getTextureResource(Item animatable) {
                                return ExtractinatorBlockModel.TEXTURE;
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

    public enum ExtractinatorPart implements StringRepresentable {
        BASE("base"),
        RIGHT("right");

        private final String name;

        ExtractinatorPart(String pName) {
            this.name = pName;
        }

        public String toString() {
            return name;
        }

        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
