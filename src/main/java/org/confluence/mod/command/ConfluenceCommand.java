package org.confluence.mod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.server.command.EnumArgument;

public class ConfluenceCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("confluence").requires(sourceStack -> sourceStack.hasPermission(2))
            .then(Commands.literal("specificMoon").then(Commands.argument("set", EnumArgument.enumArgument(SpecificMoon.class)).executes(context -> {
                SpecificMoon moon = context.getArgument("set", SpecificMoon.class);
                ConfluenceData.get(context.getSource().getLevel()).setSpecificMoon(moon);
                return moon.getId();
            })).executes(context -> {
                SpecificMoon moon = ConfluenceData.get(context.getSource().getLevel()).getSpecificMoon();
                context.getSource().sendSystemMessage(Component.literal("Specific Moon: " + moon.getSerializedName()));
                return moon.getId();
            }))
            .then(Commands.literal("gamePhase").then(Commands.argument("set", EnumArgument.enumArgument(GamePhase.class)).executes(context -> {
                GamePhase phase = context.getArgument("set", GamePhase.class);
                ConfluenceData.get(context.getSource().getLevel()).setGamePhase(phase);
                return phase.ordinal();
            })).executes(context -> {
                GamePhase phase = ConfluenceData.get(context.getSource().getLevel()).getGamePhase();
                context.getSource().sendSystemMessage(Component.literal("Game Phase: " + phase.name()));
                return phase.ordinal();
            }))
        );
    }
}
