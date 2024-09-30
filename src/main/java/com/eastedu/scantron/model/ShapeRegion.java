package com.eastedu.scantron.model; // 添加包声明

import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ShapeRegion类表示一个形状区域
 * 
 * @author superman
 * @version 1.0
 * @since 2024-07-20
 */
@Data
@AllArgsConstructor
public class ShapeRegion {
    private Point location;
    private Dimension dimension;
    private String style; // 样式

    // 转换为图形的方法
    public Shape toShape() {
        // 示例逻辑
        Shape shape = new Rectangle();
        // 设置形状属性
        return shape; // 返回转换后的形状
    }

    // 从图形加载的方法
    public static ShapeRegion fromShape(Shape shape) {
        Point location = shape.getBounds().getLocation();
        Dimension dimension = shape.getBounds().getSize();
        return new ShapeRegion(location, dimension, null);
    }
}