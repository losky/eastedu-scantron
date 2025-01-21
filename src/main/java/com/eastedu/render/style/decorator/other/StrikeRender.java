package com.eastedu.render.style.decorator.other;

import com.eastedu.render.style.decorator.DecoratorRender;

import java.awt.*;

/**
 * The type Strike render.
 *
 * @author superman
 */
public class StrikeRender implements DecoratorRender {
    /**
     * Draw strike.
     *
     * @param g2d   the g 2 d
     * @param x     the x
     * @param y     the y
     * @param width the width
     */
    @Override
    public void draw(Graphics2D g2d, int x, int y, int width) {
        g2d.drawLine(x, y, x + width, y);
    }
}
