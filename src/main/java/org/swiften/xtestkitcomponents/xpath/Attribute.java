package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 4/4/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.platform.Platform;
import org.swiften.xtestkitcomponents.common.BaseErrorType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to deal with multiple attributes being used to
 * describe the same thing. For example,
 * {@link Platform#IOS} may use both
 * 'title' and 'text' to describe an element that has a text. To be used
 * with {@link XPath}.
 */
public final class Attribute {
    @NotNull
    public static final Attribute BLANK = new Attribute();

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    @NotNull
    public static Attribute single(@NotNull String attribute) {
        return builder().addAttribute(attribute).build();
    }

    public enum Mode implements BaseErrorType {
        AND,
        OR;

        /**
         * Get the joiner {@link String} that will be used to join attributes
         * in {@link Attribute#attributes}.
         * @return {@link String} value.
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
         * different {@link XPath} queries.
         * @return {@link String} value.
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
    @NotNull private List<String> attributes;

    Attribute() {
        mode = Mode.AND;
        attributes = new ArrayList<>();
    }

    @NotNull
    public List<String> attributes() {
        return attributes;
    }

    @NotNull
    public Mode mode() {
        return mode;
    }

    public static final class Builder {
        @NotNull private final Attribute ATTRIBUTE;

        Builder() {
            ATTRIBUTE = new Attribute();
        }

        /**
         * Add an attribute to {@link #ATTRIBUTE#attributes}.
         * @param attribute The attribute to be added.
         * @return The current {@link Builder} instance.
         */
        @NotNull
        public Builder addAttribute(@NotNull String attribute) {
            ATTRIBUTE.attributes.add(attribute);
            return this;
        }

        /**
         * Add add attributes from {@link Attribute} instance.
         * @param attribute {@link Attribute} instance.
         * @return The current {@link Builder} instance.
         * @see Attribute#attributes()
         */
        @NotNull
        public Builder addAttribute(@NotNull Attribute attribute) {
            ATTRIBUTE.attributes.addAll(attribute.attributes());
            return this;
        }

        /**
         * Set the {@link #ATTRIBUTE#mode} value. This will be used to
         * decide how elements are to be searched using the
         * {@link #ATTRIBUTE#attributes} {@link List}
         * @param mode {@link Mode} instance.
         * @return The current {@link Builder} instance.
         */
        @NotNull
        public Builder withMode(@NotNull Mode mode) {
            ATTRIBUTE.mode = mode;
            return this;
        }

        @NotNull
        public Attribute build() {
            return ATTRIBUTE;
        }
    }
}
