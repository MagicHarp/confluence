package org.confluence.mod.item.common;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.boss.geoEntity.CthulhuEye;
import org.confluence.mod.entity.boss.KingSlime;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class BossSpwanItem extends Item {
    int time;
    String bossType;

    public BossSpwanItem(int time, String bossType) {
        super(new Properties().rarity(ModRarity.BLUE));
        this.time = time;
        this.bossType = bossType;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.getEntitiesOfClass(KingSlime.class, pPlayer.getBoundingBox().inflate(Short.MAX_VALUE)).isEmpty()){   //todo 我的sb检测
            return InteractionResultHolder.fail(itemStack);
        } else if (!pLevel.getEntitiesOfClass(CthulhuEye.class, pPlayer.getBoundingBox().inflate(Short.MAX_VALUE)).isEmpty()){
            return InteractionResultHolder.fail(itemStack);
        }
        if (pLevel.getDayTime() % 24000 >= time){
            itemStack.shrink(1);
            Entity boss = null;
            switch (bossType) {
                case "kingSlime" -> boss = new KingSlime(ModEntities.KING_SLIME.get(), pLevel);
                case "cthulhuEye" -> boss = new CthulhuEye(ModEntities.CTHULHU_EYE.get(), pLevel);
            }
            boss.setPos(pPlayer.getX() + pLevel.random.nextInt(-50, 50), pPlayer.getY(), pPlayer.getZ() + pLevel.random.nextInt(-50, 50));
            pLevel.addFreshEntity(boss);
        }
        return InteractionResultHolder.success(itemStack);
    }
}
