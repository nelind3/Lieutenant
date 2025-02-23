package dk.nelind.lieutenant.paper;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dk.nelind.lieutenant.Lieutenant;
import dk.nelind.lieutenant.LieutenantSource;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

/**
 * The Paper Lieutenant used to register Lieutenant commands during Paper brigadier command registration
 */
public class PaperLieutenant extends Lieutenant<CommandSourceStack> {
    private static final PaperLieutenant INSTANCE = new PaperLieutenant();

    /**
     * Converts a Lieutenant command to a Paper command.
     * @param lieutenantCommand The Lieutenant command to convert.
     * @return The converted Paper command as a built node.
     */
    public static LiteralCommandNode<CommandSourceStack> toPaperCommand(LiteralArgumentBuilder<LieutenantSource> lieutenantCommand) {
        return INSTANCE.convert(lieutenantCommand).build();
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> platformLiteral(String literal) {
        return Commands.literal(literal);
    }

    @Override
    protected  <A> RequiredArgumentBuilder<CommandSourceStack, A> platformArgument(String name, ArgumentType<A> argumentType) {
        return Commands.argument(name, argumentType);
    }

    @Override
    protected Command<CommandSourceStack> convertCommand(Command<LieutenantSource> lieutenantCommand) {
        return ctx ->
            lieutenantCommand.run(new PaperLieutenantCommandContext(ctx, lieutenantCommand));
    }

    @Override
    protected SuggestionProvider<CommandSourceStack> convertSuggestionProvider(
        SuggestionProvider<LieutenantSource> lieutenantSuggestionProvider,
        Command<LieutenantSource> lieutenantCommand
    ) {
        return (ctx, bldr) -> lieutenantSuggestionProvider.getSuggestions(
            new PaperLieutenantCommandContext(ctx, lieutenantCommand),
            bldr
        );
    }
}
