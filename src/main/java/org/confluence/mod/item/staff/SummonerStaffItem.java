package org.confluence.mod.item.staff;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.confluence.mod.entity.minion.MinionEntity;
import org.jetbrains.annotations.Nullable;

public class SummonerStaffItem extends Item {
    Factory entity;
    boolean isSummon = false;
    int cooling;
    MinionEntity minion;

    public SummonerStaffItem(Factory entity) {
        super(new Properties());
        this.entity = entity;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!(pLevel instanceof ServerLevel)) return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        if (cooling > 0){
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        }
        if (!isSummon){
            minion = entity.create(pLevel);
            minion.setPos(pPlayer.getEyePosition());
            pLevel.addFreshEntity(minion);
            cooling = 60;
            isSummon = true;
        } else {
            minion.remove(Entity.RemovalReason.DISCARDED);
            minion = null;
            isSummon = false;
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (!(pLevel instanceof ServerLevel)) return;
        if (minion != null){
            if (pEntity instanceof LivingEntity entity){
                minion.ownerAttack = entity.getLastHurtMob();
                minion.owner = entity;
            }
        }
        if (cooling > 0){
            --cooling;
        }
    }

    @FunctionalInterface
    public interface Factory {
        MinionEntity create(Level level);
    }
}
