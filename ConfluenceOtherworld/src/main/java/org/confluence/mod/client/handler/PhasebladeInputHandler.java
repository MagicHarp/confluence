package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.common.item.sword.BasePhasebladeItem;
import org.confluence.mod.network.c2s.PhasebladeControlPacketC2S;

public final class PhasebladeInputHandler implements ClientWeaponInputHandler {
    public static final PhasebladeInputHandler INSTANCE = new PhasebladeInputHandler();
    private boolean held;
    private int heldTicks;

    private PhasebladeInputHandler() {}

    @Override
    public void tick(LocalPlayer player, ItemStack stack, boolean attackHeld) {
        boolean phaseblade = stack.getItem() instanceof BasePhasebladeItem && BasePhasebladeItem.isTurnOn(stack);
        if (phaseblade && attackHeld) {
            if (!held) {
                PhasebladeControlPacketC2S.sendRecall();
                heldTicks = 0;
            }
            heldTicks++;
        } else if (held) {
            if (phaseblade && heldTicks >= 4) PhasebladeControlPacketC2S.sendThrow();
            heldTicks = 0;
        }
        held = phaseblade && attackHeld;
    }

    @Override
    public void reset() {
        held = false;
        heldTicks = 0;
    }
}
