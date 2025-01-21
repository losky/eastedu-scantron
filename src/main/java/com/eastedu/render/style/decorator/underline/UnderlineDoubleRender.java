package com.eastedu.render.style.decorator.underline;

import com.eastedu.render.style.decorator.DecoratorRender;

import java.awt.*;

/**
 * The type Double underline renderer.
 *
 * @author superman
 */
public class UnderlineDoubleRender implements DecoratorRender {
    @Override
    public void draw(Graphics2D g2d, int x, int y, int width) {
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawLine(x, y, x + width, y);
        g2d.drawLine(x, y + 3, x + width, y + 3);
    }
}