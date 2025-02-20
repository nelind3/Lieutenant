package dk.nelind.lieutenant.paper;

import com.mojang.brigadier.context.CommandContext;
import dk.nelind.lieutenant.LieutenantSource;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public class PaperLieutenantCommandContext extends CommandContext<LieutenantSource> {
    public PaperLieutenantCommandContext(CommandContext<CommandSourceStack> paperContext) {
        super(
            new PaperSourceWrapper(paperContext.getSource()),
            paperContext.getInput(),
            null,
            null,
            null,
            null,
            paperContext.getRange(),
            null,
            null,
            paperContext.isForked()
        );
    }
}
