package org.confluence.mod.block.common;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.model.block.AltarBlockModel;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.IntFunction;

@SuppressWarnings("deprecation")
public class AltarBlock extends BaseEntityBlock implements CustomModel, CustomItemModel, GeoBlockEntity {
    public static final VoxelShape SHAPE = Shapes.box(-0.125, 0.0, -0.125, 1.125, 0.8, 1.125);
    private final Variant variant;

    public AltarBlock(Variant variant) {
        super(Properties.of().strength(3.0F, 18000.0F));
        this.variant = variant;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState pState, @NotNull Player pPlayer, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return 0.0F;
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public void playerDestroy(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable BlockEntity pBlockEntity, @NotNull ItemStack pTool) {
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
        if (pLevel instanceof ServerLevel serverLevel) {
            ConfluenceData data = ConfluenceData.get(serverLevel);
            if (data.increaseRevealStep(serverLevel)) {
                serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.translatable(
                    "event.confluence.reveal_step" + data.getRevealStep()
                ), false);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof Entity entity) {
            if (!pPlayer.isShiftKeyDown()) {
                if (entity.setItem(pPlayer.getItemInHand(pHand))) {
                    pPlayer.playSound(SoundEvents.WOLF_DEATH, 2F, 2.2F);
                    if (!pPlayer.isCreative()) {
                        pPlayer.setItemInHand(pHand, new ItemStack((pPlayer.getItemInHand(pHand).getItem()), 0));
                    }
                }
            } else {
                pPlayer.playSound(SoundEvents.GHAST_HURT, 1F, 1F);
                if (pLevel instanceof ServerLevel level) {
                    entity.takeItem(level);
                }
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState).setVariant(variant);
    }

    /**
     * Register your {@link AnimationController AnimationControllers} and their respective animations and conditions.
     * Override this method in your animatable object and add your controllers via {@link AnimatableManager.ControllerRegistrar#add ControllerRegistrar.add}.
     * You may add as many controllers as wanted.
     * <br><br>
     * Each controller can only play <u>one</u> animation at a time, and so animations that you intend to play concurrently should be handled in independent controllers.
     * Note having multiple animations playing via multiple controllers can override parts of one animation with another if both animations use the same bones or child bones.
     *
     * @param controllers The object to register your controller instances to
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state ->
                state.setAndContinue(RawAnimation.begin().thenLoop("default")))
                .triggerableAnim("crafting", RawAnimation.begin().thenPlay("crafting")));
    }

    /**
     * Each instance of a {@code GeoAnimatable} must return an instance of an {@link AnimatableInstanceCache}, which handles instance-specific animation info.
     * Generally speaking, you should create your cache using {@code GeckoLibUtil#createCache} and store it in your animatable instance, returning that cached instance when called.
     *
     * @return A cached instance of an {@code AnimatableInstanceCache}
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return GeckoLibUtil.createInstanceCache(this);
    }

    public static class Entity extends BlockEntity implements GeoBlockEntity {
        private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
        private Variant variant;

        private final ItemStackHandler itemHandler = new ItemStackHandler(6);   //5 Inputs and 1 Output.
        private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.empty();

        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.ALTAR_BLOCK_ENTITY.get(), pPos, pBlockState);
            SingletonGeoAnimatable.registerSyncedAnimatable(this);
        }

        public boolean setItem(ItemStack item) {
            for (int i = 0; i < 5; i++) {
                if (this.itemHandler.getStackInSlot(i).getItem().equals(Items.AIR) ||
                        ItemStack.isSameItemSameTags(this.itemHandler.getStackInSlot(i), item)) {
                    if (this.itemHandler.getStackInSlot(i).getCount() + item.getCount() <= this.itemHandler.getStackInSlot(i).getMaxStackSize()) {
                        this.itemHandler.insertItem(i, item, false);
                        return true;
                    }
                }
            }
            return false;
        }

        public void takeItem(ServerLevel level) {
            SimpleContainer inventory = new SimpleContainer(1);
/*            if (!this.itemHandler.getStackInSlot(5).getItem().equals(Items.AIR)){
                inventory.setItem(0, this.itemHandler.getStackInSlot(5));
                if (this.level != null) {
                    Containers.dropContents(this.level, this.worldPosition.below(-1), inventory);
                }
                this.itemHandler.setStackInSlot(5, new ItemStack(Items.AIR));
                return;
            }*/                 //take result item
            for (int i = 0; i < 5; i++) {
                if (!this.itemHandler.getStackInSlot(i).getItem().equals(Items.AIR)) {
                    inventory.setItem(0, this.itemHandler.getStackInSlot(i));
                    if (this.level != null) {
                        Containers.dropContents(this.level, this.worldPosition.below(-1), inventory);
                        triggerAnim("controller", "crafting");
                        if (this.getBlockState().is(ModBlocks.DEMON_ALTAR.get())) {
                            level.sendParticles(ParticleTypes.SOUL,
                                    this.worldPosition.getX() + 0.5F,
                                    this.worldPosition.getY() + 0.75F,
                                    this.worldPosition.getZ() + 0.5F,
                                    500, 0.0625F, 0.0625F, 0.0625F, 0.15F);
                        } else if (this.getBlockState().is(ModBlocks.CRIMSON_ALTAR.get())) {
                            level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.defaultBlockState()),
                                    this.worldPosition.getX() + 0.5F,
                                    this.worldPosition.getY() + 0.75F,
                                    this.worldPosition.getZ() + 0.5F,
                                    500, 0F, 0.0625F, 0F, 0.25F);
                        }
                    }
                    this.itemHandler.setStackInSlot(i, new ItemStack(Items.AIR));
                    return;
                }
            }
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
            if (cap == ForgeCapabilities.ITEM_HANDLER) {
                return lazyItemHandler.cast();
            }
            return super.getCapability(cap);
        }

        @Override
        public void onLoad() {
            super.onLoad();
            lazyItemHandler = LazyOptional.of(() -> itemHandler);
        }

        @Override
        public void invalidateCaps() {
            super.invalidateCaps();
            lazyItemHandler.invalidate();
        }

        Entity setVariant(Variant variant) {
            this.variant = variant;
            markUpdated();
            return this;
        }

        public Variant getVariant() {
            return variant;
        }

        @Override
        public void load(@NotNull CompoundTag nbt) {
            super.load(nbt);
            this.variant = Variant.byId(nbt.getInt("variant"));
            itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        }

        @Override
        protected void saveAdditional(@NotNull CompoundTag nbt) {
            super.saveAdditional(nbt);
            nbt.putInt("variant", variant.id);
            nbt.put("inventory", itemHandler.serializeNBT());
        }

        @Override
        public ClientboundBlockEntityDataPacket getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        @Override
        public @NotNull CompoundTag getUpdateTag() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("variant", variant.id);
            return nbt;
        }

        public void markUpdated() {
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), UPDATE_CLIENTS);
            }
        }

        @Override
        public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
            controllers.add(new AnimationController<>(this, "controller", state ->
                state.setAndContinue(RawAnimation.begin().thenLoop("default")))
                .triggerableAnim("crafting", RawAnimation.begin().thenPlay("crafting"))
            );
        }

        @Override
        public AnimatableInstanceCache getAnimatableInstanceCache() {
            return CACHE;
        }
    }

    public static class Item extends BlockItem implements GeoItem {
        private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

        public Item(AltarBlock pBlock) {
            super(pBlock, new Properties().rarity(ModRarity.PURPLE));
        }

        public Variant getVariant() {
            return ((AltarBlock) getBlock()).variant;
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
                            public ResourceLocation getModelResource(AltarBlock.Item animatable) {
                                return AltarBlockModel.MODELS[animatable.getVariant().getId()];
                            }

                            @Override
                            public ResourceLocation getTextureResource(AltarBlock.Item animatable) {
                                return AltarBlockModel.TEXTURES[animatable.getVariant().getId()];
                            }

                            @Override
                            public ResourceLocation getAnimationResource(AltarBlock.Item animatable) {
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

    public enum Variant implements StringRepresentable {
        DEMON(0, "demon"),
        CRIMSON(1, "crimson");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        final int id;
        private final String name;

        Variant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public static Variant byId(int pId) {
            return BY_ID.apply(pId);
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
