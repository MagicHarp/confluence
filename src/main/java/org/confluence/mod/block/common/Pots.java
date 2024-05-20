package org.confluence.mod.block.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.minecraft.world.level.block.Block.box;

public enum Pots implements EnumRegister<Pots.BasePotsBlock> {
    FOREST_POTS("forest_pots", 0.002F),
    TUNDRA_POTS("tundra_pots", 0.002167F),
    SPIDER_NEST_POTS("spider_nest_pots", 0.003676F),
    UNDERGROUND_DESERT_POTS("underground_desert_pots", 0.002169F, Shapes.or(box(4, 0, 4, 12, 1, 12), box(4, 10, 4, 12, 11, 12), box(4, 17, 4, 12, 19, 12), box(5, 11, 5, 11, 17, 11), box(3, 1, 3, 13, 3, 13), box(3, 8, 3, 13, 10, 13), box(2, 3, 2, 14, 8, 14))),
    JUNGLE_POTS("jungle_pots", 0.0025F, Shapes.or(box(3, 1, 3, 13, 13, 13), box(4, 14, 4, 12, 15, 12), box(5, 13, 5, 11, 14, 11), box(4, 0, 4, 12, 1, 12))),
    MARBLE_CAVE_POTS("marble_cave_pots", 0.002667F, Shapes.or((box(4, 2, 4, 12, 3, 12)), box(5, 1, 5, 11, 2, 11), box(5, 12, 5, 11, 13, 11), box(4, 11, 4, 12, 12, 12), box(4, 13, 4, 12, 14, 12), box(4, 0, 4, 12, 1, 12), box(3, 3, 3, 13, 4, 13), box(2, 4, 2, 14, 10, 14), box(3, 10, 3, 13, 11, 13))),
    ANOTHER_CRIMSON_POTS("another_crimson_pots", 0.00274F, Shapes.or(box(4, 0, 4, 12, 1, 12), box(5, 11, 5, 11, 12, 11), box(4, 3, 4, 12, 11, 12), box(5, 1, 5, 11, 3, 11))),
    PYRAMID_POTS("pyramid_pots", 0.008F, Shapes.or(box(2, 2, 2, 14, 13, 14), box(3, 1, 3, 13, 2, 13), box(2, 0, 2, 14, 1, 14), box(3, 13, 3, 13, 15, 13), box(2, 15, 2, 14, 17, 14))),
    CORRUPTION_POTS("corruption_pots", 0.00274F, Shapes.or(box(3, 0, 3, 13, 1, 13), box(4, 1, 4, 12, 3, 12), box(3, 3, 3, 13, 12, 13), box(2, 12, 2, 14, 14, 14))),
    DUNGEON_POTS("dungeon_pots", 0.002604F, Shapes.or(box(3, 0, 3, 13, 6, 13), box(3, 15, 3, 13, 16, 13), box(2, 6, 2, 14, 15, 14))),
    UNDERWORLD_POTS("underworld_pots", 0.00274F),
    LIHZAHRD_POTS("lihzahrd_pots", 0.004F, Shapes.or(box(3, 0, 3, 13, 1, 13), box(4, 1, 4, 12, 3, 12), box(3, 3, 3, 13, 12, 13), box(2, 12, 2, 14, 14, 14)));

    private final RegistryObject<BasePotsBlock> value;

    Pots(String id, float moneyHoleChance, VoxelShape voxelShape) {
        this.value = ModBlocks.registerWithItem(id, () -> new BasePotsBlock(moneyHoleChance, voxelShape));
    }

    Pots(String id, float moneyHoleChance) {
        this.value = ModBlocks.registerWithItem(id, () -> new BasePotsBlock(moneyHoleChance, Shapes.or(box(3, 1, 3, 13, 10, 13), box(4, 11, 4, 12, 12, 12), box(5, 10, 5, 11, 11, 11), box(4, 0, 4, 12, 1, 12))));
    }

    @Override
    public RegistryObject<BasePotsBlock> getValue() {
        return value;
    }

    public static void init() {}

    public static class BasePotsBlock extends HorizontalDirectionalBlock implements CustomModel {
        private final VoxelShape voxelShape;
        private final float moneyHoleChance;

        public BasePotsBlock(float moneyHoleChance, VoxelShape voxelShape) {
            super(Properties.of().sound(SoundType.DECORATED_POT).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
            this.voxelShape = voxelShape;
            this.moneyHoleChance = moneyHoleChance;
            registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING);
        }

        @Override
        @Nullable
        public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
            return defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
        }

        @Override
        public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
            return voxelShape;
        }

        @Override
        public void playerDestroy(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable BlockEntity pBlockEntity, @NotNull ItemStack pTool) {
            pPlayer.awardStat(Stats.BLOCK_MINED.get(this));
            pPlayer.causeFoodExhaustion(0.005F);
            dropSequence(pLevel, pPos, pPlayer);
        }

        @Override
        public void wasExploded(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Explosion pExplosion) {
            dropSequence(pLevel, pPos, null);
        }

        @Override
        public void onProjectileHit(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockHitResult pHit, @NotNull Projectile pProjectile) {
            BlockPos blockPos = pHit.getBlockPos();
            Entity entity = pProjectile.getOwner();
            if (pLevel.destroyBlock(blockPos, true, entity)) {
                Player player = null;
                if (entity instanceof Player player1) {
                    player = player1;
                    player.awardStat(Stats.BLOCK_MINED.get(this));
                }
                dropSequence(pLevel, blockPos, player);
            }
        }

        private void dropSequence(Level level, BlockPos blockPos, @Nullable Player player) {
            if (level.isClientSide) return;
            // 钱币传送门生成的几率取决于罐子的类型以及最接近它的玩家的运气。若生成，则流程结束。
            // 如果罐子位于天然地牢墙前方且低于地表地层，有 1/35 (2.86%) 的几率掉落金钥匙。若掉落，则流程结束。
            // 如果玩家正在游玩秘密世界种子 for the worthy，有 1/4 (25%) 的几率掉落一个点燃的炸弹。若掉落，则流程结束。
            // 1/45 (2.22%)（2/45 (4.44%)）的几率掉落一瓶药水。若掉落，则流程结束。
            if (level.players().size() > 1 && level.random.nextFloat() < 0.0333F) {
                // 生成虫洞药水
                return;
            }
            switch (level.random.nextInt(0, 7)) {
                case 0 -> dropHeart(level, blockPos, player);
                case 1 -> dropTorch(level, blockPos, player);
                case 2 -> dropAmmo(level, blockPos, player);
                case 3 -> dropHeal(level, blockPos, player);
                case 4 -> dropBomb(level, blockPos, player);
                case 5 -> dropRope(level, blockPos, player);
                case 6 -> dropMoney(level, blockPos, player);
            }
        }

        private void dropHeart(Level level, BlockPos blockPos, @Nullable Player player) {
            Vec3 center = blockPos.getCenter();
            Optional<? extends Player> optional = level.players().stream().min((a, b) -> (int) (a.distanceToSqr(center) - b.distanceToSqr(center)));
            if (optional.isPresent()) {
                Player player1 = optional.get();
                if (player1.getHealth() < player1.getMaxHealth()) {
                    int amount = 1;
                    if (level.random.nextBoolean()) amount++;
                    /* 在专家模式中，有 1/8 的几率掉落 1 个心，3/8 的几率掉落 2 个心，3/8 的几率掉落 3 个心，以及 1/8 的几率掉落 4 个心。*/
                    ModUtils.createItemEntity(ModItems.HEART.get(), amount, center.x, center.y, center.z, level);
                } else if (player1.getInventory().hasAnyMatching(itemStack -> itemStack.getCount() < 20 && itemStack.is(Items.TORCH))) {
                    dropTorch(level, blockPos, player);
                } else {
                    dropMoney(level, blockPos, player);
                }
            }
        }

        private void dropTorch(Level level, BlockPos blockPos, @Nullable Player player) {
            // 掉落火把。注意如果罐子在液体中，火把会被 荧光棒 代替；如果罐子是苔原罐子，则会掉落粘性荧光棒。
        }

        private void dropAmmo(Level level, BlockPos blockPos, @Nullable Player player) {
            // 掉落 10-20 个弹药。默认类型为 木箭。在地表层或地下层时，它有 50% 的几率被手里剑手里剑（困难模式之前） / 手榴弹（困难模式）替代。
            // 然后，如果位于地狱，它会被狱炎箭替代，否则，在困难模式中，它会被邪箭或银子弹（在包含银的世界中）/ 钨子弹（在包含钨的世界中）（箭或子弹的几率各为 50%）。
        }

        private void dropHeal(Level level, BlockPos blockPos, @Nullable Player player) {
            // 掉落 1 个弱效治疗药水或治疗药水（在地狱中或困难模式下）。在专家模式，有 1/3 的几率额外掉落 1 个。
        }

        private void dropBomb(Level level, BlockPos blockPos, @Nullable Player player) {
            // 如果罐子是沙漠罐子，掉落 1-4 （1-7）个甲虫炸弹。
            // 否则，如果罐子在地下层或以下，掉落 1-4 （1-7）个普通炸弹。
            // 若以上条件都不满足，以下方所示的情况掉落绳。
        }

        private void dropRope(Level level, BlockPos blockPos, @Nullable Player player) {
            // 如果罐子不在地狱层，且处于困难模式之前，掉落 20-40 个绳。
            // 否则，以下方所示的情况掉落钱币。
        }

        private void dropMoney(Level level, BlockPos blockPos, @Nullable Player player) {
            // 和所有其它尝试失败时：罐子会掉落钱币。掉落量会极大地受位置和类型影响。所有罐子会至少掉落 80  到 360 ，然后游戏会施加下列奖励以得出总掉落量：
        }
    }
}
