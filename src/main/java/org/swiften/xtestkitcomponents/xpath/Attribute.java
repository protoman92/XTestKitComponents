package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 4/4/17.
 */

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.swiften.javautilities.log.LogUtil;
import org.swiften.javautilities.object.ObjectUtil;
import org.swiften.xtestkitcomponents.common.BaseErrorType;
import org.swiften.xtestkitcomponents.property.base.AttributeType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to deal with multiple attributes being used to describe
 * the same thing.
 */
public final class Attribute<T> implements BaseErrorType {
    /**
     * Get {@link Builder} instance.
     *
     * @return {@link Builder} instance.
     */
    @NotNull
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * This {@link Enum} contains formatter that encloses the entire
     * {@link Attribute}, not including {@link Attribute#className} and
     * {@link Attribute#index}, i.e. {@link Attribute#baseAttribute()}. This
     * is different from {@link Formatible}, which is applied to each attribute
     * in {@link Attribute#ATTRIBUTES}.
     */
    public enum Wrapper implements BaseErrorType {
        NOT,
        NONE;

        /**
         * Get the wrapper format to apply to {@link Attribute#baseAttribute()}.
         *
         * @return {@link String} value.
         * @see #NONE
         * @see #NOT
         * @see #NOT_AVAILABLE
         */
        @NotNull
        public String wrapperFormat() {
            switch (this) {
                case NONE:
                    return "%s";

                case NOT:
                    return "not(%s)";

                default:
                    throw new RuntimeException(NOT_AVAILABLE);
            }
        }
    }

    public enum Joiner implements BaseErrorType {
        AND,
        OR;

        /**
         * Get the joiner {@link String} that will be used to join attributes
         * in {@link Attribute#ATTRIBUTES}.
         *
         * @return {@link String} value.
         * @see #AND
         * @see #OR
         * @see #NOT_AVAILABLE
         */
        @NotNull
        public String joiner() {
            switch (this) {
                case AND:
                    return "and";

                case OR:
                    return "or";

                default:
                    throw new RuntimeException(NOT_AVAILABLE);
            }
        }

        /**
         * Get the joiner symbol {@link String} that can be used to join
         * different queries.
         *
         * @return {@link String} value.
         * @see #OR
         */
        @NotNull
        public String joinerSymbol() {
            switch (this) {
                case OR:
                    return "|";

                default:
                    return "";
            }
        }
    }

    @NotNull private final List<String> ATTRIBUTES;
    @NotNull private Joiner joiner;
    @NotNull private String className;
    @NotNull private Wrapper wrapper;
    @Nullable private Formatible<T> formatible;
    @Nullable private Integer index;
    @Nullable private T value;

    Attribute() {
        ATTRIBUTES = new ArrayList<>();
        className = "*";
        joiner = Joiner.AND;
        wrapper = Wrapper.NONE;
    }

    @NotNull
    @Override
    public String toString() {
        return fullAttribute();
    }

    /**
     * Get {@link #ATTRIBUTES}.
     *
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
     *
     * @return {@link Joiner} instance.
     * @see #joiner
     */
    @NotNull
    public Joiner joiner() {
        return joiner;
    }

    /**
     * Get {@link #wrapper}.
     *
     * @return {@link Wrapper} instance.
     * @see #wrapper
     */
    @NotNull
    public Wrapper wrapper() {
        return wrapper;
    }

    /**
     * Get {@link #formatible}.
     *
     * @return {@link Formatible} instance.
     * @see ObjectUtil#nonNull(Object)
     * @see #formatible
     * @see #NOT_AVAILABLE
     */
    @NotNull
    public Formatible<T> formatible() {
        if (ObjectUtil.nonNull(formatible)) {
            return formatible;
        } else {
            throw new RuntimeException(NOT_AVAILABLE);
        }
    }

    /**
     * Get {@link #className}.
     *
     * @return {@link String} value.
     * @see #className
     */
    @NotNull
    public String className() {
        return className;
    }

    /**
     * Get {@link #index}.
     *
     * @return {@link Integer} value.
     * @see #index
     */
    @Nullable
    public Integer index() {
        return index;
    }

    /**
     * Get {@link #value}.
     * @return {@link Object} instance.
     * @see ObjectUtil#nonNull(Object)
     * @see #value
     * @see #NOT_AVAILABLE
     */
    @NotNull
    public T value() {
        if (ObjectUtil.nonNull(value)) {
            return value;
        } else {
            throw new RuntimeException(NOT_AVAILABLE);
        }
    }

    /**
     * Get the base attribute based on {@link #ATTRIBUTES}.
     * @return {@link String} value.
     * @see Formatible#stringFormat(Object)
     * @see Joiner#joiner()
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

        String joined = String.format(" %s ", joiner.joiner());
        return String.join(joined, attributes);
    }

    /**
     * Get the full attribute, including {@link #className()} and
     * {@link #index()}.
     *
     * @return {@link String} value.
     * @see AttributeType#value()
     * @see ObjectUtil#nonNull(Object)
     * @see Wrapper#wrapperFormat()
     * @see #baseAttribute()
     * @see #className()
     * @see #index()
     * @see #wrapper()
     */
    @NotNull
    public String fullAttribute() {
        Wrapper wrapper = wrapper();
        String wrapperFormat = wrapper.wrapperFormat();
        String className = className();
        Integer index = index();
        String base = baseAttribute();
        String wrapped = String.format(wrapperFormat, base);
        String withClass = String.format("//%s[%s]", className, wrapped);

        if (ObjectUtil.nonNull(index)) {
            return String.format("%s[%d]", withClass, index);
        } else {
            return withClass;
        }
    }

    /**
     * Get a new {@link Attribute} instance with a class name.
     *
     * @param clsName {@link String} value.
     * @return {@link Attribute} instance.
     * @see Builder#build()
     * @see Builder#withAttribute(Attribute)
     * @see Builder#withClass(String)
     * @see #builder()
     */
    @NotNull
    public Attribute<T> withClass(@NotNull String clsName) {
        return Attribute.<T>builder()
            .withAttribute(this)
            .withClass(clsName)
            .build();
    }

    /**
     * Get a new {@link Attribute} instance with an index.
     *
     * @param index {@link Integer} value.
     * @return {@link Attribute} instance.
     * @see Builder#build()
     * @see Builder#withAttribute(Attribute)
     * @see Builder#withIndex(Integer)
     * @see #builder()
     */
    @NotNull
    public Attribute withIndex(@Nullable Integer index) {
        return Attribute.<T>builder()
            .withAttribute(this)
            .withIndex(index)
            .build();
    }

    /**
     * Get the antithesis of the current {@link Attribute}, using
     * {@link Wrapper#NOT}.
     *
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
         *
         * @param attribute The attribute to be added.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder<T> addAttribute(@NotNull String attribute) {
            ATTRIBUTE.ATTRIBUTES.add(attribute);
            return this;
        }

        /**
         * Add attributes to {@link #ATTRIBUTES}.
         *
         * @param attributes {@link Collection} of {@link String}.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder<T> addAttributes(@NotNull Collection<String> attributes) {
            ATTRIBUTE.ATTRIBUTES.addAll(attributes);
            return this;
        }

        /**
         * Replace all attributes within {@link #ATTRIBUTES}.
         *
         * @param attributes {@link Collection} of {@link String}.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder<T> withAttributes(@NotNull Collection<String> attributes) {
            ATTRIBUTE.ATTRIBUTES.clear();
            ATTRIBUTE.ATTRIBUTES.addAll(attributes);
            return this;
        }

        /**
         * Set the {@link #joiner} instance. This will be used to decide how
         * elements are to be searched using {@link #ATTRIBUTES}.
         *
         * @param joiner {@link Joiner} instance.
         * @return The current {@link Builder} instance.
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
         * @return The current {@link Builder} instance.
         * @see #wrapper
         */
        @NotNull
        public Builder<T> withWrapper(@NotNull Wrapper wrapper) {
            ATTRIBUTE.wrapper = wrapper;
            return this;
        }

        /**
         * Set the {@link #formatible} instance.
         *
         * @param formatible {@link Formatible} instance.
         * @return The current {@link Builder} instance.
         * @see #formatible
         */
        @NotNull
        public Builder<T> withFormatible(@NotNull Formatible<T> formatible) {
            ATTRIBUTE.formatible = formatible;
            return this;
        }

        /**
         * Set {@link #className}.
         *
         * @param clsName {@link String} value.
         * @return The current {@link Builder} instance.
         * @see #className
         */
        @NotNull
        public Builder<T> withClass(@NotNull String clsName) {
            ATTRIBUTE.className = clsName;
            return this;
        }

        /**
         * Set {@link #index} instance.
         *
         * @param index {@link Integer} value.
         * @return The {@link Builder} instance.
         * @see #index
         */
        @NotNull
        public Builder<T> withIndex(@Nullable Integer index) {
            ATTRIBUTE.index = index;
            return this;
        }

        /**
         * Set the {@link #value} instance.
         * @param value {@link T} instance.
         * @return The current {@link Builder} instance.
         * @see #value
         */
        @NotNull
        public Builder<T> withValue(@NotNull T value) {
            ATTRIBUTE.value = value;
            return this;
        }

        /**
         * Replace all properties with those of another {@link Attribute}.
         *
         * @param attribute {@link Attribute} instance.
         * @return The current {@link Builder} instance.
         * @see Attribute#attributes()
         * @see Attribute#className()
         * @see Attribute#formatible()
         * @see Attribute#index()
         * @see Attribute#joiner()
         * @see Attribute#value()
         * @see Attribute#wrapper()
         * @see #withAttributes(Collection)
         * @see #withClass(String)
         * @see #withFormatible(Formatible)
         * @see #withIndex(Integer)
         * @see #withJoiner(Joiner)
         * @see #withValue(Object)
         * @see #withWrapper(Wrapper)
         */
        @NotNull
        public Builder<T> withAttribute(@NotNull Attribute<T> attribute) {
            return this
                .withAttributes(attribute.attributes())
                .withClass(attribute.className())
                .withFormatible(attribute.formatible())
                .withIndex(attribute.index())
                .withJoiner(attribute.joiner())
                .withWrapper(attribute.wrapper())
                .withValue(attribute.value());
        }

        /**
         * Get {@link #ATTRIBUTE}.
         *
         * @return {@link Attribute} instance.
         * @see #ATTRIBUTE
         */
        @NotNull
        public Attribute<T> build() {
            return ATTRIBUTE;
        }
    }

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
}
