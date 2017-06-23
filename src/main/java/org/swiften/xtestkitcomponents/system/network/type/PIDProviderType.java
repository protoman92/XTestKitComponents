package org.swiften.xtestkitcomponents.system.network.type;

import org.jetbrains.annotations.NotNull;

/**
 * Created by haipham on 4/10/17.
 */

/**
 * This interface provides a PID value for a process.
 */
@FunctionalInterface
public interface PIDProviderType {
    /**
     * Get a PID value.
     * @return {@link String} value.
     */
    @NotNull String pid();
}
