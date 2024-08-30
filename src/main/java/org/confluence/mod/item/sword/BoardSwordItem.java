package org.confluence.mod.item.sword;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;
import org.confluence.mod.datagen.limit.Image24x;
import org.jetbrains.annotations.NotNull;

public class BoardSwordItem extends SwordItem {
    public BoardSwordItem(Tier tier, int rawDamage, float rawSpeed) {
        this(tier, rawDamage, rawSpeed, new Properties());
    }

    public BoardSwordItem(Tier tier, int rawDamage, float rawSpeed, Properties properties) {
        super(tier, (int) (rawDamage - tier.getAttackDamageBonus() - 1), rawSpeed - 4, properties);
    }

    @Override
    public @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
        return target.getBoundingBox().inflate(1.5D, 0.5D, 1.5D);
    }
}
