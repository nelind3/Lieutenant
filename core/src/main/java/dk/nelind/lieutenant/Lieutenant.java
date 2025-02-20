package dk.nelind.lieutenant;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;

public abstract class Lieutenant<S> {
    public static LiteralArgumentBuilder<LieutenantSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    public static <T> RequiredArgumentBuilder<LieutenantSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    public LiteralArgumentBuilder<S> convert(LiteralArgumentBuilder<LieutenantSource> lieutenantCommandNode) {
        return this.convertLiteral(lieutenantCommandNode.build());
    }

    private LiteralArgumentBuilder<S> convertLiteral(LiteralCommandNode<LieutenantSource> lieutenantCommand) {
        var platformLiteral = this.platformLiteral(lieutenantCommand.getLiteral());
        return this.convertNode(lieutenantCommand, platformLiteral);
    }

    private <T> RequiredArgumentBuilder<S, T> convertArgument(ArgumentCommandNode<LieutenantSource, T> lieutenantArgument) {
        var platformArgument = this.platformArgument(lieutenantArgument.getName(), lieutenantArgument.getType());
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

    public abstract LiteralArgumentBuilder<S> platformLiteral(String literal);

    public abstract <A> RequiredArgumentBuilder<S, A> platformArgument(String name, ArgumentType<A> argumentType);

    public abstract Command<S> convertCommand(Command<LieutenantSource> lieutenantCommand);
}
