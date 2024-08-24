package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import org.confluence.mod.datagen.limit.CustomModel;

public class HerbItem extends Item implements CustomModel {
    public HerbItem(){
        super(new Properties());
    }

    public HerbItem(Properties properties){
        super(properties);
    }
}
