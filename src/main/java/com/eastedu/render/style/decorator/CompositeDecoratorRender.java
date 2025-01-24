package com.eastedu.render.style.decorator;

import com.eastedu.render.enums.UnderlineStyle;
import com.eastedu.render.style.FontStyleRender;
import com.eastedu.render.style.TextStyle;
import com.eastedu.render.style.decorator.other.EmphasisDotRender;
import com.eastedu.render.style.decorator.other.StrikeRender;
import com.eastedu.render.style.decorator.underline.UnderlineDottedRender;
import com.eastedu.render.style.decorator.underline.UnderlineDoubleRender;
import com.eastedu.render.style.decorator.underline.UnderlineSingleRender;
import com.eastedu.render.style.decorator.underline.UnderlineWaveRender;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type Composite underline render.
 *
 * @author superman
 */
public class CompositeDecoratorRender {
    private final static Map<UnderlineStyle, DecoratorRender> renderers = new HashMap<>();
    private final static DecoratorRender emphasisDotRender = new EmphasisDotRender();
    private final static DecoratorRender strikeRender = new StrikeRender();
    private final static FontStyleRender fontStyleRender = new FontStyleRender();
    private final TextStyle style;

    /**
     * Instantiates a new Composite underline render.
     */
    public CompositeDecoratorRender(TextStyle style) {
        this.style = style;
    }

    static {
        renderers.put(UnderlineStyle.SINGLE, new UnderlineSingleRender());
        renderers.put(UnderlineStyle.DOUBLE, new UnderlineDoubleRender());
        renderers.put(UnderlineStyle.DOTTED, new UnderlineDottedRender());
        renderers.put(UnderlineStyle.WAVE, new UnderlineWaveRender());
    }

    /**
     * Draw.
     * @param g2d      the g 2 d
     * @param x        the x
     * @param baseline the baseline
     * @param width    the width
     * @param ascent   the ascent
     */
    public void draw(Graphics2D g2d, int x, int baseline, int width, int ascent) {
        if (Objects.nonNull(style)) {
            // 应用字体样式
            fontStyleRender.applyStyle(g2d, style);

            // 绘制下划线
            if (style.getUnderlineStyle() != null) {
                DecoratorRender renderer = renderers.get(style.getUnderlineStyle());
                if (renderer != null) {
                    // 下划线偏移4个像素
                    int y = baseline + 4;
                    renderer.draw(g2d, x, y, width);
                }
            }

            // 绘制删除线
            if (style.isStrike()) {
                // 删除线位于基线上方ascent/2
                int y = baseline - ascent / 2;
                strikeRender.draw(g2d, x, y, width);
            }

            // 绘制着重点
            if (style.isEmphasisDot()) {
                // 着重点偏移6个像素
                int y = baseline + 6;
                emphasisDotRender.draw(g2d, x, y, width);
            }
        }
    }

}
