package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 13/6/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.common.ErrorProviderType;

/**
 * Use this to specify how child elements are queried.
 */
public enum Path implements ErrorProviderType {
    ANY,
    DIRECT,
    NONE;

    /**
     * Get the symbol {@link String} to append to the start of the
     * {@link CompoundAttribute#baseAttribute()}.
     * @return {@link String} value.
     * @see #ANY
     * @see #DIRECT
     * @see #NONE
     * @see #NOT_AVAILABLE
     */
    @NotNull
    public String symbol() {
        switch (this) {
            case ANY:
                return "//";

            case DIRECT:
                return "/";

            case NONE:
                return "";

            default:
                throw new RuntimeException(NOT_AVAILABLE);
        }
    }
}
