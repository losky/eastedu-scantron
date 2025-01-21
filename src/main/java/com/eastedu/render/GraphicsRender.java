package com.eastedu.render;

import com.eastedu.render.content.Paragraph;
import com.eastedu.render.content.Segment;
import com.eastedu.render.enums.UnderlineStyle;
import com.eastedu.render.style.FontStyleRenderer;
import com.eastedu.render.style.TextStyle;
import com.eastedu.render.style.decorator.CompositeDecoratorRender;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Graphics render.
 *
 * @author superman
 */
public class GraphicsRender extends JPanel {
    private final List<Paragraph> paragraphs = new ArrayList<>();
    private final GridRenderer gridRenderer = new GridRenderer();
    private final CompositeDecoratorRender underlineRenderer = new CompositeDecoratorRender();
    private final FontStyleRenderer fontStyleRenderer = new FontStyleRenderer();

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Graphics Render");
        GraphicsRender graphicsRender = new GraphicsRender();

        // 添加控制按钮
        JButton toggleButton = new JButton("Toggle Grid");
        toggleButton.addActionListener(e -> {
            GridRenderer renderer = graphicsRender.gridRenderer; // 获取实例的gridRenderer
            renderer.setEnabled(!renderer.isEnabled());
            graphicsRender.repaint();
        });

        // 创建并添加第一个段落
        Paragraph p1 = new Paragraph();
        p1.addGraphContent(new Segment("汉子文化就立刻圣诞节快乐法律萨内蒂金荷娜快速的减肥", "text",
                TextStyle.builder().fontSize(10F).build()));

        // 创建并添加第二个段落
        Paragraph p2 = new Paragraph();
        p2.addGraphContent(new Segment("新年快乐", "text",
                TextStyle.builder().fontSize(20F).color(Color.RED).strike(true).underlineStyle(UnderlineStyle.DOUBLE)
                        .build()));
        p2.addGraphContent(new Segment("灵蛇出动1", "text",
                TextStyle.builder().fontSize(30F).color(Color.BLUE).emphasisDot(true).underlineStyle(UnderlineStyle.DOTTED)
                        .build()));
        p2.addGraphContent(new Segment("灵蛇出动2", "text",
                TextStyle.builder().fontSize(15F).color(Color.RED).italic(true).underlineStyle(UnderlineStyle.WAVE)
                        .build()));
        p2.addGraphContent(new Segment("灵蛇出动3", "text",
                TextStyle.builder().fontSize(25F).color(Color.RED).bold(true).underlineStyle(UnderlineStyle.SINGLE)
                        .build()));

        graphicsRender.addParagraph(p1);
        graphicsRender.addParagraph(p2);

        // 添加组件到框架
        frame.setLayout(new BorderLayout());
        frame.add(graphicsRender, BorderLayout.CENTER);
        frame.add(toggleButton, BorderLayout.SOUTH);

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Add paragraph.
     *
     * @param paragraph the paragraph
     */
    public void addParagraph(Paragraph paragraph) {
        paragraphs.add(paragraph);
    }

    /**
     * Sets grid enabled.
     *
     * @param enabled the enabled
     */
    public void setGridEnabled(boolean enabled) {
        gridRenderer.setEnabled(enabled);
        repaint(); // 重绘面板
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y = 20;
        int maxWidth = getWidth() - 40;
        int padding = 5;
        int contentSpacing = 20;

        for (Paragraph paragraph : paragraphs) {
            List<SegmentInfo> currentLine = new ArrayList<>();
            int currentLineMaxHeight = 0;
            int currentX = 20;

            for (Segment content : paragraph.getSegments()) {
                if (content.getType().equals("text")) {
                    // 设置字体
                    TextStyle style = content.getStyle();
                    int fontSize = style != null && style.getFontSize() != null
                            ? style.getFontSize().intValue()
                            : 12;
                    Font font = new Font(g.getFont().getName(), Font.PLAIN, fontSize);
                    g.setFont(font);
                    FontMetrics metrics = g.getFontMetrics();

                    String text = content.getContent();
                    int contentWidth = calculateContentWidth(text, metrics, padding);

                    // 检查是否需要换行
                    if (currentX + contentWidth > maxWidth - 20) {
                        // 绘制当前行
                        if (!currentLine.isEmpty()) {
                            drawLine(g, currentLine, y, currentLineMaxHeight, padding, contentSpacing);
                            y += currentLineMaxHeight + padding * 2;
                            currentLine.clear();
                            currentX = 20;
                            currentLineMaxHeight = 0;
                        }
                    }

                    // 添加当前content
                    currentLine.add(new SegmentInfo(text, currentX, font, contentWidth, metrics, style));
                    currentLineMaxHeight = Math.max(currentLineMaxHeight, metrics.getHeight());
                    currentX += contentWidth + contentSpacing;
                }
            }

            // 绘制段落的最后一行
            if (!currentLine.isEmpty()) {
                drawLine(g, currentLine, y, currentLineMaxHeight, padding, contentSpacing);
                y += currentLineMaxHeight + padding * 4;
            }
        }
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

    private void drawLine(Graphics g, List<SegmentInfo> line, int y, int lineHeight, int padding, int contentSpacing) {
        int currentX = 20;
        Graphics2D g2d = (Graphics2D) g;

        // 计算当前行的最大上升距离和下降距离
        int maxAscent = 0;
        int maxDescent = 0;
        for (SegmentInfo info : line) {
            maxAscent = Math.max(maxAscent, info.metrics.getAscent());
            maxDescent = Math.max(maxDescent, info.metrics.getDescent());
        }

        // 计算baseline的位置
        int baseline = y + maxAscent;

        for (int i = 0; i < line.size(); i++) {
            SegmentInfo info = line.get(i);
            TextStyle style = info.style;

            // 应用字体样式
            fontStyleRenderer.applyStyle((Graphics2D) g, style, info.font);
            FontMetrics metrics = g.getFontMetrics();

            for (int j = 0; j < info.text.length(); j++) {
                String ch = info.text.substring(j, j + 1);
                int charWidth = metrics.stringWidth(ch);
                int charHeight = metrics.getAscent() + metrics.getDescent();

                // 计算田字格大小
                int fontSize = info.font.getSize();
                int gridSize = (int) (fontSize * 1.8);

                // 计算位置
                int centerX = currentX + (gridSize - charWidth) / 2;
                int gridY = baseline - metrics.getAscent() - (gridSize - charHeight) / 2;

                // 绘制田字格
                gridRenderer.drawGrid(g2d, currentX, gridY, gridSize);

                // 绘制文字
                g.drawString(ch, centerX, baseline);

                // 绘制装饰线
                if (style != null) {
                    underlineRenderer.draw(g2d, currentX, baseline, gridSize, metrics.getAscent(), style);
                }

                currentX += gridSize;
            }

            if (i < line.size() - 1) {
                currentX += contentSpacing;
            }
        }
    }

    /**
     * 用于存储单个文本片段的渲染信息
     */
    private static class SegmentInfo {
        /**
         * The Text.
         */
        String text;
        /**
         * The X.
         */
        int x;
        /**
         * The Font.
         */
        Font font;
        /**
         * The Width.
         */
        int width;
        /**
         * The Metrics.
         */
        FontMetrics metrics;
        /**
         * The Style.
         */
        TextStyle style;

        /**
         * Instantiates a new Segment info.
         *
         * @param text    the text
         * @param x       the x
         * @param font    the font
         * @param width   the width
         * @param metrics the metrics
         * @param style   the style
         */
        SegmentInfo(String text, int x, Font font, int width, FontMetrics metrics, TextStyle style) {
            this.text = text;
            this.x = x;
            this.font = font;
            this.width = width;
            this.metrics = metrics;
            this.style = style;
        }
    }
}
