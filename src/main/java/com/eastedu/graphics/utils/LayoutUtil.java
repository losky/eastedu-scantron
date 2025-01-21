package com.eastedu.graphics.utils;

import com.eastedu.common.enums.AlignmentTypeEnum;
import com.eastedu.common.model.question.QuestionItemContent;
import com.eastedu.graphics.core.content.style.ParagraphStyle;
import com.eastedu.graphics.core.content.style.Style;
import com.eastedu.graphics.enums.ListPatternType;
import com.eastedu.utils.CollectionUtils;
import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.List;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * The type Layout util.
 *
 * @author ZhenZhong
 */
public class LayoutUtil {
    private static final String STYLE_SEPARATOR = "#";
    private static final Map<AlignmentTypeEnum, BiFunction<Float, Float, Float>> ALIGNMENT_FUNCTION = new HashMap<AlignmentTypeEnum, BiFunction<Float, Float, Float>>() {{
        put(AlignmentTypeEnum.Center, (maxWidth, currentWidth) -> (currentWidth - maxWidth) / 2);
        put(AlignmentTypeEnum.Right, (maxWidth, currentWidth) -> (currentWidth - maxWidth));
        put(AlignmentTypeEnum.Left, (maxWidth, currentWidth) -> 0f);
    }};

    private static final Map<String, BiConsumer<ParagraphStyle, String[]>> PARAGRAPH_STYLE_CONVERTORS = new HashMap<>();
    private static final Map<String, BiConsumer<Style, String[]>> STYLE_CONVERTORS = new HashMap<>();

    /**
     * 需要被替换的样式名词，
     * FontSize 一定要在Font前面，否则会出现替换异常
     */
    private static final String[] STYLE_REPLACE = {"Color", "FontSize", "Font"};
    private static final Map<String, String> STYLE_REPLACE_MAPPING = new HashMap<String, String>() {{
        put("Color", "Color" + STYLE_SEPARATOR);
        put("FontSize", "FontSize" + STYLE_SEPARATOR);
        put("Font", "Font" + STYLE_SEPARATOR);
    }};

    static {
        PARAGRAPH_STYLE_CONVERTORS.put("LineSpace", (style, value) -> {
            float lineSpace = Float.parseFloat(value[1]);
            float i = (float) ((lineSpace - 1) * new Style().getFont().getStringBounds("缩", new FontRenderContext(null, false, false)).getHeight());
            style.setLineSpace(i);
        });
        PARAGRAPH_STYLE_CONVERTORS.put("TextIndent", (style, value) -> {
            float indentation = Float.parseFloat(value[1]);
            float i = (float) (indentation * new Style().getFont().getStringBounds("缩", new FontRenderContext(null, false, false)).getWidth());
            style.setIndentation(i);
        });
        PARAGRAPH_STYLE_CONVERTORS.put("NumberedList", (style, value) -> {
            style.setList(true);
            String symbol = value[1];
            String none = "None";
            if (none.equalsIgnoreCase(symbol)) {
                style.setListSymbol('0');
                style.setListPatternType(ListPatternType.CIRCLE);
            } else {
                style.setListPatternType(ListPatternType.parse(symbol));
            }
            style.setListGroup(Integer.parseInt(value[2]));
        });

        STYLE_CONVERTORS.put("FontSize", (style, value) -> {
            float fontSize = Float.parseFloat(value[1]);
            style.setFontSize(fontSize);
        });
        STYLE_CONVERTORS.put("Font", (style, value) -> {
            String font = value[1];
            style.setFontFamily(font);
        });
        STYLE_CONVERTORS.put("Color", (style, value) -> {
            String color = value[1];
            int colorLength = 6;
            if (color.length() == colorLength) {
                style.setColor(new Color(Integer.parseInt(color, 16)));
            }
        });
        STYLE_CONVERTORS.put("width", (style, value) -> {
            float width = Float.parseFloat(value[1]);
            style.setImageWidth(width);
        });
        STYLE_CONVERTORS.put("scale", (style, value) -> {
            float scale = Float.parseFloat(value[1]);
            style.setImageScale(scale == 0 ? 1 : scale);
        });
        STYLE_CONVERTORS.put("Wordspace", (style, value) -> {
            int wordSpace = Integer.parseInt(value[1]);
            style.setWordSpace(wordSpace);
        });
        STYLE_CONVERTORS.put("Italic", (style, value) -> style.setItalic(true));
        STYLE_CONVERTORS.put("Bold", (style, value) -> style.setBold(true));
        STYLE_CONVERTORS.put("VertAlignSubscript", (style, value) -> style.setSubscript(true));
        STYLE_CONVERTORS.put("VertAlignSuperscript", (style, value) -> style.setSuperscript(true));
        STYLE_CONVERTORS.put("Strike", (style, value) -> style.setStrike(true));
        STYLE_CONVERTORS.put("EmphasisDot", (style, value) -> style.setEmphasisDot(true));
        STYLE_CONVERTORS.put("UnderlineSingle", (style, value) -> style.setUnderlineSingle(true));
        STYLE_CONVERTORS.put("UnderlineDouble", (style, value) -> style.setUnderlineDouble(true));
        STYLE_CONVERTORS.put("UnderlineDotted", (style, value) -> style.setUnderlineDotted(true));
        STYLE_CONVERTORS.put("UnderlineWave", (style, value) -> style.setUnderlineWave(true));
    }

    /**
     * Calc original abscissa.
     *
     * @param alignment    the alignment
     * @param currentWidth the current width
     * @param maxWidth     the max width
     * @return the float
     */
    public static float calcOriginalAbscissa(AlignmentTypeEnum alignment, float currentWidth, float maxWidth) {
        return ALIGNMENT_FUNCTION.get(alignment).apply(currentWidth, maxWidth);
    }

    /**
     * Gets canvas width.
     *
     * @param graphics2D the graphics 2 d
     * @return the canvas width
     */
    public static float getCanvasWidth(Graphics2D graphics2D) {
        return (float) ((SVGGraphics2D) graphics2D).getSVGCanvasSize().getWidth();
    }

    /**
     * Convert paragraph style.
     *
     * @param paragraph the paragraph
     * @return the paragraph style
     */
    public static ParagraphStyle convert(QuestionItemContent paragraph) {
        List<String> styles = paragraph.getStyles();
        ParagraphStyle style = new ParagraphStyle();
        if (Objects.nonNull(paragraph.getAlignment())) {
            style.setAlignment(paragraph.getAlignment());
        }
        if (CollectionUtils.isEmpty(styles)) {
            return style;
        }
        for (String s : styles) {
            String[] parts = s.split(STYLE_SEPARATOR);
            if (PARAGRAPH_STYLE_CONVERTORS.containsKey(parts[0])) {
                PARAGRAPH_STYLE_CONVERTORS.get(parts[0]).accept(style, parts);
            }
        }
        return style;
    }

    /**
     * Convert style.
     *
     * @param styles         样式信息
     * @param supportedStyle 包含的样式
     * @param excludeStyle   排除的样式
     * @return the style
     */
    public static Style convert(Set<String> styles, List<String> supportedStyle, List<String> excludeStyle) {
        if (CollectionUtils.isEmpty(styles)) {
            return new Style();
        }
        List<String> cacheStyle = unifiedStyleNamingConvention(styles);
        Style style = new Style();
        for (String s : cacheStyle) {
            String[] parts = s.split(STYLE_SEPARATOR);
            String styleName = parts[0];
            // 排除的样式
            if (excludeStyle.contains(styleName)) {
                continue;
            }
            if (supportedStyle.contains("*") || supportedStyle.contains(styleName)) {
                if (STYLE_CONVERTORS.containsKey(styleName)) {
                    STYLE_CONVERTORS.get(styleName).accept(style, parts);
                }
            }
        }
        return style;
    }

    private static List<String> unifiedStyleNamingConvention(Set<String> styles) {
        List<String> cacheStyle = new ArrayList<>(styles);
        // 统一处理样式名，为所有未添加#分隔符的样式增加#分隔符，方便后面转换样式
        for (int i = 0; i < cacheStyle.size(); i++) {
            String style = cacheStyle.get(i);
            if (style.contains(STYLE_SEPARATOR)) {
                continue;
            }
            for (String key : STYLE_REPLACE) {
                if (style.startsWith(key)) {
                    cacheStyle.set(i, style.replace(key, STYLE_REPLACE_MAPPING.get(key)));
                    break;
                }
            }
        }
        return cacheStyle;
    }
}
