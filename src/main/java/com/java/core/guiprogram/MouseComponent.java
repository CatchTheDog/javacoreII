package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 鼠标点击会调用三个监听器方法：mousePressed/mouseReleased/mouseClicked
 */
public class MouseComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    private static final int SIDELENGTH = 10;
    private List<Rectangle2D> squares;
    private Rectangle2D current;    //the square contaning the mouse cursor

    public MouseComponent() {
        this.squares = new ArrayList<>();
        current = null;

        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MouseFrame mouseFrame = new MouseFrame();
            mouseFrame.setTitle("Mouse Frame");
            mouseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mouseFrame.setVisible(true);
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.BLUE);
        for (Rectangle2D r : squares)
            g2.draw(r);
    }

    /**
     * Finds the first square contaning a point.
     *
     * @param p the point
     * @return the first square containing the point.
     */
    public Rectangle2D find(Point2D p) {
        for (Rectangle2D r : squares)
            if (r.contains(p)) return r;
        return null;
    }

    /**
     * 添加方块并重新绘制图形
     *
     * @param p 要添加的方块
     */
    public void add(Point2D p) {
        double x = p.getX();
        double y = p.getY();

        this.current = new Rectangle2D.Double(x - SIDELENGTH / 2, y - SIDELENGTH / 2, SIDELENGTH, SIDELENGTH);
        this.squares.add(this.current);
        repaint();
    }

    /**
     * 删除方块
     *
     * @param s
     */
    public void remove(Rectangle2D s) {
        if (s == null) return;
        if (s == current) current = null;
        this.squares.remove(s);
        repaint();
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            current = find(e.getPoint());
            if (current == null) add(e.getPoint());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            current = find(e.getPoint());
            if (current != null && e.getClickCount() >= 2) remove(current);
        }
    }

    private class MouseMotionHandler implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (current != null) {
                int x = e.getX();
                int y = e.getY();
                current.setFrame(x - SIDELENGTH / 2, y - SIDELENGTH / 2, SIDELENGTH, SIDELENGTH);
                repaint();
            }

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //鼠标在方块中移动时，将鼠标设置为十字
            if (find(e.getPoint()) == null) setCursor(Cursor.getDefaultCursor());
            else setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }
}
