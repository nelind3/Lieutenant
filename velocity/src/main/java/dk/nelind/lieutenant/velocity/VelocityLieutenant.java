package dk.nelind.lieutenant.velocity;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import dk.nelind.lieutenant.Lieutenant;
import dk.nelind.lieutenant.LieutenantSource;

import java.util.function.Predicate;

/**
 * The Velocity Lieutenant used to register Lieutenant commands during Velocity command registration
 */
public class VelocityLieutenant extends Lieutenant<CommandSource> {
    private static final VelocityLieutenant INSTANCE = new VelocityLieutenant();

    /**
     * Converts a Lieutenant command to a Velocity command.
     * @param lieutenantCommand The Lieutenant command to convert.
     * @return The converted Velocity command as a {@link BrigadierCommand}
     */
    public static BrigadierCommand toVelocityCommand(LiteralArgumentBuilder<LieutenantSource> lieutenantCommand) {
        return new BrigadierCommand(INSTANCE.convert(lieutenantCommand).build());
    }

    @Override
    protected LiteralArgumentBuilder<CommandSource> platformLiteral(String literal) {
        return BrigadierCommand.literalArgumentBuilder(literal);
    }

    @Override
    protected <A> RequiredArgumentBuilder<CommandSource, A> platformArgument(String name, ArgumentType<A> argumentType) {
        return BrigadierCommand.requiredArgumentBuilder(name, argumentType);
    }

    @Override
    protected Command<CommandSource> convertCommand(Command<LieutenantSource> lieutenantCommand) {
        return ctx ->
            lieutenantCommand.run(new VelocityLieutenantCommandContext(ctx, lieutenantCommand));
    }

    @Override
    protected SuggestionProvider<CommandSource> convertSuggestionProvider(
        SuggestionProvider<LieutenantSource> lieutenantSuggestionProvider,
        Command<LieutenantSource> lieutenantCommand
    ) {
        return (ctx, bldr) -> lieutenantSuggestionProvider.getSuggestions(
            new VelocityLieutenantCommandContext(ctx, lieutenantCommand),
            bldr
        );
    }

    @Override
    protected Predicate<CommandSource> convertRequirement(Predicate<LieutenantSource> lieutenantRequirement) {
        return source -> lieutenantRequirement.test(new VelocityLieutenantSource(source));
    }
}
