package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.entity.projectile.BeeProjectile;
import org.confluence.mod.item.curio.CurioItems;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;

public interface IHoneycomb {
    Component TOOLTIP = Component.translatable("item.confluence.honey_comb.tooltip");

    static void apply(LivingEntity living, RandomSource random) {
        AtomicBoolean honeyComb = new AtomicBoolean();
        AtomicBoolean hivePack = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                Item item = itemHandlerModifiable.getStackInSlot(i).getItem();
                if (item instanceof IHoneycomb) honeyComb.set(true);
                if (item == CurioItems.HIVE_PACK.get()) hivePack.set(true);
                if (honeyComb.get() && hivePack.get()) break;
            }
        });
        if (honeyComb.get()) {
            boolean hasHivePack = hivePack.get();
            int summon = random.nextInt(1, hasHivePack ? 5 : 4);
            for (int i = 0; i < summon; i++) {
                BeeProjectile projectile = new BeeProjectile(living.level(), living, hasHivePack && random.nextBoolean());
                projectile.setPos(living.position().add(random.nextInt(3) - 1.0, 2.0, random.nextInt(3) - 1.0));
                living.level().addFreshEntity(projectile);
            }
            living.addEffect(new MobEffectInstance(ModEffects.HONEY.get(), 100));
        }
    }
}
