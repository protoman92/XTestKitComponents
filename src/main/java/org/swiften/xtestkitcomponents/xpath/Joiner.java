package org.swiften.xtestkitcomponents.xpath;

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.common.ErrorProviderType;

/**
 * Created by haipham on 13/6/17.
 */
public enum Joiner implements ErrorProviderType {
    AND,
    OR;

    /**
     * Get the joiner {@link String} that will be used to join attributes
     * in {@link Attribute#ATTRIBUTES}.
     * @return {@link String} value.
     * @see #AND
     * @see #OR
     * @see #NOT_AVAILABLE
     */
    @NotNull
    public String symbol() {
        switch (this) {
            case AND:
                return "and";

            case OR:
                return "or";

            default:
                throw new RuntimeException(NOT_AVAILABLE);
        }
    }
}
