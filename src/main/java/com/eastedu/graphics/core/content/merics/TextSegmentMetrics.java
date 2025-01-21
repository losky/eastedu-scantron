package com.eastedu.graphics.core.content.merics;

import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/**
 * 度量信息
 *
 * @author luozhenzhong
 */
public class TextSegmentMetrics extends AbstractSegmentMetrics implements SegmentMetrics {

    /**
     * Instantiates a new Text segment metrics.
     *
     * @param content the content
     * @param font    the font
     */
    public TextSegmentMetrics(String content, Font font) {
        // todo 如果是斜体，会出现最右边文字的右上角缺失一小部分
        FontDesignMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        GlyphVector glyphVector = font.createGlyphVector(fontMetrics.getFontRenderContext(), content);
        Rectangle2D stringBounds = glyphVector.getLogicalBounds();

        setLeading(fontMetrics.getLeading());
        setTop(fontMetrics.getMaxAscent());
        setAscent(fontMetrics.getAscent());
        setDescent((float) (stringBounds.getHeight() + stringBounds.getY()));
        setBottom(fontMetrics.getMaxDescent());
        setWidth(stringBounds.getWidth());
        setHeight(stringBounds.getHeight());

        setVirtualWidth(glyphVector.getVisualBounds().getWidth());

    }
}