package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorChoosePanel extends JPanel {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;

    public ColorChoosePanel() {
        this.setLayout(new GridLayout(1, 3));
        JButton modalButton = new JButton("Modal");
        modalButton.addActionListener(new ModalListener());
        this.add(modalButton);

        JButton modelessButton = new JButton("Modeless");
        modelessButton.addActionListener(new ModelessListener());
        this.add(modelessButton);

        JButton immediateButton = new JButton("Imediate");
        immediateButton.addActionListener(new ImmediateListener());
        this.add(immediateButton);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setLayout(new BorderLayout());
            frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            ColorChoosePanel panel = new ColorChoosePanel();
            frame.add(panel, BorderLayout.SOUTH);
            JPanel preview = new JPanel();
            preview.setBackground(null);
            frame.add(preview, BorderLayout.CENTER);
            //为frame中所有组件的事件添加事件跟踪器
            EventTrace tracer = new EventTrace();
            tracer.add(frame);
            frame.setTitle("ColorChoosePanel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    private class ModalListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color defaultColor = getBackground();
            Color selected = JColorChooser.showDialog(ColorChoosePanel.this, "Set background", defaultColor);
            if (selected != null) {
                setBackground(selected);
            }
        }
    }

    private class ModelessListener implements ActionListener {

        private JDialog dialog;
        private JColorChooser chooser;

        public ModelessListener() {
            chooser = new JColorChooser();
            dialog = JColorChooser.createDialog(ColorChoosePanel.this, "Background Color", false, chooser, event -> {
                setBackground(chooser.getColor());
            }, null);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.setColor(getBackground());
            dialog.setVisible(true);
        }
    }

    private class ImmediateListener implements ActionListener {
        private JDialog dialog;
        private JColorChooser chooser;

        public ImmediateListener() {
            chooser = new JColorChooser();
            chooser.getSelectionModel().addChangeListener(event -> setBackground(chooser.getColor()));
            dialog = new JDialog((Frame) null, false);
            dialog.add(chooser);
            dialog.pack();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.setColor(getBackground());
            dialog.setVisible(true);
        }
    }
}
