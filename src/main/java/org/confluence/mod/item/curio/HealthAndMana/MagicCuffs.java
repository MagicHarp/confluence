package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.util.CuriosUtils;

public class MagicCuffs extends BandOfStarpower {
    public MagicCuffs() {
        super(ModRarity.GREEN);
    }

    public MagicCuffs(Rarity rarity) {
        super(rarity);
    }

    public static void apply(LivingEntity living, DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.DROWN) || damageSource.is(ModTags.HARMFUL_EFFECT)) return;
        if (CuriosUtils.hasCurio(living, CurioItems.MAGIC_CUFFS.get())) {
            living.getCapability(ManaProvider.CAPABILITY)
                .ifPresent(manaStorage -> manaStorage.receiveMana(() -> (int) amount));
        }
    }
}
