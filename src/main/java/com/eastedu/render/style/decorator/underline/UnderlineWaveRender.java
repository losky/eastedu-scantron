package com.eastedu.render.style.decorator.underline;

import com.eastedu.render.style.decorator.DecoratorRender;

import java.awt.*;

/**
 * The type Wave underline renderer.
 *
 * @author superman
 */
public class UnderlineWaveRender implements DecoratorRender {
    @Override
    public void draw(Graphics2D g2d, int x, int y, int width) {
        int waveHeight = 2;
        int waveLength = 4;
        for (int i = 0; i < width; i += waveLength) {
            g2d.drawLine(x + i, y, x + i + waveLength / 2, y + waveHeight);
            g2d.drawLine(x + i + waveLength / 2, y + waveHeight, x + i + waveLength, y);
        }
    }
} 