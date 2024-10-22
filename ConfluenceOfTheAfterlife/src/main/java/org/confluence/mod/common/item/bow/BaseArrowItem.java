package org.confluence.mod.common.item.bow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.common.component.ModRarity;
import org.confluence.mod.common.entity.projectile.BaseArrowEntity;
import org.confluence.mod.common.init.ModDataComponentTypes;

public class BaseArrowItem extends ArrowItem {
    public BaseArrowItem(ModRarity rarity) {
        super(new Properties().component(ModDataComponentTypes.MOD_RARITY, rarity));
    }
    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter,ItemStack weapon) {
        if(pStack.getItem() instanceof BaseArrowItem && BaseArrowEntity.selectArrowFromItemMap.containsKey(pStack.getItem())){
            BaseArrowEntity arrow = new BaseArrowEntity(pShooter,BaseArrowEntity.selectArrowFromItemMap.get(pStack.getItem()));
            //arrow.setEffectsFromItem(pStack);
            return arrow;
        }
        return super.createArrow(pLevel, pStack, pShooter,weapon);
    }



}
