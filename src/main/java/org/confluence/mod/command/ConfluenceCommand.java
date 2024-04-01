package org.confluence.mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.SpecificMoonPacketS2C;

public class ConfluenceCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("confluence").requires(sourceStack -> sourceStack.hasPermission(2))
            .then(Commands.literal("specificMoon").then(Commands.argument("id", IntegerArgumentType.integer(-1, 11)).executes(context -> {
                int id = IntegerArgumentType.getInteger(context, "id");
                NetworkHandler.CHANNEL.send(
                    PacketDistributor.ALL.noArg(),
                    new SpecificMoonPacketS2C(id)
                );
                return id;
            })))
        );
    }
}
