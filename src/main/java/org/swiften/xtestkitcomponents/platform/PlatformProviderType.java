package org.swiften.xtestkitcomponents.platform;

/**
 * Created by haipham on 5/8/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.common.ErrorProviderType;

/**
 * This interface provides {@link PlatformType} for platform-specific
 * operations.
 */
public interface PlatformProviderType extends ErrorProviderType {
    /**
     * Get the associated {@link PlatformType} instance.
     * @return {@link PlatformType} instance.
     */
    @NotNull PlatformType platform();

    /**
     * Get the associated platform name.
     * @return {@link String} value.
     * @see PlatformType#value()
     */
    @NotNull String platformName();
}
