package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 6/6/17.
 */

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.swiften.javautilities.collection.CollectionUtil;
import org.swiften.javautilities.object.ObjectUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is an enhanced version of {@link Attribute} as it adds class name and
 * index.
 */
public final class CompoundAttribute {
    /**
     * Get {@link Builder} instance.
     * @return {@link Builder} instance.
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Empty {@link CompoundAttribute}.
     * @return {@link CompoundAttribute} instance.
     * @see #builder()
     */
    @NotNull
    public static CompoundAttribute empty() {
        return CompoundAttribute.builder().build();
    }

    /**
     * {@link CompoundAttribute} with class.
     * @param clsName {@link String} value.
     * @return {@link CompoundAttribute} instance.
     * @see #empty()
     * @see #withClass(String)
     */
    @NotNull
    public static CompoundAttribute forClass(@NotNull String clsName) {
        return empty().withClass(clsName);
    }

    /**
     * {@link CompoundAttribute} with a single {@link Attribute}.
     * @param attribute {@link Attribute} instance.
     * @return {@link CompoundAttribute} instance.
     * @see Builder#addAttribute(Attribute)
     * @see Builder#build()
     * @see #builder()
     */
    @NotNull
    public static CompoundAttribute single(@NotNull Attribute attribute) {
        return builder().addAttribute(attribute).build();
    }

    @NotNull private final Collection<Attribute<?>> ATTRIBUTES;
    @NotNull private String className;
    @Nullable private Integer index;

    CompoundAttribute() {
        ATTRIBUTES = new LinkedList<>();
        className = "*";
    }

    /**
     * Get {@link #ATTRIBUTES}.
     * @return {@link Collection} of {@link Attribute}.
     * @see Collections#unmodifiableCollection(Collection)
     * @see #ATTRIBUTES
     */
    @NotNull
    public Collection<Attribute<?>> attributes() {
        return Collections.unmodifiableCollection(ATTRIBUTES);
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
     * Get the base attribute from {@link #ATTRIBUTES}.
     * @return {@link String} value.
     * @see Attribute#fullAttribute()
     * @see #attributes()
     */
    @NotNull
    private String baseAttribute() {
        List<String> attrs = attributes().stream()
            .map(Attribute::fullAttribute)
            .collect(Collectors.toList());

        return String.join("", attrs);
    }

    /**
     * Get the {@link #baseAttribute()} with {@link #className}.
     * @return {@link String} value.
     * @see #className()
     * @see #baseAttribute()
     */
    @NotNull
    private String attributeWithClass() {
        String className = className();
        String wrapped = baseAttribute();
        return String.format("//%s%s", className, wrapped);
    }

    /**
     * Get the full attribute, including {@link #index}.
     * @return {@link String} value.
     * @see ObjectUtil#nonNull(Object)
     * @see #attributeWithClass()
     * @see #index()
     */
    @NotNull
    public String fullAttribute() {
        String withClass = attributeWithClass();
        Integer index = index();

        if (ObjectUtil.nonNull(index)) {
            return String.format("%s[%d]", withClass, index);
        } else {
            return withClass;
        }
    }

    /**
     * Get a new {@link CompoundAttribute} instance with a class name.
     * @param clsName {@link String} value.
     * @return {@link CompoundAttribute} instance.
     * @see CompoundAttribute.Builder#build()
     * @see CompoundAttribute.Builder#withCompoundAttribute(CompoundAttribute)
     * @see CompoundAttribute.Builder#withClass(String)
     * @see #builder()
     */
    @NotNull
    public CompoundAttribute withClass(@NotNull String clsName) {
        return CompoundAttribute.builder()
            .withCompoundAttribute(this)
            .withClass(clsName)
            .build();
    }

    /**
     * Get a new {@link CompoundAttribute} instance with an index.
     * @param index {@link Integer} value.
     * @return {@link CompoundAttribute} instance.
     * @see CompoundAttribute.Builder#build()
     * @see CompoundAttribute.Builder#withCompoundAttribute(CompoundAttribute)
     * @see CompoundAttribute.Builder#withIndex(Integer)
     * @see #builder()
     */
    @NotNull
    public CompoundAttribute withIndex(@Nullable Integer index) {
        return CompoundAttribute.builder()
            .withCompoundAttribute(this)
            .withIndex(index)
            .build();
    }

    /**
     * Get a new {@link CompoundAttribute} instance with extra {@link Attribute}.
     * @param attrs {@link Collection} of {@link Attribute}.
     * @return {@link CompoundAttribute} instance.
     * @see CompoundAttribute.Builder#addAttributes(Collection)
     * @see CompoundAttribute.Builder#withCompoundAttribute(CompoundAttribute)
     * @see #builder()
     */
    public CompoundAttribute addAttributes(@NotNull Collection<Attribute<?>> attrs) {
        return CompoundAttribute.builder()
            .withCompoundAttribute(this)
            .addAttributes(attrs)
            .build();
    }

    /**
     * Same as above, but uses a varargs of {@link Attribute}.
     * @param attrs Varargs of {@link Attribute}.
     * @return {@link CompoundAttribute} instance.
     * @see CollectionUtil#asList(Object[])
     * @see #addAttributes(Collection)
     */
    @NotNull
    public CompoundAttribute addAttributes(@NotNull Attribute<?>...attrs) {
        return addAttributes(CollectionUtil.asList(attrs));
    }

    /**
     * Builder class for {@link CompoundAttribute}.
     */
    public static final class Builder {
        @NotNull private final CompoundAttribute ATTRIBUTE;

        Builder() {
            ATTRIBUTE = new CompoundAttribute();
        }

        /**
         * Add {@link Attribute} to {@link #ATTRIBUTES}.
         * @param attribute {@link Attribute} instance.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull Attribute attribute) {
            ATTRIBUTE.ATTRIBUTES.add(attribute);
            return this;
        }

        /**
         * Add {@link Attribute} to {@link #ATTRIBUTES}.
         * @param attributes {@link Collection} of {@link Attribute}.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttributes(@NotNull Collection<Attribute<?>> attributes) {
            ATTRIBUTE.ATTRIBUTES.addAll(attributes);
            return this;
        }

        /**
         * Replace all {@link Attribute} within {@link #ATTRIBUTES}.
         * @param attributes {@link Collection} of {@link Attribute}.
         * @return The current {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder withAttributes(@NotNull Collection<Attribute<?>> attributes) {
            ATTRIBUTE.ATTRIBUTES.clear();
            ATTRIBUTE.ATTRIBUTES.addAll(attributes);
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
         * @return The {@link Attribute.Builder} instance.
         * @see #index
         */
        @NotNull
        public Builder withIndex(@Nullable Integer index) {
            ATTRIBUTE.index = index;
            return this;
        }

        /**
         * Replace all properties with those of another {@link CompoundAttribute}.
         * @param attribute {@link CompoundAttribute} instance.
         * @return The current {@link Builder} instance.
         * @see CompoundAttribute#attributes()
         * @see CompoundAttribute#className()
         * @see CompoundAttribute#index()
         * @see #withAttributes(Collection)
         * @see #withClass(String)
         * @see #withIndex(Integer)
         */
        @NotNull
        public Builder withCompoundAttribute(@NotNull CompoundAttribute attribute) {
            return this
                .withAttributes(attribute.attributes())
                .withClass(attribute.className())
                .withIndex(attribute.index());
        }

        /**
         * Get {@link #ATTRIBUTE}.
         * @return {@link Attribute} instance.
         * @see #ATTRIBUTE
         */
        @NotNull
        public CompoundAttribute build() {
            return ATTRIBUTE;
        }
    }
}
