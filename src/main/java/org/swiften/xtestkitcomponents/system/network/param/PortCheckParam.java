package org.swiften.xtestkitcomponents.system.network.param;

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.protocol.RetryProviderType;
import org.swiften.javautilities.util.Constants;
import org.swiften.xtestkitcomponents.system.network.type.MaxPortType;
import org.swiften.xtestkitcomponents.system.network.type.PortStepProviderType;
import org.swiften.xtestkitcomponents.system.network.type.PortProviderType;

/**
 * Created by haipham on 18/5/17.
 */

/**
 * Parameter object for
 * {@link org.swiften.xtestkitcomponents.system.network.NetworkHandler#rxa_checkUntilPortAvailable(PortProviderType)}
 */
public class PortCheckParam implements PortProviderType, MaxPortType, PortStepProviderType, RetryProviderType {
    /**
     * Get {@link Builder} instance.
     * @return {@link Builder} instance.
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    private int port, maxPort, portStep, retries;

    PortCheckParam() {
        port = 0;
        maxPort = MaxPortType.super.maxPort();
        portStep = PortStepProviderType.super.portStep();
        retries = Constants.DEFAULT_RETRIES;
    }

    //region Getters
    @Override
    public int port() {
        return port;
    }

    @Override
    public int maxPort() {
        return maxPort;
    }

    @Override
    public int portStep() {
        return portStep;
    }

    @Override
    public int retries() {
        return retries;
    }
    //endregion

    /**
     * Builder class for {@link PortCheckParam}.
     */
    public static final class Builder {
        @NotNull private final PortCheckParam PARAM;

        Builder() {
            PARAM = new PortCheckParam();
        }

        /**
         * Set the {@link #port} value.
         * @param port {@link Integer} value.
         * @return {@link Builder} instance.
         */
        @NotNull
        public Builder withPort(int port) {
            PARAM.port = port;
            return this;
        }

        /**
         * Set the {@link #maxPort} value.
         * @param maxPort {@link Integer} value.
         * @return {@link Builder} instance.
         */
        @NotNull
        public Builder withMaxPort(int maxPort) {
            PARAM.maxPort = maxPort;
            return this;
        }

        /**
         * Set the {@link #portStep} value.
         * @param step {@link Integer} value.
         * @return {@link Builder} instance.
         */
        @NotNull
        public Builder withPortStep(int step) {
            PARAM.portStep = step;
            return this;
        }

        /**
         * Set the {@link #retries} value.
         * @param retries {@link Integer} value.
         * @return {@link Builder} instance.
         */
        @NotNull
        public Builder withRetries(int retries) {
            PARAM.retries = retries;
            return this;
        }

        /**
         * Set the {@link #retries} value.
         * @param type {@link RetryProviderType} instance.
         * @return {@link Builder} instance.
         * @see #withRetries(int)
         */
        @NotNull
        public Builder withRetryProvider(@NotNull RetryProviderType type) {
            return withRetries(type.retries());
        }

        /**
         * Get {@link #PARAM}.
         * @return {@link PortCheckParam} instance.
         * @see #PARAM
         */
        @NotNull
        public PortCheckParam build() {
            return PARAM;
        }
    }
}
