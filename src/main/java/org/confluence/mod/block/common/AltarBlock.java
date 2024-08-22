package org.confluence.mod.block.common;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.items.ItemStackHandler;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.model.block.AltarBlockModel;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.hammer.Hammers;
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
/*todo: 祭坛配方与合成*/
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
        return pPlayer.getMainHandItem().is(Hammers.PWNHAMMER.get()) ? super.getDestroyProgress(pState, pPlayer, pLevel, pPos) : 0.0F;
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
        if (pPlayer instanceof ServerPlayer serverPlayer && pLevel.getBlockEntity(pPos) instanceof Entity entity) { // 放/取物品
            if (pPlayer.isCrouching()) { // 取物品
                pPlayer.setItemInHand(pHand, entity.takeItem(entity.lastActivatedSlot));
            } else { // 存物品
                pPlayer.setItemInHand(pHand, entity.addItem(pPlayer.getItemInHand(pHand)));
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) { // 合成
        if (event.getLevel().getBlockState(event.getPos()).getBlock() instanceof AltarBlock altarBlock) {

        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState).setVariant(variant);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state ->
            state.setAndContinue(RawAnimation.begin().thenLoop("default")))
            .triggerableAnim("crafting", RawAnimation.begin().thenPlay("crafting")));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return GeckoLibUtil.createInstanceCache(this);
    }

    public static class Entity extends BlockEntity implements GeoBlockEntity {
        private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
        private Variant variant;
        private transient int lastActivatedSlot;

        private final ItemStackHandler itemHandler = new ItemStackHandler(6); //5 Inputs and 1 Output.
        private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.empty();

        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.ALTAR_BLOCK_ENTITY.get(), pPos, pBlockState);
            this.lastActivatedSlot = -1;
            SingletonGeoAnimatable.registerSyncedAnimatable(this);
        }

        public ItemStack addItem(ItemStack toAdd) {
            int firstEmptySlot = -1;
            for (int i = 0; i < 5; i++) {
                ItemStack stack = itemHandler.getStackInSlot(i);
                if (firstEmptySlot == -1 && stack.isEmpty()) {
                    firstEmptySlot = i;
                }
                if (ItemStack.isSameItemSameTags(stack, toAdd)) {
                    this.lastActivatedSlot = i;
                    return itemHandler.insertItem(i, toAdd, false);
                }
            }
            if (firstEmptySlot != -1) {
                itemHandler.setStackInSlot(firstEmptySlot, toAdd);
                this.lastActivatedSlot = firstEmptySlot;
                return ItemStack.EMPTY;
            }
            this.lastActivatedSlot = -1;
            return ItemStack.EMPTY;
        }

        public ItemStack takeItem(int slot) {
            if (slot == -1) {
                for (int i = 0; i < 5; i++) {
                    if (!itemHandler.getStackInSlot(i).isEmpty()) {
                        return itemHandler.extractItem(i, 64, false);
                    }
                }
                return ItemStack.EMPTY;
            } else {
                ItemStack stack = itemHandler.getStackInSlot(slot).copy();
                itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
                return stack;
            }

//            SimpleContainer inventory = new SimpleContainer(1);
//            for (int i = 0; i < 5; i++) {
//                if (!itemHandler.getStackInSlot(i).getItem().equals(Items.AIR)) {
//                    inventory.setItem(0, itemHandler.getStackInSlot(i));
//                    if (level != null) {
//                        Containers.dropContents(level, worldPosition.below(-1), inventory);
//                        triggerAnim("controller", "crafting");
//                        if (getBlockState().is(ModBlocks.DEMON_ALTAR.get())) {
//                            level.sendParticles(ParticleTypes.SOUL,
//                                worldPosition.getX() + 0.5F,
//                                worldPosition.getY() + 0.75F,
//                                worldPosition.getZ() + 0.5F,
//                                500, 0.0625F, 0.0625F, 0.0625F, 0.15F);
//                        } else if (getBlockState().is(ModBlocks.CRIMSON_ALTAR.get())) {
//                            level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.defaultBlockState()),
//                                worldPosition.getX() + 0.5F,
//                                worldPosition.getY() + 0.75F,
//                                worldPosition.getZ() + 0.5F,
//                                500, 0F, 0.0625F, 0F, 0.25F);
//                        }
//                    }
//                    itemHandler.setStackInSlot(i, new ItemStack(Items.AIR));
//                    return;
//                }
//            }
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
            variant = Variant.byId(nbt.getInt("variant"));
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
                        renderer = new GeoItemRenderer<>(new GeoModel<>() {
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
