package org.swiften.xtestkitcomponents.xpath;

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.collection.CollectionUtil;
import org.swiften.javautilities.log.LogUtil;
import org.swiften.xtestkitcomponents.platform.PlatformType;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by haipham on 3/19/17.
 */
public final class XPathTest {
    @Test
    public void test_buildXPath_shouldSucceed() {
        // Setup
        PlatformType platform = () -> "value";
        Attributes attrs = Attributes.of(platform);

        XPath xPath1 = XPath.builder()
            .addAttribute(attrs.atIndex(1).withClass("TC"))
            .addAttribute(attrs.ofInstance(1))
            .build();

        XPath xPath2 = XPath.builder()
            .addAttribute(attrs.containsID("test-id").withIndex(1))
            .addAttribute(attrs.not(attrs.containsText("text").withClass("TC")))
            .addChildXPath(xPath1)
            .build();

        XPath xPath3 = XPath.builder()
            .withXPath(xPath2)
            .withXPath(xPath1)
            .addAttribute(attrs.isClickable(true))
            .build();

        // When & Then
        LogUtil.println(xPath1.attribute());
        LogUtil.println(xPath2.attribute());
        LogUtil.println(xPath3.attribute());
    }

    @Test
    public void test_attributeCreation_shouldWork() {
        // Setup && When & Then
        PlatformType platform = new PlatformType() {
            @NotNull
            @Override
            public String value() {
                return "value";
            }

            @NotNull
            @Override
            public List<String> textAttribute() {
                return CollectionUtil.asList("text", "value", "label");
            }
        };

        Attributes attrs = Attributes.of(platform);

        Attribute attribute = Attribute.builder()
            .addAttribute("id")
            .withFormatible((Attributes.ContainsString) () -> "test-id")
            .withMode(Attribute.Mode.OR)
            .build();

        LogUtil.println(attribute);
        LogUtil.println(attrs.not(attribute));
        LogUtil.println(attrs.containsID("test-id"));
        LogUtil.println(attrs.atIndex(1));
        LogUtil.println(attrs.hasText("test-text"));
        LogUtil.println(attrs.containsText("test-text").withClass("TC").withIndex(1));
        LogUtil.println(attrs.isEnabled(true).withClass("TC").withIndex(2));
    }
}
