package com.eastedu.graphics.core.content.style.underline;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The type Underline wave.
 * 由于更改了坐标计算规则，因此波浪线的y坐标与文字的y坐标相等了，需要向下偏移一点
 *
 * @author ZhenZhong
 */
public class UnderlineWave extends BaseUnderline {

    /**
     * Instantiates a new Underline wave.
     *
     * @param bold    the bold
     * @param width   the width
     * @param content the content
     * @param g       the g
     */
    public UnderlineWave(boolean bold, double width, String content, Graphics2D g) {
        super(bold, width, content, g);
    }

    @Override
    public void draw() {

        GeneralPath path = new GeneralPath();
        double stepWidth = 2.4d;
        double high = 1.44d;
        double controlPointX = 1.2d;
        double controlPointY = 0d;

        int count = BigDecimal.valueOf(getWidth() / stepWidth).setScale(2, RoundingMode.HALF_UP).intValue() / 2;
        int mode = 2;
        if (count % mode != 0) {
            count++;
        }

        double x = 0;
        double y = 5;
        path.moveTo(x, y);
        for (int i = 1; i < count + 1; i++) {
            double[] xx = init();
            xx[0] = x + controlPointX;
            xx[1] = x + controlPointX + controlPointX;
            xx[2] = x + controlPointX + controlPointX + stepWidth;
            double[] yy = init();
            yy[0] = y + controlPointY;
            if (i % 2 == 0) {
                yy[1] = y + controlPointY + high;
                yy[2] = y + controlPointY + high + high;
            } else {
                yy[1] = y + controlPointY - high;
                yy[2] = y + controlPointY - high - high;
            }
            path.curveTo(xx[0], yy[0], xx[1], yy[1], xx[2], yy[2]);
            x = xx[2];
            y = yy[2];
        }
        getGraphics2D().setStroke(new BasicStroke(1.2F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10));
        getGraphics2D().draw(path);
        getGraphics2D().dispose();
    }

    private double[] init() {
        return new double[3];
    }

}
