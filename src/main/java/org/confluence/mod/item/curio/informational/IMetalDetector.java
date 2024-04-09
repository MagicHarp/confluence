package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

public interface IMetalDetector {
    static Component getInfo(LocalPlayer localPlayer) {
        AtomicReference<Component> atomic = new AtomicReference<>(Component.translatable("info.confluence.metal_detector.none"));
        localPlayer.level().getBlockStates(new AABB(localPlayer.getOnPos()).inflate(15, 15, 15))
            .filter(blockState -> blockState.getBlock() instanceof Detectable)
            .max(Comparator.comparingInt(blockState -> ((Detectable) blockState.getBlock()).getValue()))
            .ifPresent(blockState -> {
                Block block = blockState.getBlock();
                Item item = block.asItem();
                atomic.set(Component.translatable(
                    "info.confluence.metal_detector",
                    block.getName().withStyle(item.getRarity(new ItemStack(item)).getStyleModifier()))
                );
            });
        return atomic.get();
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.metal_detector");

    interface Detectable {
        int getValue();
    }
}
