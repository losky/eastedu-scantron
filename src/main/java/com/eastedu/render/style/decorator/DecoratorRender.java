package com.eastedu.render.style.decorator;

import java.awt.*;

/**
 * The interface Underline renderer.
 *
 * @author superman
 */
public interface DecoratorRender {
    /**
     * Draw.
     *
     * @param g2d   the g 2 d
     * @param x     the x
     * @param y     the y
     * @param width the width
     */
    void draw(Graphics2D g2d, int x, int y, int width);
} 