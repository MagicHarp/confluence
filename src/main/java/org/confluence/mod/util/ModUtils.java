package org.confluence.mod.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.color.FloatRGBA;
import org.confluence.mod.item.ModItems;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

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

    public static void addPotionTooltip(MobEffect effect, List<Component> components,
                                        int amplifier, int duration) {
        if (effect == null){
            components.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
            return;
        }
        components.add(Component.translatable(effect.getDescriptionId()).append(amplifier == 0 ? "" : " ")
                .append(Component.translatable(amplifier == 0 ? "" : ("enchantment.level." + (amplifier + 1))))
                .append("（" + tickFormat(duration) + "）").withStyle(getPotionCategoryColor(effect)));
    }

    private static ChatFormatting getPotionCategoryColor(MobEffect effect) {
        return effect.getCategory().equals(MobEffectCategory.NEUTRAL) ?
                ChatFormatting.GRAY : effect.getCategory().equals(MobEffectCategory.BENEFICIAL) ?
                ChatFormatting.BLUE : ChatFormatting.RED;
    }

    public static String tickFormat(int tick){
        int sec = tick / 20;
        return (sec / 60 < 10 ? "0" : "") + sec / 60
                + ":" +
                (sec % 60 < 10 ? "0" : "") + sec % 60;
    }

    /**
     * 计算向量夹角
     * @param v1
     * @param v2
     * @return degree
     */
    public static double angleBetween(Vec3 v1,Vec3 v2){
        return Math.acos(v1.dot(v2)/v1.length()/v2.length());
    }

    public static void renderCube(PoseStack stack, VertexConsumer consumer, FloatRGBA rgb, float px, float py, float pz, float u, float v){
        renderPart(stack, consumer, rgb.red(), rgb.green(), rgb.blue(), rgb.alpha(),px,py,pz ,u,v);
    }

    private static void renderPart(PoseStack pPoseStack, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, float px,float py,float pz,float u,float v) {
        pPoseStack.pushPose();
        PoseStack.Pose posestack$pose = pPoseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        Consumer<VertexConsumer> consumer = c->c.color(pRed, pGreen, pBlue, pAlpha).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrix3f, 0F, 0F, 0F).endVertex();
        //z=0
        consumer.accept(pConsumer.vertex(matrix4f,0f,0f,0f));
        consumer.accept(pConsumer.vertex(matrix4f,0f,py,0f));
        consumer.accept(pConsumer.vertex(matrix4f,px,py,0f));
        consumer.accept(pConsumer.vertex(matrix4f,px,0f,0f));
        //x=1
        matrix4f.translate(px,0,0);
        matrix4f.rotate(Axis.YP.rotationDegrees(-90));
        consumer.accept(pConsumer.vertex(matrix4f,0f,0f,0f));
        consumer.accept(pConsumer.vertex(matrix4f,0f,py,0f));
        consumer.accept(pConsumer.vertex(matrix4f,pz,py,0f));
        consumer.accept(pConsumer.vertex(matrix4f,pz,0f,0f));
        //z=1
        matrix4f.translate(pz,0,0);
        matrix4f.rotate(Axis.YP.rotationDegrees(-90));
        consumer.accept(pConsumer.vertex(matrix4f,0f,0f,0f));
        consumer.accept(pConsumer.vertex(matrix4f,0f,py,0f));
        consumer.accept(pConsumer.vertex(matrix4f,px,py,0f));
        consumer.accept(pConsumer.vertex(matrix4f,px,0f,0f));
        //x=0
        matrix4f.translate(px,0,0);
        matrix4f.rotate(Axis.YP.rotationDegrees(-90));
        consumer.accept(pConsumer.vertex(matrix4f,0f,0f,0f));
        consumer.accept(pConsumer.vertex(matrix4f,0f,py,0f));
        consumer.accept(pConsumer.vertex(matrix4f,pz,py,0f));
        consumer.accept(pConsumer.vertex(matrix4f,pz,0f,0f));
        //y=0
        matrix4f.translate(0,0,px);
        matrix4f.rotate(Axis.XP.rotationDegrees(-90));
        consumer.accept(pConsumer.vertex(matrix4f,0f,0f,0f));
        consumer.accept(pConsumer.vertex(matrix4f,0f,px,0f));
        consumer.accept(pConsumer.vertex(matrix4f,pz,px,0f));
        consumer.accept(pConsumer.vertex(matrix4f,pz,0f,0f));
        //y=1
        matrix4f.translate(0,px,py);
        matrix4f.rotate(Axis.XP.rotationDegrees(180));
        consumer.accept(pConsumer.vertex(matrix4f,0f,0f,0f));
        consumer.accept(pConsumer.vertex(matrix4f,0f,px,0f));
        consumer.accept(pConsumer.vertex(matrix4f,pz,px,0f));
        consumer.accept(pConsumer.vertex(matrix4f,pz,0f,0f));

        pPoseStack.popPose();
    }

    public static List<? extends Entity> getNearbyEntities(double radius, Level level,
                                          Class<? extends Entity> entity, AABB box) {
        return level.getEntitiesOfClass(entity, box.inflate(radius));
    }
}
