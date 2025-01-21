package com.eastedu.render.style.decorator.other;

import com.eastedu.render.style.decorator.DecoratorRender;

import java.awt.*;

/**
 * The type Emphasis dot render.
 *
 * @author superman
 */
public class EmphasisDotRender implements DecoratorRender {
    /**
     * Draw emphasis dot.
     * This method draws an emphasis dot at the specified location.
     * 点线
     *
     * @param g2d   the g 2 d
     * @param x     the x
     * @param y     the y
     * @param width the width
     */
    @Override
    public void draw(Graphics2D g2d, int x, int y, int width) {
        g2d.fillOval(x + width / 2 - 1, y, 2, 2);
    }
}
