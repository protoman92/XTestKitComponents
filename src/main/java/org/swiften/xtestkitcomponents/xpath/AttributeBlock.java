package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 13/6/17.
 */

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a block of one or more {@link Attribute}, wrapped
 * within square brackets.
 */
public final class AttributeBlock implements AttributeType {
    /**
     * Get {@link Builder} instance.
     * @return {@link Builder} instance.
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    /**
     * {@link AttributeBlock} with a single {@link AttributeType}.
     * @param attribute {@link AttributeType} instance.
     * @return {@link AttributeBlock} instance.
     * @see Builder#addAttribute(AttributeType)
     * @see Builder#build()
     * @see #builder()
     */
    @NotNull
    public static AttributeBlock single(@NotNull AttributeType attribute) {
        return builder().addAttribute(attribute).build();
    }

    @NotNull private final Collection<AttributeType> ATTRIBUTES;
    @NotNull private Joiner joiner;

    private AttributeBlock() {
        ATTRIBUTES = new ArrayList<>();
        joiner = Joiner.OR;
    }

    @NotNull
    @Override
    public String toString() {
        return fullAttribute();
    }

    /**
     * Get {@link #ATTRIBUTES}.
     * @return {@link List} of {@link AttributeType}.
     * @see #ATTRIBUTES
     */
    @NotNull
    public Collection<AttributeType> attributes() {
        return Collections.unmodifiableCollection(ATTRIBUTES);
    }

    /**
     * Override this method to provide default implementation.
     * @return {@link Wrapper} instance.
     * @see Wrapper#BASIC
     * @see #wrapper
     */
    @NotNull
    private Wrapper wrapper() {
        return Wrapper.BASIC;
    }

    /**
     * Override this method to provide default implementation.
     * @return {@link Joiner} instance.
     * @see #joiner
     */
    @NotNull
    public Joiner joiner() {
        return joiner;
    }

    /**
     * Override this method to provide default implementation.
     * @param joiner {@link Joiner} instance.
     * @return {@link AttributeBlock} instance.
     * @see Builder#withBlock(AttributeBlock)
     * @see Builder#withJoiner(Joiner)
     * @see Builder#build()
     * @see #builder()
     */
    @NotNull
    public AttributeBlock withJoiner(@NotNull Joiner joiner) {
        return builder().withBlock(this).withJoiner(joiner).build();
    }

    /**
     * Override this method to provide default implementation.
     * @return {@link String} value.
     * @see AttributeType#fullAttribute()
     * @see Joiner#symbol()
     * @see #joiner()
     */
    @NotNull
    private String baseAttribute() {
        Joiner joiner = joiner();
        String joinerSymbol = String.format(" %s ", joiner.symbol());

        List<String> attrs = attributes().stream()
            .map(AttributeType::fullAttribute)
            .collect(Collectors.toList());

        return String.join(joinerSymbol, attrs);
    }

    /**
     * Get the wrapped {@link #baseAttribute()}.
     * @return {@link String} value.
     * @see Wrapper#wrapperFormat()
     * @see #baseAttribute()
     * @see #wrapper()
     */
    @NotNull
    private String wrappedAttribute() {
        Wrapper wrapper = wrapper();
        String wrapperFormat = wrapper.wrapperFormat();
        String base = baseAttribute();
        return String.format(wrapperFormat, base);
    }

    /**
     * Get the full attribute.
     * @return {@link String} value.
     * @see #wrappedAttribute()
     */
    @NotNull
    @Override
    public String fullAttribute() {
        return wrappedAttribute();
    }

    /**
     * Builder class for {@link AttributeBlock}.
     */
    public static final class Builder {
        @NotNull private final AttributeBlock BLOCK;

        Builder() {
            BLOCK = new AttributeBlock();
        }

        /**
         * Add {@link Attribute} to {@link #ATTRIBUTES}.
         * @param attribute {@link AttributeType} instance.
         * @return {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull AttributeType attribute) {
            BLOCK.ATTRIBUTES.add(attribute);
            return this;
        }

        /**
         * Add {@link Attribute} to {@link #ATTRIBUTES}.
         * @param attrs {@link Collection} of {@link AttributeType}.
         * @return {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull Collection<AttributeType> attrs) {
            BLOCK.ATTRIBUTES.addAll(attrs);
            return this;
        }

        /**
         * Add {@link Attribute} to {@link #ATTRIBUTES}.
         * @param attrs Vararg of {@link AttributeType} instances.
         * @return {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull AttributeType...attrs) {
            Collections.addAll(BLOCK.ATTRIBUTES, attrs);
            return this;
        }

        /**
         * Replace {@link #ATTRIBUTES} with new {@link AttributeType}.
         * @param attrs {@link Collection} of {@link AttributeType}.
         * @return {@link Builder} instance.
         * @see #addAttribute(Collection)
         */
        @NotNull
        public Builder withAttribute(@NotNull Collection<AttributeType> attrs) {
            BLOCK.ATTRIBUTES.clear();
            return addAttribute(attrs);
        }

        /**
         * Set the {@link #joiner} instance.
         * @param joiner {@link Joiner} instance.
         * @return {@link Builder} instance.
         * @see #joiner
         */
        @NotNull
        public Builder withJoiner(@NotNull Joiner joiner) {
            BLOCK.joiner = joiner;
            return this;
        }

        /**
         * Copy properties from another {@link AttributeBlock}.
         * @param block {@link AttributeBlock} instance.
         * @return {@link Builder} instance.
         * @see AttributeBlock#attributes()
         * @see AttributeBlock#joiner()
         * @see AttributeBlock#wrapper()
         * @see #withAttribute(Collection)
         * @see #withJoiner(Joiner)
         */
        @NotNull
        public Builder withBlock(@NotNull AttributeBlock block) {
            return this
                .withAttribute(block.attributes())
                .withJoiner(block.joiner());
        }

        /**
         * Get {@link #BLOCK}.
         * @return {@link AttributeBlock} instance.
         * @see #BLOCK
         */
        @NotNull
        public AttributeBlock build() {
            return BLOCK;
        }
    }
}
