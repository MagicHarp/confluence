package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.capability.ability.AbilityProvider;

import java.util.concurrent.atomic.AtomicReference;

public interface IFishermansPocketGuide {
    static Component getInfo(Player localPlayer) {
        AtomicReference<Component> atomic = new AtomicReference<>(Component.translatable("info.confluence.fishermans_pocket_guide", 0.0F));
        localPlayer.getCapability(AbilityProvider.CAPABILITY)
            .ifPresent(playerAbility -> atomic.set(Component.translatable(
                "info.confluence.fishermans_pocket_guide",
                "%.2f".formatted(localPlayer.getLuck())
            )));
        return atomic.get();
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.fishermans_pocket_guide");
    byte INDEX = 3;
}
