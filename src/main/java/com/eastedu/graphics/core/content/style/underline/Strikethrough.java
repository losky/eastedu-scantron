package com.eastedu.graphics.core.content.style.underline;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * The type Strikethrough.
 *
 * @author ZhenZhong
 */
public class Strikethrough extends BaseUnderline {

    private final double height;

    /**
     * Instantiates a new Strikethrough.
     *
     * @param bold    the bold
     * @param width   the width
     * @param height  the height
     * @param content the content
     * @param g       the g
     */
    public Strikethrough(boolean bold, double width, double height, String content, Graphics2D g) {
        super(bold, width, content, g);
        this.height = height;
    }


    @Override
    public void draw() {
        GeneralPath path = new GeneralPath();
        path.moveTo(0, -height / 2);
        path.lineTo(getWidth(), -height / 2);
        setBold();

        Graphics2D graphics2D = getGraphics2D();
        graphics2D.draw(path);
        graphics2D.dispose();
    }
}
