package org.swiften.xtestkitcomponents.platform;

import io.appium.java_client.remote.MobilePlatform;
import org.jetbrains.annotations.NotNull;
import org.swiften.xtestkitcomponents.common.BaseErrorType;
import org.swiften.xtestkitcomponents.property.base.AttributeType;
import org.swiften.xtestkitcomponents.xpath.Attribute;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by haipham on 3/20/17.
 */
public enum Platform implements BaseErrorType, PlatformType, AttributeType<String> {
    ANDROID,
    IOS;

    /**
     * Get {@link Platform} from {@link String} value.
     * @param value {@link String} value.
     * @return {@link Platform} instance.
     * @see #NOT_AVAILABLE
     */
    @NotNull
    public static Platform fromValue(@NotNull String value) {
        Optional<Platform> ops = Arrays.stream(values())
            .filter(a -> a.value().equalsIgnoreCase(value))
            .findFirst();

        if (ops.isPresent()) {
            return ops.get();
        } else {
            throw new RuntimeException(BaseErrorType.NOT_AVAILABLE);
        }
    }

    //region AttributeType
    @NotNull
    @Override
    public String value() {
        switch (this) {
            case ANDROID:
                return MobilePlatform.ANDROID;

            case IOS:
                return MobilePlatform.IOS;

            default:
                return "";
        }
    }
    //endregion

    //region PlatformType
    @NotNull
    @Override
    public Attribute classAttribute() {
        switch (this) {
            case IOS:
                return Attribute.builder()
                    .addAttribute(PlatformType.super.classAttribute())
                    .addAttribute("type")
                    .withMode(Attribute.Mode.OR)
                    .build();

            default:
                return PlatformType.super.classAttribute();
        }
    }

    @NotNull
    @Override
    public Attribute idAttribute() {
        switch (this) {
            case ANDROID:
                return Attribute.single("resource-id");

            case IOS:
                return Attribute.builder()
                    .addAttribute("name")
                    .addAttribute("accessibility")
                    .withMode(Attribute.Mode.OR)
                    .build();

            default:
                return Attribute.BLANK;
        }
    }

    @NotNull
    @Override
    public Attribute hintAttribute() {
        switch (this) {
            case ANDROID:
                return Attribute.single("hint");

            case IOS:
                return Attribute.single("placeholder");

            default:
                return Attribute.BLANK;
        }
    }

    @NotNull
    @Override
    public Attribute textAttribute() {
        switch (this) {
            case ANDROID:
                return Attribute.single("text");

            case IOS:
                return Attribute.builder()
                    .addAttribute("title")
                    .addAttribute("text")
                    .addAttribute("value")
                    .addAttribute("label")
                    .withMode(Attribute.Mode.OR)
                    .build();

            default:
                return Attribute.BLANK;
        }
    }
    //endregion
}
