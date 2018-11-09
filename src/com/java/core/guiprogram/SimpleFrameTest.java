package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * AWT为java1.0推出的GUI框架，后续在AWT的基础上，推出了Swing.
 * Swing在AWT的基础上，负责窗口组件生成，而AWT仍然负责事件处理等。
 */
public class SimpleFrameTest {
    public static void main(String[] args) {
        //每个Swing程序中，所有的Swing组件必须由事件分派线程进行配置，线程将鼠标点击或者按键控制转移到用户接口组件。
        EventQueue.invokeLater(() -> {
            SimpleFrame frame = new SimpleFrame();
            frame.setTitle("SizedFrame");
            //设置框架大小为整个屏幕大小
            //frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            //设置框架大小不能调整
            frame.setResizable(false);
            //定义用户关闭框架时的响应动作
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //设置框架可见
            frame.setVisible(true);
        });
    }
}

/**
 * 如果框架只包含标准的组件，如按钮和文本框，可以通过调用pack方法设置框架大小为刚好能够放置所有组件的大小。通常情况下，将程序主框架尺寸设置为最大。
 * frame.setExtendedState(Frame.MAXIMIZED_BOTH);
 */
class SimpleFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public SimpleFrame() {
        Dimension dimension = getScreenSize();
        //窗口默认带下为 0 * 0 ，所以不设置大小看不到窗口
        //setSize(DEFAULT_WIDTH == 0 ? dimension.width/2 : DEFAULT_WIDTH,DEFAULT_HEIGHT == 0 ? dimension.height/2 : DEFAULT_HEIGHT);
        setSize(dimension.width / 2, dimension.height / 2);
        setLocationByPlatform(true);
        //使用GIF图窗口无法显式，而且路径默认是从工程根路径下开始的。
        URL url = getClass().getResource("logo.jpg");
        Image image = new ImageIcon(url).getImage();
        setIconImage(image);
    }

    /**
     * 获取当前屏幕分辨率
     *
     * @return
     */
    private Dimension getScreenSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize();
    }
}
