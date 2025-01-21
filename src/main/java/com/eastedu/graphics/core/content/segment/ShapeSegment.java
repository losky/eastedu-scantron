package com.eastedu.graphics.core.content.segment;


import com.eastedu.common.model.question.Fragment;
import com.eastedu.graphics.core.content.merics.SegmentMetrics;
import com.eastedu.graphics.core.content.merics.ShapeSegmentMetrics;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * The type Shape segment.
 *
 * @author ZhenZhong
 */
public class ShapeSegment extends BaseSegment {

    private final Shape shape;

    /**
     * Instantiates a new Shape segment.
     *
     * @param row   the row
     * @param shape the shape
     */
    public ShapeSegment(int row, Shape shape) {
        super(row, new Fragment());
        this.shape = shape;
    }


    @Override
    protected void doDraw(Graphics2D graphics2D) {
        Ellipse2D shape = (Ellipse2D) getShape();
        shape.setFrame(shape.getX(), -shape.getHeight(), shape.getWidth(), shape.getHeight());
        graphics2D.fill(shape);
    }

    /**
     * Gets shape.
     *
     * @return the shape
     */
    public Shape getShape() {
        return shape;
    }

    @Override
    public boolean isTextual() {
        return true;
    }

    @Override
    protected SegmentMetrics initFontMetrics() {
        return new ShapeSegmentMetrics(shape);
    }

    @Override
    protected BaseSegment sub(String content) {
        throw new UnsupportedOperationException("不支持的操作");
    }
}
