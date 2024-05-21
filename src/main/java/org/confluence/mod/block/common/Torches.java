package org.confluence.mod.block.common;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Torches implements EnumRegister<ColorfulTorchBlock> {
    PURPLE_TORCH("purple_torch", () -> new ColorfulTorchBlock(14.0F, 0.9F, 0.0F, 0.9F)), // 紫
    YELLOW_TORCH("yellow_torch", () -> new ColorfulTorchBlock(14.0F, 0.9F, 0.9F, 0.0F)), // 黄
    BLUE_TORCH("blue_torch", -1.0F, -0.9F, 0.3F), // 蓝
    GREEN_TORCH("green_torch", -1.0F, 0.0F, -0.9F), // 绿
    RED_TORCH("red_torch", 0.0F, -0.9F, -0.9F), // 红
    ORANGE("orange_torch", 0.0F, -0.5F, -1.0F), // 橙
    WHITE_TORCH("white_torch", 0.4F, 0.4F, 0.4F), // 白
    ICE_TORCH("ice_torch", -0.25F, -0.15F, 0.4F), // 冰雪
    PINK_TORCH("pink_torch", 0.0F, -1.0F, 0.0F), // 粉
    BONE_TORCH("bone_torch", -0.5F, -0.25F, 0.3F), // 骨头
    ULTRABRIGHT_TORCH("ultrabright_torch"), // 超亮
    DEMON_TORCH("demon_torch"), // 恶魔
    CURSED_TORCH("cursed_torch", 0.0F, 0.6F, -0.5F), // 诅咒
    ICHOR_TORCH("ichor_torch", 0.4F, 0.4F, -0.3F), // 灵液
    RAINBOW_TORCH("rainbow_torch"), // 彩虹
    DESERT_TORCH("desert_torch", 0.4F, -0.15F, -0.45F), // 沙漠
    CORAL_TORCH("coral_torch", -0.75F, 0.3F, -0.2F), // 珊瑚
    CORRUPT_TORCH("corrupt_torch", -0.05F, -0.6F, 0.4F), // 腐化
    CRIMSON_TORCH("crimson_torch", 0.4F, -0.3F, -0.5F), // 猩红
    HALLOWED_TORCH("hallowed_torch", 0.25F, -0.4F, 0.2F),// 神圣
    JUNGLE_TORCH("jungle_torch", -0.25F, 0.45F, -0.1F), // 丛林
    MUSHROOM_TORCH("mushroom_torch"), // 蘑菇
    AETHER_TORCH("aether_torch"); // 以太

    private final RegistryObject<ColorfulTorchBlock> value;

    Torches(String id, Supplier<ColorfulTorchBlock> supplier) {
        this.value = ModBlocks.registerWithItem(id, supplier);
    }

    @Override
    public RegistryObject<ColorfulTorchBlock> getValue() {
        return value;
    }

    public static void init() {}
}
