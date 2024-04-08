package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.confluence.mod.capability.ability.PlayerAbilityProvider;

import java.util.concurrent.atomic.AtomicReference;

public interface IFishermansPocketGuide {
    static Component getInfo(LocalPlayer localPlayer) {
        AtomicReference<Component> atomic = new AtomicReference<>(Component.translatable("info.confluence.fishermans_pocket_guide", 0.0F));
        localPlayer.getCapability(PlayerAbilityProvider.CAPABILITY)
            .ifPresent(playerAbility -> atomic.set(Component.translatable("info.confluence.fishermans_pocket_guide", playerAbility.getFishing())));
        return atomic.get();
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.fishermans_pocket_guide");
}
