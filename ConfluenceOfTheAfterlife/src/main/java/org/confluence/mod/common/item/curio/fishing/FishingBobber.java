package org.confluence.mod.common.item.curio.fishing;

import org.confluence.mod.common.entity.fishing.CurioFishingHook;
import org.confluence.mod.terra_curio.common.component.CombineRule;
import org.confluence.mod.terra_curio.common.component.ModRarity;
import org.confluence.mod.terra_curio.common.component.PrimitiveComponent;
import org.confluence.mod.terra_curio.common.init.ModDataComponentTypes;
import org.confluence.mod.terra_curio.common.item.curio.BaseCurioItem;

public class FishingBobber extends BaseCurioItem {
    public final CurioFishingHook.Variant variant;

    public FishingBobber(CurioFishingHook.Variant variant) {
        super(new Properties().component(ModDataComponentTypes.MOD_RARITY, ModRarity.BLUE).component(ModDataComponentTypes.PRIMITIVE, PrimitiveComponent.ofFloat(10.0F, CombineRule.FLOAT_ADDITION)));
        this.variant = variant;
    }
}
