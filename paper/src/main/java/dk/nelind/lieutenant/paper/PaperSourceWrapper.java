package dk.nelind.lieutenant.paper;

import dk.nelind.lieutenant.LieutenantSource;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.util.TriState;
import org.jetbrains.annotations.NotNull;

public class PaperSourceWrapper implements LieutenantSource {
    private CommandSourceStack wrapped;

    public PaperSourceWrapper(CommandSourceStack paperSource) {
        this.wrapped = paperSource;
    }

    @Override
    public Audience audience() {
        return this.wrapped.getSender();
    }

    @Override
    public @NotNull TriState value(@NotNull String permission) {
        return this.wrapped.getSender().permissionValue(permission);
    }
}
