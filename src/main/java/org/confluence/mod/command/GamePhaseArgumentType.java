package org.confluence.mod.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class GamePhaseArgumentType implements ArgumentType<GamePhase> {
    private static final GamePhase[] VALUES = GamePhase.values();
    private static final Collection<String> EXAMPLES = Arrays.stream(VALUES).map(GamePhase::name).toList();
    private static final DynamicCommandExceptionType ERROR_INVALID = new DynamicCommandExceptionType(o ->
        Component.translatable("argument.confluence.gamephase.invalid", o));

    public static GamePhase getPhase(CommandContext<CommandSourceStack> context, String set) {
        return context.getArgument(set, GamePhase.class);
    }

    @Override
    public GamePhase parse(StringReader reader) throws CommandSyntaxException {
        String s = reader.readUnquotedString();
        try {
            return GamePhase.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw ERROR_INVALID.createWithContext(reader, s);
        }
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> pContext, SuggestionsBuilder pBuilder) {
        return SharedSuggestionProvider.suggest(Arrays.stream(VALUES).map(GamePhase::name), pBuilder);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static GamePhaseArgumentType gamePhase() {
        return new GamePhaseArgumentType();
    }
}
