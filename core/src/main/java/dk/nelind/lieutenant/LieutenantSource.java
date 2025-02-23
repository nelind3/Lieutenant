package dk.nelind.lieutenant;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.permission.PermissionChecker;
import net.kyori.adventure.text.Component;

/**
 * The Lieutenant source interface.
 *
 * <p>Lieutenant commands are provided with command sources of this type either as a
 * wrapper around the native command source or implemented directly on the native source.</p>
 * <p>This source provides access to the {@link Audience} of the sender of the command and is a
 * {@link PermissionChecker} of the sender</p>
 *
 * @see Audience
 * @see PermissionChecker
 * @see Lieutenant
 */
public interface LieutenantSource extends PermissionChecker {
    /**
     * Gets the {@link Audience} of the command sender. If the platform distinguishes between the "sender" and
     * "executor" of a command (these may be different in cases like the execute command) this method returns the
     * sender {@link Audience}.
     * @return The {@link Audience} of the command sender.
     */
    Audience audience();

    /**
     * Sends a message to the {@link Audience} provided by {@link #audience()}
     * @param source the source of the message
     * @param message a message
     */
    default void sendMessage(Identified source, Component message) {
        this.audience().sendMessage(source, message);
    }

    /**
     * Sends a message to the {@link Audience} provided by {@link #audience()}
     * @param source the source of the message
     * @param message a message
     */
    default void sendMessage(Identity source, Component message) {
        this.audience().sendMessage(source, message);
    }

    /**
     * Sends a message to the {@link Audience} provided by {@link #audience()}
     * @param message a message
     */
    default void sendMessage(Component message) {
        this.audience().sendMessage(message);
    }
}
