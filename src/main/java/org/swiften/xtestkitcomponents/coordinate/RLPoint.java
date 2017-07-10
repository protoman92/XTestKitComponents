package org.swiften.xtestkitcomponents.coordinate;

/**
 * Created by haipham on 6/10/17.
 */

import org.openqa.selenium.WebElement;
import org.swiften.xtestkitcomponents.common.ErrorProviderType;

/**
 * Use this {@link Enum} with {@link WebElement#getLocation()} and
 * {@link WebElement#getSize()}.
 */
public enum RLPoint implements ErrorProviderType, RLPositionType {
    MIN,
    MID,
    MAX;

    /**
     * Override this method to provide default implementation.
     * @return {@link Double} value.
     * @see #MAX
     * @see #MID
     * @see #MIN
     * @see #NOT_AVAILABLE
     */
    @Override
    public double dimensionRatio() {
        switch (this) {
            case MIN:
                return 0d;

            case MID:
                return 0.5d;

            case MAX:
                return 1d;

            default:
                throw new RuntimeException(NOT_AVAILABLE);
        }
    }
}
