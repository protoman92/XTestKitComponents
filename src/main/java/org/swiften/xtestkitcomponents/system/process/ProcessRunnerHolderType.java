package org.swiften.xtestkitcomponents.system.process;

import org.jetbrains.annotations.NotNull;

/**
 * Created by haipham on 5/18/17.
 */

/**
 * This interface provides {@link ProcessRunner}.
 */
@FunctionalInterface
public interface ProcessRunnerHolderType {
    /**
     * Get the associated {@link ProcessRunner} instance.
     * @return {@link ProcessRunner} instance.
     */
    @NotNull
    ProcessRunner processRunner();
}
