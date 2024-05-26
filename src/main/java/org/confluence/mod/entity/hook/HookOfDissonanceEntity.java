package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class HookOfDissonanceEntity extends AbstractHookEntity {
    public HookOfDissonanceEntity(EntityType<HookOfDissonanceEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public HookOfDissonanceEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.HOOK_OF_DISSONANCE.get(), item, player, level);
    }

    @Override
    protected void onHooked(BlockHitResult hitResult, ItemStack itemStack) {
        super.onHooked(hitResult, itemStack);
        Entity owner = getOwner();
        if (owner != null) {
            Vec3 pos = position();
            Vec3 delta = owner.position().subtract(pos).normalize().scale(0.5);
            owner.teleportTo(pos.x + delta.x, pos.y + delta.y, pos.z + delta.z);
        }
    }
}
