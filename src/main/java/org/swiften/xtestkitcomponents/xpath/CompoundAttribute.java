package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 6/6/17.
 */

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.swiften.javautilities.collection.HPIterables;
import org.swiften.javautilities.object.HPObjects;
import org.swiften.javautilities.protocol.ClassNameProviderType;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is an enhanced version of {@link Attribute} as it adds class name and
 * index.
 */
public final class CompoundAttribute implements AttributeType {
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
     * Same as above, but uses {@link ClassNameProviderType}.
     * @param param {@link ClassNameProviderType} instance.
     * @return {@link CompoundAttribute} instance.
     * @see ClassNameProviderType#className()
     * @see #forClass(ClassNameProviderType)
     */
    @NotNull
    public static CompoundAttribute forClass(@NotNull ClassNameProviderType param) {
        return forClass(param.className());
    }

    /**
     * {@link CompoundAttribute} with a single {@link AttributeType}.
     * @param attribute {@link AttributeType} instance.
     * @return {@link CompoundAttribute} instance.
     * @see Builder#addAttribute(AttributeType)
     * @see Builder#withAttribute(CompoundAttribute)
     * @see Builder#build()
     * @see #builder()
     */
    @NotNull
    public static CompoundAttribute single(@NotNull AttributeType attribute) {
        if (attribute instanceof CompoundAttribute) {
            return builder().withAttribute((CompoundAttribute)attribute).build();
        } else {
            return builder().addAttribute(attribute).build();
        }
    }

    @NotNull private final Collection<AttributeBlock> ATTRIBUTES;
    @NotNull private Axis axis;
    @NotNull private Path path;
    @NotNull private Wrapper wrapper;
    @NotNull private String className;
    @Nullable private Integer index;

    CompoundAttribute() {
        ATTRIBUTES = new LinkedList<>();
        path = Path.ANY;
        axis = Axis.NONE;
        wrapper = Wrapper.NONE;
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
     * Get {@link #wrapper}.
     * @return {@link Wrapper} instance.
     * @see #wrapper
     */
    @NotNull
    public Wrapper wrapper() {
        return wrapper;
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
     * Get {@link #attributeWithPath()}, including {@link #index}.
     * @return {@link String} value.
     * @see HPObjects#nonNull(Object)
     * @see #attributeWithPath()
     * @see #index()
     */
    @NotNull
    private String attributeWithIndex() {
        String withDM = attributeWithPath();
        Integer index = index();

        if (HPObjects.nonNull(index)) {
            return String.format("%s[%d]", withDM, index);
        } else {
            return withDM;
        }
    }

    /**
     * Get {@link #attributeWithIndex()} with {@link #wrapper}.
     * @return {@link String} value.
     * @see Wrapper#wrapperFormat()
     * @see #attributeWithIndex()
     * @see #wrapper()
     */
    @NotNull
    private String wrappedAttribute() {
        String withIndex = attributeWithIndex();
        Wrapper wrapper = wrapper();
        String format = wrapper.wrapperFormat();
        return String.format(format, withIndex);
    }

    /**
     * Get the full attribute.
     * @return {@link String} value.
     * @see #wrappedAttribute()
     */
    @NotNull
    public String fullAttribute() {
        return wrappedAttribute();
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
     * Same as above, but uses {@link ClassNameProviderType}.
     * @param param {@link ClassNameProviderType} instance.
     * @return {@link CompoundAttribute} instance.
     * @see ClassNameProviderType#className()
     * @see #withClass(String)
     */
    @NotNull
    public CompoundAttribute withClass(@NotNull ClassNameProviderType param) {
        return withClass(param.className());
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
     * @see HPIterables#asList(Object[])
     * @see #addAttribute(Collection)
     */
    @NotNull
    public CompoundAttribute addAttribute(@NotNull AttributeType...attrs) {
        return addAttribute(HPIterables.asList(attrs));
    }

    /**
     * Return a new {@link CompoundAttribute} wrapped in {@link Wrapper#NOT}.
     * @return {@link CompoundAttribute} instance.
     * @see CompoundAttribute.Builder#withAttribute(CompoundAttribute)
     * @see CompoundAttribute.Builder#withWrapper(Wrapper)
     * @see Wrapper#NOT
     */
    @NotNull
    public CompoundAttribute not() {
        return CompoundAttribute.builder()
            .withAttribute(this)
            .withWrapper(Wrapper.NOT)
            .build();
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
         * @see HPIterables#asList(Object[])
         * @see #addAttribute(Collection)
         */
        @NotNull
        public Builder addAttribute(@NotNull AttributeType...attributes) {
            return addAttribute(HPIterables.asList(attributes));
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
         * Same as above, but uses {@link ClassNameProviderType}.
         * @param param {@link ClassNameProviderType} instance.
         * @return {@link Builder} instance.
         * @see ClassNameProviderType#className()
         * @see #withClass(String)
         */
        @NotNull
        public Builder withClass(@NotNull ClassNameProviderType param) {
            return withClass(param.className());
        }

        /**
         * Set {@link #className} to none.
         * @return {@link Builder} instance.
         * @see #withClass(String)
         */
        @NotNull
        public Builder withNoClass() {
            return withClass("");
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
         * Set the {@link #wrapper} instance.
         * @param wrapper {@link Wrapper} instance.
         * @return {@link Builder} instance.
         * @see #wrapper
         */
        @NotNull
        public Builder withWrapper(@NotNull Wrapper wrapper) {
            ATTRIBUTE.wrapper = wrapper;
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
                .withIndex(attribute.index())
                .withWrapper(attribute.wrapper());
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
