package org.confluence.mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ConfluenceCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("confluence").requires(sourceStack -> sourceStack.hasPermission(2))
            .then(Commands.literal("specificMoon").then(Commands.argument("id", IntegerArgumentType.integer(-1, 11)).executes(context -> {
                int id = IntegerArgumentType.getInteger(context, "id");
                ConfluenceData.get(context.getSource().getLevel()).setMoonSpecific(id);
                return id;
            })))
        );
    }
}
