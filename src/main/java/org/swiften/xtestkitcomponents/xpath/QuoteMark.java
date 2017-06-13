package org.swiften.xtestkitcomponents.xpath;

/**
 * Created by haipham on 5/11/17.
 */

import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.property.base.ValueType;

import java.util.Optional;

/**
 * Use this with
 * {@link org.swiften.xtestkitcomponents.xpath.Attributes.QuotationFree} to
 * strip quotation marks.
 */
public enum QuoteMark implements ValueType<String> {
    SINGLE,
    DOUBLE;

    /**
     * Return {@link QuoteMark} from a quotation mark.
     * @param quote {@link String} value.
     * @return {@link QuoteMark} instance wrapped in {@link Optional}.
     */
    @NotNull
    public static Optional<QuoteMark> from(@NotNull String quote) {
        for (QuoteMark qm : values()) {
            if (qm.value().equals(quote)) {
                return Optional.of(qm);
            }
        }

        return Optional.empty();
    }

    /**
     * Check if {@link String} is a quotation mark.
     * @param value {@link String} value.
     * @return {@link Boolean} value.
     * @see #from(String)
     */
    public static boolean isQuoteMarks(@NotNull String value) {
        return from(value).isPresent();
    }

    /**
     * Wrap {@link String} within appropriate quotation marks.
     * @param value {@link String} value.
     * @return {@link String} value.
     * @see #from(String)
     * @see #wrappedInQuotation()
     */
    @NotNull
    @SuppressWarnings("OptionalIsPresent")
    public static String wrapInQuotation(@NotNull String value) {
        Optional<QuoteMark> qm = from(value);

        if (qm.isPresent()) {
            return qm.get().wrappedInQuotation();
        } else {
            return String.format("'%s'", value);
        }
    }

    //region ValueType
    /**
     * Get the quotation mark to be used.
     * @return {@link String} value.
     * @see ValueType#value()
     */
    @NotNull
    public String value() {
        switch (this) {
            case SINGLE:
                return "'";

            case DOUBLE:
                return "\"";

            default:
                return "";
        }
    }
    //endregion

    /**
     * Return a quotation mark that is wrapped in quotation marks of the
     * opposite kind.
     * @return {@link String} value.
     */
    @NotNull
    public String wrappedInQuotation() {
        switch (this) {
            case SINGLE:
                return "\"'\"";

            case DOUBLE:
                return "'\"'";

            default:
                return "";
        }
    }
}
