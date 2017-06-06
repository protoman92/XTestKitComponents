package org.swiften.xtestkitcomponents.system.network.param;

/**
 * Created by haipham on 4/10/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.common.RetryType;
import org.swiften.xtestkitcomponents.system.network.NetworkHandler;
import org.swiften.xtestkitcomponents.system.network.type.PIDIdentifiableType;

/**
 * Parameter object for
 * {@link NetworkHandler#rxa_getProcessName(PIDIdentifiableType)}
 */
public class GetProcessNameParam implements PIDIdentifiableType, RetryType {
    /**
     * Get {@link Builder} instance.
     * @return {@link Builder} instance.
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    @NotNull private String pid;

    private int retries;

    GetProcessNameParam() {
        retries = RetryType.super.retries();
        pid = "";
    }

    /**
     * Return {@link #pid}.
     * @return {@link String} value.
     * @see #pid
     */
    @NotNull
    @Override
    public String pid() {
        return pid;
    }

    /**
     * Return {@link #retries}.
     * @return {@link Integer} value.
     * @see #retries
     */
    @Override
    public int retries() {
        return retries;
    }

    //region Builder.
    /**
     * Builder class for {@link GetProcessNameParam}.
     */
    public static final class Builder {
        @NotNull private GetProcessNameParam PARAM;

        Builder() {
            PARAM = new GetProcessNameParam();
        }

        /**
         * Set the {@link #pid} value.
         * @param pid {@link String} value.
         * @return The current {@link Builder} instance.
         * @see #pid
         */
        @NotNull
        public Builder withPID(@NotNull String pid) {
            PARAM.pid = pid;
            return this;
        }

        /**
         * Set the {@link #pid} value.
         * @param param {@link PIDIdentifiableType} instance.
         * @return The current {@link Builder} instance.
         * @see PIDIdentifiableType#pid()
         * @see #withPID(String)
         */
        @NotNull
        public Builder withPIDProtocol(@NotNull PIDIdentifiableType param) {
            return withPID(param.pid());
        }

        /**
         * Set the {@link #retries} value.
         * @param retries {@link Integer} value.
         * @return The current {@link Builder} instance.
         * @see #retries
         */
        @NotNull
        public Builder withRetries(int retries) {
            PARAM.retries = retries;
            return this;
        }

        /**
         * Set the {@link #retries} value.
         * @param param {@link RetryType} instance.
         * @return The current {@link Builder} instance.
         * @see RetryType#retries()
         * @see #withRetries(int)
         */
        @NotNull
        public Builder withRetryType(@NotNull RetryType param) {
            return withRetries(param.retries());
        }

        @NotNull
        public GetProcessNameParam build() {
            return PARAM;
        }
    }
    //endregion
}
