package net.dv8tion.jda.api.entities;

import javax.annotation.Nonnull;

public interface ThreadChannel {

    /**
     * The values permitted for the auto archive duration of a {@link ThreadChannel}.
     *
     * <p>This is the time before an idle thread will be automatically hidden.
     *
     * <p>Sending a message to the thread will reset the timer.
     *
     * @see ChannelField#AUTO_ARCHIVE_DURATION
     */
    enum AutoArchiveDuration
    {
        TIME_1_HOUR(60),
        TIME_24_HOURS(1440),
        TIME_3_DAYS(4320),
        TIME_1_WEEK(10080);

        private final int minutes;

        AutoArchiveDuration(int minutes)
        {
            this.minutes = minutes;
        }

        /**
         * The number of minutes before an idle thread will be automatically hidden.
         *
         * @return The number of minutes
         */
        public int getMinutes()
        {
            return minutes;
        }

        /**
         * Provides the corresponding enum constant for the provided number of minutes.
         *
         * @param  minutes
         *         The number of minutes. (must be one of the valid values)
         *
         * @throws IllegalArgumentException
         *         If the provided minutes is not a valid value.
         *
         * @return The corresponding enum constant.
         */
        @Nonnull
        public static AutoArchiveDuration fromKey(int minutes)
        {
            for (AutoArchiveDuration duration : values())
            {
                if (duration.getMinutes() == minutes)
                    return duration;
            }
            throw new IllegalArgumentException("Provided key was not recognized. Minutes: " + minutes);
        }
    }
}
