package org.swiften.xtestkitcomponents.xpath;

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.common.BaseErrorType;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by haipham on 3/19/17.
 */

/**
 * Use this utility class to easily compose {@link XPath} queries in order
 * to write cross-platform test.
 */
public class XPath {
    /**
     * Get {@link XPath} instance.
     * @return {@link Builder} instance.
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    @NotNull private final List<Attribute> ATTRIBUTES;

    protected XPath() {
        ATTRIBUTES = new LinkedList<>();
    }

    @NotNull
    public String toString() {
        return attribute();
    }

    /**
     * Get {@link #ATTRIBUTES}.
     * @return {@link List} of {@link Attribute}.
     * @see Collections#unmodifiableList(List)
     * @see #ATTRIBUTES
     */
    @NotNull
    public List<Attribute> attributes() {
        return Collections.unmodifiableList(ATTRIBUTES);
    }

    /**
     * Get an attribute represented by {@link #ATTRIBUTES}.
     * @return {@link String} value.
     * @see Attribute#fullAttribute()
     * @see #ATTRIBUTES
     */
    @NotNull
    public String attribute() {
        List<Attribute> attributes = attributes();

        List<String> components = attributes.stream()
            .map(Attribute::fullAttribute)
            .collect(Collectors.toList());

        return String.join("", components);
    }

    //region Builder
    /**
     * Builder class for {@link XPath}.
     */
    public static class Builder implements BaseErrorType {
        @NotNull private final XPath XPATH;

        protected Builder() {
            XPATH = new XPath();
        }

        /**
         * Replace {@link #ATTRIBUTES} with new {@link Attribute} instances.
         * @param xPath {@link XPath} instance.
         * @return The current {@link Builder} instance.
         * @see XPath#attributes()
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder withXPath(@NotNull XPath xPath) {
            XPATH.ATTRIBUTES.clear();
            XPATH.ATTRIBUTES.addAll(xPath.attributes());
            return this;
        }

        /**
         * Add a child {@link XPath}'s {@link #ATTRIBUTES} to
         * {@link #ATTRIBUTES}.
         * @param xPath {@link XPath} instance.
         * @return The current {@link Builder} instance.
         * @see XPath#attributes()
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addChildXPath(@NotNull XPath xPath) {
            XPATH.ATTRIBUTES.addAll(xPath.attributes());
            return this;
        }

        /**
         * Add {@link Attribute} to {@link #ATTRIBUTES}.
         * @param attribute {@link Attribute} instance.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull Attribute attribute) {
            XPATH.ATTRIBUTES.add(attribute);
            return this;
        }

        @NotNull
        public XPath build() {
            return XPATH;
        }
    }
    //endregion
}
