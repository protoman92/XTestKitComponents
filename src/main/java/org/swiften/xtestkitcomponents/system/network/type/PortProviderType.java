package org.swiften.xtestkitcomponents.system.network.type;

/**
 * Created by haipham on 4/9/17.
 */

/**
 * This interface provides a port value.
 */
@FunctionalInterface
public interface PortProviderType {
    /**
     * Get the associated port value.
     * @return {@link Integer} value.
     */
    int port();
}
