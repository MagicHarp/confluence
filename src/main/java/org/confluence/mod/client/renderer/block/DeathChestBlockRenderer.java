package org.confluence.mod.client.renderer.block;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.common.BaseChestBlock;
import org.jetbrains.annotations.NotNull;

public class DeathChestBlockRenderer extends ChestRenderer<BaseChestBlock.Entity> { // 死人箱没有上锁变种
    public static final Material DEATH_GOLDEN = chest("death_golden");
    public static final Material DEATH_GOLDEN_LEFT = chest("death_golden_left");
    public static final Material DEATH_GOLDEN_RIGHT = chest("death_golden_right");

    public DeathChestBlockRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    protected @NotNull Material getMaterial(BaseChestBlock.Entity blockEntity, @NotNull ChestType chestType) {
        return switch (blockEntity.variant) {
            default -> chooseMaterial(chestType, DEATH_GOLDEN, DEATH_GOLDEN_LEFT, DEATH_GOLDEN_RIGHT);
        };
    }

    private static Material chest(String pChestName) {
        return new Material(Sheets.CHEST_SHEET, new ResourceLocation(Confluence.MODID, "entity/chest/" + pChestName));
    }

    private static Material chooseMaterial(ChestType pChestType, Material pDoubleMaterial, Material pLeftMaterial, Material pRightMaterial) {
        return switch (pChestType) {
            case LEFT -> pLeftMaterial;
            case RIGHT -> pRightMaterial;
            case SINGLE -> pDoubleMaterial;
        };
    }
}
