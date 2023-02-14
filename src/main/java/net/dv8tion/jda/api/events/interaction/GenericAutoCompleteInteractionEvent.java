package net.dv8tion.jda.api.events.interaction;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.IAutoCompleteCallback;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.interactions.AutoCompleteCallbackAction;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Indicates that a user is typing in an auto-complete interactive field.
 *
 * <p><b>Requirements</b><br>
 * To receive these events, you must unset the <b>Interactions Endpoint URL</b> in your application dashboard.
 * You can simply remove the URL for this endpoint in your settings at the <a href="https://discord.com/developers/applications" target="_blank">Discord Developers Portal</a>.
 *
 * @see IAutoCompleteCallback
 * @see OptionData#setAutoComplete(boolean)
 */
public class GenericAutoCompleteInteractionEvent extends GenericInteractionCreateEvent implements IAutoCompleteCallback
{
    public GenericAutoCompleteInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull Interaction interaction)
    {
        super(api, responseNumber, interaction);
    }

    @Nonnull
    @Override
    public IAutoCompleteCallback getInteraction()
    {
        return (IAutoCompleteCallback) super.getInteraction();
    }

    @Nonnull
    @Override
    public AutoCompleteCallbackAction replyChoices(@Nonnull Collection<Command.Choice> choices)
    {
        return getInteraction().replyChoices(choices);
    }
}

