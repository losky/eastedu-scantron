package com.eastedu.graphics.core.content.merics;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * 度量信息
 *
 * @author luozhenzhong
 */
public class ShapeSegmentMetrics extends AbstractSegmentMetrics implements SegmentMetrics {

    /**
     * Instantiates a new Shape segment metrics.
     *
     * @param shape the shape
     */
    public ShapeSegmentMetrics(Shape shape) {
        Rectangle2D stringBounds = shape.getBounds2D();

        //为了使文字相对图片垂直居中显示，这里折半计算
        double width = stringBounds.getWidth();
        double height = stringBounds.getHeight();

        setLeading(6);
        setTop(0);
        setAscent(0);
        setDescent(0);
        setBottom(0);
        setWidth(width);
        setHeight(height);

    }

}