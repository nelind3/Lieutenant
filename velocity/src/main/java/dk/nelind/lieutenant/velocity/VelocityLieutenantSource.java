package dk.nelind.lieutenant.velocity;

import com.velocitypowered.api.command.CommandSource;
import dk.nelind.lieutenant.LieutenantSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.util.TriState;
import org.jetbrains.annotations.NotNull;

public class VelocityLieutenantSource implements LieutenantSource {
    private CommandSource wrapped;

    public VelocityLieutenantSource(CommandSource velocitySource) {
        this.wrapped = velocitySource;
    }

    @Override
    public Audience audience() {
        return this.wrapped;
    }

    @Override
    public @NotNull TriState value(@NotNull String permission) {
        return this.wrapped.getPermissionChecker().value(permission);
    }

    @Override
    public boolean test(@NotNull String permission) {
        return this.wrapped.hasPermission(permission);
    }
}
