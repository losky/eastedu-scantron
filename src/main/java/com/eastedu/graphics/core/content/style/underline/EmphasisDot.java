package com.eastedu.graphics.core.content.style.underline;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/**
 * 根据调研（word），着重号的大小不跟随文字样式变化
 * todo 着重号的大小只随字体大小变化，后期考虑优化
 *
 * @author ZhenZhong
 */
public class EmphasisDot extends BaseUnderline {

    private static final int DIAMETER = 4;

    /**
     * Instantiates a new Emphasis dot.
     *
     * @param width   the width
     * @param content the content
     * @param g       the g
     */
    public EmphasisDot(double width, String content, Graphics2D g) {
        super(false, width, content, g);
    }


    @Override
    public void draw() {
        Graphics2D g = getGraphics2D();
        String content = getContent();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            GlyphVector glyphVector = g.getFont().createGlyphVector(g.getFontRenderContext(), String.valueOf(c));
            Rectangle2D bounds = glyphVector.getLogicalBounds();
            double textWidth = bounds.getWidth();
            try {
                if (c == 32 || c == 12288) {
                    continue;
                }
                double centerX = glyphVector.getVisualBounds().getCenterX();
                g.fillOval((int) Math.floor(centerX) - DIAMETER / 2, 3, DIAMETER, DIAMETER);
            } finally {
                g.translate(textWidth, 0);
            }
        }

        g.dispose();
    }
}
