package net.dv8tion.jda.api.requests.restaction;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.ThreadChannel;
import net.dv8tion.jda.api.requests.RestAction;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
public interface AbstractThreadCreateAction<T, R extends AbstractThreadCreateAction<T, R>> extends RestAction<T>
{
    /**
     * The guild to create this {@link net.dv8tion.jda.api.entities.GuildChannel} for.
     *
     * @return The guild
     */
    @Nonnull
    Guild getGuild();

    /**
     * The {@link ChannelType} for the resulting channel.
     *
     * @return The channel type
     */
    @Nonnull
    ChannelType getType();

    /**
     * Sets the name for the new GuildChannel.
     *
     * @param  name
     *         The not-null name for the new GuildChannel (up to {@value net.dv8tion.jda.api.entities.AbstractChannel#MAX_NAME_LENGTH} characters)
     *
     * @throws IllegalArgumentException
     *         If the provided name is null, empty, or longer than {@value net.dv8tion.jda.api.entities.AbstractChannel#MAX_NAME_LENGTH} characters
     *
     * @return The current action, for chaining convenience
     */
    @Nonnull
    @CheckReturnValue
    R setName(@Nonnull String name);

    /**
     * Sets the {@link ThreadChannel.AutoArchiveDuration} for the new thread.
     * <br>This is primarily used to <em>hide</em> threads after the provided time of inactivity.
     * Threads are automatically archived after 7 days of inactivity regardless.
     *
     * @param  autoArchiveDuration
     *         The new archive inactivity duration (which hides the thread)
     *
     * @throws IllegalArgumentException
     *         If the provided duration is null
     *
     * @return The current action, for chaining convenience
     */
    @Nonnull
    @CheckReturnValue
    R setAutoArchiveDuration(@Nonnull ThreadChannel.AutoArchiveDuration autoArchiveDuration);
}
