package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mr.X
 * @version 1.0.0
 * @since 2018/11/12 13:31
 */
public class ButtonFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private final JPanel buttonPanel = new JPanel();

    public ButtonFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        makeButton("Yellow", Color.YELLOW);
        makeButton("Blue", Color.BLUE);
        makeButton("Red", Color.RED);
        add(buttonPanel);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame jFrame = new ButtonFrame();
            jFrame.setTitle("ButtonFrame");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setVisible(true);
        });
    }

    /**
     * 简洁设置监听事件的辅助方法
     *
     * @param name
     * @param backgroundColor
     */
    private void makeButton(final String name, final Color backgroundColor) {
        JButton button = new JButton(name);
        this.buttonPanel.add(button);
        button.addActionListener(event -> buttonPanel.setBackground(backgroundColor));
    }
}
