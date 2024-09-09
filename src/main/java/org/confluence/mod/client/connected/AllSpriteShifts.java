package org.confluence.mod.client.connected;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;

public class AllSpriteShifts {
    public static final CTSpriteShiftEntry ANDESITE_CASING = omni("andesite_casing");

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
        return SpriteShifter.get(new ResourceLocation(Confluence.MODID, originalLocation), new ResourceLocation(Confluence.MODID, targetLocation));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, new ResourceLocation(Confluence.MODID, "block/" + blockTextureName), new ResourceLocation(Confluence.MODID, "block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }
}
