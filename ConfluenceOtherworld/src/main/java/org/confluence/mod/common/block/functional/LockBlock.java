package org.confluence.mod.common.block.functional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.confluence.mod.common.init.block.FunctionalBlocks;
import org.mesdag.portlib.wrapper.PortEnvironment;

import java.util.Iterator;
import java.util.Optional;

public class LockBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public LockBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.WEST));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof BEntity entity) {
            ItemStack stack = player.getItemInHand(hand);
            if (entity.matchTool.isEmpty() || entity.matchTool.get().matches(stack)) {
                if (level.destroyBlock(pos, false, player)) {
                    BlockPos relative = pos.relative(state.getValue(BlockStateProperties.FACING));
                    if (level.getBlockState(relative).getDestroySpeed(level, relative) != -1) {
                        level.destroyBlock(relative, false, player);
                        if (entity.consumeTool) {
                            stack.shrink(1);
                            return InteractionResult.CONSUME;
                        }
                        return InteractionResult.CONSUME_PARTIAL;
                    }
                }
            } else {
                entity.matchTool.get().tag.ifPresent(tag -> {
                    HolderSet<Item> holders = BuiltInRegistries.ITEM.getOrCreateTag(tag);
                    MutableComponent itemsNeed = Component.translatable("message.confluence.lock.need");
                    Component or = Component.translatable("message.confluence.lock.or");
                    Iterator<Holder<Item>> iterator = holders.iterator();
                    while (iterator.hasNext()) {
                        itemsNeed.append(iterator.next().value().getDescription());
                        if (iterator.hasNext()) itemsNeed.append(or);
                    }
                    player.displayClientMessage(itemsNeed, true);
                });
                entity.matchTool.get().items.ifPresent(items -> {
                    MutableComponent itemsNeed = Component.translatable("message.confluence.lock.need");
                    Component or = Component.translatable("message.confluence.lock.or");
                    Iterator<Holder<Item>> iterator = items.iterator();
                    while (iterator.hasNext()) {
                        itemsNeed.append(iterator.next().value().getDescription());
                        if (iterator.hasNext()) itemsNeed.append(or);
                    }
                    player.displayClientMessage(itemsNeed, true);
                });
                return InteractionResult.PASS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    public static class BEntity extends BlockEntity {
        private Optional<MatchTool> matchTool = Optional.empty();
        private boolean consumeTool = false;

        public BEntity(BlockPos pos, BlockState blockState) {
            super(FunctionalBlocks.LOCK_BLOCK_ENTITY.get(), pos, blockState);
        }

        @Override
        public void load(CompoundTag tag) {
            super.load(tag);
            this.matchTool = MatchTool.CODEC.parse(PortEnvironment.registryAccess().createSerializationContext(NbtOps.INSTANCE), tag.get("MatchTool")).result();
            this.consumeTool = tag.getBoolean("ConsumeTool");
        }

        @Override
        protected void saveAdditional(CompoundTag tag) {
            super.saveAdditional(tag);
            matchTool.flatMap(predicate -> MatchTool.CODEC.encodeStart(PortEnvironment.registryAccess().createSerializationContext(NbtOps.INSTANCE), predicate).result()).ifPresent(nbt -> tag.put("MatchTool", nbt));
            tag.putBoolean("ConsumeTool", consumeTool);
        }
    }

    record MatchTool(Optional<HolderSet<Item>> items, Optional<TagKey<Item>> tag) {
        static final Codec<MatchTool> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RegistryCodecs.homogeneousList(Registries.ITEM).optionalFieldOf("items").forGetter(MatchTool::items),
                TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(MatchTool::tag)
        ).apply(instance, MatchTool::new));

        public boolean matches(ItemStack stack) {
            return (items.isEmpty() || items.get().contains(stack.getItemHolder())) && (tag.isEmpty() || stack.is(tag.get()));
        }
    }
}
