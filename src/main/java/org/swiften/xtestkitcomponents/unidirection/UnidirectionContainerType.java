package org.swiften.xtestkitcomponents.unidirection;

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.unidirection.Unidirection;

/**
 * Created by haipham on 5/8/17.
 */

/**
 * This interface provides {@link Unidirection} for certain actions, such as
 * swipes.
 */
@FunctionalInterface
public interface UnidirectionContainerType {
    /**
     * Get the associated {@link Unidirection}.
     * @return {@link Unidirection} instance.
     */
    @NotNull
    Unidirection direction();
}
