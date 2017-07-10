package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 13/6/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.common.ErrorProviderType;

/**
 * This {@link Enum} contains formatter that encloses the entire
 * {@link Attribute}, i.e. {@link Attribute#baseAttribute()}.
 */
public enum Wrapper implements ErrorProviderType {
    BASIC,
    NOT,
    NONE;

    /**
     * Get the wrapper format to apply to {@link Attribute#baseAttribute()}.
     * @return {@link String} value.
     * @see #BASIC
     * @see #NOT
     * @see #NONE
     * @see #NOT_AVAILABLE
     */
    @NotNull
    public String wrapperFormat() {
        switch (this) {
            case BASIC:
                return "(%s)";

            case NOT:
                return "not(%s)";

            case NONE:
                return "%s";

            default:
                throw new RuntimeException(NOT_AVAILABLE);
        }
    }
}
