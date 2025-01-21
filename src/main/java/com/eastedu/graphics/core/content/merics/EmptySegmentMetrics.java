package com.eastedu.graphics.core.content.merics;


import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/**
 * The type Html segment metrics.
 *
 * @author luozhenzhong
 */
public class EmptySegmentMetrics extends AbstractSegmentMetrics implements SegmentMetrics {
    /**
     * 该构造为完全为0的度量信息
     */
    public EmptySegmentMetrics() {
        setLeading(6);
        setTop(0);
        setAscent(0);
        setDescent(0);
        setBottom(0);
        setWidth(0);
        setHeight(0);
    }

    /**
     * 该构造是为了支持空白高度
     *
     * @param font the font
     */
    public EmptySegmentMetrics(Font font) {
        FontDesignMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        GlyphVector glyphVector = font.createGlyphVector(fontMetrics.getFontRenderContext(), "空");
        Rectangle2D stringBounds = glyphVector.getLogicalBounds();

        setLeading(fontMetrics.getLeading());
        setTop(fontMetrics.getMaxAscent());
        setAscent(fontMetrics.getAscent());
        setDescent((float) (stringBounds.getHeight() + stringBounds.getY()));
        setBottom(fontMetrics.getMaxDescent());
        setWidth(0);
        setHeight(stringBounds.getHeight());
    }
}
