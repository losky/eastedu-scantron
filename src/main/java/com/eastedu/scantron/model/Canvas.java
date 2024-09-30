package com.eastedu.scantron.model;

import java.util.ArrayList;
import java.util.List;import javax.swing.Timer; // 添加导入

/**
 * Canvas 类表示一个可以包含多个形状的画布
 *
 * @author superman
 * @version 1.0
 * @since 2024-07-20
 */
public class Canvas {
    // 存储画布上的所有形状
    private List<Shape> shapes = new ArrayList<>();
    private Timer timer; // 定义定时器
    public Canvas() {
        timer = new Timer(5000, e -> drawAll()); // 5秒后执行 drawAll
        timer.setRepeats(false); // 不重复执行
    }

    /**
     * 向画布添加一个新的形状
     * 
     * @param shape 要添加的形状
     */
    public void addShape(Shape shape) {
        shapes.add(shape);
        timer.restart(); // 重置定时器
    }

    /**
     * 绘制画布上的所有形状
     */
    public void drawAll() {
        for (Shape shape : shapes) {
            shape.draw();
        }
    }
}
