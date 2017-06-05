package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 4/4/17.
 */

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
public final class Attribute implements BaseErrorType {
    /**
     * Get {@link Builder} instance.
     * @return {@link Builder} instance.
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    public enum Mode implements BaseErrorType {
        AND,
        OR;

        /**
         * Get the joiner {@link String} that will be used to join attributes
         * in {@link Attribute#ATTRIBUTES}.
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

    @NotNull private Mode mode;
    @NotNull private List<String> ATTRIBUTES;
    @NotNull private String className;
    @Nullable private Formatible<?> formatible;
    @Nullable private Integer index;

    Attribute() {
        ATTRIBUTES = new ArrayList<>();
        className = "*";
        mode = Mode.AND;
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
     * Get {@link #mode}.
     * @return {@link Mode} instance.
     * @see #mode
     */
    @NotNull
    public Mode mode() {
        return mode;
    }

    /**
     * Get {@link #formatible}.
     * @return {@link Formatible} instance.
     * @see ObjectUtil#nonNull(Object)
     * @see #formatible
     * @see #NOT_AVAILABLE
     */
    @NotNull
    public Formatible<?> formatible() {
        if (ObjectUtil.nonNull(formatible)) {
            return formatible;
        } else {
            throw new RuntimeException(NOT_AVAILABLE);
        }
    }

    /**
     * Get {@link #className}.
     * @return {@link String} value.
     * @see #className
     */
    @NotNull
    public String className() {
        return className;
    }

    /**
     * Get {@link #index}.
     * @return {@link Integer} value.
     * @see #index
     */
    @Nullable
    public Integer index() {
        return index;
    }

    /**
     * Get the base attribute based on {@link #ATTRIBUTES}.
     * @return {@link String} value.
     * @see Formatible#stringFormat()
     * @see Mode#joiner()
     * @see #attributes()
     * @see #formatible()
     * @see #mode
     */
    @NotNull
    String baseAttribute() {
        Formatible<?> formatible = formatible();
        Attribute.Mode mode = mode();
        final String FORMAT = formatible.stringFormat();

        List<String> attributes = attributes().stream()
            .map(a -> String.format(FORMAT, a))
            .collect(Collectors.toList());

        String joiner = String.format(" %s ", mode.joiner());
        return String.join(joiner, attributes);
    }

    /**
     * Get the full attribute, including {@link #className()} and
     * {@link #index()}.
     * @return {@link String} value.
     * @see AttributeType#value()
     * @see ObjectUtil#nonNull(Object)
     * @see #baseAttribute()
     * @see #className()
     * @see #index()
     */
    @NotNull
    public String fullAttribute() {
        String className = className();
        Integer index = index();
        String base = baseAttribute();
        String withClass = String.format("//%s[%s]", className, base);

        if (ObjectUtil.nonNull(index)) {
            return String.format("%s[%d]", withClass, index);
        } else {
            return withClass;
        }
    }

    /**
     * Get a new {@link Attribute} instance with a class name.
     * @param clsName {@link String} value.
     * @return {@link Attribute} instance.
     * @see Builder#build()
     * @see Builder#withAttribute(Attribute)
     * @see Builder#withClass(String)
     * @see #builder()
     */
    @NotNull
    public Attribute withClass(@NotNull String clsName) {
        return builder().withAttribute(this).withClass(clsName).build();
    }

    /**
     * Get a new {@link Attribute} instance with an index.
     * @param index {@link Integer} value.
     * @return {@link Attribute} instance.
     * @see Builder#build()
     * @see Builder#withAttribute(Attribute)
     * @see Builder#withIndex(Integer)
     * @see #builder()
     */
    @NotNull
    public Attribute withIndex(@Nullable Integer index) {
        return builder().withAttribute(this).withIndex(index).build();
    }

    /**
     * Builder class for {@link Attribute}.
     */
    public static final class Builder {
        @NotNull private final Attribute ATTRIBUTE;

        Builder() {
            ATTRIBUTE = new Attribute();
        }

        /**
         * Add an attribute to {@link #ATTRIBUTES}.
         * @param attribute The attribute to be added.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull String attribute) {
            ATTRIBUTE.ATTRIBUTES.add(attribute);
            return this;
        }

        /**
         * Add attributes from {@link Attribute} instance.
         * @param attribute {@link Attribute} instance.
         * @return The current {@link Builder} instance.
         * @see Attribute#attributes()
         * @see #addAttributes(Collection)
         */
        @NotNull
        public Builder addAttributes(@NotNull Attribute attribute) {
            return addAttributes(attribute.attributes());
        }

        /**
         * Add attributes to {@link #ATTRIBUTES}.
         * @param attributes {@link Collection} of {@link String}.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttributes(@NotNull Collection<String> attributes) {
            ATTRIBUTE.ATTRIBUTES.addAll(attributes);
            return this;
        }

        /**
         * Replace all attributes within {@link #ATTRIBUTES}.
         * @param attributes {@link Collection} of {@link String}.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder withAttributes(@NotNull Collection<String> attributes) {
            ATTRIBUTE.ATTRIBUTES.clear();
            ATTRIBUTE.ATTRIBUTES.addAll(attributes);
            return this;
        }

        /**
         * Replace all attributes with those from another {@link Attribute}.
         * @param attribute {@link Attribute} instance.
         * @return The current {@link Builder} instance.
         * @see Attribute#attributes()
         * @see #withAttributes(Collection)
         */
        @NotNull
        public Builder withAttributes(@NotNull Attribute attribute) {
            return withAttributes(attribute.attributes());
        }

        /**
         * Set the {@link #mode} instance. This will be used to decide how
         * elements are to be searched using {@link #ATTRIBUTES}.
         * @param mode {@link Mode} instance.
         * @return The current {@link Builder} instance.
         * @see #mode
         */
        @NotNull
        public Builder withMode(@NotNull Mode mode) {
            ATTRIBUTE.mode = mode;
            return this;
        }

        /**
         * Set the {@link #formatible} instance.
         * @param formatible {@link Formatible} instance.
         * @return The current {@link Builder} instance.
         * @see #formatible
         */
        @NotNull
        public Builder withFormatible(@NotNull Formatible<?> formatible) {
            ATTRIBUTE.formatible = formatible;
            return this;
        }

        /**
         * Set {@link #className}.
         * @param clsName {@link String} value.
         * @return The current {@link Builder} instance.
         * @see #className
         */
        @NotNull
        public Builder withClass(@NotNull String clsName) {
            ATTRIBUTE.className = clsName;
            return this;
        }

        /**
         * Set {@link #index} instance.
         * @param index {@link Integer} value.
         * @return The {@link Builder} instance.
         * @see #index
         */
        @NotNull
        public Builder withIndex(@Nullable Integer index) {
            ATTRIBUTE.index = index;
            return this;
        }

        /**
         * Replace all properties with those of another {@link Attribute}.
         * @param attribute {@link Attribute} instance.
         * @return The current {@link Builder} instance.
         * @see Attribute#className()
         * @see Attribute#formatible()
         * @see Attribute#index()
         * @see Attribute#mode()
         * @see #withAttribute(Attribute)
         * @see #withClass(String)
         * @see #withFormatible(Formatible)
         * @see #withIndex(Integer)
         * @see #withMode(Mode)
         */
        @NotNull
        public Builder withAttribute(@NotNull Attribute attribute) {
            return this
                .withAttributes(attribute)
                .withClass(attribute.className())
                .withFormatible(attribute.formatible())
                .withIndex(attribute.index())
                .withMode(attribute.mode());
        }

        @NotNull
        public Attribute build() {
            return ATTRIBUTE;
        }
    }

    /**
     * Classes that implement this interface must provide {@link String} format
     * that can be used to construct {@link Attribute}.
     */
    @FunctionalInterface
    public interface Formatible<T> extends AttributeType<T> {
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
         * Get the format {@link String} with which we construct an XPath
         * query.
         * @return {@link String} value.
         * @see #formatValue(Object)
         * @see #value()
         */
        @NotNull
        default String stringFormat() {
            String raw = formatValue(value());
            return String.format("@%1$s=%2$s", "%1$s", raw);
        }
    }
}
