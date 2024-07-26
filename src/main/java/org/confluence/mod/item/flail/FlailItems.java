package org.confluence.mod.item.flail;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum FlailItems implements EnumRegister<AbstractFlailItem> {
    MACE("mace", () -> new AbstractFlailItem(3, null, null, 0)),;

    private final RegistryObject<AbstractFlailItem> value;
    FlailItems(String id, Supplier<AbstractFlailItem> flail){
        value = ModItems.ITEMS.register(id, flail);
    }

    @Override
    public RegistryObject<AbstractFlailItem> getValue(){
        return value;
    }
    public static void init(){}
}
