package net.dv8tion.jda.internal.interactions;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.AutoCompleteQuery;
import net.dv8tion.jda.api.interactions.commands.*;
import net.dv8tion.jda.api.requests.restaction.interactions.AutoCompleteCallbackAction;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.requests.restaction.interactions.AutoCompleteCallbackActionImpl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class CommandAutoCompleteInteractionImpl extends CommandInteractionImpl implements CommandInteraction, CommandAutoCompleteInteraction
{
    private final CommandInteractionImpl payload;
    private AutoCompleteQuery focused;

    public CommandAutoCompleteInteractionImpl(JDAImpl jda, DataObject data)
    {
        super(jda, data);
        this.payload = new CommandInteractionImpl(jda, data);

        DataArray options = data.getObject("data").getArray("options");
        findFocused(options);

        if (focused == null)
            throw new IllegalStateException("Failed to get focused option for auto complete interaction");
    }

    private void findFocused(DataArray options)
    {
        for (int i = 0; i < options.length(); i++)
        {
            DataObject option = options.getObject(i);
            switch (OptionType.fromKey(option.getInt("type")))
            {
            case SUB_COMMAND:
            case SUB_COMMAND_GROUP:
                findFocused(option.getArray("options"));
                break;
            default:
                if (option.getBoolean("focused"))
                {
                    OptionMapping opt = getOption(option.getString("name"));
                    focused = new AutoCompleteQuery(opt);
                    break;
                }
            }
        }
    }

    @Nonnull
    @Override
    public AutoCompleteQuery getFocusedOption()
    {
        return focused;
    }

    @Nonnull
    @Override
    @SuppressWarnings("ConstantConditions")
    public TextChannel getChannel()
    {
        return (TextChannel) super.getChannel();
    }

    public CommandInteractionImpl getCommandPayload()
    {
        return payload;
    }

    @Nonnull
    @Override
    public AutoCompleteCallbackAction replyChoices(@Nonnull Collection<Command.Choice> choices)
    {
        return new AutoCompleteCallbackActionImpl(this, focused.getType()).addChoices(choices);
    }

    @Nonnull
    @Override
    public String getName()
    {
        return getCommandPayload().getName();
    }

    @Nullable
    @Override
    public String getSubcommandName()
    {
        return getCommandPayload().getSubcommandName();
    }

    @Nullable
    @Override
    public String getSubcommandGroup()
    {
        return getCommandPayload().getSubcommandGroup();
    }

    @Override
    public long getCommandIdLong()
    {
        return getCommandPayload().getCommandIdLong();
    }

    @Nonnull
    @Override
    public List<OptionMapping> getOptions()
    {
        return getCommandPayload().getOptions();
    }
}
