package org.confluence.mod.item.curio.combat;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;

public class HoneyComb extends BaseCurioItem {
    public HoneyComb() {
        super(ModRarity.GREEN);
    }

    public static void apply(LivingEntity living, RandomSource random) {
        if (CuriosUtils.hasCurio(living, CurioItems.HONEY_COMB.get())) {
            int summon = random.nextInt(1, /* 蜂巢背包 ? 5 : */4);
            for (int i = 0; i < summon; i++) {
                /* 蜜蜂射弹 */
            }
        }
    }
}
