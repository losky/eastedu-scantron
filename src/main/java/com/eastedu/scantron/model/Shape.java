package com.eastedu.scantron.model;

/**
 * Shape 接口定义了可绘制形状的基本行为。
 * 实现此接口的类应提供绘制形状的具体方法。
 *
 * @author superman
 * @version 1.0
 * @since 2024-07-20
 */
public interface Shape {
    /**
     * 绘制形状的方法。
     * 实现类应在此方法中提供绘制形状的具体逻辑。
     */
    void draw();
}
