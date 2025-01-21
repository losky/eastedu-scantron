//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.eastedu.graphics.core.style;

import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.StyleHandler;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自定义样式处理器
 *
 * @author luozhenzhong
 */
public class CustomStyleHandler implements StyleHandler, SVGConstants {
    /**
     * The Ignore attributes.
     */
    static Map<String, Set<String>> ignoreAttributes = new HashMap<>();

    static {
        Set<String> textAttributes = new HashSet<>();
        textAttributes.add("font-size");
        textAttributes.add("font-family");
        textAttributes.add("font-style");
        textAttributes.add("font-weight");
        ignoreAttributes.put("rect", textAttributes);
        ignoreAttributes.put("circle", textAttributes);
        ignoreAttributes.put("ellipse", textAttributes);
        ignoreAttributes.put("polygon", textAttributes);
        ignoreAttributes.put("line", textAttributes);
        ignoreAttributes.put("path", textAttributes);
    }

    @Override
    public void setStyle(Element element, Map styleMap, SVGGeneratorContext generatorContext) {
        String tagName = element.getTagName();

        for (Map.Entry<String, Object> entry : ((Map<String, Object>) styleMap).entrySet()) {
            String styleName = entry.getKey();
            if (element.getAttributeNS(null, styleName).isEmpty() && this.appliesTo(styleName, tagName)) {
                element.setAttributeNS(null, styleName, (String) entry.getValue());
            }
        }
    }

    /**
     * Applies to boolean.
     *
     * @param styleName the style name
     * @param tagName   the tag name
     * @return the boolean
     */
    protected boolean appliesTo(String styleName, String tagName) {
        Set<String> s = ignoreAttributes.get(tagName);
        if (s == null) {
            return true;
        } else {
            return !s.contains(styleName);
        }
    }
}
