package org.confluence.mod.item.gun;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Vanishable;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public abstract class AbstractGunItem extends Item implements Vanishable, GeoItem {
    protected final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

    public AbstractGunItem(Rarity rarity) {
        super(new Properties().rarity(rarity).stacksTo(1));
    }

    public boolean isAuto() {
        return true;
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
        return 10;
    }

    public float getShootingSpeed() {
        return 0.0F;
    }

    @Override
    public abstract void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }
}
