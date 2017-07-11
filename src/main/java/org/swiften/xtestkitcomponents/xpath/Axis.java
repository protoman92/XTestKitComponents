package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 13/6/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.common.ErrorProviderType;

/**
 * Use this to add axis to {@link CompoundAttribute}.
 */
public enum Axis implements ErrorProviderType {
    CHILD,
    DESCENDANT,
    FOLLOWING_SIBLING,
    PRECEDING_SIBLING,
    NONE;

    /**
     * Get the associated symbol {@link String}.
     * @return {@link String} value.
     */
    @NotNull
    public String symbol() {
        switch (this) {
            case CHILD:
                return "child::";

            case DESCENDANT:
                return "descendant::";

            case FOLLOWING_SIBLING:
                return "following-sibling::";

            case PRECEDING_SIBLING:
                return "preceding-sibling::";

            case NONE:
                return "";

            default:
                throw new RuntimeException(NOT_AVAILABLE);
        }
    }
}
