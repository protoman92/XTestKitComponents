package org.swiften.xtestkitcomponents.util;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by haipham on 5/11/17.
 */

/**
 * This interface provides methods to generate {@link List} of values, based
 * on min/max/step values.
 */
public interface ValueRangeType {
    /**
     * Get a range of {@link V}, based on a min {@link V} and max {@link V}.
     * @param min {@link V} instance.
     * @param max {@link V} instance.
     * @param step {@link V} instance.
     * @param converter {@link Converter} instance.
     * @param <V> Generics parameter that extends {@link Number}.
     * @return {@link List} of {@link V}.
     */
    @NotNull
    default <V extends Number> List<V> valueRange(@NotNull V min,
                                                  @NotNull V max,
                                                  @NotNull V step,
                                                  @NotNull Converter<V> converter) {
        List<V> range = new LinkedList<>();

        V current = min;

        while (current.doubleValue() < max.doubleValue()) {
            range.add(current);
            double currentValue = current.doubleValue() + step.doubleValue();
            current = converter.convert(currentValue);
        }

        return range;
    }

    @FunctionalInterface
    interface Converter<V extends Number> {
        /**
         * Convert {@link Double} value to {@link V}.
         * @param value {@link Double} value.
         * @return {@link V} instance.
         */
        @NotNull V convert(double value);
    }
}
