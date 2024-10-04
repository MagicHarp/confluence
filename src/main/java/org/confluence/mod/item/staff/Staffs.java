package org.confluence.mod.item.staff;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.entity.minion.FinchMinionEntity;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Staffs implements EnumRegister<Item> {

    FINCH_STAFF("finch_staff",() -> new SummonerStaffItem(FinchMinionEntity::new));


    ;

    private final RegistryObject<Item> value;

    Staffs(String id, Supplier<Item> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {}
}
