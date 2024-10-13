package org.confluence.mod.item.curio.fishing;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import org.confluence.mod.misc.ModSoundEvents;
import org.confluence.mod.util.CuriosUtils;

public interface IHighTestFishingLine {
    static void apply(ItemFishedEvent event) {
        Player entity = event.getEntity();
        Level level = entity.level();
        if (level.random.nextFloat() < 0.1429F && CuriosUtils.noSameCurio(entity, IHighTestFishingLine.class)) {
            level.playSound(null, entity.getOnPos().above(), ModSoundEvents.DECOUPLING.get(), SoundSource.PLAYERS);
            event.setCanceled(true);
        }
    }
}
