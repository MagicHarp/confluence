package org.confluence.mod.item.curio.expert;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.CuriosUtils;

public class WormScarf extends BaseCurioItem implements ModRarity.Expert {
    public WormScarf() {
        super(ModRarity.EXPERT);
    }

    public static float apply(LivingEntity living, float amount) {
        if (CuriosUtils.noSameCurio(living, CurioItems.WORM_SCARF.get())) return amount;
        return amount * 0.83F;
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.worm_scarf.info")
        };
    }
}
