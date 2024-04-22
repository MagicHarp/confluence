package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.confluence.mod.capability.ability.AbilityProvider;

import java.util.concurrent.atomic.AtomicReference;

import static org.confluence.mod.client.handler.ClientPacketHandler.getMoonPhase;

public interface IFishermansPocketGuide {
    static Component getInfo(LocalPlayer localPlayer) {
        AtomicReference<Component> atomic = new AtomicReference<>(Component.translatable("info.confluence.fishermans_pocket_guide", 0.0F));
        localPlayer.getCapability(AbilityProvider.CAPABILITY)
            .ifPresent(playerAbility -> atomic.set(Component.translatable(
                "info.confluence.fishermans_pocket_guide",
                "%.2f".formatted(playerAbility.getFishingPower() * (getMoonPhase() < 0 ? 1.1F : 1.0F))
            )));
        return atomic.get();
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.fishermans_pocket_guide");
}
