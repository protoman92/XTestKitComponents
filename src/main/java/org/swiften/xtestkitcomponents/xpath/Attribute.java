package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 4/4/17.
 */

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.swiften.javautilities.object.HPObjects;
import org.swiften.xtestkitcomponents.common.ErrorProviderType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to deal with multiple attributes being used to describe
 * the same thing.
 */
public final class Attribute<T> implements AttributeType, ErrorProviderType {
    /**
     * Get {@link Builder} instance.
     * @return {@link Builder} instance.
     */
    @NotNull
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    @NotNull private final List<String> ATTRIBUTES;
    @NotNull private Joiner joiner;
    @NotNull private Wrapper wrapper;
    @NotNull private Formatible<T> formatible;
    @Nullable private T value;

    Attribute() {
        ATTRIBUTES = new ArrayList<>();
        formatible = new Formatible<T>() {};
        joiner = Joiner.AND;
        wrapper = Wrapper.BASIC;
    }

    @NotNull
    @Override
    public String toString() {
        return fullAttribute();
    }

    /**
     * Get {@link #ATTRIBUTES}.
     * @return {@link List} of {@link String}.
     * @see Collections#unmodifiableList(List)
     * @see #ATTRIBUTES
     */
    @NotNull
    public List<String> attributes() {
        return Collections.unmodifiableList(ATTRIBUTES);
    }

    /**
     * Get {@link #joiner}.
     * @return {@link Joiner} instance.
     * @see #joiner
     */
    @NotNull
    public Joiner joiner() {
        return joiner;
    }

    /**
     * Get {@link #wrapper}.
     * @return {@link Wrapper} instance.
     * @see #wrapper
     */
    @NotNull
    public Wrapper wrapper() {
        return wrapper;
    }

    /**
     * Get {@link #formatible}.
     * @return {@link Formatible} instance.
     * @see HPObjects#nonNull(Object)
     * @see #formatible
     */
    @NotNull
    public Formatible<T> formatible() {
        return formatible;
    }

    /**
     * Get {@link #value}.
     * @return {@link Object} instance.
     * @see HPObjects#requireNotNull(Object, String)
     * @see #value
     * @see #NOT_AVAILABLE
     */
    @NotNull
    @SuppressWarnings("ConstantConditions")
    public T value() {
        HPObjects.requireNotNull(value, NOT_AVAILABLE);
        return value;
    }

    /**
     * Get a new {@link Attribute} instance with a possibly different
     * {@link Wrapper}.
     * @param wrapper {@link Wrapper} instance.
     * @return {@link Attribute} instance.
     * @see Builder#build()
     * @see Builder#withAttribute(Attribute)
     * @see Builder#withWrapper(Wrapper)
     * @see #builder()
     */
    @NotNull
    public Attribute<T> withWrapper(@NotNull Wrapper wrapper) {
        return Attribute.<T>builder()
            .withAttribute(this)
            .withWrapper(wrapper)
            .build();
    }

    /**
     * Override this method to provide default implementation.
     * @return {@link String} value.
     * @see Formatible#stringFormat(Object)
     * @see Joiner#symbol()
     * @see #attributes()
     * @see #formatible()
     * @see #value()
     */
    @NotNull
    private String baseAttribute() {
        Formatible<T> formatible = formatible();
        Joiner joiner = joiner();
        T value = value();
        final String FORMAT = formatible.stringFormat(value);

        List<String> attributes = attributes().stream()
            .map(a -> String.format(FORMAT, a))
            .collect(Collectors.toList());

        String joined = String.format(" %s ", joiner.symbol());
        return String.join(joined, attributes);
    }

    /**
     * Get the wrapped {@link #baseAttribute()}.
     * @return {@link String} value.
     * @see Wrapper#wrapperFormat()
     * @see #baseAttribute()
     * @see #wrapper()
     */
    @NotNull
    private String wrappedAttribute() {
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
    @Override
    public String fullAttribute() {
        return wrappedAttribute();
    }

    /**
     * Override this method to provide default implementation.
     * @param joiner {@link Joiner} instance.
     * @return {@link Attribute} instance.
     * @see Builder#build()
     * @see Builder#withAttribute(Attribute)
     * @see Builder#withJoiner(Joiner)
     * @see #builder()
     */
    @NotNull
    public Attribute<T> withJoiner(@NotNull Joiner joiner) {
        return Attribute.<T>builder()
            .withAttribute(this)
            .withJoiner(joiner)
            .build();
    }

    /**
     * Get a new {@link Attribute} instance with a different {@link #value}.
     * @param value {@link T} instance.
     * @return {@link Attribute} instance.
     * @see Builder#build()
     * @see Builder#withAttribute(Attribute)
     * @see Builder#withValue(Object)
     * @see #builder()
     */
    @NotNull
    public Attribute<T> withValue(@NotNull T value) {
        return Attribute.<T>builder()
            .withAttribute(this)
            .withValue(value)
            .build();
    }

    /**
     * Get the antithesis of the current {@link Attribute}, using
     * {@link Wrapper#NOT}.
     * @return {@link Attribute} instance.
     * @see Builder#withAttribute(Attribute)
     * @see Builder#withWrapper(Wrapper)
     * @see Builder#build()
     * @see Wrapper#NOT
     * @see #builder()
     */
    @NotNull
    public Attribute not() {
        return Attribute.<T>builder()
            .withAttribute(this)
            .withWrapper(Wrapper.NOT)
            .build();
    }

    /**
     * Builder class for {@link Attribute}.
     */
    public static final class Builder<T> {
        @NotNull private final Attribute<T> ATTRIBUTE;

        Builder() {
            ATTRIBUTE = new Attribute<>();
        }

        /**
         * Add an attribute to {@link #ATTRIBUTES}.
         * @param attribute The attribute to be added.
         * @return {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder<T> addAttribute(@NotNull String attribute) {
            ATTRIBUTE.ATTRIBUTES.add(attribute);
            return this;
        }

        /**
         * Add attributes to {@link #ATTRIBUTES}.
         * @param attributes {@link Collection} of {@link String}.
         * @return {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder<T> addAttribute(@NotNull Collection<String> attributes) {
            ATTRIBUTE.ATTRIBUTES.addAll(attributes);
            return this;
        }

        /**
         * Replace all attributes within {@link #ATTRIBUTES}.
         * @param attributes {@link Collection} of {@link String}.
         * @return {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder<T> withAttribute(@NotNull Collection<String> attributes) {
            ATTRIBUTE.ATTRIBUTES.clear();
            ATTRIBUTE.ATTRIBUTES.addAll(attributes);
            return this;
        }

        /**
         * Set the {@link #joiner} instance. This will be used to join
         * attributes in {@link #ATTRIBUTES}.
         * @param joiner {@link Joiner} instance.
         * @return {@link Builder} instance.
         * @see #joiner
         */
        @NotNull
        public Builder<T> withJoiner(@NotNull Joiner joiner) {
            ATTRIBUTE.joiner = joiner;
            return this;
        }

        /**
         * Set the {@link #wrapper} instance.
         * @param wrapper {@link Wrapper} instance.
         * @return {@link Builder} instance.
         * @see #wrapper
         */
        @NotNull
        public Builder<T> withWrapper(@NotNull Wrapper wrapper) {
            ATTRIBUTE.wrapper = wrapper;
            return this;
        }

        /**
         * Set the {@link #formatible} instance.
         * @param formatible {@link Formatible} instance.
         * @return {@link Builder} instance.
         * @see #formatible
         */
        @NotNull
        public Builder<T> withFormatible(@NotNull Formatible<T> formatible) {
            ATTRIBUTE.formatible = formatible;
            return this;
        }

        /**
         * Set the {@link #value} instance.
         * @param value {@link T} instance.
         * @return {@link Builder} instance.
         * @see #value
         */
        @NotNull
        public Builder<T> withValue(@NotNull T value) {
            ATTRIBUTE.value = value;
            return this;
        }

        /**
         * Replace all properties with those of another {@link Attribute}.
         * @param attribute {@link Attribute} instance.
         * @return {@link Builder} instance.
         * @see Attribute#attributes()
         * @see Attribute#formatible()
         * @see Attribute#joiner()
         * @see Attribute#value()
         * @see Attribute#wrapper()
         * @see #withAttribute(Collection)
         * @see #withFormatible(Formatible)
         * @see #withJoiner(Joiner)
         * @see #withValue(Object)
         * @see #withWrapper(Wrapper)
         */
        @NotNull
        public Builder<T> withAttribute(@NotNull Attribute<T> attribute) {
            return this
                .withAttribute(attribute.attributes())
                .withFormatible(attribute.formatible())
                .withJoiner(attribute.joiner())
                .withWrapper(attribute.wrapper())
                .withValue(attribute.value());
        }

        /**
         * Get {@link #ATTRIBUTE}.
         * @return {@link Attribute} instance.
         * @see #ATTRIBUTE
         */
        @NotNull
        public Attribute<T> build() {
            return ATTRIBUTE;
        }
    }
}
