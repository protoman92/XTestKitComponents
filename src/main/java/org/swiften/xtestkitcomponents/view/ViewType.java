package org.swiften.xtestkitcomponents.view;

import org.swiften.javautilities.protocol.ClassNameProviderType;
import org.swiften.xtestkitcomponents.platform.PlatformType;

/**
 * Created by haipham on 3/20/17.
 */

/**
 * This interface provides view-specific properties. Each {@link PlatformType}
 * should have a set of {@link ViewType} that can be used to search for
 * elements.
 */
public interface ViewType extends ClassNameProviderType {
    /**
     * Check whether the current {@link ViewType} could display {@link String}
     * text.
     * @return {@link Boolean} value.
     */
    default boolean hasText() {
        return false;
    }

    /**
     * Check whether the current {@link ViewType} is clickable. For e.g., Android's
     * Button and iOS's UIButton classes.
     * @return {@link Boolean} value.
     */
    default boolean isClickable() {
        return false;
    }

    /**
     * Check whether the current {@link ViewType} is editable.
     * @return {@link Boolean} value.
     */
    default boolean isEditable() {
        return false;
    }
}
