package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;

public class DialogFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    private AboutDialog dialog;

    public DialogFrame() {
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(event -> {
            if (dialog == null) {
                dialog = new AboutDialog(DialogFrame.this);
            }
            dialog.setVisible(true);
        });
        fileMenu.add(aboutItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(exitItem);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            DialogFrame frame = new DialogFrame();
            frame.setTitle("DialogFrame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
