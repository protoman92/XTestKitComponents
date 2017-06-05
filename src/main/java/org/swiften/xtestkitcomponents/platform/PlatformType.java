package org.swiften.xtestkitcomponents.platform;

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.collection.CollectionUtil;

import java.util.List;

/**
 * Created by haipham on 3/21/17.
 */

/**
 * This interface provides platform-specific properties.
 */
public interface PlatformType {
    @NotNull String value();

    /**
     * Specify the name of an index attribute. Generally should be 'index'.
     * @return {@link List} of {@link String}.
     * @see CollectionUtil#asList(Object[])
     */
    @NotNull
    default List<String> indexAttribute() {
        return CollectionUtil.asList("index");
    }

    /**
     * Specify the name of an instance attribute. Generally should be
     * 'instance'.
     * @return {@link List} of {@link String}.
     * @see CollectionUtil#asList(Object[])
     */
    @NotNull
    default List<String> instanceAttribute() {
        return CollectionUtil.asList("instance");
    }

    /**
     * Specify the name of a class attribute. Generally should be 'class'.
     * @return {@link List} of {@link String}.
     * @see CollectionUtil#asList(Object[])
     */
    @NotNull
    default List<String> classAttribute() {
        return CollectionUtil.asList("class");
    }

    /**
     * Specify the name for a id attribute. Generally should be 'id'.
     * @return {@link List} of {@link String}.
     * @see CollectionUtil#asList(Object[])
     */
    @NotNull default List<String> idAttribute() {
        return CollectionUtil.asList("id");
    }

    /**
     * Specify the name for a text attribute. Generally should be 'text'.
     * @return {@link List} of {@link String}.
     * @see CollectionUtil#asList(Object[])
     */
    @NotNull default List<String> textAttribute() {
        return CollectionUtil.asList("text");
    }

    /**
     * Specify the name for an enabled attribute. Generally should be
     * 'enabled'.
     * @return {@link List} of {@link String}.
     * @see CollectionUtil#asList(Object[])
     */
    @NotNull
    default List<String> enabledAttribute() {
        return CollectionUtil.asList("enabled");
    }

    /**
     * Specify the name for a clickable attribute. Generally should be
     * 'clickable'.
     * @return {@link List} of {@link String}.
     * @see CollectionUtil#asList(Object[])
     */
    @NotNull
    default List<String> clickableAttribute() {
        return CollectionUtil.asList("clickable");
    }

    /**
     * Specify the name for a editable attribute. Generally should be
     * 'editable'.
     * @return {@link List} of {@link String}.
     * @see CollectionUtil#asList(Object[])
     */
    @NotNull
    default List<String> editableAttribute() {
        return CollectionUtil.asList("editable");
    }

    /**
     * Specify the name for a focused attribute. Generally should be 'focused'.
     * @return {@link List} of {@link String}.
     * @see CollectionUtil#asList(Object[])
     */
    @NotNull
    default List<String> focusedAttribute() {
        return CollectionUtil.asList("focused");
    }
}
