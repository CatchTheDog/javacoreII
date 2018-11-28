package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CheckBoxFrame extends JFrame {
    private static final int FONTSIZE = 24;
    JPanel buttonPanel;
    private JLabel label;
    private JCheckBox bold;
    private JCheckBox italic;
    private ButtonGroup buttonGroup;

    public CheckBoxFrame() {
        label = new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif", Font.BOLD, FONTSIZE));
        this.add(label, BorderLayout.NORTH);
        ActionListener listener = event -> {
            int mode = 0;
            if (bold.isSelected()) mode += Font.BOLD;
            if (italic.isSelected()) mode += Font.ITALIC;
            label.setFont(new Font("Serif", mode, FONTSIZE));
        };

        buttonPanel = new JPanel();
        bold = new JCheckBox("Bold");
        bold.addActionListener(listener);
        bold.setSelected(true);
        buttonPanel.add(bold);

        italic = new JCheckBox("Italic");
        italic.addActionListener(listener);
        buttonPanel.add(italic);

        buttonGroup = new ButtonGroup();
        addRadioButton("Small", 23, false);
        addRadioButton("Medium", 24, true);
        addRadioButton("Large", 25, false);
        addRadioButton("Extra Large", 26, false);

        this.add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CheckBoxFrame checkBoxFrame = new CheckBoxFrame();
            checkBoxFrame.setTitle("CheckBoxFrame");
            checkBoxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            checkBoxFrame.setVisible(true);
        });
    }

    private void addRadioButton(String name, int fontSize, boolean isSelected) {
        JRadioButton button = new JRadioButton(name, isSelected);
        button.addActionListener(event -> {
            label.setFont(new Font("Serif", Font.PLAIN, fontSize));
        });
        buttonGroup.add(button);
        buttonPanel.add(button);
    }
}
