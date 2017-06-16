package org.swiften.xtestkitcomponents.util;

import org.swiften.xtestkitcomponents.common.RetryType;

/**
 * Created by haipham on 16/6/17.
 */
public final class Constants {
    /**
     * This value will be used to set default retry count for
     * {@link RetryType#retries()}
     */
    private static int DEFAULT_RETRIES = 2;

    /**
     * Set the default retry count.
     * @param retry {@link Integer} value.
     * @see #DEFAULT_RETRIES
     */
    public static void setDefaultRetries(int retry) {
        DEFAULT_RETRIES = retry;
    }

    /**
     * Get the default retry count.
     * @return {@link Integer} value.
     * @see #DEFAULT_RETRIES
     */
    public static int defaultRetries() {
        return DEFAULT_RETRIES;
    }
}
