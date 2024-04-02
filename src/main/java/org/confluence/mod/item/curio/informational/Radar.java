package org.confluence.mod.item.curio.informational;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.Team;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.EnemyInfoPacketS2C;
import top.theillusivec4.curios.api.SlotContext;

public class Radar extends BaseCurioItem {
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        Level level = living.level();
        if (living instanceof ServerPlayer owner && (level.getGameTime() + owner.getId()) % 100 == 0) {
            AABB region = new AABB(owner.getOnPos()).inflate(63.5);
            int amount = level.getEntities(owner, region, entity -> entity instanceof Enemy).size();
            Team team = owner.getTeam();
            owner.serverLevel().players().stream()
                .filter(serverPlayer -> serverPlayer.getTeam() == team && serverPlayer.distanceTo(owner) < 31.5F)
                .forEach(mate -> NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> mate), new EnemyInfoPacketS2C(amount)));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer owner) {
            owner.serverLevel().players()
                .forEach(mate -> NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> mate), new EnemyInfoPacketS2C(-1)));
        }
    }
}
