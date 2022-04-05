/*
 * Copyright 2015 Austin Keener, Michael Ritter, Florian Spieß, and the JDA contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dv8tion.jda.internal.handle;

import net.dv8tion.jda.api.entities.GuildScheduledEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.GuildScheduledEventCreateEvent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.entities.GuildImpl;

public class GuildScheduledEventCreateHandler extends SocketHandler
{
    public GuildScheduledEventCreateHandler(JDAImpl api)
    {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content)
    {
        if (!getJDA().isCacheFlagSet(CacheFlag.GUILD_SCHEDULED_EVENTS))
            return null;
        long guildId = content.getUnsignedLong("guild_id", 0L);
        if (getJDA().getGuildSetupController().isLocked(guildId))
            return guildId;

        GuildImpl guild = (GuildImpl) getJDA().getGuildById(guildId);
        if (guild == null)
        {
            EventCache.LOG.debug("Caching GUILD_SCHEDULED_EVENT_CREATE for uncached guild with id {}", guildId);
            getJDA().getEventCache().cache(EventCache.Type.GUILD_SCHEDULED_EVENT, guildId, responseNumber, allContent, this::handle);
            return null;
        }

        GuildScheduledEvent event = getJDA().getEntityBuilder().createGuildScheduledEvent(guild, content, guildId);
        if (event != null)
        {
            getJDA().handleEvent(new GuildScheduledEventCreateEvent(getJDA(), responseNumber, event));
        }
        return null;
    }
}
