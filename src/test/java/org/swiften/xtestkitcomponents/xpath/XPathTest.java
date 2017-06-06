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
@SuppressWarnings("UndeclaredTests")
public final class XPathTest {
    @Test
    public void test_buildXPath_shouldSucceed() {
        // Setup
        PlatformType platform = () -> "value";
        Attributes attrs = Attributes.of(platform);

        XPath xPath1 = XPath.builder()
            .addAttribute(attrs.atIndex(1))
            .addAttribute(attrs.ofInstance(1))
            .build();

        XPath xPath2 = XPath.builder()
            .addAttribute(attrs.containsID("test-id"))
            .addAttribute(attrs.containsText("text").not())
            .build();

        XPath xPath3 = XPath.builder()
            .withXPath(xPath2)
            .withXPath(xPath1)
            .addAttribute(attrs.isClickable(true))
            .build();

        CompoundAttribute compoundAttr = CompoundAttribute.builder()
            .addAttribute(attrs.containsText("text1"))
            .addAttribute(attrs.ofClass("class1").not())
            .addAttribute(attrs.isEditable(true))
            .build()
            .withClass("TC")
            .withIndex(1);

        XPath xPath4 = XPath.builder()
            .addAttribute(compoundAttr)
            .build();

        // When & Then
        LogUtil.println(xPath1.attribute());
        LogUtil.println(xPath2.attribute());
        LogUtil.println(xPath3.attribute());
        LogUtil.println(xPath4.attribute());
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

        Attribute attribute = Attribute.<String>builder()
            .addAttribute("id")
            .withFormatible(new Attributes.ContainsString() {})
            .withValue("test-id")
            .withJoiner(Attribute.Joiner.OR)
            .build();

        LogUtil.println(CompoundAttribute.empty());
        LogUtil.println(CompoundAttribute.forClass("TC"));
        LogUtil.println(attribute);
        LogUtil.println(attribute.not());
        LogUtil.println(attrs.containsID("test-id"));
        LogUtil.println(attrs.atIndex(1));
        LogUtil.println(attrs.hasText("test-text"));
        LogUtil.println(attrs.containsText("test-text"));
        LogUtil.println(attrs.isEnabled(true));
    }
}
