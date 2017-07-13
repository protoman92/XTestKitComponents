package org.swiften.xtestkitcomponents.xpath;

import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.collection.HPIterables;
import org.swiften.javautilities.util.HPLog;
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
        HPLog.println(xpath1.attribute());
        HPLog.println(xpath2.attribute());
        HPLog.println(xpath3.attribute());
        HPLog.println(xpath4.attribute());
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
                return HPIterables.asList("text", "value", "label");
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
        HPLog.println(CompoundAttribute.empty());
        HPLog.println(CompoundAttribute.forClass("TC"));
        HPLog.println(attribute);
        HPLog.println(attribute.not());
        HPLog.println(attrs.containsID("test-id"));
        HPLog.println(attrs.atIndex(1));
        HPLog.println(attrs.hasText("test-text"));
        HPLog.println(attrs.containsText("test-text"));
        HPLog.println(attrs.isEnabled(true));
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
        HPLog.println(a1);
        HPLog.println(a2);
        HPLog.println(a3);
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
        HPLog.println(xp1);
    }
}
