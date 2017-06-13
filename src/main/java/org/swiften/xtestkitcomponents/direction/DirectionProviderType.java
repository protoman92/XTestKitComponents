package org.swiften.xtestkitcomponents.direction;

import org.jetbrains.annotations.NotNull;

/**
 * Created by haipham on 5/8/17.
 */

/**
 * This interface provides {@link Direction} for certain actions, such as
 * swipes.
 */
@FunctionalInterface
public interface DirectionProviderType {
    /**
     * Get the associated {@link Direction}.
     * @return {@link Direction} instance.
     */
    @NotNull
    Direction direction();
}
