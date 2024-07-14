package org.confluence.mod.block.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;
import java.util.function.Supplier;

public class BaseChestBlock extends ChestBlock implements CustomModel, CustomItemModel, CustomName {
    public BaseChestBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CHEST), ModBlocks.BASE_CHEST_BLOCK_ENTITY::get);
    }

    public BaseChestBlock(Properties properties, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(properties, supplier);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState);
    }

    @Override
    public void setPlacedBy(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull LivingEntity pPlacer, @NotNull ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        CompoundTag tag = pStack.getTag();
        if (tag == null) return;
        if (pLevel.getBlockEntity(pPos) instanceof Entity entity) {
            entity.variant = Variant.byId(tag.getInt("VariantId"));
        }
    }

    @Nullable
    @Override
    protected Direction candidatePartnerFacing(BlockPlaceContext pContext, @NotNull Direction pDirection) {
        BlockPos relative = pContext.getClickedPos().relative(pDirection);
        if (pContext.getLevel().getBlockEntity(relative) instanceof Entity entity) {
            ItemStack itemStack = pContext.getItemInHand();
            if (itemStack.getTag() == null || Variant.byId(itemStack.getTag().getInt("VariantId")) != entity.variant)
                return null;

            BlockState blockstate = pContext.getLevel().getBlockState(relative);
            return blockstate.is(this) && blockstate.getValue(TYPE) == ChestType.SINGLE ? blockstate.getValue(FACING) : null;
        }
        return null;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof Entity entity && entity.isLocked()) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            boolean isShadow = itemStack.is(ModItems.SHADOW_KEY.get());
            if ((entity.variant == Variant.LOCKED_SHADOW && isShadow) || itemStack.is(ModItems.GOLDEN_KEY.get())) {
                int unlock = entity.variant.unlock;
                if (unlock > 0) {
                    if (!isShadow && !pPlayer.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    entity.variant = Variant.byId(unlock);
                    MutableComponent name = Component.translatable("block.confluence.base_chest_block." + entity.variant.name);
                    entity.setCustomName(name);
                    Direction relativeDir = ChestBlock.getConnectedDirection(pState);
                    boolean isDouble = false;
                    if (pState.getValue(TYPE) != ChestType.SINGLE && pLevel.getBlockEntity(pPos.relative(relativeDir)) instanceof Entity entity1) {
                        entity1.variant = entity.variant;
                        entity1.setCustomName(name);
                        isDouble = true;
                    }
                    if (pLevel instanceof ServerLevel serverLevel) {
                        serverLevel.playSound(null, pPos, SoundEvents.CHAIN_BREAK, SoundSource.BLOCKS);
                        double posX = pPos.getX() + 0.5;
                        double posZ = pPos.getZ() + 0.5;
                        if (isDouble) {
                            posX += relativeDir.getStepX() * 0.5;
                            posZ += relativeDir.getStepZ() * 0.5;
                        }
                        serverLevel.sendParticles(
                            new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CHAIN.defaultBlockState()),
                            posX, pPos.getY() + 0.5, posZ, 200, 0.0625, 0.0625, 0.0625, 0.15
                        );
                    }
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack itemStack = new ItemStack(this);
        if (level.getBlockEntity(pos) instanceof Entity entity) {
            return setData(itemStack, entity.variant);
        }
        return itemStack;
    }

    public static ItemStack setData(ItemStack itemStack, Variant variant) {
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putInt("VariantId", variant.id);
        itemStack.setHoverName(Component.translatable("block.confluence.base_chest_block." + variant.name));
        return itemStack;
    }

    @Override
    public @Nullable String getGenName() {
        return null;
    }

    public static class Entity extends ChestBlockEntity {
        public Variant variant = Variant.LOCKED_GOLDEN;

        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.BASE_CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
        }

        public Entity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
            super(pType, pPos, pBlockState);
        }

        public boolean isLocked() {
            return variant == Variant.LOCKED_GOLDEN;
        }

        @Override
        public boolean canOpen(@NotNull Player pPlayer) {
            return !isLocked() && super.canOpen(pPlayer);
        }

        @Override
        public void load(@NotNull CompoundTag pTag) {
            super.load(pTag);
            this.variant = Variant.byId(pTag.getInt("VariantId"));
        }

        @Override
        protected void saveAdditional(@NotNull CompoundTag pTag) {
            super.saveAdditional(pTag);
            pTag.putInt("VariantId", variant.id);
        }

        @Override
        public ClientboundBlockEntityDataPacket getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        @Override
        public @NotNull CompoundTag getUpdateTag() {
            CompoundTag nbt = super.getUpdateTag();
            nbt.putInt("VariantId", variant.id);
            return nbt;
        }
    }

    public enum Variant implements StringRepresentable { // 对于死人箱，只使用unlocked开头的
        LOCKED_GOLDEN(0, "locked_golden", 1),
        UNLOCKED_GOLDEN(1, "unlocked_golden"),
        LOCKED_SHADOW(2, "locked_shadow", 3),
        UNLOCKED_SHADOW(3, "unlocked_shadow");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        private final int id;
        private final String name;
        private final int unlock;

        Variant(int id, String name, int unlock) {
            this.id = id;
            this.name = name;
            this.unlock = unlock;
        }

        Variant(int id, String name) {
            this(id, name, -1);
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
