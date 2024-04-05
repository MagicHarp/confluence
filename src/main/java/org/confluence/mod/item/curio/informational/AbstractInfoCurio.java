package org.confluence.mod.item.curio.informational;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;

public abstract class AbstractInfoCurio extends BaseCurioItem {
    public AbstractInfoCurio(Rarity rarity) {
        super(rarity);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            ArrayList<ItemStack> curios = CuriosUtils.getCurios(serverPlayer);
            curios.addAll(serverPlayer.getInventory().items);
            InfoCurioCheckPacketS2C.send(serverPlayer, curios);
        }
    }
}
