package com.eastedu.graphics.core.content.style.underline;

import lombok.Data;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * The type Base underline.
 * <p>
 * 移除加粗逻辑，因为下换线等样式不会跟随文字加粗变化，而是跟随文字大小变化
 *
 * @author ZhenZhong
 */
@Data
public abstract class BaseUnderline {
    private final Graphics2D graphics2D;
    private final boolean bold;
    private final String content;
    /**
     * 文字宽度
     */
    private final double width;

    /**
     * Instantiates a new Base underline.
     *
     * @param bold    the bold
     * @param width   the width
     * @param content the content
     * @param g       the g
     */
    public BaseUnderline(boolean bold, double width, String content, Graphics2D g) {
        this.bold = bold;
        this.width = width;
        this.content = content;
        this.graphics2D = g;
    }


    /**
     * Draw.
     */
    public void draw() {
        GeneralPath path = new GeneralPath();
        path.moveTo(0, 0);
        path.lineTo(width, 0);
        setBold();

        Graphics2D graphics2D = getGraphics2D();
        graphics2D.draw(path);
        graphics2D.dispose();
    }

    /**
     * Sets bold.
     */
    protected void setBold() {
        Graphics2D graphics2D = getGraphics2D();
        graphics2D.setStroke(new BasicStroke(1.0F));
    }
}
