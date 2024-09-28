package org.confluence.mod.client.connected;

import org.confluence.mod.Confluence;

public class AllSpriteShifts {
    public static final CTSpriteShiftEntry ANDESITE_CASING = omni("andesite_casing");
    public static final CTSpriteShiftEntry SUN_PLATE = omni("sun_plate");
    public static final CTSpriteShiftEntry PURE_GLASS = omni("pure_glass");

    private static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }

    private static CTSpriteShiftEntry horizontal(String name) {
        return getCT(AllCTTypes.HORIZONTAL, name);
    }

    private static CTSpriteShiftEntry vertical(String name) {
        return getCT(AllCTTypes.VERTICAL, name);
    }

    private static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(Confluence.asResource(originalLocation), Confluence.asResource(targetLocation));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, Confluence.asResource("block/" + blockTextureName), Confluence.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }
}
