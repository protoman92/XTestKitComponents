package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 16/6/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.string.HPStrings;
import org.swiften.xtestkitcomponents.property.base.IgnoreCaseType;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * This class provides predefined {@link Formatible} instances.
 */
public final class Formatibles {
    /**
     * Get a new {@link QuotationFree} instance.
     * @return {@link Formatible} instance.
     */
    @NotNull
    public static Formatible<String> quotationFree() {
        return new QuotationFree() {};
    }

    /**
     * Get a new {@link ContainsString} instance.
     * @return {@link ContainsString} instance.
     */
    @NotNull
    public static Formatible<String> containsString() {
        return new ContainsString() {};
    }

    /**
     * This interface provides methods to cle{@link String} of double
     * and single quote marks. Note that this is applicable both to direct
     * comparison queries and @contain(@translate) - however, we must not
     * use concat() when there are no quotation marks.
     */
    private interface QuotationFree extends Formatible<String> {
        /**
         * Strip the {@link String} to be formatted of single and double
         * quotes by separating and concatenating.
         * @param value {@link String} value.
         * @return {@link String} value.
         * @see Formatible#stringFormat(Object)
         * @see QuoteMark#from(String)
         * @see QuoteMark#wrapInQuotation(String)
         * @see HPStrings#requireNotNullOrEmpty(String)
         */
        @NotNull
        @Override
        default String formatValue(@NotNull String value) {
            HPStrings.requireNotNullOrEmpty(value);
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
    private interface ContainsString extends IgnoreCaseType, QuotationFree {
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

    interface AtIndex extends Formatible<Integer> {}

    interface OfInstance extends Formatible<Integer> {}

    interface Clickable extends Formatible<Boolean> {}

    interface ContainsID extends ContainsString {}

    interface ContainsText extends ContainsString {}

    interface Editable extends Formatible<Boolean> {}

    interface Enabled extends Formatible<Boolean> {}

    interface Focused extends Formatible<Boolean> {}

    interface HasText extends QuotationFree {}

    interface OfClass extends ContainsString {}
}
