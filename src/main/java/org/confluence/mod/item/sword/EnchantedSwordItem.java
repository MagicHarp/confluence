package org.confluence.mod.item.sword;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.projectile.EnchantedSwordProjectile;

public class EnchantedSwordItem extends SwordItem {

    public EnchantedSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            if (player.getMainHandItem().getItem().equals(Swords.ENCHANTED_SWORD.get())) {
                if (!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())) {
                    player.getMainHandItem().hurtAndBreak(1, player, null);

                    Vec3 lookDirection = player.getViewVector(1.5F);
                    Vec3 spawnPos = player.position().add(lookDirection.scale(0.2F));

                    EnchantedSwordProjectile projectile = new EnchantedSwordProjectile(ModEntities.ENCHANTED_SWORD_PROJECTILE.get(), level);
                    projectile.setPos(spawnPos.x, spawnPos.y + 1.5F, spawnPos.z);
                    projectile.setDeltaMovement(lookDirection.scale(2.5F));
                    projectile.setOwner(player);

                    level.addFreshEntity(projectile);

                    player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 15);
                }
            }
        }
        return super.use(level, player, usedHand);
    }
}
