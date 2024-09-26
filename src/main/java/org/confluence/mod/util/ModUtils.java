package org.confluence.mod.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.client.color.FloatRGBA;
import org.confluence.mod.item.ModItems;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Calendar;
import java.util.List;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public final class ModUtils {
    public static final Direction[] DIRECTIONS = Direction.values();
    public static final Direction[] HORIZONTAL = new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH};

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
        for (ItemStack itemStack : itemStacks) {
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
     */
    public static Vec3 rotToDir(float yaw, float pitch) {
        float yawRad = (float) Math.toRadians(yaw);
        float pitchRad = (float) Math.toRadians(pitch);
        // Mth类的三角函数优化较好
        double y = -1 * Mth.sin(pitchRad);
        double div = Mth.cos(pitchRad);
        double x = -1 * Mth.sin(yawRad);
        double z = Mth.cos(yawRad);
        x *= div;
        z *= div;
        return new Vec3(x, y, z); // Vec3.directionFromRotation(pitch, yaw);
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
     *
     * @param start  开始位置的位置向量
     * @param end    结束位置的位置向量
     * @param length 返回向量的长度
     */
    public static Vec3 getDirection(Vec3 start, Vec3 end, double length) {
        return getDirection(start, end, length, new Vec3(0, length, 0));
    }

    /**
     * 获得两个位置之间的方向向量；若两点重合则默认返回的向量
     *
     * @param start      开始位置的位置向量
     * @param end        结束位置的位置向量
     * @param length     返回向量的长度
     * @param defaultVec 两点重合时返回的默认向量（注：直接原样返回，不会判定该向量的长度）
     */
    public static Vec3 getDirection(Vec3 start, Vec3 end, double length, Vec3 defaultVec) {
        return getDirection(start, end, length, defaultVec, false);
    }

    /**
     * 获得两个位置之间的方向向量
     * 若preserveShorterVectors为true且两点之间的距离小于length则不会改变向量长度
     *
     * @param start                  开始位置的位置向量
     * @param end                    结束位置的位置向量
     * @param length                 返回向量的长度
     * @param defaultVec             两点重合时返回的默认向量（注：直接原样返回，不会判定该向量的长度）
     * @param preserveShorterVectors 若向量比length短，是否保留原向量
     */
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

    /**
     * 为专家?在处理if...else if时应先使用isMaster
     */
    public static boolean isAtLeastExpert(Level level) {
        return level.getDifficulty().getId() >= Difficulty.NORMAL.getId();
    }

    /**
     * 为大师?在处理if...else if时应先使用此方法
     */
    public static boolean isMaster(Level level) {
        return level.getDifficulty() == Difficulty.HARD;
    }

    /**
     * 根据游戏难度选择值
     *
     * @param classic 经典难度的值
     * @param expert  专家难度的值
     * @param master  大师难度的值
     * @return 选择到的值
     */
    public static <T> T switchByDifficulty(Level level, T classic, T expert, T master) {
        return switch (level.getDifficulty()) {
            case PEACEFUL, EASY -> classic;
            case NORMAL -> expert;
            case HARD -> master;
        };
    }

    /**
     * 获得从实体A到实体B的单位向量，即A→B
     *
     * @param a 实体A
     * @param b 实体B
     * @return A→B的单位向量
     */
    public static Vec3 getVectorA2B(Entity a, Entity b) {
        return b.position().subtract(a.position()).normalize();
    }

    /**
     * 给予实体B一个击退动量，方向为A→B
     *
     * @param a       实体A
     * @param b       实体B
     * @param scale   击退动量的缩放
     * @param motionY 击退的Y轴动量
     */
    public static void knockBackA2B(Entity a, Entity b, double scale, double motionY) {
        if (b instanceof LivingEntity living) {
            AttributeInstance instance = living.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (instance != null) scale *= (1.0 - instance.getValue());
        }
        if (scale > 0.0) {
            if (a instanceof LivingEntity living) {
                AttributeInstance instance = living.getAttribute(Attributes.ATTACK_KNOCKBACK);
                if (instance != null) scale *= (1.0 + instance.getValue());
            }
            b.addDeltaMovement(getVectorA2B(a, b).scale(scale).add(0.0, motionY, 0.0));
        }
    }

    public static Vec3 componentMin(Vec3 vec1, Vec3 vec2) {
        return new Vec3(Math.min(vec1.x, vec2.x), Math.min(vec1.y, vec2.y), Math.min(vec1.z, vec2.z));
    }

    public static Vec3 componentMax(Vec3 vec1, Vec3 vec2) {
        return new Vec3(Math.max(vec1.x, vec2.x), Math.max(vec1.y, vec2.y), Math.max(vec1.z, vec2.z));
    }

    public static Direction[] directionsInAxis(Direction.Axis axis) {
        return switch (axis) {
            case X -> new Direction[]{Direction.EAST, Direction.WEST};
            case Y -> new Direction[]{Direction.UP, Direction.DOWN};
            default -> new Direction[]{Direction.SOUTH, Direction.NORTH};
        };
    }

    /**
     * 将输入的向量的某个轴乘一个缩放
     *
     * @param vec3  输入的向量
     * @param axis  某个轴
     * @param scale 缩放
     * @return 新向量
     */
    public static Vec3 relativeScale(Vec3 vec3, Direction.Axis axis, double scale) {
        double x = axis == Direction.Axis.X ? scale * vec3.x : vec3.x;
        double y = axis == Direction.Axis.Y ? scale * vec3.y : vec3.y;
        double z = axis == Direction.Axis.Z ? scale * vec3.z : vec3.z;
        return new Vec3(x, y, z);
    }

    public static void renderCube(PoseStack stack, VertexConsumer consumer, FloatRGBA rgb,
                                  float l, float w, int h, float x, int y, float z){
        renderPart(stack, consumer, rgb.red(), rgb.green(), rgb.blue(), rgb.alpha(), y, y + h,
                x, z, x + l, z + l, x + w, z + w, x + w, z + l, 0, 0, 0, 0);
    }

    private static void renderPart(PoseStack pPoseStack, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, int pMinY, int pMaxY, float pX0, float pZ0, float pX1, float pZ1, float pX2, float pZ2, float pX3, float pZ3, float pMinU, float pMaxU, float pMinV, float pMaxV) {
        PoseStack.Pose posestack$pose = pPoseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX0, pZ0, pX1, pZ1, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX3, pZ3, pX2, pZ2, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX1, pZ1, pX3, pZ3, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX2, pZ2, pX0, pZ0, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMinY, pX0, pX3, pZ0, pZ3, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMaxY, pX0, pX3, pZ0, pZ3, pMinU, pMaxU, pMinV, pMaxV);
    }

    private static void renderQuad(Matrix4f pPose, Matrix3f pNormal, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, int pMinY, int pMaxY, float pMinX, float pMinZ, float pMaxX, float pMaxZ, float pMinU, float pMaxU, float pMinV, float pMaxV) {
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMinX, pMinZ, pMaxU, pMinV);
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMinX, pMinZ, pMaxU, pMaxV);
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxX, pMaxZ, pMinU, pMaxV);
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMaxX, pMaxZ, pMinU, pMinV);
    }

    private static void addVertex(Matrix4f pPose, Matrix3f pNormal, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, int pY, float pX, float pZ, float pU, float pV) {
        pConsumer.vertex(pPose, pX, (float)pY, pZ).color(pRed, pGreen, pBlue, pAlpha).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }
}
