package com.java.core.guiprogram;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ImageViewerFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;
    private JLabel label;
    private JFileChooser chooser;

    public ImageViewerFrame() {
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(event -> {
            chooser.setCurrentDirectory(new File(".")); //设置当前目录
            int result = chooser.showOpenDialog(ImageViewerFrame.this);//打开一个模式对话框
            if (result == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getPath(); //获取用户选择的文件名
                label.setIcon(new ImageIcon(name));
                this.pack();
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(event -> System.exit(0));

        label = new JLabel();
        this.add(label);

        chooser = new JFileChooser();
        //设置文件过滤器，只允许选择扩展名为  Images files,jpg,jpeg,gif的文件
        FileFilter fileFilter = new FileNameExtensionFilter("Images files", "jpg", "jpeg", "gif");
        chooser.setFileFilter(fileFilter);
        chooser.setAccessory(new ImagePreviewer(chooser)); //设置图片预览器
        chooser.setFileView(new FileIconView(fileFilter, new ImageIcon("palette.gif"))); //为满足过滤条件的文件设置特殊的图标
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ImageViewerFrame frame = new ImageViewerFrame();
            frame.setTitle("ImageViewFrame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
