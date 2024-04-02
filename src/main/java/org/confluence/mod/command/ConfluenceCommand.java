package org.confluence.mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ConfluenceCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("confluence").requires(sourceStack -> sourceStack.hasPermission(2))
            .then(Commands.literal("specificMoon").then(Commands.argument("set", IntegerArgumentType.integer(-1, 11)).executes(context -> {
                int id = IntegerArgumentType.getInteger(context, "set");
                ConfluenceData.get(context.getSource().getLevel()).setMoonSpecific(id);
                return id;
            })).executes(context -> {
                int id = ConfluenceData.get(context.getSource().getLevel()).getMoonSpecific();
                context.getSource().sendSystemMessage(Component.literal("Specific Moon: " + id));
                return id;
            }))
            .then(Commands.literal("gamePhase").then(Commands.argument("set", IntegerArgumentType.integer(0, 6)).executes(context -> {
                int phase = IntegerArgumentType.getInteger(context, "set");
                ConfluenceData.get(context.getSource().getLevel()).setGamePhase(phase);
                return phase;
            })).executes(context -> {
                int phase = ConfluenceData.get(context.getSource().getLevel()).getGamePhase();
                context.getSource().sendSystemMessage(Component.literal("Game Phase: " + phase));
                return phase;
            }))
        );
    }
}
