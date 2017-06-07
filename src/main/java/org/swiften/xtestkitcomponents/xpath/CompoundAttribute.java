package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 6/6/17.
 */

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.swiften.javautilities.collection.CollectionUtil;
import org.swiften.javautilities.object.ObjectUtil;
import org.swiften.xtestkitcomponents.common.BaseErrorType;

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

    /**
     * Get {@link Collection} of {@link CompoundAttribute} that, once used
     * together, would produce a query that locates the following sibling
     * {@link CompoundAttribute}.
     * @param target {@link CompoundAttribute} instance.
     * @param sibling {@link CompoundAttribute} instance.
     * @return {@link Collection} of {@link CompoundAttribute}.
     * @see Builder#withAxis(Axis)
     * @see Builder#withCompoundAttribute(CompoundAttribute)
     * @see CollectionUtil#asList(Object[])
     * @see Axis#FOLLOWING_SIBLING
     * @see Path#DIRECT
     * @see #builder()
     */
    @NotNull
    public static Collection<CompoundAttribute> followingSibling(
        @NotNull CompoundAttribute target,
        @NotNull CompoundAttribute sibling
    ) {
        CompoundAttribute c1 = builder()
            .withCompoundAttribute(sibling)
            .build();

        CompoundAttribute c2 = builder()
            .withCompoundAttribute(target)
            .withAxis(Axis.FOLLOWING_SIBLING)
            .withPath(Path.DIRECT)
            .build();

        return CollectionUtil.asList(c1, c2);
    }

    /**
     * Get {@link Collection} of {@link CompoundAttribute} that, once used
     * together, would produce a query that locates the preceding sibling
     * {@link CompoundAttribute}.
     * @param target {@link CompoundAttribute} instance.
     * @param sibling {@link CompoundAttribute} instance.
     * @return {@link Collection} of {@link CompoundAttribute}.
     * @see Builder#withAxis(Axis)
     * @see Builder#withCompoundAttribute(CompoundAttribute)
     * @see CollectionUtil#asList(Object[])
     * @see Axis#PRECEDING_SIBLING
     * @see Path#DIRECT
     * @see #builder()
     */
    public static Collection<CompoundAttribute> precedingSibling(
        @NotNull CompoundAttribute target,
        @NotNull CompoundAttribute sibling
    ) {
        CompoundAttribute c1 = builder()
            .withCompoundAttribute(sibling)
            .build();

        CompoundAttribute c2 = builder()
            .withCompoundAttribute(target)
            .withAxis(Axis.PRECEDING_SIBLING)
            .withPath(Path.DIRECT)
            .build();

        return CollectionUtil.asList(c1, c2);
    }

    /**
     * Use this to specify how child elements are queried.
     */
    public enum Path implements BaseErrorType {
        ANY,
        DIRECT;

        /**
         * Get the symbol {@link String} to append to the start of the
         * {@link CompoundAttribute#baseAttribute()}.
         * @return {@link String} value.
         * @see #ANY
         * @see #DIRECT
         * @see #NOT_AVAILABLE
         */
        @NotNull
        private String symbol() {
            switch (this) {
                case ANY:
                    return "//";

                case DIRECT:
                    return "/";

                default:
                    throw new RuntimeException(NOT_AVAILABLE);
            }
        }
    }

    /**
     * Use this to add axis to {@link CompoundAttribute}.
     */
    public enum Axis implements BaseErrorType {
        FOLLOWING_SIBLING,
        PRECEDING_SIBLING,
        NONE;

        /**
         * Get the associated symbol {@link String}.
         * @return {@link String} value.
         * @see #FOLLOWING_SIBLING
         * @see #PRECEDING_SIBLING
         * @see #NONE
         * @see #NOT_AVAILABLE
         */
        @NotNull
        private String symbol() {
            switch (this) {
                case FOLLOWING_SIBLING:
                    return "following-sibling::";

                case PRECEDING_SIBLING:
                    return "preceding-sibling::";

                case NONE:
                    return "";

                default:
                    throw new RuntimeException(NOT_AVAILABLE);
            }
        }
    }

    @NotNull private final Collection<Attribute<?>> ATTRIBUTES;
    @NotNull private Axis axis;
    @NotNull private Path path;
    @NotNull private String className;
    @Nullable private Integer index;

    CompoundAttribute() {
        ATTRIBUTES = new LinkedList<>();
        path = Path.ANY;
        axis = Axis.NONE;
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
     * Get {@link #path}.
     * @return {@link Path} instance.
     * @see #path
     */
    @NotNull
    public Path path() {
        return path;
    }

    /**
     * Get {@link #axis}.
     * @return {@link Axis} instance.
     * @see #axis
     */
    @NotNull
    public Axis axis() {
        return axis;
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
        return String.format("%s%s", className, wrapped);
    }

    /**
     * Get the {@link #attributeWithClass()} with {@link #path}.
     * @return {@link String} value.
     * @see Path#symbol()
     * @see Axis#symbol()
     * @see #attributeWithClass()
     * @see #path()
     * @see #axis()
     */
    @NotNull
    private String attributeWithPath() {
        String withClass = attributeWithClass();
        Path descendant = path();
        Axis dmSuffix = axis();
        String dSymbol = descendant.symbol();
        String dmSymbol = dmSuffix.symbol();
        return String.format("%s%s%s", dSymbol, dmSymbol, withClass);
    }

    /**
     * Get the full attribute, including {@link #index}.
     * @return {@link String} value.
     * @see ObjectUtil#nonNull(Object)
     * @see #attributeWithPath()
     * @see #index()
     */
    @NotNull
    public String fullAttribute() {
        String withDM = attributeWithPath();
        Integer index = index();

        if (ObjectUtil.nonNull(index)) {
            return String.format("%s[%d]", withDM, index);
        } else {
            return withDM;
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
     * @see CompoundAttribute.Builder#addAttribute(Collection)
     * @see CompoundAttribute.Builder#withCompoundAttribute(CompoundAttribute)
     * @see #builder()
     */
    public CompoundAttribute addAttributes(@NotNull Collection<Attribute<?>> attrs) {
        return CompoundAttribute.builder()
            .withCompoundAttribute(this)
            .addAttribute(attrs)
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
        public Builder addAttribute(@NotNull Collection<Attribute<?>> attributes) {
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
        public Builder withAttribute(@NotNull Collection<Attribute<?>> attributes) {
            ATTRIBUTE.ATTRIBUTES.clear();
            ATTRIBUTE.ATTRIBUTES.addAll(attributes);
            return this;
        }

        /**
         * Set the {@link #path} instance.
         * @param mode {@link Path} instance.
         * @return The current {@link Builder} instance.
         * @see #path
         */
        @NotNull
        public Builder withPath(@NotNull Path mode) {
            ATTRIBUTE.path = mode;
            return this;
        }

        /**
         * Set the {@link #axis} instance.
         * @param axis {@link Axis} instance.
         * @return The current {@link Builder} instance.
         * @see #axis
         */
        @NotNull
        public Builder withAxis(@NotNull Axis axis) {
            ATTRIBUTE.axis = axis;
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
         * @see CompoundAttribute#axis()
         * @see CompoundAttribute#className()
         * @see CompoundAttribute#index()
         * @see CompoundAttribute#path()
         * @see #withAttribute(Collection)
         * @see #withAxis(Axis)
         * @see #withClass(String)
         * @see #withIndex(Integer)
         * @see #withPath(Path)
         */
        @NotNull
        public Builder withCompoundAttribute(@NotNull CompoundAttribute attribute) {
            return this
                .withAttribute(attribute.attributes())
                .withAxis(attribute.axis())
                .withPath(attribute.path())
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
