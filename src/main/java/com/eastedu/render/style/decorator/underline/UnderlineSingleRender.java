package com.eastedu.render.style.decorator.underline;

import com.eastedu.render.style.decorator.DecoratorRender;

import java.awt.*;

/**
 * The type Single underline renderer.
 *
 * @author superman
 */
public class UnderlineSingleRender implements DecoratorRender {
    @Override
    public void draw(Graphics2D g2d, int x, int y, int width) {
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawLine(x, y, x + width, y);
    }
}