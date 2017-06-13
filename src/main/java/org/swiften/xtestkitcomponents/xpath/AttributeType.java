package org.swiften.xtestkitcomponents.xpath;

import org.jetbrains.annotations.NotNull;

/**
 * Created by haipham on 13/6/17.
 */
public interface AttributeType {
    /**
     * Get the associated {@link Joiner} instance.
     * @return {@link Joiner} instance.
     */
    @NotNull Joiner joiner();

    /**
     * Get the associated {@link Wrapper} instance.
     * @return {@link Wrapper} instance.
     */
    @NotNull Wrapper wrapper();

    /**
     * Get a new {@link AttributeType} instance with a possibly different
     * {@link Joiner}.
     * @param joiner {@link Joiner} instance.
     * @return {@link AttributeType} instance.
     */
    @NotNull AttributeType withJoiner(@NotNull Joiner joiner);

    /**
     * Get the base attribute without any wrapping.
     * @return {@link String} value.
     */
    @NotNull String baseAttribute();

    /**
     * Get the wrapped {@link #baseAttribute()}.
     * @return {@link String} value.
     * @see Wrapper#wrapperFormat()
     * @see #baseAttribute()
     * @see #wrapper()
     */
    @NotNull
    default String wrappedAttribute() {
        Wrapper wrapper = wrapper();
        String wrapperFormat = wrapper.wrapperFormat();
        String base = baseAttribute();
        return String.format(wrapperFormat, base);
    }

    /**
     * Get the full attribute.
     * @return {@link String} value.
     * @see #wrappedAttribute()
     */
    @NotNull
    default String fullAttribute() {
        return wrappedAttribute();
    }
}
