package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 5/6/17.
 */

import io.reactivex.annotations.NonNull;
import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.platform.PlatformType;
import org.swiften.xtestkitcomponents.property.base.StringType;
import org.swiften.xtestkitcomponents.property.sub.*;

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
     * Get an antithesis of an {@link Attribute} instance.
     * @param ATTRIBUTE {@link Attribute} instance.
     * @return {@link Attribute} instance.
     * @see Attribute#className()
     * @see Attribute#index()
     * @see Attribute.Builder#addAttribute(String)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     */
    @NotNull
    public Attribute not(@NotNull final Attribute ATTRIBUTE) {
        return Attribute.builder()
            .addAttribute("")
            .withClass(ATTRIBUTE.className())
            .withIndex(ATTRIBUTE.index())
            .withFormatible((Antithesis) () -> ATTRIBUTE)
            .build();
    }

    /**
     * Get a @index {@link Attribute}.
     * @param atIndex {@link AtIndex} instance.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttributes(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     * @see Attribute.Builder#withMode(Attribute.Mode)
     * @see org.swiften.xtestkitcomponents.xpath.Attribute.Mode#OR
     * @see PlatformType#indexAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute atIndex(@NotNull AtIndex atIndex) {
        return Attribute.builder()
            .addAttributes(platform().indexAttribute())
            .withFormatible(atIndex)
            .withMode(Attribute.Mode.OR)
            .build();
    }

    /**
     * Same as above, but uses a default {@link AtIndex} instance.
     * @param INDEX {@link Integer} value.
     * @return {@link Attribute} instance.
     * @see #atIndex(int)
     */
    @NotNull
    public Attribute atIndex(final int INDEX) {
        return atIndex(() -> INDEX);
    }

    /**
     * Get a @instance {@link Attribute}.
     * @param ofInstance {@link OfInstance} instance.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttributes(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     * @see Attribute.Builder#withMode(Attribute.Mode)
     * @see org.swiften.xtestkitcomponents.xpath.Attribute.Mode#OR
     * @see PlatformType#instanceAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute ofInstance(@NotNull OfInstance ofInstance) {
        return Attribute.builder()
            .addAttributes(platform().instanceAttribute())
            .withFormatible(ofInstance)
            .withMode(Attribute.Mode.OR)
            .build();
    }

    /**
     * Same as above, but uses a default {@link OfInstance} instance.
     * @param INSTANCE {@link Integer} value.
     * @return {@link Attribute} instance.
     * @see #ofInstance(int)
     */
    @NotNull
    public Attribute ofInstance(final int INSTANCE) {
        return ofInstance(() -> INSTANCE);
    }

    /**
     * Get a contains(@class) attribute.
     * @param ofClass {@link OfClass} instance.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttributes(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     * @see Attribute.Builder#withMode(Attribute.Mode)
     * @see org.swiften.xtestkitcomponents.xpath.Attribute.Mode#OR
     * @see PlatformType#classAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute ofClass(@NotNull OfClass ofClass) {
        return Attribute.builder()
            .addAttributes(platform().classAttribute())
            .withFormatible(ofClass)
            .withMode(Attribute.Mode.OR)
            .build();
    }

    /**
     * Same as above, but uses a default {@link OfClass}.
     * @param STRING_TYPE {@link StringType} instance.
     * @return {@link Attribute} instance.
     * @see StringType#value()
     * @see StringType#ignoreCase()
     * @see #ofClass(StringType)
     */
    @NotNull
    public Attribute ofClass(@NotNull final StringType STRING_TYPE) {
        return ofClass(new OfClass() {
            @NotNull
            @Override
            public String value() {
                return STRING_TYPE.value();
            }

            @Override
            public boolean ignoreCase() {
                return STRING_TYPE.ignoreCase();
            }
        });
    }

    /**
     * Same as above, but uses a default {@link OfClass}.
     * @param CLS {@link String} value.
     * @return {@link Attribute} instance.
     * @see #ofClass(OfClass)
     */
    @NotNull
    public Attribute ofClass(@NotNull final String CLS) {
        return ofClass(() -> CLS);
    }

    /**
     * Get a contains(@id) attribute.
     * @param containsID {@link ContainsID} instance.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttributes(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     * @see Attribute.Builder#withMode(Attribute.Mode)
     * @see org.swiften.xtestkitcomponents.xpath.Attribute.Mode#OR
     * @see PlatformType#idAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute containsID(@NotNull ContainsID containsID) {
        return Attribute.builder()
            .addAttributes(platform().idAttribute())
            .withMode(Attribute.Mode.OR)
            .withFormatible(containsID)
            .build();
    }

    /**
     * Same as above, but uses a default {@link ContainsID} instance.
     * @param STRING_TYPE {@link StringType} instance.
     * @return {@link Attribute} instance.
     * @see StringType#value()
     * @see StringType#ignoreCase()
     * @see #containsID(ContainsID)
     */
    @NotNull
    public Attribute containsID(@NotNull final StringType STRING_TYPE) {
        return containsID(new ContainsID() {
            @NotNull
            @Override
            public String value() {
                return STRING_TYPE.value();
            }

            @Override
            public boolean ignoreCase() {
                return STRING_TYPE.ignoreCase();
            }
        });
    }

    /**
     * Same as above, but uses a default {@link ContainsID} instance.
     * @param ID {@link String} value.
     * @return {@link Attribute} instance.
     * @see #containsID(ContainsID)
     */
    @NotNull
    public Attribute containsID(@NotNull final String ID) {
        return containsID(() -> ID);
    }

    /**
     * Get a @text {@link Attribute}.
     * @param hasText {@link HasText} instance.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttributes(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     * @see Attribute.Builder#withMode(Attribute.Mode)
     * @see PlatformType#textAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute hasText(@NotNull HasText hasText) {
        return Attribute.builder()
            .addAttributes(platform().textAttribute())
            .withFormatible(hasText)
            .withMode(Attribute.Mode.OR)
            .build();
    }

    /**
     * Same as above, but uses an anonymously-created {@link HasText},
     * based on properties from {@link StringType} instance.
     * @param STRING_TYPE {@link StringType} instance.
     * @return {@link Attribute} instance.
     * @see StringType#value()
     * @see StringType#ignoreCase()
     * @see #hasText(HasText)
     */
    @NotNull
    public Attribute hasText(@NotNull final StringType STRING_TYPE) {
        return hasText(new HasText() {
            @NotNull
            @Override
            public String value() {
                return STRING_TYPE.value();
            }

            @Override
            public boolean ignoreCase() {
                return STRING_TYPE.ignoreCase();
            }
        });
    }

    /**
     * Same as above, but uses {@link HasText} instance.
     * @param TEXT The text to be appended.
     * @return {@link Attribute} instance.
     * @see #hasText(HasText)
     */
    @NotNull
    public Attribute hasText(@NotNull final String TEXT) {
        return hasText(() -> TEXT);
    }

    /**
     * Get a contains(@text) {@link Attribute}.
     * @param containsText {@link ContainsText} instance.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttributes(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     * @see Attribute.Builder#withMode(Attribute.Mode)
     * @see PlatformType#textAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute containsText(@NotNull ContainsText containsText) {
        return Attribute.builder()
            .addAttributes(platform().textAttribute())
            .withFormatible(containsText)
            .withMode(Attribute.Mode.OR)
            .build();
    }

    /**
     * Same as above, but uses an anonymously-created {@link HasText},
     * based on properties from {@link StringType} instance.
     * @param STRING_TYPE {@link StringType} instance.
     * @return {@link Attribute} instance.
     * @see StringType#value()
     * @see StringType#ignoreCase()
     * @see #containsText(ContainsText)
     */
    @NotNull
    public Attribute containsText(@NotNull final StringType STRING_TYPE) {
        return containsText(new ContainsText() {
            @NotNull
            @Override
            public String value() {
                return STRING_TYPE.value();
            }

            @Override
            public boolean ignoreCase() {
                return STRING_TYPE.ignoreCase();
            }
        });
    }

    /**
     * Same as above, but uses a default {@link ContainsText} instance.
     * @param TEXT The text to be appended.
     * @return {@link Attribute} instance.
     * @see #containsText(ContainsText)
     */
    @NotNull
    public Attribute containsText(@NotNull final String TEXT) {
        return containsText(() -> TEXT);
    }

    /**
     * Get an @enabled {@link Attribute}.
     * @param enabled {@link Enabled} instance.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttributes(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     * @see Attribute.Builder#withMode(Attribute.Mode)
     * @see PlatformType#enabledAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute isEnabled(@NotNull Enabled enabled) {
        return Attribute.builder()
            .addAttributes(platform().enabledAttribute())
            .withFormatible(enabled)
            .withMode(Attribute.Mode.OR)
            .build();
    }

    /**
     * Same as above, but uses a default {@link Enabled} instance.
     * @param ENABLED {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see #isEnabled(Enabled)
     */
    @NotNull
    public Attribute isEnabled(final boolean ENABLED) {
        return isEnabled(() -> ENABLED);
    }

    /**
     * Get a @clickable {@link Attribute}.
     * @param clickable {@link Clickable} instance.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttributes(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     * @see Attribute.Builder#withMode(Attribute.Mode)
     * @see PlatformType#clickableAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute isClickable(@NonNull Clickable clickable) {
        return Attribute.builder()
            .addAttributes(platform().clickableAttribute())
            .withFormatible(clickable)
            .withMode(Attribute.Mode.OR)
            .build();
    }

    /**
     * Same as above, but uses a default {@link Clickable} instance.
     * @param CLICKABLE {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see #isClickable(Clickable)
     */
    @NotNull
    public Attribute isClickable(final boolean CLICKABLE) {
        return isClickable(() -> CLICKABLE);
    }

    /**
     * Get a @editable {@link Attribute}.
     * @param editable {@link Editable} instance.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttributes(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     * @see Attribute.Builder#withMode(Attribute.Mode)
     * @see PlatformType#editableAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute isEditable(@NonNull Editable editable) {
        return Attribute.builder()
            .addAttributes(platform().editableAttribute())
            .withFormatible(editable)
            .withMode(Attribute.Mode.OR)
            .build();
    }

    /**
     * Same as above, but uses a default {@link Editable}.
     * @param EDITABLE {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see #isEditable(Editable)
     */
    @NotNull
    public Attribute isEditable(final boolean EDITABLE) {
        return isEditable(() -> EDITABLE);
    }

    /**
     * Get a @focused {@link Attribute}.
     * @param focused {@link Focused} instance.
     * @return {@link Attribute} instance.
     * @see Attribute.Builder#addAttributes(Collection)
     * @see Attribute.Builder#withFormatible(Attribute.Formatible)
     * @see Attribute.Builder#withMode(Attribute.Mode)
     * @see PlatformType#focusedAttribute()
     * @see #platform()
     */
    @NotNull
    public Attribute isFocused(@NotNull Focused focused) {
        return Attribute.builder()
            .addAttributes(platform().editableAttribute())
            .withFormatible(focused)
            .withMode(Attribute.Mode.OR)
            .build();
    }

    /**
     * Same as above, but uses a default {@link Focused} instance.
     * @param FOCUSED {@link Boolean} value.
     * @return {@link Attribute} instance.
     * @see #isFocused(Focused)
     */
    @NotNull
    public Attribute isFocused(final boolean FOCUSED) {
        return isFocused(() -> FOCUSED);
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
         * @see Attribute.Formatible#stringFormat()
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
    @FunctionalInterface
    public interface ContainsString extends StringType, QuotationFree {
        /**
         * Override this method to provide custom format that can add ignore
         * case capability.
         * @return {@link String} value.
         * @see QuotationFree#stringFormat()
         * @see #formatValue(Object)
         * @see #ignoreCase()
         * @see #value()
         */
        @NotNull
        @Override
        default String stringFormat() {
            String value = value();

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

    /**
     * This is a special {@link Attribute.Formatible} in the sense that it
     * reuses most of {@link Attribute#fullAttribute()}, but wrap it with
     * "not()" to query the opposite.
     */
    public interface Antithesis extends Attribute.Formatible<Attribute> {
        /**
         * Override this method to provide default implementation.
         * @param attribute {@link Attribute} instance.
         * @return {@link String} value.
         * @see Attribute#baseAttribute()
         * @see Attribute.Formatible#formatValue(Object)
         */
        @NotNull
        @Override
        default String formatValue(@NotNull Attribute attribute) {
            return attribute.baseAttribute();
        }

        /**
         * Override this method to provide default implementation.
         * @return {@link String} value.
         * @see #formatValue(Object)
         * @see #value()
         */
        @NotNull
        @Override
        default String stringFormat() {
            return String.format("not(%s)", formatValue(value()));
        }
    }

    @FunctionalInterface
    @SuppressWarnings("WeakerAccess")
    public interface AtIndex extends Attribute.Formatible<Integer> {}

    @FunctionalInterface
    @SuppressWarnings("WeakerAccess")
    public interface OfInstance extends Attribute.Formatible<Integer> {}

    @FunctionalInterface
    @SuppressWarnings("WeakerAccess")
    public interface Clickable extends ClickableType, Attribute.Formatible<Boolean> {}

    @FunctionalInterface
    @SuppressWarnings("WeakerAccess")
    public interface ContainsID extends ContainsIDType, ContainsString {}

    @FunctionalInterface
    @SuppressWarnings("WeakerAccess")
    public interface ContainsText extends ContainsTextType, ContainsString {}

    @FunctionalInterface
    @SuppressWarnings("WeakerAccess")
    public interface Editable extends EditableType, Attribute.Formatible<Boolean> {}

    @FunctionalInterface
    @SuppressWarnings("WeakerAccess")
    public interface Enabled extends EnabledType, Attribute.Formatible<Boolean> {}

    @FunctionalInterface
    @SuppressWarnings("WeakerAccess")
    public interface Focused extends FocusedType, Attribute.Formatible<Boolean> {}

    @FunctionalInterface
    @SuppressWarnings("WeakerAccess")
    public interface HasText extends HasTextType, QuotationFree {}

    @FunctionalInterface
    @SuppressWarnings("WeakerAccess")
    public interface OfClass extends OfClassType, ContainsString {}
    //endregion
}
