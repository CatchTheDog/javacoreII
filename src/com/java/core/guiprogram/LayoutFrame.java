package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;

/**
 * 网格组布局
 */
public class LayoutFrame extends JFrame {
    public static final int TEXT_ROWS = 10;
    public static final int TEXT_COLUMNS = 20;

    private JComboBox<String> face;
    private JComboBox<Integer> size;
    private JCheckBox bold;
    private JCheckBox italic;
    private JTextArea sample;

    public LayoutFrame() {
        GridBagLayout layout = new GridBagLayout();

    }
}
