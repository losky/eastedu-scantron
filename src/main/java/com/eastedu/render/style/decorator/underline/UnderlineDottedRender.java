package com.eastedu.render.style.decorator.underline;

import com.eastedu.render.style.decorator.DecoratorRender;

import java.awt.*;

/**
 * The type Dotted underline renderer.
 *
 * @author superman
 */
public class UnderlineDottedRender implements DecoratorRender {
    @Override
    public void draw(Graphics2D g2d, int x, int y, int width) {
        float[] dash = {2f, 2f};
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        g2d.drawLine(x, y, x + width, y);
    }
}