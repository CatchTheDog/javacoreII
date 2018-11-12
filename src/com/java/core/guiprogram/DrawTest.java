package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Mr.X
 * @version 1.0.0
 * <p>
 * 构造椭圆：给出其外接矩形即可
 * E11ipse2D e = new Ellipse2D.Double(150, 200, 100, 50);
 * 如果已知椭圆中心点坐标，宽和高；则使用如下方式：
 * E11ipse2D ellipse = new E111pse2D.Doub1e(centerX - width / 2, centerY - height / 2, width, height);
 * 或者：
 * Ellipse2D circle = new Ellipse2D.Double();
 * circle.setFrameFromCenter(centerX,centerY,centerX+radius,centerY+radius);
 * 构造矩形：给出左上坐标，宽和高即可
 * 如果已知矩形对角点（不了解哪一个是左上角顶点），可以使用如下方式：
 * Rectangle2D rect = new Rectangle2D.Doub1e();
 * rect.setFrameFromDiagonal (px, py, qx, qy);
 * 如果一直的顶点分别使用Point2D对象表示，则可以：
 * ract.setFrameFroiiiDiagonal (p, q);
 * 构造直线：
 * Line2D line = new Li ne2D.Double(start, end);
 * 或者
 * Line2D line = new Line2D.0ouble(startX, startY, endX, endY) ;
 * @since 2018/11/12 8:44
 */
public class DrawTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new DrawFrame();
            frame.setTitle("DrawTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }


}

class DrawFrame extends JFrame {
    public DrawFrame() {
        //多次调用add方法，覆盖
        add(new DrawComponent());
        add(new TextComponent());
        pack();
    }
}

/**
 * A component that displays rectangles and ellipses.
 */
class DrawComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    /**
     * 使用方法paintComponent未能显示框架内容
     * 改为使用paint方法可以显示
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //draw a rectangle

        double leftX = 100;
        double topY = 100;
        double width = 200;
        double height = 150;

        Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
        g2.draw(rect);

        //draw the enclosed ellipse

        Ellipse2D ellipse = new Ellipse2D.Double();
        ellipse.setFrame(rect);
        g2.draw(ellipse);


        //draw a diagonal line
        g2.draw(new Line2D.Double(leftX, topY, leftX + width, topY + height));


        //draw a circle with the same center

        double centerX = rect.getCenterX();
        double centerY = rect.getCenterY();
        double radius = 125;

        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrameFromCenter(centerX, centerY, centerX + radius, centerY + radius);
        g2.draw(circle);
    }

    /**
     * If the <code>preferredSize</code> has been set to a
     * non-<code>null</code> value just returns it.
     * If the UI delegate's <code>getPreferredSize</code>
     * method returns a non <code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>preferredSize</code> property
     * @see #setPreferredSize
     * @see ComponentUI
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}

class TextComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    /**
     *
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setPaint(Color.BLACK);
        graphics2D.drawString("Draw Test", 100, 20);
    }

    /**
     * If the <code>preferredSize</code> has been set to a
     * non-<code>null</code> value just returns it.
     * If the UI delegate's <code>getPreferredSize</code>
     * method returns a non <code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>preferredSize</code> property
     * @see #setPreferredSize
     * @see ComponentUI
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}


