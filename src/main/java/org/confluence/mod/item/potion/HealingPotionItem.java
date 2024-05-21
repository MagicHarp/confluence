package org.confluence.mod.item.potion;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.harmful.PotionSicknessEffect;

public class HealingPotionItem extends AbstractPotionItem {
    private final int amount;

    public HealingPotionItem(int amount, Rarity rarity) {
        super(new Properties().rarity(rarity));
        this.amount = amount;
    }

    @Override
    protected boolean canUse(Level level, Player player, InteractionHand hand) {
        return !player.hasEffect(ModEffects.POTION_SICKNESS.get());
    }

    @Override
    protected void apply(ItemStack itemStack, Level level, LivingEntity living) {
        if (level.isClientSide) return;
        living.heal(amount);
        PotionSicknessEffect.addTo(living, 1200);
    }
}
