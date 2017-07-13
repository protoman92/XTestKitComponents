package org.swiften.xtestkitcomponents.platform;

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.collection.HPIterables;

import java.util.Collection;
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
     * @return {@link Collection} of {@link String}.
     * @see HPIterables#asList(Object[])
     */
    @NotNull
    default Collection<String> indexAttribute() {
        return HPIterables.asList("index");
    }

    /**
     * Specify the name of an instance attribute. Generally should be
     * 'instance'.
     * @return {@link Collection} of {@link String}.
     * @see HPIterables#asList(Object[])
     */
    @NotNull
    default Collection<String> instanceAttribute() {
        return HPIterables.asList("instance");
    }

    /**
     * Specify the name of a class attribute. Generally should be 'class'.
     * @return {@link Collection} of {@link String}.
     * @see HPIterables#asList(Object[])
     */
    @NotNull
    default Collection<String> classAttribute() {
        return HPIterables.asList("class");
    }

    /**
     * Specify the name for a id attribute. Generally should be 'id'.
     * @return {@link Collection} of {@link String}.
     * @see HPIterables#asList(Object[])
     */
    @NotNull
    default Collection<String> idAttribute() {
        return HPIterables.asList("id");
    }

    /**
     * Specify the name for a text attribute. Generally should be 'text'.
     * @return {@link Collection} of {@link String}.
     * @see HPIterables#asList(Object[])
     */
    @NotNull
    default Collection<String> textAttribute() {
        return HPIterables.asList("text");
    }

    /**
     * Specify the name for an enabled attribute. Generally should be
     * 'enabled'.
     * @return {@link List} of {@link String}.
     * @see HPIterables#asList(Object[])
     */
    @NotNull
    default Collection<String> enabledAttribute() {
        return HPIterables.asList("enabled");
    }

    /**
     * Specify the name for a clickable attribute. Generally should be
     * 'clickable'.
     * @return {@link List} of {@link String}.
     * @see HPIterables#asList(Object[])
     */
    @NotNull
    default Collection<String> clickableAttribute() {
        return HPIterables.asList("clickable");
    }

    /**
     * Specify the name for a editable attribute. Generally should be
     * 'editable'.
     * @return {@link List} of {@link String}.
     * @see HPIterables#asList(Object[])
     */
    @NotNull
    default Collection<String> editableAttribute() {
        return HPIterables.asList("editable");
    }

    /**
     * Specify the name for a focused attribute. Generally should be 'focused'.
     * @return {@link List} of {@link String}.
     * @see HPIterables#asList(Object[])
     */
    @NotNull
    default Collection<String> focusedAttribute() {
        return HPIterables.asList("focused");
    }
}
