package org.swiften.xtestkitcomponents.xpath;

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.collection.HPIterables;
import org.swiften.xtestkitcomponents.common.ErrorProviderType;

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
     * @param ATTRS {@link Collection} of {@link AttributeType} instance.
     * @return {@link XPath} instance.
     * @see Builder#addAttribute(Collection)
     * @see Builder#build()
     * @see #builder()
     * @see #ATTRIBUTES
     */
    @NotNull
    public XPath addToEach(@NotNull final Collection<AttributeType> ATTRS) {
        List<CompoundAttribute> attributes = ATTRIBUTES.stream()
            .map(a -> a.addAttribute(ATTRS))
            .collect(Collectors.toList());

        return builder().addAttribute(attributes).build();
    }

    /**
     * Same as above, but uses a varargs of {@link Attribute}.
     * @param attrs Varargs of {@link AttributeType} instance.
     * @return {@link XPath} instance.
     * @see HPIterables#asList(Object[])
     * @see #addToEach(Collection)
     */
    @NotNull
    public XPath addToEach(@NotNull AttributeType...attrs) {
        return addToEach(HPIterables.asList(attrs));
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
    public static class Builder implements ErrorProviderType {
        @NotNull private final XPath XPATH;

        protected Builder() {
            XPATH = new XPath();
        }

        /**
         * Replace {@link #ATTRIBUTES} with new {@link Attribute} instances.
         * @param xpath {@link XPath} instance.
         * @return {@link Builder} instance.
         * @see XPath#compoundAttributes()
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder withXPath(@NotNull XPath xpath) {
            XPATH.ATTRIBUTES.clear();
            XPATH.ATTRIBUTES.addAll(xpath.compoundAttributes());
            return this;
        }

        /**
         * Add {@link #ATTRIBUTES} from another {@link XPath} instance.
         * @param xpath {@link XPath} instance.
         * @return {@link Builder} instance.
         * @see XPath#compoundAttributes()
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addXPath(@NotNull XPath xpath) {
            XPATH.ATTRIBUTES.addAll(xpath.compoundAttributes());
            return this;
        }

        /**
         * Add {@link AttributeType} to {@link #ATTRIBUTES} by wrapping it
         * within {@link CompoundAttribute}.
         * @param attribute {@link AttributeType} instance.
         * @return {@link Builder} instance.
         * @see CompoundAttribute#single(AttributeType)
         * @see #addAttribute(CompoundAttribute)
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull AttributeType attribute) {
            return addAttribute(CompoundAttribute.single(attribute));
        }

        /**
         * Add {@link CompoundAttribute} to {@link #ATTRIBUTES}.
         * @param attribute {@link CompoundAttribute} instance.
         * @return {@link Builder} instance.
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
         * @return {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull Collection<CompoundAttribute> attributes) {
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
