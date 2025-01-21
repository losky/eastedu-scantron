package com.eastedu.graphics.core.content.style.underline;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * The type Underline double.
 *
 * @author ZhenZhong
 */
public class UnderlineDouble extends BaseUnderline {
    private static final int NUMBER = 2;

    /**
     * Instantiates a new Underline double.
     *
     * @param bold    the bold
     * @param width   the width
     * @param content the content
     * @param g       the g
     */
    public UnderlineDouble(boolean bold, double width, String content, Graphics2D g) {
        super(bold, width, content, g);
    }

    @Override
    public void draw() {
        for (int i = 0; i < NUMBER; i++) {
            GeneralPath path = new GeneralPath();
            float offset = (float) (i * 2 * 1.5);
            path.moveTo(0, offset);
            path.lineTo(getWidth(), offset);
            setBold();
            getGraphics2D().draw(path);
            getGraphics2D().dispose();
        }
    }
}
