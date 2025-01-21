package com.eastedu.graphics.core;

import lombok.ToString;

import java.awt.*;

/**
 * The type Scalable dimension.
 *
 * @author ZhenZhong
 */
@ToString(callSuper = true)
public class ScalableDimension extends Dimension {
    private final float scale;

    /**
     * Instantiates a new Scalable dimension.
     *
     * @param scale the scale
     */
    public ScalableDimension(int scale) {
        this.scale = scale;
    }

    /**
     * Instantiates a new Scalable dimension.
     *
     * @param d     the d
     * @param scale the scale
     */
    public ScalableDimension(Dimension d, float scale) {
        super(d);
        this.scale = scale;
    }

    /**
     * Instantiates a new Scalable dimension.
     *
     * @param width  the width
     * @param height the height
     * @param scale  the scale
     */
    public ScalableDimension(int width, int height, int scale) {
        super(width, height);
        this.scale = scale;
    }

    /**
     * Create dimension.
     *
     * @param dimension the dimension
     * @param scale     the scale
     * @return the dimension
     */
    public static Dimension create(Dimension dimension, int scale) {
        return new ScalableDimension(dimension, scale);
    }

    @Override
    public double getWidth() {
        return super.getWidth() * scale;
    }

    @Override
    public double getHeight() {
        return super.getHeight() * scale;
    }

}
