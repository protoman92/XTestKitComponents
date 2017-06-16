package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 16/6/17.
 */

import org.jetbrains.annotations.NotNull;

/**
 * Classes that implement this interface must provide {@link String} format
 * that can be used to construct {@link Attribute}.
 */
public interface Formatible<T> {
    /**
     * Get the value to be formatted. Override this to provide custom
     * values.
     * @param value {@link T} instance.
     * @return {@link String} value.
     */
    @NotNull
    default String formatValue(@NotNull T value) {
        return String.format("'%s'", value);
    }

    /**
     * Get the format {@link String} with which we construct an
     * {@link XPath} query.
     * @param value {@link T} instance.
     * @return {@link String} value.
     * @see #formatValue(Object)
     */
    @NotNull
    default String stringFormat(@NotNull T value) {
        String raw = formatValue(value);
        return String.format("@%1$s=%2$s", "%1$s", raw);
    }
}
