package net.dv8tion.jda.api.interactions;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

import javax.annotation.Nonnull;
import java.util.Objects;

public class AutoCompleteQuery
{
    private final String name;
    private final String value;
    private final OptionType type;

    public AutoCompleteQuery(@Nonnull OptionMapping option)
    {
        this.name = option.getName();
        this.value = option.getAsString();
        this.type = option.getType();
    }

    /**
     * The name of the input field, usually an option name in {@link CommandAutoCompleteInteraction}.
     *
     * @return The option name
     */
    @Nonnull
    public String getName()
    {
        return name;
    }

    /**
     * The query value that the user is currently typing.
     *
     * <p>This is not validated and may not be a valid value for an actual command.
     * For instance, a user may input invalid numbers for {@link OptionType#NUMBER}.
     *
     * @return The current auto-completable query value
     */
    @Nonnull
    public String getValue()
    {
        return value;
    }

    /**
     * The expected option type for this query.
     *
     * @return The option type expected from this auto-complete response
     */
    @Nonnull
    public OptionType getType()
    {
        return type;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, value, type);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;
        if (!(obj instanceof AutoCompleteQuery))
            return false;
        AutoCompleteQuery query = (AutoCompleteQuery) obj;
        return type == query.type && name.equals(query.name) && value.equals(query.value);
    }
}
