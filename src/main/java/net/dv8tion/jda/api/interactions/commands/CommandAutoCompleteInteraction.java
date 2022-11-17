package net.dv8tion.jda.api.interactions.commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.AutoCompleteQuery;
import net.dv8tion.jda.api.interactions.IAutoCompleteCallback;
import net.dv8tion.jda.api.interactions.Interaction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface CommandAutoCompleteInteraction extends IAutoCompleteCallback, Interaction
{
    /**
     * The focused option which the user is typing.
     *
     * <p>This is not validated by the Discord API and may contain invalid/incomplete inputs.
     *
     * @return The focused {@link AutoCompleteQuery}
     */
    @Nonnull
    AutoCompleteQuery getFocusedOption();

    @Nullable
    @Override
    TextChannel getChannel();
}
