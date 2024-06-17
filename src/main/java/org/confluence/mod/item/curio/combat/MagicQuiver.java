package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MagicQuiver extends BaseCurioItem implements IProjectileAttack {
    public MagicQuiver() {
        super(ModRarity.LIGHT_RED);
    }

    public MagicQuiver(Rarity rarity) {
        super(rarity);
    }

    @Override
    public float getProjectileBonus() {
        return ModConfigs.MAGIC_QUIVER_PROJECTILE_BONUS.get().floatValue();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.magic_quiver.tooltip"));
        list.add(Component.translatable("item.confluence.magic_quiver.tooltip2"));
    }

    public static void applyToArrow(LivingEntity living, AbstractArrow arrow) {
        AtomicBoolean isEmpty = new AtomicBoolean(true);
        AtomicBoolean hasFire = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    Item item = stack.getItem();
                    if (!stack.isEmpty() && item instanceof MagicQuiver) {
                        isEmpty.set(false);
                        if (item == CurioItems.MOLTEN_QUIVER.get()) {
                            hasFire.set(true);
                        }
                    }
                }
            }
        });
        if (!isEmpty.get()) arrow.setDeltaMovement(arrow.getDeltaMovement().scale(2.0));
        if (hasFire.get()) arrow.setSecondsOnFire(100);
    }

    public static boolean shouldConsume(LivingEntity living) {
        return living.getRandom().nextFloat() >= 0.2F || CuriosUtils.noSameCurio(living, MagicQuiver.class);
    }
}
