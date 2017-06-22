package org.swiften.xtestkitcomponents.xpath;

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.collection.CollectionUtil;
import org.swiften.javautilities.util.LogUtil;
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

        XPath xpath1 = XPath.builder()
            .addAttribute(attrs.atIndex(1))
            .addAttribute(attrs.ofInstance(1))
            .build();

        XPath xpath2 = XPath.builder()
            .addAttribute(attrs.containsID("test-id"))
            .addAttribute(attrs.containsText("text").not())
            .build();

        XPath xpath3 = XPath.builder()
            .withXPath(xpath2)
            .withXPath(xpath1)
            .addAttribute(attrs.isClickable(true))
            .build();

        XPath xpath4 = XPath.builder()
            .addAttribute(CompoundAttribute.builder()
                .addAttribute(attrs.containsText("text1"))
                .addAttribute(attrs.ofClass("class1").not())
                .addAttribute(attrs.isEditable(true))
                .build()
                .withClass("TC")
                .withIndex(1))
            .addAttribute(Axes.followingSibling(CompoundAttribute.builder()
                .addAttribute(attrs.containsID("parent1"))
                .build()))
            .build();

        // When & Then
        LogUtil.println(xpath1.attribute());
        LogUtil.println(xpath2.attribute());
        LogUtil.println(xpath3.attribute());
        LogUtil.println(xpath4.attribute());
    }

    @Test
    public void test_attributeCreation_shouldWork() {
        // Setup
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
            .withFormatible(Formatibles.containsString())
            .withValue("test-id")
            .withJoiner(Joiner.OR)
            .build();

        // When & Then
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

    @Test
    public void test_attributeBlockCreation_shouldWork() {
        // Setup
        PlatformType platform = () -> "value";
        Attributes attrs = Attributes.of(platform);

        AttributeBlock a1 = AttributeBlock.builder()
            .addAttribute(attrs.atIndex(0))
            .addAttribute(attrs.containsID("test-id"))
            .build();

        AttributeBlock a2 = AttributeBlock.builder()
            .addAttribute(attrs.hasText("text1").not())
            .build();

        AttributeBlock a3 = AttributeBlock.builder()
            .addAttribute(attrs.isEditable(true).not())
            .addAttribute(a1)
            .addAttribute(a2)
            .build();

        // When & Then
        LogUtil.println(a1);
        LogUtil.println(a2);
        LogUtil.println(a3);
    }

    @Test
    public void test_notContainingDescendant_shouldWork() {
        // Setup
        PlatformType platform = () -> "value";
        Attributes attrs = Attributes.of(platform);
        Attribute a1 = attrs.containsText("text1");
        Attribute a2 = attrs.containsID("id1");
        CompoundAttribute c1 = CompoundAttribute.single(a1);
        CompoundAttribute c2 = CompoundAttribute.single(a2);

        // When
        XPath xp1 = XPath.builder()
            .addAttribute(c1)
            .addAttribute(Axes.descendant(c2.not()))
            .build();

        // Then
        LogUtil.println(xp1);
    }
}
