package com.eastedu.render;

import lombok.Data;

import java.awt.*;

/**
 * The type Grid renderer.
 *
 * @author superman
 */
@Data
public class GridRenderer {
    private boolean enabled = true;
    private Color gridColor = Color.LIGHT_GRAY;
    private float[] dashPattern = {2f, 2f};

    /**
     * Draw grid.
     *
     * @param g2d  the g 2 d
     * @param x    the x
     * @param y    the y
     * @param size the size
     */
    public void drawGrid(Graphics2D g2d, int x, int y, int size) {
        if (!enabled) {
            return;
        }

        Graphics2D graphics = (Graphics2D) g2d.create();
        try {
            // 设置颜色
            graphics.setColor(gridColor);

            // 创建虚线样式
            BasicStroke dashedStroke = new BasicStroke(
                    1f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f,
                    dashPattern,
                    0.0f
            );

            // 创建实线样式
            BasicStroke solidStroke = new BasicStroke(1f);

            // 绘制外框（实线）
            graphics.setStroke(solidStroke);
            graphics.drawRect(x, y, size, size);

            // 绘制十字线（虚线）
            graphics.setStroke(dashedStroke);
            graphics.drawLine(x, y + size / 2, x + size, y + size / 2);
            graphics.drawLine(x + size / 2, y, x + size / 2, y + size);

        } finally {
            // 释放资源
            graphics.dispose();
        }
    }
} 