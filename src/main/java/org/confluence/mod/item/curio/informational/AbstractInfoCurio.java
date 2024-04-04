package org.confluence.mod.item.curio.informational;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;

public abstract class AbstractInfoCurio extends BaseCurioItem {
    public AbstractInfoCurio(Rarity rarity) {
        super(rarity);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            ArrayList<ItemStack> items = new ArrayList<>();
            CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
                IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
                for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                    ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
                    if (!itemStack.isEmpty()) items.add(itemStack);
                }
            });
            InfoCurioCheckPacketS2C.send(serverPlayer, items);
        }
    }
}
