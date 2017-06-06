package org.swiften.xtestkitcomponents.xpath;

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.collection.CollectionUtil;
import org.swiften.xtestkitcomponents.common.BaseErrorType;

import java.util.Collection;
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

    @NotNull private final List<CompoundAttribute> ATTRIBUTES;

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
    public List<CompoundAttribute> compoundAttributes() {
        return Collections.unmodifiableList(ATTRIBUTES);
    }

    /**
     * Get a new {@link XPath} by adding extra {@link Attribute} to each
     * {@link CompoundAttribute} within {@link #ATTRIBUTES}.
     * @param ATTRS {@link Attribute} instance.
     * @return {@link XPath} instance.
     * @see Builder#addAttributes(Collection)
     * @see Builder#build()
     * @see #builder()
     * @see #ATTRIBUTES
     */
    @NotNull
    public XPath addToEach(@NotNull final Collection<Attribute<?>> ATTRS) {
        List<CompoundAttribute> attributes = ATTRIBUTES.stream()
            .map(a -> a.addAttributes(ATTRS))
            .collect(Collectors.toList());

        return builder().addAttributes(attributes).build();
    }

    /**
     * Same as above, but uses a varargs of {@link Attribute}.
     * @param attrs {@link Attribute} instance.
     * @return {@link XPath} instance.
     * @see CollectionUtil#asList(Object[])
     * @see #addToEach(Collection)
     */
    @NotNull
    public XPath addToEach(@NotNull Attribute<?>...attrs) {
        return addToEach(CollectionUtil.asList(attrs));
    }

    /**
     * Get an attribute represented by {@link #ATTRIBUTES}.
     * @return {@link String} value.
     * @see Attribute#fullAttribute()
     * @see #compoundAttributes()
     * @see #ATTRIBUTES
     */
    @NotNull
    public String attribute() {
        List<CompoundAttribute> attributes = compoundAttributes();

        List<String> components = attributes.stream()
            .map(CompoundAttribute::fullAttribute)
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
         * @see XPath#compoundAttributes()
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder withXPath(@NotNull XPath xPath) {
            XPATH.ATTRIBUTES.clear();
            XPATH.ATTRIBUTES.addAll(xPath.compoundAttributes());
            return this;
        }

        /**
         * Add {@link Attribute} to {@link #ATTRIBUTES} by wrapping it within
         * {@link CompoundAttribute}.
         * @param attribute {@link Attribute} instance.
         * @return The current {@link Builder} instance.
         * @see CompoundAttribute#single(Attribute)
         * @see #addAttribute(CompoundAttribute)
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull Attribute attribute) {
            return addAttribute(CompoundAttribute.single(attribute));
        }

        /**
         * Add {@link CompoundAttribute} to {@link #ATTRIBUTES}.
         * @param attribute {@link CompoundAttribute} instance.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull CompoundAttribute attribute) {
            XPATH.ATTRIBUTES.add(attribute);
            return this;
        }

        /**
         * Add {@link CompoundAttribute} to {@link #ATTRIBUTES}.
         * @param attributes {@link Collection} of {@link CompoundAttribute}.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttributes(@NotNull Collection<CompoundAttribute> attributes) {
            XPATH.ATTRIBUTES.addAll(attributes);
            return this;
        }

        @NotNull
        public XPath build() {
            return XPATH;
        }
    }
    //endregion
}
