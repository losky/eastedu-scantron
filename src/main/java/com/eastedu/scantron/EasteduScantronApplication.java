package com.eastedu.scantron;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
// 添加导入
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu; // 添加导入
import javax.swing.JMenuBar; // 添加导入
import javax.swing.JMenuItem; // 添加导入
import javax.swing.JOptionPane; // 添加导入
import javax.swing.JPanel;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这是 Eastedu Scantron 应用程的主类
 * 使用 Spring Boot 框架
 *
 * @author superman
 * @version 1.0
 * @since 2024-07-20
 */
@SpringBootApplication
public class EasteduScantronApplication {

    private enum ResizeHandle {
        NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST
    }

    private static String currentFileName = null; // 修改为静态变量

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

 

        // 添加重置按钮到JFrame
        frame.setLayout(new BorderLayout()); // 设置布局
        frame.add(canvas, BorderLayout.CENTER); // 将画布添加到中心
 

        JMenuBar menuBar = new JMenuBar(); // 创建菜单栏
        JMenu fileMenu = new JMenu("文件"); // 创建文件菜单
        JMenuItem resetItem = new JMenuItem("重置"); // 创建重置菜单项
        JMenuItem loadItem = new JMenuItem("加载"); // 创建加载菜单项

        resetItem.addActionListener(new ActionListener() { // 使用 ActionListener
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("输入保存名称:"); // 输入保存名称
                if (name != null && !name.trim().isEmpty()) {
                    saveCanvas((CustomCanvas) canvas); // 保存当前画布状态
                    currentFileName = name + ".dat"; // 更新当前文件名
                }

            }
        });
        loadItem.addActionListener(new ActionListener() { // 使用 ActionListener
            @Override
            public void actionPerformed(ActionEvent e) {
                File folder = new File("."); // 当前目录
                File[] files = folder.listFiles((dir, name) -> name.endsWith(".dat")); // 获取所有.dat文件
                List<String> fileNames = new ArrayList<>();
                if (files != null) {
                    for (File file : files) {
                        fileNames.add(file.getName().replace(".dat", "")); // 去掉文件扩展名
                    }
                }
                
                String name = (String) JOptionPane.showInputDialog(
                    null,
                    "选择加载名称:",
                    "加载画布",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    fileNames.toArray(),
                    fileNames.isEmpty() ? null : fileNames.get(0) // 默认选择第一个
                );
    
                if (name != null && !name.trim().isEmpty()) {
                    loadCanvas((CustomCanvas) canvas, name); // 加载画布状态
                }
            }
        });

        fileMenu.add(resetItem); // 将重置项添加到菜单
        fileMenu.add(loadItem); // 将加载项添加到菜单
        menuBar.add(fileMenu); // 将文件菜单添加到菜单栏
        frame.setJMenuBar(menuBar); // 设置菜单栏

        // 添加新建功能到菜单
        JMenuItem newMenuItem = new JMenuItem("新建");
        newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 添加对外部类的引用
                if (canvas.isCanvasSaved()) { // 检测画布是否已保存
                    canvas.clearCanvas();
                } else {
                    saveCanvas(canvas); // 调用保存功能
                }
            }
        });
        fileMenu.add(newMenuItem); // 将菜单项添加到菜单

        frame.setVisible(true);
    }

 
    // 修改保存方法
    private static void saveCanvas(CustomCanvas canvas) {
        try {
            if (currentFileName == null) { // 判断是否为新建文件
                String name = JOptionPane.showInputDialog("输入保存名称:"); // 输入保存名称
                if (name != null && !name.trim().isEmpty()) {
                    currentFileName = name + ".dat"; // 更新当前文件名
                } else {
                    return; // 如果没有输入名称，则返回
                }
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentFileName))) {
                oos.writeObject(canvas.shapes); // 保存图形列表
                oos.writeObject(canvas.allDragPaths); // 保存拖动路径
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadCanvas(CustomCanvas canvas, String name) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name + ".dat"))) {
            @SuppressWarnings("unchecked")
            List<Shape> shapes = (List<Shape>) ois.readObject(); // 读取图形列表
            @SuppressWarnings("unchecked")
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
        private boolean isCanvasSaved = false;
        private int dragStartX, dragStartY; // 添加这一行
        private List<Point> dragPath = new ArrayList<>();
        private List<List<Point>> allDragPaths = new ArrayList<>();
        private Point currentMousePosition;
        private ResizeHandle resizeHandle; // 添加这一行

        public boolean isCanvasSaved() {
            // 返回画布是否���保存的逻辑
            return isCanvasSaved; // 示例返回值
        }

        public void clearCanvas() {
            this.shapes.clear();
            this.allDragPaths.clear();
            this.repaint();
        }

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
                    isCanvasSaved = false;
                }

                public void mouseReleased(MouseEvent e) {
                    if (isDragging && !dragPath.isEmpty()) {
                        allDragPaths.add(new ArrayList<>(dragPath));
                    }
                    selectedShape = null;
                    isResizing = false;
                    isDragging = false;
                    dragPath.clear();
                    repaint();
                    isCanvasSaved = false;
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
                    isCanvasSaved = false;
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
                        isCanvasSaved = false;
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
                    isCanvasSaved = false;
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
                    isCanvasSaved = false;
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
                this.isCanvasSaved = false;
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
                this.isCanvasSaved = false;
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
                this.isCanvasSaved = false;
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