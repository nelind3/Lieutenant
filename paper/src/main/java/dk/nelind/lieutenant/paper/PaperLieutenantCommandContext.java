package dk.nelind.lieutenant.paper;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import dk.nelind.lieutenant.LieutenantSource;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public class PaperLieutenantCommandContext extends CommandContext<LieutenantSource> {
    private final CommandContext<CommandSourceStack> paperContext;

    public PaperLieutenantCommandContext(CommandContext<CommandSourceStack> paperContext, Command<LieutenantSource> lieutenantCommand) {
        super(
            new PaperLieutenantSource(paperContext.getSource()),
            paperContext.getInput(),
            null,
            lieutenantCommand,
            null,
            null,
            paperContext.getRange(),
            null,
            null,
            paperContext.isForked()
        );

        this.paperContext = paperContext;
    }

    @Override
    public <V> V getArgument(String name, Class<V> clazz) {
        return paperContext.getArgument(name, clazz);
    }
}
