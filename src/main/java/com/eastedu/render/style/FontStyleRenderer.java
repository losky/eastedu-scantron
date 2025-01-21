package com.eastedu.render.style;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * The type Font style renderer.
 * @author superman
 */
public class FontStyleRenderer {
    /**
     * Apply style.
     *
     * @param g2d      the g 2 d
     * @param style    the style
     * @param baseFont the base font
     */
    public void applyStyle(Graphics2D g2d, TextStyle style, Font baseFont) {
        if (style != null) {
            // 设置字体样式
            int fontStyle = Font.PLAIN;
            if (style.isBold()) {
                fontStyle |= Font.BOLD;
            }
            if (style.isItalic()) {
                fontStyle |= Font.ITALIC;
            }
            
            // 创建新字体
            Font font = new Font(
                style.getFontFamily() != null ? style.getFontFamily() : baseFont.getFamily(),
                fontStyle,
                style.getFontSize() != null ? style.getFontSize().intValue() : baseFont.getSize()
            );
            g2d.setFont(font);
            
            // 应用颜色
            if (style.getColor() != null) {
                g2d.setColor(style.getColor());
            } else {
                g2d.setColor(Color.BLACK);
            }
        }
    }
} 