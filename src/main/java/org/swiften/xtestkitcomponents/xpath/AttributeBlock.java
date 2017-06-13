package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 13/6/17.
 */

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a block of one or more {@link Attribute}, wrapped
 * within square brackets.
 */
public final class AttributeBlock {
    @NotNull private final List<Attribute<?>> ATTRIBUTES;

    private AttributeBlock() {
        ATTRIBUTES = new ArrayList<>();
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
         * @param attribute {@link Attribute} instance.
         * @return {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull Attribute<?> attribute) {
            BLOCK.ATTRIBUTES.add(attribute);
            return this;
        }

        /**
         * Add {@link Attribute} to {@link #ATTRIBUTES}.
         * @param attrs {@link Collection} of {@link Attribute}.
         * @return {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull Collection<Attribute<?>> attrs) {
            BLOCK.ATTRIBUTES.addAll(attrs);
            return this;
        }

        /**
         * Add {@link Attribute} to {@link #ATTRIBUTES}.
         * @param attrs Vararg of {@link Attribute} instances.
         * @return {@link Builder} instance.
         * @see #ATTRIBUTES
         */
        @NotNull
        public Builder addAttribute(@NotNull Attribute<?>...attrs) {
            Collections.addAll(BLOCK.ATTRIBUTES, attrs);
            return this;
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
