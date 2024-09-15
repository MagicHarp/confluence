package org.confluence.mod.item.bomb;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.projectile.bombs.BaseBombEntity;
import org.confluence.mod.entity.projectile.bombs.BouncyBombEntity;
import org.confluence.mod.entity.projectile.bombs.ScarabBombEntity;
import org.confluence.mod.entity.projectile.bombs.StickyBombEntity;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class Bomb extends Item {
    protected float throwSpeed;

    public Bomb() {
        super(new Properties().rarity(Rarity.COMMON));
        this.throwSpeed = 0.8f;
    }

    protected BaseBombEntity EntityConstructor(Level level, Player player) {
        return new BaseBombEntity(level, player);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        BaseBombEntity bomb;
        if (!level.isClientSide) {
            if (itemStack.getItem() == ModItems.BOUNCY_BOMB.get()){
                bomb = new BouncyBombEntity(ModEntities.BOUNCY_BOMB_ENTITY.get(), player, level);
            }else if (itemStack.getItem() == ModItems.SCARAB_BOMB.get()){
                bomb = new ScarabBombEntity(ModEntities.SCARAB_BOMB_ENTITY.get(), player, level);
            }else if (itemStack.getItem() == ModItems.STICKY_BOMB.get()){
                bomb = new StickyBombEntity(ModEntities.STICKY_BOMB_ENTITY.get(), player, level);
            }else {
                bomb = this.EntityConstructor(level, player);
            }
            bomb.setOwner(player);
            bomb.setItem(itemStack);
            bomb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, throwSpeed, 1.0F);
            level.addFreshEntity(bomb);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        // 以此判定创造模式
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
