package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;

public class PlafFrame extends JFrame {
    private final JPanel buttonPanel = new JPanel();

    public PlafFrame() {
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : infos)
            makeButton(info.getName(), info.getClassName());

        add(buttonPanel);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame jFrame = new PlafFrame();
            jFrame.setTitle("PlafFrame");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Dimension dimension = new Dimension(800, 800);
            jFrame.setSize(dimension);
            jFrame.setVisible(true);
        });
    }

    private void makeButton(final String name, final String className) {
        JButton button = new JButton(name);
        this.buttonPanel.add(button);
        button.addActionListener(event -> {
            try {
                UIManager.setLookAndFeel(className);
                SwingUtilities.updateComponentTreeUI(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        });
    }
}
