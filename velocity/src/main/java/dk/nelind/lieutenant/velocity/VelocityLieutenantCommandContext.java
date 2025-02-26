package dk.nelind.lieutenant.velocity;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import dk.nelind.lieutenant.LieutenantSource;

public class VelocityLieutenantCommandContext extends CommandContext<LieutenantSource> {
    private final CommandContext<CommandSource> velocityContext;

    public VelocityLieutenantCommandContext(CommandContext<CommandSource> velocityContext, Command<LieutenantSource> lieutenantCommand) {
        super(
            new VelocityLieutenantSource(velocityContext.getSource()),
            velocityContext.getInput(),
            null,
            lieutenantCommand,
            null,
            null,
            velocityContext.getRange(),
            null,
            null,
            velocityContext.isForked()
        );

        this.velocityContext = velocityContext;
    }

    @Override
    public <V> V getArgument(String name, Class<V> clazz) {
        return velocityContext.getArgument(name, clazz);
    }
}
