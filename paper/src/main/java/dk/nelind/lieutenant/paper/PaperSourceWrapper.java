package dk.nelind.lieutenant.paper;

import dk.nelind.lieutenant.LieutenantSource;
import io.papermc.paper.command.brigadier.CommandSourceStack;


public class PaperSourceWrapper implements LieutenantSource {
    private CommandSourceStack wrapped;

    public PaperSourceWrapper(CommandSourceStack paperSource) {
        this.wrapped = paperSource;
    }
}
