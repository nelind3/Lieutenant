package dk.nelind.lieutenant;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;

/**
 * The core Lieutenant class. Provides builder methods {@link #literal(String)} and
 * {@link #argument(String, ArgumentType)} for building Lieutenant commands in a platform-agnostic core as well as the
 * core infrastructure that platform specific subclasses use to register Lieutenant commands as platform native commands.
 * @param <S> The platforms native command source
 */
public abstract class Lieutenant<S> {
    /**
     * Utility to create a literal command node builder with the Lieutenant command source.
     *
     * @param name literal name
     * @return a new builder instance
     */
    public static LiteralArgumentBuilder<LieutenantSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    /**
     * Utility to create a required argument builder with the Lieutenant command source.
     *
     * @param name the name of the argument
     * @param type the type of the argument
     * @param <T>  the generic type of the argument value
     * @return a new required argument builder
     */
    public static <T> RequiredArgumentBuilder<LieutenantSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    /**
     * Converts a Lieutenant command builder to the platform native command builder
     *
     * @param lieutenantCommand the Lieutenant command to be converted
     * @return the converted platform native command
     */
    public LiteralArgumentBuilder<S> convert(LiteralArgumentBuilder<LieutenantSource> lieutenantCommand) {
        return this.convertLiteral(lieutenantCommand.build());
    }

    private LiteralArgumentBuilder<S> convertLiteral(LiteralCommandNode<LieutenantSource> lieutenantCommand) {
        var platformLiteral = this.platformLiteral(lieutenantCommand.getLiteral());
        return this.convertNode(lieutenantCommand, platformLiteral);
    }

    private <T> RequiredArgumentBuilder<S, T> convertArgument(ArgumentCommandNode<LieutenantSource, T> lieutenantArgument) {
        var platformArgument = this.platformArgument(lieutenantArgument.getName(), lieutenantArgument.getType());
        platformArgument.suggests(this.convertSuggestionProvider(
            lieutenantArgument.getCustomSuggestions(),
            lieutenantArgument.getCommand()
        ));
        return this.convertNode(lieutenantArgument, platformArgument);
    }

    private <B extends ArgumentBuilder<S, B>> B convertNode(CommandNode<LieutenantSource> lieutenantNode, B platformNodeBuilder) {
        for (var child : lieutenantNode.getChildren()) {
            if (child instanceof LiteralCommandNode<LieutenantSource> literalChild) {
                platformNodeBuilder.then(this.convertLiteral(literalChild));
            } else if (child instanceof ArgumentCommandNode<LieutenantSource, ?> argumentChild) {
                platformNodeBuilder.then(this.convertArgument(argumentChild));
            }
        }

        if (lieutenantNode.getCommand() != null) {
            platformNodeBuilder.executes(this.convertCommand(lieutenantNode.getCommand()));
        }

        return platformNodeBuilder;
    }

    protected abstract LiteralArgumentBuilder<S> platformLiteral(String literal);

    protected abstract <A> RequiredArgumentBuilder<S, A> platformArgument(String name, ArgumentType<A> argumentType);

    protected abstract Command<S> convertCommand(Command<LieutenantSource> lieutenantCommand);

    protected abstract SuggestionProvider<S> convertSuggestionProvider(
        SuggestionProvider<LieutenantSource> lieutenantSuggestionProvider,
        Command<LieutenantSource> lieutenantCommand
    );
}
