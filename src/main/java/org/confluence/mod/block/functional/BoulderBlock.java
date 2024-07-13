package org.confluence.mod.block.functional;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.entity.projectile.BoulderEntity;
import org.confluence.mod.util.EnumRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;


public class BoulderBlock extends AbstractMechanicalBlock implements CustomModel, CustomItemModel {
    public static final ResourceLocation NORMAL = new ResourceLocation(Confluence.MODID, "boulder");

    public BoulderBlock() {
        super(Properties.of());
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public void playerDestroy(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable BlockEntity pBlockEntity, @NotNull ItemStack pTool) {
        summon(pLevel, pPos, entity -> pPlayer);
    }

    @Override
    public void wasExploded(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Explosion pExplosion) {
        summon(pLevel, pPos, entity -> pLevel.getNearestPlayer(entity, BoulderEntity.SEARCH_RANGE));
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public void neighborChanged(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pNeighborBlock, @NotNull BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (!pLevel.isClientSide) {
            if (pLevel.hasNeighborSignal(pPos)) {
                execute(pState, (ServerLevel) pLevel, pPos, true);
            } else {
                BlockState below = pLevel.getBlockState(pPos.below());
                if (below.isAir()) onExecute(pState, (ServerLevel) pLevel, pPos);
            }
        }
    }

    @Override
    public void onExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        pLevel.removeBlock(pPos, false);
        summon(pLevel, pPos, entity -> pLevel.getNearestPlayer(entity, BoulderEntity.SEARCH_RANGE));
    }

    public static void summon(Level level, BlockPos pos, Function<BoulderEntity, Player> function) {
        Vec3 position = new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        BoulderEntity entity = new BoulderEntity(level, position);
        if (level.getBlockState(pos.below()).isAir()) {
            entity.getEntityData().set(BoulderEntity.isVertical,true);
        }else {
            entity.targetTo(function.apply(entity));
            entity.getEntityData().set(BoulderEntity.isVertical,false);
        }
        level.addFreshEntity(entity);
    }

    public enum Variant implements EnumRegister<BoulderBlock>, StringRepresentable {
        NORMAL(0, "boulder", BoulderBlock::new);

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        public static final Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);
        private final int id;
        private final String name;
        private final RegistryObject<BoulderBlock> value;

        Variant(int id, String name, Supplier<BoulderBlock> supplier) {
            this.id = id;
            this.name = name;
            this.value = ModBlocks.registerWithItem(name, supplier);
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

        @Override
        public RegistryObject<BoulderBlock> getValue() {
            return value;
        }

        public static void init() {}
    }
}
