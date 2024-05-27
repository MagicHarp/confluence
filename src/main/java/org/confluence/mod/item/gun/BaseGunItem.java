package org.confluence.mod.item.gun;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import org.jetbrains.annotations.Nullable;

public class BaseGunItem extends Item implements Vanishable {
    public BaseGunItem() {
        super(new Properties().stacksTo(1));
    }

    public boolean isAuto() {
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

    public @Nullable Tuple<ItemStack, BaseAmmoItem> getAmmoTuple(ServerPlayer serverPlayer) {
        ItemStack offhandItem = serverPlayer.getOffhandItem();
        if (offhandItem.getItem() instanceof BaseAmmoItem baseAmmoItem) {
            return new Tuple<>(offhandItem, baseAmmoItem);
        }
        for (ItemStack itemStack : serverPlayer.getInventory().items) {
            if (itemStack.getItem() instanceof BaseAmmoItem baseAmmoItem) {
                return new Tuple<>(itemStack, baseAmmoItem);
            }
        }
        return null;
    }

    public int getCoolDown() {
        return 0;
    }

    public float getShootingSpeed() {
        return 0.0F;
    }
}
