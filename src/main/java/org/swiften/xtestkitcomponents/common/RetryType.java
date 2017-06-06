package org.swiften.xtestkitcomponents.common;

import org.jetbrains.annotations.NotNull;

/**
 * Created by haipham on 3/23/17.
 */

/**
 * This interface provides retry count.
 */
public interface RetryType {
    @NotNull
    RetryType DEFAULT = new RetryType() {};

    /**
     * Use this retry count if we are running an operation that is not expected
     * to throw {@link Exception}.
     * @return {@link Integer} value.
     */
    default int retries() {
        return 3;
    }
}
