package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 5/6/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.platform.PlatformProviderType;
import org.swiften.xtestkitcomponents.platform.PlatformType;
import org.swiften.xtestkitcomponents.property.base.IgnoreCaseType;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
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
            .withFormatible(new AtIndex() {})
            .withJoiner(Joiner.OR)
            .withValue(index)
            .build();
    }

    /**
     * Get a @instance {@link Attribute}.
     * @param instance {@link Integer} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
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
            .withFormatible(new OfInstance() {})
            .withJoiner(Joiner.OR)
            .withValue(instance)
            .build();
    }

    /**
     * Get a contains(@class) attribute.
     * @param className {@link String} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
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
            .withFormatible(new OfClass() {})
            .withJoiner(Joiner.OR)
            .withValue(className)
            .build();
    }

    /**
     * Get a contains(@id) attribute.
     * @param id {@link String} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
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
            .withFormatible(new ContainsID() {})
            .withValue(id)
            .build();
    }

    /**
     * Get a @text {@link Attribute}.
     * @param text The text to be appended.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
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
            .withFormatible(new HasText() {})
            .withJoiner(Joiner.OR)
            .withValue(text)
            .build();
    }

    /**
     * Get a contains(@text) {@link Attribute}.
     * @param text The text to be appended.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
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
            .withFormatible(new ContainsText() {})
            .withJoiner(Joiner.OR)
            .withValue(text)
            .build();
    }

    /**
     * Get an @enabled {@link Attribute}.
     * @param enabled {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
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
            .withFormatible(new Enabled() {})
            .withJoiner(Joiner.OR)
            .withValue(enabled)
            .build();
    }

    /**
     * Get a @clickable {@link Attribute}.
     * @param clickable {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
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
            .withFormatible(new Clickable() {})
            .withJoiner(Joiner.OR)
            .withValue(clickable)
            .build();
    }

    /**
     * Get a @editable {@link Attribute}.
     * @param editable {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
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
            .withFormatible(new Editable() {})
            .withJoiner(Joiner.OR)
            .withValue(editable)
            .build();
    }

    /**
     * Get a @focused {@link Attribute}.
     * @param FOCUSED {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttribute(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
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
            .withFormatible(new Focused() {})
            .withJoiner(Joiner.OR)
            .withValue(FOCUSED)
            .build();
    }

    //region Locator Types
    /**
     * This interface provides methods to cle{@link String} of double
     * and single quote marks. Note that this is applicable both to direct
     * comparison queries and @contain(@translate) - however, we must not
     * use concat() when there are no quotation marks.
     */
    public interface QuotationFree extends Attribute.Formatible<String> {
        /**
         * Strip the {@link String} to be formatted of single and double
         * quotes by separating and concatenating.
         * @param value {@link String} value.
         * @return {@link String} value.
         * @see Attribute.Formatible#stringFormat(Object)
         * @see QuoteMark#from(String)
         * @see QuoteMark#wrapInQuotation(String)
         */
        @NotNull
        @Override
        default String formatValue(@NotNull String value) {
            String fQuote = "", lQuote = "";
            List<String> fParts = new LinkedList<>();

            /* We need to take case of cases whereby the quotation marks
             * are the first or the last character, or both. */
            String fChar = String.valueOf(value.charAt(0));
            String lChar = String.valueOf(value.charAt(value.length() - 1));
            Optional<QuoteMark> fqm = QuoteMark.from(fChar);
            Optional<QuoteMark> lqm = QuoteMark.from(lChar);
            String fFormat = "%s";

            if (fqm.isPresent()) {
                fQuote = fqm.get().wrappedInQuotation();
                value = value.substring(1, value.length());
                fFormat = String.format("%1$s,%2$s", fQuote, fFormat);
            }

            if (lqm.isPresent()) {
                lQuote = lqm.get().wrappedInQuotation();
                value = value.substring(0, value.length() - 1);
                fFormat = String.format("%1$s,%2$s", fFormat, lQuote);
            }

            /* Sequentially split the String using ', and then split each
             * sub-string using " */
            String[] fSplit = value.split("'");

            for (String fs : fSplit) {
                List<String> lParts = new LinkedList<>();
                String[] lSplit = fs.split("\"");

                for (String ss : lSplit) {
                    lParts.add(String.format("'%s'", ss));
                }

                String lJoined = String.join(",'\"',", lParts);
                fParts.add(lJoined);
            }

            String joined = String.join(",\"'\",", fParts);
            String formatted = String.format(fFormat, joined);

            /* Only use concat if there is more than one concatenated
             * sub-string, or there is either a quotation mark at the
             * start/end of the String */
            if (fParts.size() > 1 || !(fQuote + lQuote).isEmpty()) {
                return String.format("concat(%s)", formatted);
            } else {
                return formatted;
            }
        }
    }

    /**
     * This is a special case - we can use a translate operation to perform
     * case-insensitive contains-text locator operations.
     * We need to convert each character in the text we are search for into
     * lowercase. This way, it does not matter where the text is capitalized;
     * it will be standardized and subsequently can be searched.
     */
    public interface ContainsString extends IgnoreCaseType, QuotationFree {
        /**
         * Override this method to provide custom format that can add ignore
         * case capability.
         * @return {@link String} value.
         * @see QuotationFree#stringFormat(Object)
         * @see #formatValue(Object)
         * @see #ignoreCase()
         */
        @NotNull
        @Override
        default String stringFormat(@NotNull String value) {
            if (ignoreCase()) {
                return String.format(
                    "contains(translate(@%1$s, %2$s, %3$s), %3$s)",
                    "%1$s",
                    formatValue(value.toUpperCase()),
                    formatValue(value.toLowerCase())
                );
            } else {
                return String.format(
                    "contains(@%1$s, %2$s)", "%1$s",
                    formatValue(value)
                );
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public interface AtIndex extends Attribute.Formatible<Integer> {}

    @SuppressWarnings("WeakerAccess")
    public interface OfInstance extends Attribute.Formatible<Integer> {}

    @SuppressWarnings("WeakerAccess")
    public interface Clickable extends Attribute.Formatible<Boolean> {}

    @SuppressWarnings("WeakerAccess")
    public interface ContainsID extends ContainsString {}

    @SuppressWarnings("WeakerAccess")
    public interface ContainsText extends ContainsString {}

    @SuppressWarnings("WeakerAccess")
    public interface Editable extends Attribute.Formatible<Boolean> {}

    @SuppressWarnings("WeakerAccess")
    public interface Enabled extends Attribute.Formatible<Boolean> {}

    @SuppressWarnings("WeakerAccess")
    public interface Focused extends Attribute.Formatible<Boolean> {}

    @SuppressWarnings("WeakerAccess")
    public interface HasText extends QuotationFree {}

    @SuppressWarnings("WeakerAccess")
    public interface OfClass extends ContainsString {}
    //endregion
}
