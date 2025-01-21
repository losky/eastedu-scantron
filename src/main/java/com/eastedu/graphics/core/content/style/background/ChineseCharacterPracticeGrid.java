package com.eastedu.graphics.core.content.style.background;

import java.awt.*;

/**
 * 汉字练习网格
 *
 * @author luozhenzhong
 */
public class ChineseCharacterPracticeGrid {

    /**
     * Draw.
     *
     * @param graph  the graph
     * @param x      the x
     * @param y      the y
     * @param length the length
     * @param cross  是否米字
     */
    public static void draw(Graphics2D graph, float x, float y, int length, boolean cross) {

        int xMin = (int) x;
        int xMax = xMin + length;
        int yMin = (int) y;
        int yMax = yMin + length;

        // 绘制虚线十字
        graph.setColor(Color.GRAY);
        graph.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{2, 2}, 1f));
        graph.drawLine(xMin, (yMin + yMax) / 2, xMax, (yMin + yMax) / 2);
        graph.drawLine((xMin + xMax) / 2, yMin, (xMin + xMax) / 2, yMax);

        // 绘制虚线X
        if (cross) {
            graph.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{2, 2}, 0.8f));
            graph.drawLine(xMin, yMin, (xMin + xMax) / 2, (yMin + yMax) / 2);
            graph.drawLine(xMin, yMax, (xMin + xMax) / 2, (yMin + yMax) / 2);
            graph.drawLine((xMin + xMax) / 2, (yMin + yMax) / 2, xMax, yMax);
            graph.drawLine((xMin + xMax) / 2, (yMin + yMax) / 2, xMax, yMin);
        }

        // 绘制矩形
        graph.setColor(Color.DARK_GRAY);
        graph.setStroke(new BasicStroke(0.5f));
        graph.drawRect(xMin, yMin, length, length);


    }
}
