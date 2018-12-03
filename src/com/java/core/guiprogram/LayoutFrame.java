package com.java.core.guiprogram;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * 网格组布局
 * 组件约束：
 * gridx gridy :指定被添加组件的左上角的行列位置
 * gridwidth gridheight：指定了组件占据的行数和列数
 * 增量域 weightx,weighty:在窗口缩放时，单元格的大小改变增量（增量域表示分配给每个区域的扩展比例）
 * fill:如果不希望组件拉伸至整个区域，则使用fill约束；四个有效值：GridBagConstraints.NONE,GridBagConstraints.HORIZONTAL,GridBagConstraints.VERTICAL,GridBagConstraints.BOTH
 * anchor:若组件没有填充整个区域，可以通过anchor域指定其位置：GridBagConstraints.CENTER,GridBagConstraints.NORTH,GridBagConstraints.NORTHEAST,GridBagConstraints.EAST
 * insets:可以通过设置insets域在组件周围增加附件的空白区域
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
        this.setLayout(layout);
        ActionListener listener = event -> updateSample();
        JLabel faceLabel = new JLabel("Face:");
        face = new JComboBox<>(new String[]{"Serif", "SansSerif", "Monospaced", "Dialog", "DialogInput"});
        face.addActionListener(listener);
        JLabel sizeLabel = new JLabel("Size:");
        size = new JComboBox<>(new Integer[]{8, 10, 12, 15, 18, 24, 36, 48});
        size.addActionListener(listener);
        bold = new JCheckBox("Bold");
        bold.addActionListener(listener);
        italic = new JCheckBox("Italic");
        italic.addActionListener(listener);

        sample = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        sample.setText("The quick brown fox jumps over the lazy dog");
        sample.setEditable(false);
        sample.setLineWrap(true);
        sample.setBorder(BorderFactory.createEtchedBorder());

        //添加约束
        this.add(faceLabel, new GBC(0, 0).setAnchor(GBC.EAST));
        this.add(face, new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
        this.add(sizeLabel, new GBC(0, 1).setAnchor(GBC.EAST));
        this.add(size, new GBC(1, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
        this.add(bold, new GBC(0, 2, 2, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
        this.add(italic, new GBC(0, 3, 2, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
        this.add(sample, new GBC(2, 0, 1, 4).setFill(GBC.BOTH).setWeight(100, 100));
        pack();
        updateSample();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LayoutFrame frame = new LayoutFrame();
            frame.setTitle("LayoutFrame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public void updateSample() {
        String fontFace = (String) face.getSelectedItem();
        int fontStyle = (bold.isSelected() ? Font.BOLD : 0) + (italic.isSelected() ? Font.ITALIC : 0);
        int fontSize = size.getItemAt(size.getSelectedIndex());
        Font font = new Font(fontFace, fontStyle, fontSize);
        sample.setFont(font);
        sample.repaint();
    }
}
