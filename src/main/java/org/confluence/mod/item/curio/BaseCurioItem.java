package org.confluence.mod.item.curio;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.util.PlayerUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Objects;

public class BaseCurioItem extends Item implements ICurioItem {
    public BaseCurioItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public BaseCurioItem() {
        this(new Properties());
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return PlayerUtils.noSameCurio(slotContext.entity(), this);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return PlayerUtils.noSameCurio(slotContext.entity(), this);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(0, Component.translatable("item.confluence."+ Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this)).getPath() +".tooltip"));
    }
}
