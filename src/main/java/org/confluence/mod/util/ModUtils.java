package org.confluence.mod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;

import java.util.Calendar;
import java.util.List;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public final class ModUtils {
    public static float nextFloat(RandomSource randomSource, float origin, float bound) {
        if (origin >= bound) {
            throw new IllegalArgumentException("bound - origin is non positive");
        } else {
            return origin + randomSource.nextFloat() * (bound - origin);
        }
    }

    public static double nextDouble(RandomSource randomSource, double origin, double bound) {
        if (origin >= bound) {
            throw new IllegalArgumentException("bound - origin is non positive");
        } else {
            return origin + randomSource.nextDouble() * (bound - origin);
        }
    }

    public static void createItemEntity(ItemStack itemStack, double x, double y, double z, Level level) {
        createItemEntity(itemStack, x, y, z, level, 40);
    }

    public static void createItemEntity(List<ItemStack> itemStacks, double x, double y, double z, Level level) {
        for (ItemStack itemStack : itemStacks){
            createItemEntity(itemStack, x, y, z, level, 40);
        }
    }

        public static void createItemEntity(ItemStack itemStack, double x, double y, double z, Level level, int pickUpDelay) {
        ItemEntity itemEntity = new ItemEntity(level, x, y, z, itemStack);
        itemEntity.setPickUpDelay(pickUpDelay);
        level.addFreshEntity(itemEntity);
    }

    public static void createItemEntity(Item item, int count, double x, double y, double z, Level level) {
        createItemEntity(item, count, x, y, z, level, 40);
    }

    public static void createItemEntity(Item item, int count, double x, double y, double z, Level level, int pickUpDelay) {
        if (count <= 0) return;
        ItemEntity itemEntity = new ItemEntity(level, x, y, z, new ItemStack(item, count));
        itemEntity.setPickUpDelay(pickUpDelay);
        level.addFreshEntity(itemEntity);
    }

    public static void createItemEntity(ItemStack itemStack, Vec3 vec, Level level) {
        createItemEntity(itemStack, vec.x, vec.y, vec.z, level, 40);
    }

    public static void dropMoney(int amount, double x, double y, double z, Level level) {
        int copper_count = amount % 9;
        int i = ((amount - copper_count) / 9);
        int silver_count = i % 9;
        int j = ((i - silver_count) / 9);
        int golden_count = j % 9;
        int k = (j - golden_count) / 9;
        createItemEntity(ModItems.COPPER_COIN.get(), copper_count, x, y, z, level, 0);
        createItemEntity(ModItems.SILVER_COIN.get(), silver_count, x, y, z, level, 0);
        createItemEntity(ModItems.GOLDEN_COIN.get(), golden_count, x, y, z, level, 0);
        createItemEntity(ModItems.PLATINUM_COIN.get(), k, x, y, z, level, 0);
    }

    public static Component getModifierTooltip(double amount, String type) {
        boolean b = amount > 0.0;
        amount *= 100.0;
        return Component.translatable(
            "prefix.confluence.tooltip." + (b ? "plus" : "take"),
            ATTRIBUTE_MODIFIER_FORMAT.format(b ? amount : -amount),
            Component.translatable("prefix.confluence.tooltip." + type)
        ).withStyle(b ? ChatFormatting.BLUE : ChatFormatting.RED);
    }

    @SuppressWarnings("unchecked")
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> getTicker(BlockEntityType<A> a, BlockEntityType<E> b, BlockEntityTicker<? super E> ticker) {
        return a == b ? (BlockEntityTicker<A>) ticker : null;
    }

    public static boolean isHalloween() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) == Calendar.OCTOBER && calendar.get(Calendar.DATE) >= 15) || // 从 十月中旬
            (calendar.get(Calendar.MONTH) == Calendar.NOVEMBER && calendar.get(Calendar.DATE) <= 15); // 到 十一月中旬
    }

    /**
     * 把向量转成角度
     */
    public static float[] dirToRot(Vec3 vec) {
        double x = vec.x;
        double y = vec.y;
        double z = vec.z;

        double yaw = Math.toDegrees(Mth.atan2(-x, z));
        double pitch = Math.toDegrees(Mth.atan2(-y, Math.sqrt(x * x + z * z)));

        return new float[]{(float) yaw, (float) pitch};
    }

    /**
     * 把角度转成向量
     *
     * @param yaw   角度的yaw，单位为角度而非弧度
     * @param pitch 角度的pitch，单位为角度
     * @return 返回朝向对应角度（yaw、pitch）的单位向量
     * */
    public static Vec3 rotToDir(float yaw, float pitch){
        float yawRad = (float) Math.toRadians(yaw);
        float pitchRad = (float) Math.toRadians(pitch);
        // Mth类的三角函数优化较好
        double y = -1 * Mth.sin(pitchRad);
        double div = Mth.cos(pitchRad);
        double x = -1 * Mth.sin(yawRad);
        double z = Mth.cos(yawRad);
        x *= div;
        z *= div;
        return new Vec3(x, y, z);
    }

    /**
     * 更新实体朝向
     */
    public static void updateEntityRotation(Entity entity, Vec3 dir) {
        float[] angle = dirToRot(dir);
        entity.setYRot(angle[0]);
        entity.setXRot(angle[1]);
    }

    /**
     * 获得两个位置之间的方向向量；若两点重合则默认返回向上的向量
     * 若要自定义默认返回的向量，请在length后传入一个默认向量
     * @param start 开始位置的位置向量
     * @param end 结束位置的位置向量
     * @param length 返回向量的长度
     * */
    public static Vec3 getDirection(Vec3 start, Vec3 end, double length) {
        return getDirection(start, end, length, new Vec3(0, length, 0));
    }
    /**
     * 获得两个位置之间的方向向量；若两点重合则默认返回的向量
     * @param start 开始位置的位置向量
     * @param end 结束位置的位置向量
     * @param length 返回向量的长度
     * @param defaultVec 两点重合时返回的默认向量（注：直接原样返回，不会判定该向量的长度）
     * */
    public static Vec3 getDirection(Vec3 start, Vec3 end, double length, Vec3 defaultVec) {
        return getDirection(start, end, length, defaultVec, false);
    }
    /**
     * 获得两个位置之间的方向向量
     * 若preserveShorterVectors为true且两点之间的距离小于length则不会改变向量长度
     * @param start 开始位置的位置向量
     * @param end 结束位置的位置向量
     * @param length 返回向量的长度
     * @param defaultVec 两点重合时返回的默认向量（注：直接原样返回，不会判定该向量的长度）
     * @param preserveShorterVectors 若向量比length短，是否保留原向量
     * */
    public static Vec3 getDirection(Vec3 start, Vec3 end, double length,
                                    Vec3 defaultVec, boolean preserveShorterVectors) {
        Vec3 result = end.subtract(start);
        double distSqr = result.lengthSqr();
        // 此时直接返回比length更短的向量
        if (preserveShorterVectors && distSqr <= length * length) {
            return result;
        }
        // 向量长度重设为length

        // 两点之间过近
        if (distSqr < 1e-9) {
            return defaultVec;
        }
        result.scale(length / Math.sqrt(distSqr));
        return result;
    }

    /**
     * 测试信息；使用此接口有助于集中管理防止漏网之鱼
     */
    public static void testMessage(String msg) {
        Confluence.LOGGER.info(msg);
    }

    public static void testMessage(Player player, String msg) {
        player.sendSystemMessage(Component.literal(msg));
    }

    public static void testMessage(Level level, String msg) {
        for (Player ply : level.players())
            ply.sendSystemMessage(Component.literal(msg));
    }

    /**为专家?在处理if...else if时应先使用isMaster*/
    public static boolean isExpert(Level level) {
        return level.getDifficulty().equals(Difficulty.NORMAL) || level.getDifficulty().equals(Difficulty.HARD);
    }

    /**为大师?在处理if...else if时应先使用此方法*/
    public static boolean isMaster(Level level) {
        return level.getDifficulty().equals(Difficulty.HARD);
    }

    /**
     * 获得从实体A到实体B的单位向量，即A→B
     * @param a 实体A
     * @param b 实体B
     * @return A→B的单位向量
     */
    public static Vec3 getVectorA2B(Entity a, Entity b) {
        return b.position().subtract(a.position()).normalize();
    }

    /**
     * 给予实体B一个击退动量，方向为A→B
     * @param a 实体A
     * @param b 实体B
     * @param scale 击退动量的缩放
     * @param motionY 击退的Y轴动量
     */
    public static void knockBackA2B(Entity a, Entity b, double scale, double motionY) {
        b.addDeltaMovement(getVectorA2B(a, b).scale(scale).add(0.0, motionY, 0.0));
    }
}
