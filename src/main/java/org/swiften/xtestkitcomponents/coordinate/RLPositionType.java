package org.swiften.xtestkitcomponents.coordinate;

/**
 * Created by haipham on 6/10/17.
 */

import org.openqa.selenium.WebElement;

/**
 * This interface provides dimension ratio to be used with
 * {@link WebElement#getSize()}.
 */
@FunctionalInterface
public interface RLPositionType {
    /**
     * Get dimension ratio, which will be multiplied with a dimension to
     * produce an offset value.
     * @return {@link Double} value.
     */
    double dimensionRatio();
}
