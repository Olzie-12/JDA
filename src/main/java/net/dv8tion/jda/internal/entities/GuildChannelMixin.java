package net.dv8tion.jda.internal.entities;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.internal.requests.Route;
import net.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

public interface GuildChannelMixin<T extends GuildChannelMixin<T>> extends GuildChannel {
    // ---- Default implementations of interface ----
    @Override
    @Nonnull
    @CheckReturnValue
    default AuditableRestAction<Void> delete()
    {
        checkCanManage();

        Route.CompiledRoute route = Route.Channels.DELETE_CHANNEL.compile(getId());
        return new AuditableRestActionImpl<>(getJDA(), route);
    }

    // ---- Helpers ---
    default boolean hasPermission(Permission permission)
    {
        return getGuild().getSelfMember().hasPermission(this, permission);
    }

    default void checkPermission(Permission permission) { checkPermission(permission, null); }
    default void checkPermission(Permission permission, String message)
    {
        if (!hasPermission(permission))
        {
            if (message != null)
                throw new InsufficientPermissionException(this, permission, message);
            else
                throw new InsufficientPermissionException(this, permission);
        }
    }

    // Overridden by ThreadChannelImpl
    default void checkCanManage()
    {
        checkPermission(Permission.MANAGE_CHANNEL);
    }
}
