package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class TestStickItem extends Item implements ModRarity.Master {
    private int clickCount;
    private LivingEntity firstClickEntity;
    private int waitTick;

    public TestStickItem(Properties pProperties) {
        super(pProperties);
        clickCount = 0;
        waitTick = 20;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (waitTick > 0) {
            waitTick--;
        }
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack itemStack) {
        return withColor(getDescriptionId(itemStack));
    }

    public int getClickCount(){
        return clickCount;
    }

    public void setClickCount(int count){
        this.clickCount = count;
    }

    public LivingEntity getFirstClickEntity(){
        return firstClickEntity;
    }

    public void setFirstClickEntity(LivingEntity entity){
        this.firstClickEntity = entity;
    }

    public int getWaitTick(){
        return waitTick;
    }

    public void setWaitTick(int tick){
        this.waitTick = tick;
    }
}
