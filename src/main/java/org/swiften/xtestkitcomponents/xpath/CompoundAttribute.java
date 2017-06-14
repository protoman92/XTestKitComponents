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
     * {@link CompoundAttribute} with a single {@link AttributeType}.
     * @param block {@link AttributeType} instance.
     * @return {@link CompoundAttribute} instance.
     * @see Builder#addAttribute(AttributeType)
     * @see Builder#build()
     * @see #builder()
     */
    @NotNull
    public static CompoundAttribute single(@NotNull AttributeType block) {
        return builder().addAttribute(block).build();
    }

    /**
     * Get {@link Collection} of {@link CompoundAttribute} that, once used
     * together, would produce a query that locates the following sibling
     * {@link CompoundAttribute}.
     * @param target {@link CompoundAttribute} instance.
     * @param sibling {@link CompoundAttribute} instance.
     * @return {@link Collection} of {@link CompoundAttribute}.
     * @see Builder#withAxis(Axis)
     * @see Builder#withAttribute(CompoundAttribute)
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
        CompoundAttribute c1 = builder().withAttribute(sibling).build();

        CompoundAttribute c2 = builder()
            .withAttribute(target)
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
     * @see Builder#withAttribute(CompoundAttribute)
     * @see CollectionUtil#asList(Object[])
     * @see Axis#PRECEDING_SIBLING
     * @see Path#DIRECT
     * @see #builder()
     */
    public static Collection<CompoundAttribute> precedingSibling(
        @NotNull CompoundAttribute target,
        @NotNull CompoundAttribute sibling
    ) {
        CompoundAttribute c1 = builder().withAttribute(sibling).build();

        CompoundAttribute c2 = builder()
            .withAttribute(target)
            .withAxis(Axis.PRECEDING_SIBLING)
            .withPath(Path.DIRECT)
            .build();

        return CollectionUtil.asList(c1, c2);
    }

    @NotNull private final Collection<AttributeBlock> ATTRIBUTES;
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
    public Collection<AttributeType> attributes() {
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
            .map(AttributeType::fullAttribute)
            .map(a -> String.format("[%s]", a))
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
     * @see CompoundAttribute.Builder#withAttribute(CompoundAttribute)
     * @see CompoundAttribute.Builder#withClass(String)
     * @see #builder()
     */
    @NotNull
    public CompoundAttribute withClass(@NotNull String clsName) {
        return CompoundAttribute.builder()
            .withAttribute(this)
            .withClass(clsName)
            .build();
    }

    /**
     * Get a new {@link CompoundAttribute} instance with an index.
     * @param index {@link Integer} value.
     * @return {@link CompoundAttribute} instance.
     * @see CompoundAttribute.Builder#build()
     * @see CompoundAttribute.Builder#withAttribute(CompoundAttribute)
     * @see CompoundAttribute.Builder#withIndex(Integer)
     * @see #builder()
     */
    @NotNull
    public CompoundAttribute withIndex(@Nullable Integer index) {
        return CompoundAttribute.builder()
            .withAttribute(this)
            .withIndex(index)
            .build();
    }

    /**
     * Get a new {@link CompoundAttribute} instance with extra
     * {@link AttributeType}.
     * @param attrs {@link Collection} of {@link AttributeType}.
     * @return {@link CompoundAttribute} instance.
     * @see CompoundAttribute.Builder#addAttribute(Collection)
     * @see CompoundAttribute.Builder#withAttribute(CompoundAttribute)
     * @see #builder()
     */
    public CompoundAttribute addAttribute(@NotNull Collection<AttributeType> attrs) {
        return CompoundAttribute.builder()
            .withAttribute(this)
            .addAttribute(attrs)
            .build();
    }

    /**
     * Same as above, but uses a varargs of {@link AttributeType}.
     * @param attrs Varargs of {@link AttributeType}.
     * @return {@link CompoundAttribute} instance.
     * @see CollectionUtil#asList(Object[])
     * @see #addAttribute(Collection)
     */
    @NotNull
    public CompoundAttribute addAttribute(@NotNull AttributeType...attrs) {
        return addAttribute(CollectionUtil.asList(attrs));
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
         * Add {@link AttributeType} to {@link #ATTRIBUTES}.
         * @param attribute {@link AttributeType} instance.
         * @return {@link Builder} instance.
         * @see AttributeBlock#single(AttributeType)
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull AttributeType attribute) {
            AttributeBlock block;

            if (attribute instanceof AttributeBlock) {
                block = (AttributeBlock)attribute;
            } else {
                block = AttributeBlock.single(attribute);
            }

            ATTRIBUTE.ATTRIBUTES.add(block);
            return this;
        }

        /**
         * Add {@link AttributeType} to {@link #ATTRIBUTES}.
         * @param attributes {@link Collection} of {@link AttributeType}.
         * @return {@link Builder} instance.
         * @see AttributeBlock#single(AttributeType)
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull Collection<AttributeType> attributes) {
            Collection<AttributeBlock> block1 = attributes.stream()
                .filter(AttributeBlock.class::isInstance)
                .map(AttributeBlock.class::cast)
                .collect(Collectors.toList());

            Collection<AttributeBlock> block2 = attributes.stream()
                .filter(a -> !AttributeBlock.class.isInstance(a))
                .map(AttributeBlock::single)
                .collect(Collectors.toList());

            ATTRIBUTE.ATTRIBUTES.addAll(block1);
            ATTRIBUTE.ATTRIBUTES.addAll(block2);
            return this;
        }

        /**
         * Add {@link AttributeType} to {@link #ATTRIBUTES}.
         * @param attributes Varargs of {@link AttributeType}.
         * @return {@link Builder} instance.
         * @see CollectionUtil#asList(Object[])
         * @see #addAttribute(Collection)
         */
        @NotNull
        public Builder addAttribute(@NotNull AttributeType...attributes) {
            return addAttribute(CollectionUtil.asList(attributes));
        }

        /**
         * Replace all {@link AttributeType} within {@link #ATTRIBUTES}.
         * @param attributes {@link Collection} of {@link AttributeType}.
         * @return {@link Builder} instance.
         * @see #addAttribute(Collection)
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder withAttribute(@NotNull Collection<AttributeType> attributes) {
            ATTRIBUTE.ATTRIBUTES.clear();
            return addAttribute(attributes);
        }

        /**
         * Set the {@link #path} instance.
         * @param mode {@link Path} instance.
         * @return {@link Builder} instance.
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
         * @return {@link Builder} instance.
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
         * @return {@link Builder} instance.
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
         * @return {@link Builder} instance.
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
        public Builder withAttribute(@NotNull CompoundAttribute attribute) {
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
