package net.dv8tion.jda.internal.requests.restaction.interactions;

import net.dv8tion.jda.api.interactions.IAutoCompleteCallback;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.interactions.AutoCompleteCallbackAction;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.interactions.CommandAutoCompleteInteractionImpl;
import net.dv8tion.jda.internal.interactions.InteractionHookImpl;
import net.dv8tion.jda.internal.interactions.InteractionImpl;
import net.dv8tion.jda.internal.utils.Checks;
import okhttp3.RequestBody;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;

public class AutoCompleteCallbackActionImpl extends InteractionCallbackActionImpl implements AutoCompleteCallbackAction
{
    private final OptionType type;
    private final List<Command.Choice> choices = new ArrayList<>(26);

    public AutoCompleteCallbackActionImpl(IAutoCompleteCallback interaction, OptionType type)
    {
        super((InteractionHookImpl) interaction.getHook());
        this.type = type;
    }

    @Nonnull
    @Override
    public OptionType getOptionType()
    {
        return type;
    }

    @Nonnull
    @Override
    public AutoCompleteCallbackAction addChoices(@Nonnull Collection<Command.Choice> choices)
    {
        Checks.noneNull(choices, "Choices");
        Checks.check(choices.size() + this.choices.size() <= OptionData.MAX_CHOICES,
                "Can only reply with up to %d choices. Limit your suggestions!", OptionData.MAX_CHOICES);
        for (Command.Choice choice : choices)
        {
            Checks.inRange(choice.getName(), 1, OptionData.MAX_CHOICE_NAME_LENGTH, "Choice name");

            switch (type)
            {
            case INTEGER:
                Checks.check(choice.getType() == OptionType.INTEGER,
                        "Choice of type %s cannot be converted to INTEGER", choice.getType());
                long valueLong = choice.getAsLong();
                Checks.check(valueLong <= OptionData.MAX_POSITIVE_NUMBER,
                        "Choice value cannot be larger than %d Provided: %d",
                        OptionData.MAX_POSITIVE_NUMBER, valueLong);
                Checks.check(valueLong >= OptionData.MIN_NEGATIVE_NUMBER,
                        "Choice value cannot be smaller than %d. Provided: %d",
                        OptionData.MIN_NEGATIVE_NUMBER, valueLong);
                break;
            case NUMBER:
                Checks.check(choice.getType() == OptionType.NUMBER || choice.getType() == OptionType.INTEGER,
                        "Choice of type %s cannot be converted to NUMBER", choice.getType());
                double valueDouble = choice.getAsDouble();
                Checks.check(valueDouble <= OptionData.MAX_POSITIVE_NUMBER,
                        "Choice value cannot be larger than %d Provided: %d",
                        OptionData.MAX_POSITIVE_NUMBER, valueDouble);
                Checks.check(valueDouble >= OptionData.MIN_NEGATIVE_NUMBER,
                        "Choice value cannot be smaller than %d. Provided: %d",
                        OptionData.MIN_NEGATIVE_NUMBER, valueDouble);
                break;
            case STRING:
                // String can be any type, we just toString it
                String valueString = choice.getAsString();
                Checks.inRange(valueString, 1, OptionData.MAX_CHOICE_VALUE_LENGTH, "Choice value");
                break;
            }
        }
        this.choices.addAll(choices);
        return this;
    }

    @Override
    protected DataObject toData() {
        DataObject data = DataObject.empty();
        DataArray array = DataArray.empty();
        DataObject payload = DataObject.empty();
        choices.forEach(choice -> array.add(choice.toData(type)));
        payload.put("choices", array);
        data.put("type", ResponseType.COMMAND_AUTOCOMPLETE_CHOICES.getRaw());
        data.put("data", payload);
        return data;
    }

    @Nonnull
    @Override
    public AutoCompleteCallbackAction setCheck(BooleanSupplier checks)
    {
        return (AutoCompleteCallbackAction) super.setCheck(checks);
    }

    @Nonnull
    @Override
    public AutoCompleteCallbackAction deadline(long timestamp)
    {
        return (AutoCompleteCallbackAction) super.deadline(timestamp);
    }
}
