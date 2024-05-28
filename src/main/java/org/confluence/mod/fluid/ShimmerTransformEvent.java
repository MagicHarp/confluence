package org.confluence.mod.fluid;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

@Cancelable
public class ShimmerTransformEvent extends Event {
    private final ItemEntity source;
    private @Nullable ItemStack target;
    private int transformTime = 20;
    private int coolDown;

    public ShimmerTransformEvent(ItemEntity source) {
        this.source = source;
        this.coolDown = source.lifespan;
    }

    public ItemEntity getSource() {
        return source;
    }

    public void setTarget(@Nullable ItemStack target) {
        this.target = target;
    }

    public @Nullable ItemStack getTarget() {
        return target;
    }

    public void setTransformTime(int transformTime) {
        this.transformTime = transformTime;
    }

    public int getTransformTime() {
        return transformTime;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public int getCoolDown() {
        return coolDown;
    }
}
