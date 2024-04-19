package org.confluence.mod.item.curio.construction;

import net.minecraft.world.entity.player.Player;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;

public class AncientChisel extends BaseCurioItem {
    public static float apply(Player player, float speed) {
        if (CuriosUtils.noSameCurio(player, CurioItems.ANCIENT_CHISEL.get())) return speed;
        return speed * 1.25F;
    }
}
