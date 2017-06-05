package org.swiften.xtestkitcomponents.xpath;

import org.swiften.javautilities.log.LogUtil;
import org.swiften.xtestkitcomponents.platform.PlatformType;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by haipham on 3/19/17.
 */
public final class XPathTest {
    public void test_buildXPath_shouldSucceed() {
        // Setup
        PlatformType platform = mock(PlatformType.class);
        doReturn("id").when(platform).idAttribute();
        doReturn("text").when(platform).textAttribute();
        doReturn("hint").when(platform).hintAttribute();

        XPath xPath1 = XPath.builder(platform)
            .atIndex(1)
            .ofInstance(1)
//            .ofClass("class1")
//            .containsID("id1")
//            .hasText("\"2'11\"")
//            .containsText("Register")
//            .containsText("Te'xt2")
//            .hasHint("Hint1")
//            .containsHint("Hint2")
//            .isEnabled(true)
//            .isClickable(true)
            .setIndex(0)
            .addAnyClass()
            .build();

        XPath xPath2 = XPath.builder(platform)
            .atIndex(0)
            .ofInstance(2)
            .addChildXPath(xPath1)
            .addAnyClass()
            .build();

        XPath xPath3 = XPath.builder(platform)
            .withXPath(xPath2)
            .withXPath(xPath1)
            .isClickable(true)
            .addAnyClass()
            .build();

        // When & Then
        LogUtil.println(xPath1.attribute());
        LogUtil.println(xPath2.attribute());
        LogUtil.println(xPath3.attribute());
    }
}
