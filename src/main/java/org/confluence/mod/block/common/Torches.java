package org.confluence.mod.block.common;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.util.EnumRegister;

public enum Torches implements EnumRegister<TorchBlock> {
    PURPLE_TORCH("purple_torch"), // 紫
    YELLOW_TORCH("yellow_torch"), // 黄
    BLUE_TORCH("blue_torch"), // 蓝
    GREEN_TORCH("green_torch"), // 绿
    RED_TORCH("red_torch"), // 红
    ORANGE("orange_torch"), // 橙
    WHITE_TORCH("white_torch"), // 白
    ICE_TORCH("ice_torch"), // 冰雪
    PINK_TORCH("pink_torch"), // 粉
    BONE_TORCH("bone_torch"), // 骨头
    ULTRABRIGHT_TORCH("ultrabright_torch"), // 超亮
    DEMON_TORCH("demon_torch"), // 恶魔
    CURSED_TORCH("cursed_torch"), // 诅咒
    ICHOR_TORCH("ichor_torch"), // 灵液
    RAINBOW_TORCH("rainbow_torch"), // 彩虹
    DESERT_TORCH("desert_torch"), // 沙漠
    CORAL_TORCH("coral_torch"), // 珊瑚
    CORRUPT_TORCH("corrupt_torch"), // 腐化
    CRIMSON_TORCH("crimson_torch"), // 猩红
    HALLOWED_TORCH("hallowed_torch"),// 神圣
    JUNGLE_TORCH("jungle_torch"), // 丛林
    MUSHROOM_TORCH("mushroom_torch"), // 蘑菇
    AETHER_TORCH("aether_torch"); // 以太

    private final RegistryObject<TorchBlock> value;

    Torches(String id) {
        this.value = ModBlocks.registerWithItem(id, () -> new TorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH), ParticleTypes.FLAME));
    }

    @Override
    public RegistryObject<TorchBlock> getValue() {
        return value;
    }

    public static void init() {}
}
