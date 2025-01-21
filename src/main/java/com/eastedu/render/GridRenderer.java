package com.eastedu.render;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

/**
 * The type Grid renderer.
 * @author superman
 */
public class GridRenderer {
    private boolean enabled = true;
    private Color gridColor = Color.LIGHT_GRAY;
    private float[] dashPattern = {2f, 2f};

    /**
     * Is enabled boolean.
     *
     * @return the boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets grid color.
     *
     * @param color the color
     */
    public void setGridColor(Color color) {
        this.gridColor = color;
    }

    /**
     * Sets dash pattern.
     *
     * @param pattern the pattern
     */
    public void setDashPattern(float[] pattern) {
        this.dashPattern = pattern;
    }

    /**
     * Draw grid.
     *
     * @param g2d  the g 2 d
     * @param x    the x
     * @param y    the y
     * @param size the size
     */
    public void drawGrid(Graphics2D g2d, int x, int y, int size) {
        if (!enabled) return;
        
        // 保存原始状态
        Stroke originalStroke = g2d.getStroke();
        Color originalColor = g2d.getColor();
        
        try {
            // 创建虚线样式
            BasicStroke dashedStroke = new BasicStroke(
                1f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f,
                dashPattern,
                0.0f
            );
            
            // 设置颜色
            g2d.setColor(gridColor);
            
            // 绘制外框（实线）
            g2d.setStroke(originalStroke);
            g2d.drawRect(x, y, size, size);
            
            // 绘制十字线（虚线）
            g2d.setStroke(dashedStroke);
            g2d.drawLine(x, y + size/2, x + size, y + size/2);
            g2d.drawLine(x + size/2, y, x + size/2, y + size);
            
        } finally {
            // 恢复原始状态
            g2d.setStroke(originalStroke);
            g2d.setColor(originalColor);
        }
    }
} 