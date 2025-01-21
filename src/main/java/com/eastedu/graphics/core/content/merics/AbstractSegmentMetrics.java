package com.eastedu.graphics.core.content.merics;

import java.awt.*;
import java.util.StringJoiner;

/**
 * The type Abstract segment metrics.
 *
 * @author ZhenZhong
 */
public abstract class AbstractSegmentMetrics implements SegmentMetrics {
    /**
     * The maximum distance above the baseline for the tallest glyph in
     * the font at a given text size.
     */
    private float top;
    /**
     * The recommended distance above the baseline for singled spaced text.
     * 从baseline到文字顶部的距离
     */
    private float ascent;
    /**
     * The recommended distance below the baseline for singled spaced text.
     */
    private float descent;
    /**
     * The maximum distance below the baseline for the lowest glyph in
     * the font at a given text size.
     */
    private float bottom;
    /**
     * The recommended additional space to add between lines of text.
     * 行间距
     */
    private float leading;

    private double width;
    private double height;

    private double virtualWidth;
    @Override
    public float getTop() {
        return top;
    }

    /**
     * Sets top.
     *
     * @param top the top
     */
    protected void setTop(float top) {
        this.top = top;
    }

    @Override
    public float getAscent() {
        return ascent;
    }

    /**
     * Sets ascent.
     *
     * @param ascent the ascent
     */
    protected void setAscent(float ascent) {
        this.ascent = ascent;
    }

    @Override
    public float getDescent() {
        return descent;
    }

    /**
     * Sets descent.
     *
     * @param descent the descent
     */
    protected void setDescent(float descent) {
        this.descent = descent;
    }

    @Override
    public float getBottom() {
        return bottom;
    }

    /**
     * Sets bottom.
     *
     * @param bottom the bottom
     */
    protected void setBottom(float bottom) {
        this.bottom = bottom;
    }

    @Override
    public float getLeading() {
        return leading;
    }

    /**
     * Sets leading.
     *
     * @param leading the leading
     */
    protected void setLeading(float leading) {
        this.leading = leading;
    }

    @Override
    public double getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    protected void setWidth(double width) {
        this.width = width;
    }

    /**
     * Gets virtual width.
     *
     * @return the virtual width
     */
    @Override
    public double getVirtualWidth() {
        return virtualWidth;
    }

    /**
     * Sets virtual width.
     *
     * @param virtualWidth the virtual width
     */
    public void setVirtualWidth(double virtualWidth) {
        this.virtualWidth = virtualWidth;
    }

    @Override
    public double getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    protected void setHeight(double height) {
        this.height = height;
    }

    @Override
    public Dimension getDimension() {
        return new Dimension((int) Math.round(this.width), (int) Math.round(this.height));
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", AbstractSegmentMetrics.class.getSimpleName() + "[", "]")
                .add("top=" + top)
                .add("ascent=" + ascent)
                .add("descent=" + descent)
                .add("bottom=" + bottom)
                .add("leading=" + leading)
                .add("virtualWidth=" + virtualWidth)
                .add("width=" + width)
                .add("height=" + height)
                .toString();
    }
}
