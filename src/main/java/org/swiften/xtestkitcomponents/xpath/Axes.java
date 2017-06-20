package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 20/6/17.
 */

import org.jetbrains.annotations.NotNull;

/**
 * Utility class to provide {@link CompoundAttribute} with specific {@link Axis}.
 */
public final class Axes {
    /**
     * Get {@link CompoundAttribute} that can be used in a {@link Axis#CHILD}
     * query.
     * @param child {@link AttributeType} instance.
     * @return {@link CompoundAttribute} instance.
     * @see CompoundAttribute.Builder#withAxis(Axis)
     * @see CompoundAttribute.Builder#withAttribute(CompoundAttribute)
     * @see CompoundAttribute.Builder#withPath(Path)
     * @see Axis#CHILD
     * @see Path#DIRECT
     */
    @NotNull
    public static CompoundAttribute child(@NotNull AttributeType child) {
        return CompoundAttribute.builder()
            .withAttribute(CompoundAttribute.single(child))
            .withAxis(Axis.CHILD)
            .withPath(Path.DIRECT)
            .build();
    }

    /**
     * Get {@link CompoundAttribute} that can be used in a
     * {@link Axis#FOLLOWING_SIBLING} query.
     * @param target {@link AttributeType} instance.
     * @return {@link CompoundAttribute} instance.
     * @see CompoundAttribute.Builder#withAxis(Axis)
     * @see CompoundAttribute.Builder#withAttribute(CompoundAttribute)
     * @see CompoundAttribute.Builder#withPath(Path)
     * @see Axis#FOLLOWING_SIBLING
     * @see Path#DIRECT
     */
    @NotNull
    public static CompoundAttribute followingSibling(@NotNull AttributeType target) {
        return CompoundAttribute.builder()
            .withAttribute(CompoundAttribute.single(target))
            .withAxis(Axis.FOLLOWING_SIBLING)
            .withPath(Path.DIRECT)
            .build();
    }

    /**
     * Get {@link CompoundAttribute} that can be used in a
     * {@link Axis#PRECEDING_SIBLING} query.
     * @param target {@link AttributeType} instance.
     * @return {@link CompoundAttribute} instance.
     * @see CompoundAttribute.Builder#withAxis(Axis)
     * @see CompoundAttribute.Builder#withAttribute(CompoundAttribute)
     * @see CompoundAttribute.Builder#withPath(Path)
     * @see Axis#PRECEDING_SIBLING
     * @see Path#DIRECT
     */
    public static CompoundAttribute precedingSibling(@NotNull AttributeType target) {
        return CompoundAttribute.builder()
            .withAttribute(CompoundAttribute.single(target))
            .withAxis(Axis.PRECEDING_SIBLING)
            .withPath(Path.DIRECT)
            .build();
    }

    /**
     * Get {@link CompoundAttribute} that can be used in a
     * {@link Axis#DESCENDANT} query.
     * @param descendant {@link CompoundAttribute} instance.
     * @return {@link CompoundAttribute} instance.
     * @see CompoundAttribute.Builder#addAttribute(AttributeType)
     * @see CompoundAttribute.Builder#withAxis(Axis)
     * @see CompoundAttribute.Builder#withAttribute(CompoundAttribute)
     * @see CompoundAttribute.Builder#withNoClass()
     * @see CompoundAttribute.Builder#withPath(Path)
     * @see CompoundAttribute#single(AttributeType)
     * @see Axis#DESCENDANT
     * @see Path#NONE
     */
    @NotNull
    public static CompoundAttribute descendant(@NotNull AttributeType descendant) {
        CompoundAttribute c2 = CompoundAttribute.builder()
            .withAttribute(CompoundAttribute.single(descendant))
            .withAxis(Axis.DESCENDANT)
            .withPath(Path.NONE)
            .build();

        return CompoundAttribute.builder()
            .withNoClass()
            .withPath(Path.NONE)
            .addAttribute(AttributeBlock.single(c2))
            .build();
    }
}
