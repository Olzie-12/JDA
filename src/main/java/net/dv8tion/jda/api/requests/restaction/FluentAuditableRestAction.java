package net.dv8tion.jda.api.requests.restaction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

/**
 * Interface used to mixin the customization parameters for {@link AuditableRestAction AuditableRestActions}.
 * <br>This simply fixes the return types to be the concrete implementation instead of the base interface.
 *
 * @param <T>
 *        The result type of the AuditableRestAction
 * @param <R>
 *        The concrete AuditableRestAction type used for chaining (fluent interface)
 */
@SuppressWarnings("unchecked")
public interface FluentAuditableRestAction<T, R extends FluentAuditableRestAction<T, R>> extends AuditableRestAction<T>
{
    @Nonnull
    @Override
    R reason(@Nullable String reason);

    @Nonnull
    @Override
    R setCheck(@Nullable BooleanSupplier checks);

    @Nonnull
    @Override
    default R addCheck(@Nonnull BooleanSupplier checks)
    {
        return (R) AuditableRestAction.super.addCheck(checks);
    }

    @Nonnull
    @Override
    default R timeout(long timeout, @Nonnull TimeUnit unit)
    {
        return (R) AuditableRestAction.super.timeout(timeout, unit);
    }

    @Nonnull
    @Override
    default R deadline(long timestamp)
    {
        return (R) AuditableRestAction.super.deadline(timestamp);
    }
}
