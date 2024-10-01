package org.confluence.mod.item.bow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.projectile.arrows.BaseArrowEntity;

public class BaseArrowItem extends ArrowItem {
    public BaseArrowItem(Properties pProperties) {
        super(pProperties);
    }
    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        if(pStack.getItem() instanceof BaseArrowItem && BaseArrowEntity.selectArrowFromItemMap.containsKey(pStack.getItem())){
            BaseArrowEntity arrow = new BaseArrowEntity(pShooter,BaseArrowEntity.selectArrowFromItemMap.get(pStack.getItem()));
            //arrow.setEffectsFromItem(pStack);
            return arrow;
        }
        Arrow arrow = new Arrow(pLevel, pShooter);
        arrow.setEffectsFromItem(pStack);
        return arrow;
    }



}
