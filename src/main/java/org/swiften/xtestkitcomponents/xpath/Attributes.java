package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 5/6/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.protocol.ClassNameProviderType;
import org.swiften.xtestkitcomponents.platform.PlatformProviderType;
import org.swiften.xtestkitcomponents.platform.PlatformType;

import java.util.Collection;

/**
 * This utility class contains convenient methods to construct
 * {@link Attribute}.
 */
public final class Attributes {
    /**
     * Get a new {@link Attribute} instance.
     * @param platform {@link PlatformType} instance.
     * @return {@link Attributes} instance.
     */
    @NotNull
    public static Attributes of(@NotNull PlatformType platform) {
        return new Attributes(platform);
    }

    /**
     * Get a new {@link Attributes} instance.
     * @param type {@link PlatformProviderType} instance.
     * @return {@link Attributes} instance.
     * @see PlatformProviderType#platform()
     * @see #of(PlatformType)
     */
    @NotNull
    public static Attributes of(@NotNull PlatformProviderType type) {
        return of(type.platform());
    }

    @NotNull private final PlatformType PLATFORM;

    private Attributes(@NotNull PlatformType platform) {
        PLATFORM = platform;
    }

    /**
     * Get {@link #PLATFORM}.
     * @return {@link PlatformType} instance.
     * @see #PLATFORM
     */
    @NotNull
    public PlatformType platform() {
        return PLATFORM;
    }

    /**
     * Get a @index {@link Attribute}.
     * @param index {@link Integer} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Formatible)
     * @see Attribute.Builder#withJoiner(Joiner)
     * @see Attribute.Builder#withValue(Object)
     * @see Joiner#OR
     * @see PlatformType#indexAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute atIndex(int index) {
        return Attribute.<Integer>builder()
            .addAttribute(platform().indexAttribute())
            .withFormatible(new Formatibles.AtIndex() {})
            .withJoiner(Joiner.OR)
            .withValue(index)
            .build();
    }

    /**
     * Get a @instance {@link Attribute}.
     * @param instance {@link Integer} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Formatible)
     * @see Attribute.Builder#withJoiner(Joiner)
     * @see Attribute.Builder#withValue(Object)
     * @see Joiner#OR
     * @see PlatformType#instanceAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute ofInstance(int instance) {
        return Attribute.<Integer>builder()
            .addAttribute(platform().instanceAttribute())
            .withFormatible(new Formatibles.OfInstance() {})
            .withJoiner(Joiner.OR)
            .withValue(instance)
            .build();
    }

    /**
     * Get a contains(@class) attribute.
     * @param className {@link String} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Formatible)
     * @see Attribute.Builder#withJoiner(Joiner)
     * @see Attribute.Builder#withValue(Object)
     * @see Joiner#OR
     * @see PlatformType#classAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute ofClass(@NotNull String className) {
        return Attribute.<String>builder()
            .addAttribute(platform().classAttribute())
            .withFormatible(new Formatibles.OfClass() {})
            .withJoiner(Joiner.OR)
            .withValue(className)
            .build();
    }

    /**
     * Same as above, but uses {@link ClassNameProviderType}.
     * @param param {@link ClassNameProviderType} instance.
     * @return {@link Attribute} instance.
     * @see ClassNameProviderType#className()
     * @see #ofClass(String)
     */
    @NotNull
    public Attribute ofClass(@NotNull ClassNameProviderType param) {
        return ofClass(param.className());
    }

    /**
     * Get a contains(@id) attribute.
     * @param id {@link String} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Formatible)
     * @see Attribute.Builder#withJoiner(Joiner)
     * @see Attribute.Builder#withValue(Object)
     * @see Joiner#OR
     * @see PlatformType#idAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute containsID(@NotNull String id) {
        return Attribute.<String>builder()
            .addAttribute(platform().idAttribute())
            .withJoiner(Joiner.OR)
            .withFormatible(new Formatibles.ContainsID() {})
            .withValue(id)
            .build();
    }

    /**
     * Get a @text {@link Attribute}.
     * @param text The text to be appended.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Formatible)
     * @see Attribute.Builder#withJoiner(Joiner)
     * @see Attribute.Builder#withValue(Object)
     * @see Joiner#OR
     * @see PlatformType#textAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute hasText(@NotNull String text) {
        return Attribute.<String>builder()
            .addAttribute(platform().textAttribute())
            .withFormatible(new Formatibles.HasText() {})
            .withJoiner(Joiner.OR)
            .withValue(text)
            .build();
    }

    /**
     * Get a contains(@text) {@link Attribute}.
     * @param text The text to be appended.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Formatible)
     * @see Attribute.Builder#withJoiner(Joiner)
     * @see Attribute.Builder#withValue(Object)
     * @see Joiner#OR
     * @see PlatformType#textAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute containsText(@NotNull String text) {
        return Attribute.<String>builder()
            .addAttribute(platform().textAttribute())
            .withFormatible(new Formatibles.ContainsText() {})
            .withJoiner(Joiner.OR)
            .withValue(text)
            .build();
    }

    /**
     * Get an @enabled {@link Attribute}.
     * @param enabled {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Formatible)
     * @see Attribute.Builder#withJoiner(Joiner)
     * @see Attribute.Builder#withValue(Object)
     * @see Joiner#OR
     * @see PlatformType#enabledAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute isEnabled(boolean enabled) {
        return Attribute.<Boolean>builder()
            .addAttribute(platform().enabledAttribute())
            .withFormatible(new Formatibles.Enabled() {})
            .withJoiner(Joiner.OR)
            .withValue(enabled)
            .build();
    }

    /**
     * Get a @clickable {@link Attribute}.
     * @param clickable {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Formatible)
     * @see Attribute.Builder#withJoiner(Joiner)
     * @see Attribute.Builder#withValue(Object)
     * @see Joiner#OR
     * @see PlatformType#clickableAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute isClickable(final boolean clickable) {
        return Attribute.<Boolean>builder()
            .addAttribute(platform().clickableAttribute())
            .withFormatible(new Formatibles.Clickable() {})
            .withJoiner(Joiner.OR)
            .withValue(clickable)
            .build();
    }

    /**
     * Get a @editable {@link Attribute}.
     * @param editable {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Formatible)
     * @see Attribute.Builder#withJoiner(Joiner)
     * @see Attribute.Builder#withValue(Object)
     * @see Joiner#OR
     * @see PlatformType#editableAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute isEditable(boolean editable) {
        return Attribute.<Boolean>builder()
            .addAttribute(platform().editableAttribute())
            .withFormatible(new Formatibles.Editable() {})
            .withJoiner(Joiner.OR)
            .withValue(editable)
            .build();
    }

    /**
     * Get a @focused {@link Attribute}.
     * @param FOCUSED {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Formatible)
     * @see Attribute.Builder#withJoiner(Joiner)
     * @see Attribute.Builder#withValue(Object)
     * @see Joiner#OR
     * @see PlatformType#focusedAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute isFocused(final boolean FOCUSED) {
        return Attribute.<Boolean>builder()
            .addAttribute(platform().focusedAttribute())
            .withFormatible(new Formatibles.Focused() {})
            .withJoiner(Joiner.OR)
            .withValue(FOCUSED)
            .build();
    }
}
