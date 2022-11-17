package net.dv8tion.jda.api.events.interaction;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.internal.interactions.CommandInteractionImpl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CommandAutoCompleteInteractionEvent extends GenericInteractionCreateEvent implements CommandInteraction
{
    private final CommandInteractionImpl commandInteraction;

    public CommandAutoCompleteInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull CommandInteractionImpl interaction)
    {
        super(api, responseNumber, interaction);
        this.commandInteraction = interaction;
    }

    @Nonnull
    @Override
    public MessageChannel getChannel()
    {
        return commandInteraction.getChannel();
    }

    @Nonnull
    @Override
    public String getName()
    {
        return commandInteraction.getName();
    }

    @Nullable
    @Override
    public String getSubcommandName()
    {
        return commandInteraction.getSubcommandName();
    }

    @Nullable
    @Override
    public String getSubcommandGroup()
    {
        return commandInteraction.getSubcommandGroup();
    }

    @Override
    public long getCommandIdLong()
    {
        return commandInteraction.getCommandIdLong();
    }

    @Nonnull
    @Override
    public List<OptionMapping> getOptions()
    {
        return commandInteraction.getOptions();
    }

    @Nonnull
    @Override
    public String getCommandString()
    {
        return commandInteraction.getCommandString();
    }
}
