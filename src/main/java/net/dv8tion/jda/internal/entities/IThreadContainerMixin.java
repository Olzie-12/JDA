package net.dv8tion.jda.internal.entities;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.IThreadContainer;
import net.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import net.dv8tion.jda.internal.requests.restaction.ThreadChannelActionImpl;
import net.dv8tion.jda.internal.utils.Checks;

import javax.annotation.Nonnull;

public interface IThreadContainerMixin<T extends IThreadContainerMixin<T>> extends IThreadContainer, GuildChannelMixin<T> {
    // ---- Default implementations of interface ----
    @Nonnull
    @Override
    default ThreadChannelAction createThreadChannel(@Nonnull String name, boolean isPrivate)
    {
        Checks.notNull(name, "Name");
        name = name.trim();
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 100, "Name");

        Checks.checkAccess(getGuild().getSelfMember(), this);
        if (isPrivate)
            checkPermission(Permission.CREATE_PRIVATE_THREADS);
        else
            checkPermission(Permission.CREATE_PUBLIC_THREADS);

        ChannelType threadType = isPrivate ? ChannelType.GUILD_PRIVATE_THREAD : getType() == ChannelType.TEXT ? ChannelType.GUILD_PUBLIC_THREAD : ChannelType.GUILD_NEWS_THREAD;
        return new ThreadChannelActionImpl(this, name, threadType);
    }

    @Nonnull
    @Override
    default ThreadChannelAction createThreadChannel(@Nonnull String name, long messageId)
    {
        Checks.notNull(name, "Name");
        name = name.trim();
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 100, "Name");

        Checks.checkAccess(getGuild().getSelfMember(), this);
        checkPermission(Permission.CREATE_PUBLIC_THREADS);

        return new ThreadChannelActionImpl(this, name, Long.toUnsignedString(messageId));
    }

    T setDefaultThreadSlowmode(int slowmode);
}
