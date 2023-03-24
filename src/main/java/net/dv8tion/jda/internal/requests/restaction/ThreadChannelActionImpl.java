package net.dv8tion.jda.internal.requests.restaction;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.Request;
import net.dv8tion.jda.api.requests.Response;
import net.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.requests.Route;
import net.dv8tion.jda.internal.utils.Checks;
import okhttp3.RequestBody;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class ThreadChannelActionImpl extends AuditableRestActionImpl<ThreadChannel> implements ThreadChannelAction
{
    protected final Guild guild;
    protected final ChannelType type;
    protected final String parentMessageId;

    protected String name;
    protected ThreadChannel.AutoArchiveDuration autoArchiveDuration = null;
    protected Boolean invitable = null;

    public ThreadChannelActionImpl(GuildChannel channel, String name, ChannelType type)
    {
        super(channel.getJDA(), Route.Channels.CREATE_THREAD.compile(channel.getId()));
        this.guild = channel.getGuild();
        this.type = type;
        this.parentMessageId = null;

        this.name = name;
    }

    public ThreadChannelActionImpl(GuildChannel channel, String name, String parentMessageId)
    {
        super(channel.getJDA(), Route.Channels.CREATE_THREAD_FROM_MESSAGE.compile(channel.getId(), parentMessageId));
        this.guild = channel.getGuild();
        this.type = channel.getType() == ChannelType.TEXT ? ChannelType.GUILD_PUBLIC_THREAD : ChannelType.GUILD_NEWS_THREAD;
        this.parentMessageId = parentMessageId;

        this.name = name;
    }

    @Nonnull
    @Override
    public ThreadChannelActionImpl reason(String reason)
    {
        return (ThreadChannelActionImpl) super.reason(reason);
    }

    @Nonnull
    @Override
    public ThreadChannelActionImpl setCheck(BooleanSupplier checks)
    {
        return (ThreadChannelActionImpl) super.setCheck(checks);
    }

    @Nonnull
    @Override
    public ThreadChannelActionImpl timeout(long timeout, @Nonnull TimeUnit unit)
    {
        return (ThreadChannelActionImpl) super.timeout(timeout, unit);
    }

    @Nonnull
    @Override
    public ThreadChannelActionImpl deadline(long timestamp)
    {
        return (ThreadChannelActionImpl) super.deadline(timestamp);
    }

    @Nonnull
    @Override
    public Guild getGuild()
    {
        return guild;
    }

    @Nonnull
    @Override
    public ChannelType getType()
    {
        return type;
    }

    @Nonnull
    @Override
    @CheckReturnValue
    public ThreadChannelActionImpl setName(@Nonnull String name)
    {
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, AbstractChannel.MAX_NAME_LENGTH, "Name");
        this.name = name;
        return this;
    }

    @Nonnull
    @Override
    public ThreadChannelAction setAutoArchiveDuration(@Nonnull ThreadChannel.AutoArchiveDuration autoArchiveDuration)
    {
        Checks.notNull(autoArchiveDuration, "autoArchiveDuration");
        this.autoArchiveDuration = autoArchiveDuration;
        return this;
    }

    @Nonnull
    @Override
    public ThreadChannelAction setInvitable(boolean invitable)
    {
        if (type != ChannelType.GUILD_PRIVATE_THREAD)
            throw new UnsupportedOperationException("Can only set invitable on private threads");

        this.invitable = invitable;
        return this;
    }

    @Override
    protected RequestBody finalizeData()
    {
        DataObject object = DataObject.empty();

        object.put("name", name);

        //The type is selected by discord itself if we are using a parent message, so don't send it.
        if (parentMessageId == null)
            object.put("type", type.getId());

        if (autoArchiveDuration != null)
            object.put("auto_archive_duration", autoArchiveDuration.getMinutes());
        if (invitable != null)
            object.put("invitable", invitable);

        return getRequestBody(object);
    }

    @Override
    protected void handleSuccess(Response response, Request<ThreadChannel> request)
    {
        ThreadChannel channel = api.getEntityBuilder().createThreadChannel(response.getObject(), guild.getIdLong());
        request.onSuccess(channel);
    }
}
