package com.eastedu.render.content;

import com.eastedu.render.GridRenderer;
import com.eastedu.render.style.TextStyle;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Paragraph.
 *
 * @author superman
 */
@Data
public class Paragraph {
    private final List<Segment> segments = new ArrayList<>();

    /**
     * Add graph content.
     *
     * @param content the content
     */
    public void addGraphContent(Segment content) {
        segments.add(content);
    }

    /**
     * Render.
     * @param g2d               the g 2 d
     * @param startX            the start x
     * @param startY            the start y
     * @param maxWidth          the max width
     * @param gridRenderer      the grid renderer
     */
    public void render(Graphics2D g2d, int startX, int startY, int maxWidth,
                       GridRenderer gridRenderer) {
        int currentX = startX;
        int currentY = startY;
        int padding = 5;
        int contentSpacing = 20;
        List<SegmentInfo> currentLine = new ArrayList<>();
        int currentLineMaxHeight = 0;

        // 处理每个segment
        for (Segment segment : segments) {
            if (segment.getType().equals("text")) {
                // 计算字体度量
                FontMetrics metrics = calculateMetrics(g2d, segment);
                int contentWidth = calculateContentWidth(segment.getContent(), metrics, padding);

                // 检查是否需要换行
                if (currentX + contentWidth > maxWidth - startX) {
                    drawLine(g2d, currentLine, currentY, currentLineMaxHeight,
                            gridRenderer);
                    currentY += currentLineMaxHeight + padding * 2;
                    currentLine.clear();
                    currentX = startX;
                    currentLineMaxHeight = 0;
                }

                // 添加到当前行
                currentLine.add(new SegmentInfo(segment, currentX, metrics));
                currentLineMaxHeight = Math.max(currentLineMaxHeight, metrics.getHeight());
                currentX += contentWidth + contentSpacing;
            }
        }

        // 绘制最后一行
        if (!currentLine.isEmpty()) {
            drawLine(g2d, currentLine, currentY, currentLineMaxHeight,
                    gridRenderer);
        }
    }

    private void drawLine(Graphics2D g2d, List<SegmentInfo> line, int y, int lineHeight,
                         GridRenderer gridRenderer) {
        // 计算baseline
        int maxAscent = line.stream()
                .mapToInt(info -> info.metrics.getAscent())
                .max()
                .orElse(0);
        int baseline = y + maxAscent;

        // 绘制每个segment
        for (SegmentInfo info : line) {
            int fontSize = info.segment.getStyle().getFontSize();
            String text = info.segment.getContent();
            int currentX = info.x;
            
            for (char ch : text.toCharArray()) {
                int gridSize = (int) (fontSize * 1.8);
                
                // 渲染单个字符（包括田字格）
                info.segment.render(g2d, currentX, baseline, gridSize, 
                                  gridRenderer, ch, info.metrics);
                
                currentX += gridSize;
            }
        }
    }

    private FontMetrics calculateMetrics(Graphics2D g2d, Segment segment) {
        // 设置字体
        TextStyle style = segment.getStyle();
        g2d.setFont(style.getFont());
        return g2d.getFontMetrics();
    }

    private int calculateContentWidth(String text, FontMetrics metrics, int padding) {
        int totalWidth = 0;
        Font font = metrics.getFont();
        int fontSize = font.getSize();
        // 保持与drawLine中相同的计算方式
        int gridSize = (int) (fontSize * 1.8);

        for (char ch : text.toCharArray()) {
            totalWidth += gridSize;
        }
        return totalWidth;
    }

    private static class SegmentInfo {
        /**
         * The Segment.
         */
        final Segment segment;
        /**
         * The X.
         */
        final int x;
        /**
         * The Metrics.
         */
        final FontMetrics metrics;

        /**
         * Instantiates a new Segment info.
         *
         * @param segment the segment
         * @param x       the x
         * @param metrics the metrics
         */
        SegmentInfo(Segment segment, int x, FontMetrics metrics) {
            this.segment = segment;
            this.x = x;
            this.metrics = metrics;
        }
    }
}
