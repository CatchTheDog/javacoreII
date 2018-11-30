package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;

/**
 * 实现自定义布局管理器:实现LayoutManager接口
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2018/11/30 10:25
 */
public class CircleLayout implements LayoutManager {

    private int minWidth = 0;
    private int minHeight = 0;
    private int preferredWidth = 0;
    private int preferredHeight = 0;
    private boolean sizeSet = false;
    private int maxComponentWidth = 0;
    private int maxComponentHeight = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CircleLayoutFrame frame = new CircleLayoutFrame();
            frame.setTitle("CircleLayoutFrame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        });
    }

    /**
     * add component to layout manager
     *
     * @param name component name
     * @param comp the component added to mananger
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    /**
     * remove component from layout mananger
     *
     * @param comp the componenet removed from the manager
     */
    @Override
    public void removeLayoutComponent(Component comp) {

    }

    /**
     * set preferred size of the component
     *
     * @param parent the parent container of the component
     * @return the size of the component in <em>Dimension</em>
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        this.setSizes(parent);
        Insets insets = parent.getInsets();
        int width = preferredWidth + insets.left + insets.right;
        int height = preferredHeight = insets.top + insets.bottom;
        return new Dimension(width, height);
    }

    /**
     * set minimum size of the component
     *
     * @param parent the parent container of the component
     * @return the size of the component in <em>Dimension</em>
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        this.setSizes(parent);
        Insets insets = parent.getInsets();
        int width = minWidth + insets.left + insets.right;
        int height = minHeight + insets.top + insets.bottom;
        return new Dimension(width, height);
    }

    /**
     * call the <code>setBounds()</code>,set size and position of the component
     *
     * @param parent the parent container of the component
     */
    @Override
    public void layoutContainer(Container parent) {
        this.setSizes(parent);
        Insets insets = parent.getInsets();
        int containerWidth = parent.getSize().width - insets.left - insets.right;
        int containerHeight = parent.getSize().height - insets.top - insets.bottom;
        int xcenter = insets.left + containerWidth / 2;
        int ycenter = insets.top + containerHeight / 2;

        int xradius = (containerWidth - maxComponentWidth) / 2;
        int yradius = (containerHeight - maxComponentHeight) / 2;
        int radius = Math.min(xradius, yradius);

        int n = parent.getComponentCount();
        for (int i = 0; i < n; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                double angle = 2 * Math.PI * i / n;
                int x = xcenter + (int) (Math.cos(angle) * radius);
                int y = ycenter + (int) (Math.sin(angle) * radius);
                Dimension d = c.getPreferredSize();
                c.setBounds(x - d.width / 2, y - d.height / 2, d.width, d.height);
            }
        }
    }

    public void setSizes(Container parent) {
        if (this.sizeSet) return;
        int n = parent.getComponentCount();
        preferredWidth = 0;
        preferredHeight = 0;
        minWidth = 0;
        minHeight = 0;
        maxComponentHeight = 0;
        maxComponentWidth = 0;

        for (int i = 0; i < n; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                Dimension d = c.getPreferredSize();
                maxComponentWidth = Math.max(maxComponentHeight, d.width);
                maxComponentHeight = Math.max(maxComponentHeight, d.height);
                preferredWidth += d.width;
                preferredHeight += d.height;
            }
        }
        minWidth = preferredWidth / 2;
        minHeight = preferredHeight / 2;
        sizeSet = true;
    }
}

class CircleLayoutFrame extends JFrame {
    public CircleLayoutFrame() {
        this.setLayout(new CircleLayout());
        add(new JButton("Yellow"));
        add(new JButton("Blue"));
        add(new JButton("Red"));
        add(new JButton("Green"));
        add(new JButton("Orange"));
        add(new JButton("Funchsia"));
        add(new JButton("Indigo"));
        pack();
    }
}
