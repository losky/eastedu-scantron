package com.eastedu.graphics.core.content.merics;

import java.awt.*;

/**
 * 度量信息
 *
 * @author luozhenzhong
 */
public class MattsSegmentMetrics extends AbstractSegmentMetrics implements SegmentMetrics {

    /**
     * Instantiates a new Image segment metrics.
     *
     * @param font the font
     */
    public MattsSegmentMetrics(Font font) {
        double length = font.getSize() * 1.3;

        setLeading(8);
        setTop(0);
        setAscent((float) (length * 0.78));
        setDescent((float) (length * 0.22));
        setBottom(5);
        setWidth(length);
        setHeight(length);

    }

}