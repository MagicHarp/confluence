package org.confluence.mod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import org.confluence.mod.item.curio.combat.IAutoAttack;
import org.confluence.mod.item.curio.combat.IScope;
import org.confluence.mod.item.curio.construction.IRightClickSubtractor;
import org.confluence.mod.item.curio.movement.IMayFly;
import org.confluence.mod.item.curio.movement.IMultiJump;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public final class ModUtils {
    public static float nextFloat(RandomSource randomSource, float origin, float bound) {
        if (origin >= bound) {
            throw new IllegalArgumentException("bound - origin is non positive");
        } else {
            return origin + randomSource.nextFloat() * (bound - origin);
        }
    }

    public static Component getModifierTooltip(double amount, String type) {
        boolean b = amount > 0.0;
        amount *= 100.0;
        return Component.translatable(
            "prefix.confluence.tooltip." + (b ? "plus" : "take"),
            ATTRIBUTE_MODIFIER_FORMAT.format(b ? amount : -amount),
            Component.translatable("prefix.confluence.tooltip." + type)
        ).withStyle(b ? ChatFormatting.BLUE : ChatFormatting.RED);
    }

    public static void resetClientPacket(ServerPlayer serverPlayer) {
        IMultiJump.sendMsg(serverPlayer);
        IMayFly.sendMsg(serverPlayer);
        IAutoAttack.sendMsg(serverPlayer);
        IScope.sendMsg(serverPlayer);
        IRightClickSubtractor.sendMsg(serverPlayer);
    }
}
