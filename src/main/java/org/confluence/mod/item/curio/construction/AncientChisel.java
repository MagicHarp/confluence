package org.confluence.mod.item.curio.construction;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AncientChisel extends BaseCurioItem {
    public static float apply(Player player, float speed) {
        if (CuriosUtils.noSameCurio(player, CurioItems.ANCIENT_CHISEL.get())) return speed;
        return speed * 1.25F;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.ancient_chisel.tooltip1"));
        list.add(Component.translatable("item.confluence.ancient_chisel.tooltip2"));
    }
}
