package com.eastedu.render.content;

import com.eastedu.render.GridRenderer;
import com.eastedu.render.style.TextStyle;
import com.eastedu.render.style.decorator.CompositeDecoratorRender;
import lombok.Data;
import java.awt.*;

/**
 * The type Segment.
 * 
 * @author superman
 */
@Data
public class Segment {
    private String content;
    private String type;
    private TextStyle style;
    private CompositeDecoratorRender decoratorRender;

    /**
     * Instantiates a new Segment.
     *
     * @param content the content
     * @param type    the type
     * @param style   the style
     */
    public Segment(String content, String type, TextStyle style) {
        this.content = content;
        this.type = type;
        this.style = style;
        this.decoratorRender = new CompositeDecoratorRender(style);
    }

    public void render(Graphics2D g2d, int x, int baseline, int gridSize,
            GridRenderer gridRenderer,
            char ch,
            FontMetrics metrics) {
        if (!"text".equals(type))
            return;

        // 计算田字格位置
        int charHeight = metrics.getAscent() + metrics.getDescent();
        int gridY = baseline - metrics.getAscent() - (gridSize - charHeight) / 2;

        // 绘制田字格
        gridRenderer.drawGrid(g2d, x, gridY, gridSize);

        // 绘制装饰
        if (style != null) {
            decoratorRender.draw(g2d, x, baseline, gridSize, metrics.getAscent());
        }

        // 计算单个字符宽度和位置
        int charWidth = metrics.charWidth(ch);
        int centerX = x + (gridSize - charWidth) / 2;

        // 绘制文字
        g2d.drawString(String.valueOf(ch), centerX, baseline);

    }
}
