package com.eastedu.render;

import com.eastedu.render.content.Paragraph;
import com.eastedu.render.content.Segment;
import com.eastedu.render.enums.UnderlineStyle;
import com.eastedu.render.style.TextStyle;

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
                TextStyle.builder().fontSize(10).build()));

        // 创建并添加第二个段落
        Paragraph p2 = new Paragraph();
        p2.addGraphContent(new Segment("新年快乐", "text",
                TextStyle.builder().fontSize(20).color(Color.RED).strike(true).underlineStyle(UnderlineStyle.DOUBLE)
                        .build()));
        p2.addGraphContent(new Segment("灵蛇出动1", "text",
                TextStyle.builder().fontSize(30).color(Color.BLUE).emphasisDot(true).underlineStyle(UnderlineStyle.DOTTED)
                        .build()));
        p2.addGraphContent(new Segment("灵蛇出动2", "text",
                TextStyle.builder().fontSize(15).color(Color.RED).italic(true).underlineStyle(UnderlineStyle.WAVE)
                        .build()));
        p2.addGraphContent(new Segment("灵蛇出动3", "text",
                TextStyle.builder().fontSize(25).color(Color.RED).bold(true).underlineStyle(UnderlineStyle.SINGLE)
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int startY = 20;
        int maxWidth = getWidth() - 40;

        // 直接使用每个段落的render方法
        for (Paragraph paragraph : paragraphs) {
            paragraph.render(g2d, 20, startY, maxWidth,
                    gridRenderer);
            startY += 50; // 段落间距
        }
    }
}
