package org.confluence.mod.item.curio.expert;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class RoyalGel extends BaseCurioItem implements ModRarity.Expert {
    public RoyalGel() {
        super(ModRarity.EXPERT);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        LivingEntity living = slotContext.entity();
        living.level().getEntitiesOfClass(Slime.class, new AABB(living.getOnPos()).inflate(31.5)).forEach(slime -> {
            if (slime.getTarget() == living) slime.setTarget(null);
        });
    }

    public static boolean apply(LivingEntity attacker, LivingEntity target) {
        return attacker instanceof Slime && CuriosUtils.hasCurio(target, CurioItems.ROYAL_GEL.get());
    }

    public static boolean isInvul(LivingEntity living, DamageSource damageSource) {
        return damageSource.getEntity() instanceof Slime && CuriosUtils.hasCurio(living, CurioItems.ROYAL_GEL.get());
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.royal_gel.info"),
                Component.translatable("item.confluence.royal_gel.info2"),
                Component.translatable("item.confluence.royal_gel.info3"),
                Component.translatable("item.confluence.royal_gel.info4"),
                Component.translatable("item.confluence.royal_gel.info5")
        };
    }
}
