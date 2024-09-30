package com.eastedu.scantron;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.*; // 添加导入
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton; // 添加导入
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这是 Eastedu Scantron 应用程序的主类
 * 使用 Spring Boot 框架
 *
 * @author superman
 * @version 1.0
 * @since 2024-07-20
 */
@SpringBootApplication
public class EasteduScantronApplication {

    private enum Anchor {
        LEFT, RIGHT, TOP, BOTTOM
    }

    private enum ResizeHandle {
        NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST
    }

    /**
     * 应用程序的入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 创建一个JFrame作为主窗口
        JFrame frame = new JFrame("画布演示");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // 创建自定义画布
        CustomCanvas canvas = new CustomCanvas();

        // 在创建JFrame的代码中添加重置按钮
        JButton resetButton = new JButton("重置");
        resetButton.addActionListener(e -> {
            saveCanvas((CustomCanvas) canvas); // 保存当前画布状态
            ((CustomCanvas) canvas).shapes.clear(); // 清空所有图形
            ((CustomCanvas) canvas).allDragPaths.clear(); // 清空拖动路径
            canvas.repaint(); // 重新绘制画布
        });

        // 添加重置按钮到JFrame
        frame.setLayout(new BorderLayout()); // 设置布局
        frame.add(resetButton, BorderLayout.NORTH); // 将按钮添加到顶部
        frame.add(canvas, BorderLayout.CENTER); // 将画布添加到中心

        // 添加加载按钮
        JButton loadButton = new JButton("加载");
        loadButton.addActionListener(e -> {
            loadCanvas((CustomCanvas) canvas); // 加载画布状态
        });

        // 添加加载按钮到JFrame
        frame.add(loadButton, BorderLayout.SOUTH); // 将按钮添加到底部

        frame.setVisible(true);
    }

    // 添加保存和加载方法
    private static void saveCanvas(CustomCanvas canvas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("canvas.dat"))) {
            oos.writeObject(canvas.shapes); // 保存图形列表
            oos.writeObject(canvas.allDragPaths); // 保存拖动路径
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadCanvas(CustomCanvas canvas) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("canvas.dat"))) {
            // 使用类型安全的方式读取图形列表和拖动路径
            @SuppressWarnings("unchecked") // 添加此行以抑制警告
            List<Shape> shapes = (List<Shape>) ois.readObject(); // 读取图形列表
            @SuppressWarnings("unchecked") // 添加此行以抑制警告
            List<List<Point>> dragPaths = (List<List<Point>>) ois.readObject(); // 读取拖动路径
            canvas.shapes = shapes; // 赋值
            canvas.allDragPaths = dragPaths; // 赋值
            canvas.repaint(); // 重新绘制画布
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 添加以下方法
    private static ResizeHandle getResizeHandle(Shape shape, Point point) {
        // 根据形状和点的相对位置返回相应的 ResizeHandle
        Rectangle bounds = shape.getBounds();
        if (bounds.contains(point)) {
            // 逻辑判断以确定哪个锚点被接近
            // 示例：返回相应的 ResizeHandle
        }
        return null; // 默认返回
    }

    // 自定义JPanel类
    static class CustomCanvas extends JPanel {
        private List<Shape> shapes = new ArrayList<>();
        private Shape selectedShape;
        private Point startPoint;
        private boolean isResizing = false;
        private boolean isDragging = false;
        private int dragStartX, dragStartY; // 添加这一行
        private List<Point> dragPath = new ArrayList<>();
        private List<List<Point>> allDragPaths = new ArrayList<>();
        private Point currentMousePosition;
        private ResizeHandle resizeHandle; // 添加这一行

        {
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint();
                    dragPath.clear();
                    dragPath.add(startPoint);
                    selectedShape = null;
                    for (Shape shape : shapes) {
                        if (shape.contains(startPoint)) {
                            selectedShape = shape;
                            if (isNearAnchor(shape, startPoint)) {
                                isResizing = true;
                                resizeHandle = getResizeHandle(shape, e.getPoint()); // 添加方法定义
                            } else {
                                isDragging = true;
                            }
                            break;
                        }
                    }
                    if (selectedShape == null) {
                        selectedShape = new Rectangle(startPoint.x, startPoint.y, 0, 0);
                        shapes.add(selectedShape);
                    }
                    dragStartX = e.getX(); // 添加这一行
                    dragStartY = e.getY(); // 添加这一行
                    repaint();
                }

                public void mouseReleased(MouseEvent e) {
                    if (isDragging && !dragPath.isEmpty()) {
                        allDragPaths.add(new ArrayList<>(dragPath));
                    }
                    selectedShape = null;
                    isResizing = false;
                    isDragging = false;
                    dragPath.clear();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (selectedShape != null) {
                        if (isResizing) {
                            handleDrag(e);
                        } else if (isDragging) {
                            moveShape(selectedShape, e.getPoint());
                        } else {
                            // 创建新图形的逻辑保持不变
                            int x = Math.min(startPoint.x, e.getX());
                            int y = Math.min(startPoint.y, e.getY());
                            int width = Math.abs(startPoint.x - e.getX());
                            int height = Math.abs(startPoint.y - e.getY());
                            if (selectedShape instanceof Rectangle) {
                                ((Rectangle) selectedShape).setBounds(x, y, width, height);
                            } else if (selectedShape instanceof java.awt.geom.Ellipse2D.Double) {
                                ((java.awt.geom.Ellipse2D.Double) selectedShape).setFrame(x, y, width, height);
                            }
                        }
                        repaint();
                    }
                    // 更新当前鼠标位置
                    currentMousePosition = e.getPoint();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (isDragging) {
                        // 确保dragPath已经初始化
                        if (dragPath == null) {
                            dragPath = new ArrayList<>();
                        }
                        dragPath.add(e.getPoint());
                    }
                    // 其他拖动逻辑...
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (selectedShape != null) {
                        if (isResizing) {
                            resizeShape(selectedShape, e.getPoint());
                        } else if (isDragging) {
                            moveShape(selectedShape, e.getPoint());
                        } else {
                            // 创建新图形的逻辑保持不变
                            int x = Math.min(startPoint.x, e.getX());
                            int y = Math.min(startPoint.y, e.getY());
                            int width = Math.abs(startPoint.x - e.getX());
                            int height = Math.abs(startPoint.y - e.getY());
                            if (selectedShape instanceof Rectangle) {
                                ((Rectangle) selectedShape).setBounds(x, y, width, height);
                            } else if (selectedShape instanceof java.awt.geom.Ellipse2D.Double) {
                                ((java.awt.geom.Ellipse2D.Double) selectedShape).setFrame(x, y, width, height);
                            }
                        }
                        repaint();
                    }
                    // 更新当前鼠标位置
                    currentMousePosition = e.getPoint();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (selectedShape != null) {
                        if (isNearAnchor(selectedShape, e.getPoint())) {
                            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                        } else {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    } else {
                        setCursor(Cursor.getDefaultCursor());
                    }
                }
            });
        }

        private boolean isNearAnchor(Shape shape, Point point) {
            Rectangle bounds = shape.getBounds();
            Rectangle[] anchors = getAnchors(bounds);
            for (Rectangle anchor : anchors) {
                if (anchor.contains(point)) {
                    return true;
                }
            }
            return false;
        }

        private Rectangle[] getAnchors(Rectangle bounds) {
            int anchorSize = 10; // 增加锚点大小
            return new Rectangle[] {
                    new Rectangle(bounds.x - anchorSize / 2, bounds.y - anchorSize / 2, anchorSize, anchorSize),
                    new Rectangle(bounds.x + bounds.width - anchorSize / 2, bounds.y - anchorSize / 2, anchorSize,
                            anchorSize),
                    new Rectangle(bounds.x - anchorSize / 2, bounds.y + bounds.height - anchorSize / 2, anchorSize,
                            anchorSize),
                    new Rectangle(bounds.x + bounds.width - anchorSize / 2, bounds.y + bounds.height - anchorSize / 2,
                            anchorSize, anchorSize)
            };
        }

        private void resizeShape(Shape shape, Point newPoint) {
            if (shape instanceof Rectangle) {
                Rectangle rect = (Rectangle) shape;
                int x = Math.min(rect.x, newPoint.x);
                int y = Math.min(rect.y, newPoint.y);
                int width = Math.abs(rect.x - newPoint.x);
                int height = Math.abs(rect.y - newPoint.y);
                rect.setBounds(x, y, width, height);
            }
            // 对于其他形状类型，可以在这里添加相应的处理逻辑
        }

        private void moveShape(Shape shape, Point newPoint) {
            if (shape instanceof Rectangle) {
                Rectangle rect = (Rectangle) shape;
                int dx = newPoint.x - startPoint.x;
                int dy = newPoint.y - startPoint.y;
                rect.setLocation(rect.x + dx, rect.y + dy);
                startPoint = newPoint;
            }
            // 对于其他形状类型，可以在这里添加相应的处理逻辑
        }

        private void handleDrag(MouseEvent e) {
            if (selectedShape != null && selectedShape instanceof Rectangle) {
                Rectangle rect = (Rectangle) selectedShape;
                int dx = e.getX() - dragStartX;
                int dy = e.getY() - dragStartY;

                if (resizeHandle == ResizeHandle.NORTHWEST) {
                    rect.x += dx;
                    rect.y += dy;
                    rect.width -= dx;
                    rect.height -= dy;
                } else if (resizeHandle == ResizeHandle.NORTHEAST) {
                    rect.y += dy;
                    rect.width += dx;
                    rect.height -= dy;
                } else if (resizeHandle == ResizeHandle.SOUTHWEST) {
                    rect.x += dx;
                    rect.width -= dx;
                    rect.height += dy;
                } else if (resizeHandle == ResizeHandle.SOUTHEAST) {
                    rect.width += dx;
                    rect.height += dy;
                }

                // 确保矩形不会变成负值
                if (rect.width < 1) {
                    rect.x -= 1 - rect.width;
                    rect.width = 1;
                }
                if (rect.height < 1) {
                    rect.y -= 1 - rect.height;
                    rect.height = 1;
                }

                dragStartX = e.getX();
                dragStartY = e.getY();
                repaint();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // 绘制所有图形
            for (Shape shape : shapes) {
                g2d.setColor(Color.BLACK);
                g2d.draw(shape);
            }

            // 绘制选中图形的锚点
            if (selectedShape != null) {
                g2d.setColor(Color.BLUE);
                g2d.draw(selectedShape);
                Rectangle bounds = selectedShape.getBounds();
                Rectangle[] anchors = getAnchors(bounds);
                for (Rectangle anchor : anchors) {
                    g2d.fill(anchor);
                }
            }

            // 绘制拖动轨迹
            g2d.setColor(Color.RED);
            for (List<Point> path : allDragPaths) {
                for (int i = 1; i < path.size(); i++) {
                    Point p1 = path.get(i - 1);
                    Point p2 = path.get(i);
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }
    };

}
