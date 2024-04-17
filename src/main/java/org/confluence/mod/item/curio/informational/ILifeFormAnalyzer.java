package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import org.confluence.mod.misc.ModConfigs;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

public interface ILifeFormAnalyzer {
    static Component getInfo(LocalPlayer localPlayer) {
        AtomicReference<Component> atomic = new AtomicReference<>(Component.translatable("info.confluence.life_form_analyzer.none"));
        localPlayer.level().getEntities(localPlayer, new AABB(localPlayer.getOnPos()).inflate(47.5), entity -> ModConfigs.rareCreatures.contains(entity.getType()))
            .stream().min(Comparator.comparingInt(entity -> ModConfigs.rareCreatures.indexOf(entity.getType())))
            .ifPresent(entity -> atomic.set(Component.translatable("info.confluence.life_form_analyzer", entity.getType().getDescription())));
        return atomic.get();
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.life_form_analyzer");
}
