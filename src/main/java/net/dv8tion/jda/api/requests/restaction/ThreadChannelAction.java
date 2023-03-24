package net.dv8tion.jda.api.requests.restaction;


import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.ThreadChannel;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * Extension of {@link net.dv8tion.jda.api.requests.RestAction RestAction} specifically
 * designed to create a {@link ThreadChannel ThreadChannel}.
 * This extension allows setting properties before executing the action.
 *
 * @see    Message#createThreadChannel(String)
 * @see    net.dv8tion.jda.api.entities.channel.attribute.IThreadContainer#createThreadChannel(String)
 * @see    net.dv8tion.jda.api.entities.channel.attribute.IThreadContainer#createThreadChannel(String, boolean)
 * @see    net.dv8tion.jda.api.entities.channel.attribute.IThreadContainer#createThreadChannel(String, long)
 * @see    net.dv8tion.jda.api.entities.channel.attribute.IThreadContainer#createThreadChannel(String, String)
 */public interface ThreadChannelAction extends AbstractThreadCreateAction<ThreadChannel, ThreadChannelAction>, FluentAuditableRestAction<ThreadChannel, ThreadChannelAction>
{
    //TODO-v5: Docs
    @Nonnull
    @CheckReturnValue
    ThreadChannelAction setInvitable(boolean isInvitable);
}
