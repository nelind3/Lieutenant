package dk.nelind.lieutenant.paper;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import dk.nelind.lieutenant.Lieutenant;
import dk.nelind.lieutenant.LieutenantSource;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class PaperLieutenant extends Lieutenant<CommandSourceStack> {
    @Override
    public LiteralArgumentBuilder<CommandSourceStack> platformLiteral(String literal) {
        return Commands.literal(literal);
    }

    @Override
    public <A> RequiredArgumentBuilder<CommandSourceStack, A> platformArgument(String name, ArgumentType<A> argumentType) {
        return Commands.argument(name, argumentType);
    }

    @Override
    public Command<CommandSourceStack> convertCommand(Command<LieutenantSource> lieutenantCommand) {
        return ctx -> lieutenantCommand.run(new PaperLieutenantCommandContext(ctx));
    }
}
