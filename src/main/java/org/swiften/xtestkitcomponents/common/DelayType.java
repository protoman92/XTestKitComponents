package org.swiften.xtestkitcomponents.common;

/**
 * Created by haipham on 5/9/17.
 */

/**
 * This interface provides delay duration and
 * {@link java.util.concurrent.TimeUnit}.
 */
@FunctionalInterface
public interface DelayType extends TimeUnitType {
    /**
     * Get the associated delay duration.
     * @return {@link Long} value.
     */
    long delay();
}
