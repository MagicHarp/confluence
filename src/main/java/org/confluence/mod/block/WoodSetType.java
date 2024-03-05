package org.confluence.mod.block;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public enum WoodSetType {
    EBONY("ebony"),
    PEARL("pearl"),
    SHADOW("shadow"),
    PALM("palm"),
    ASH("ash"),
    SPOOKY("spooky");

    public final BlockSetType SET;
    public final WoodType TYPE;

    WoodSetType(String id) {
        this.SET = BlockSetType.register(new BlockSetType(id));
        this.TYPE = WoodType.register(new WoodType(id, SET));
    }
}
