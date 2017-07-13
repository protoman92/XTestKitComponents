package org.swiften.xtestkitcomponents.direction;

/**
 * Created by haipham on 5/9/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.collection.HPIterables;
import org.swiften.xtestkitcomponents.common.ErrorProviderType;

/**
 * This enum provides simplistic directions for certain actions such as
 * swipes.
 */
public enum Direction implements ErrorProviderType {
    UP_DOWN,
    DOWN_UP,
    LEFT_RIGHT,
    RIGHT_LEFT,
    NONE;

    /**
     * Get {@link #UP_DOWN} or {@link #DOWN_UP}, depending whether top-down
     * is true.
     * @param topDown {@link Boolean} value. If this is true, return
     *                {@link Direction#UP_DOWN}, and
     *                {@link Direction#DOWN_UP} otherwise.
     * @return {@link Direction} instance.
     * @see #DOWN_UP
     * @see #UP_DOWN
     */
    @NotNull
    public static Direction vertical(boolean topDown) {
        return topDown ? UP_DOWN : DOWN_UP;
    }

    /**
     * Get {@link #LEFT_RIGHT} or {@link #RIGHT_LEFT}, depending on whether
     * left-right is true.
     * @param leftRight {@link Boolean} value. If this is true, return
     *                  {@link #LEFT_RIGHT}, and {@link #RIGHT_LEFT} otherwise.
     * @return {@link Direction} instance.
     * @see #LEFT_RIGHT
     * @see #RIGHT_LEFT
     */
    @NotNull
    public static Direction horizontal(boolean leftRight) {
        return leftRight ? LEFT_RIGHT : RIGHT_LEFT;
    }

    /**
     * Get a random vertical {@link Direction}.
     * @return {@link Direction} instance.
     * @see HPIterables#randomElement(Object[])
     * @see #DOWN_UP
     * @see #UP_DOWN
     */
    @NotNull
    public static Direction randomVertical() {
        return HPIterables.randomElement(UP_DOWN, DOWN_UP);
    }

    /**
     * Get a random horizontal {@link Direction}.
     * @return {@link Direction} instance.
     * @see HPIterables#randomElement(Object[])
     * @see #LEFT_RIGHT
     * @see #RIGHT_LEFT
     */
    @NotNull
    public static Direction randomHorizontal() {
        return HPIterables.randomElement(LEFT_RIGHT, RIGHT_LEFT);
    }

    /**
     * Check whether the current {@link Direction} is vertical.
     * @return {@link Boolean} value.
     * @see #DOWN_UP
     * @see #UP_DOWN
     */
    public boolean isVertical() {
        switch (this) {
            case UP_DOWN:
            case DOWN_UP:
                return true;

            default:
                return false;
        }
    }

    /**
     * Check whether the current {@link Direction} is horizontal.
     * @return {@link Boolean} value.
     * @see #LEFT_RIGHT
     * @see #RIGHT_LEFT
     */
    public boolean isHorizontal() {
        switch (this) {
            case LEFT_RIGHT:
            case RIGHT_LEFT:
                return true;

            default:
                return false;
        }
    }

    /**
     * Get the opposite {@link Direction}.
     * @return {@link Direction} instance.
     * @see #DOWN_UP
     * @see #LEFT_RIGHT
     * @see #RIGHT_LEFT
     * @see #UP_DOWN
     * @see #NOT_AVAILABLE
     */
    @NotNull
    public Direction opposite() {
        switch (this) {
            case UP_DOWN:
                return DOWN_UP;

            case DOWN_UP:
                return UP_DOWN;

            case LEFT_RIGHT:
                return RIGHT_LEFT;

            case RIGHT_LEFT:
                return LEFT_RIGHT;

            default:
                throw new RuntimeException(NOT_AVAILABLE);
        }
    }
}
