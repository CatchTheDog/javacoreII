package com.java.core.guiprogram;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class SliderFrame extends JFrame {
    private JPanel sliderPanel;
    private JTextField textField;
    private ChangeListener listener;

    public SliderFrame() {
        sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout());

        listener = event -> {
            JSlider source = (JSlider) event.getSource();
            textField.setText("" + source.getValue());
        };

        JSlider slider = new JSlider();
        addSlider(slider, "Plain");

        slider = new JSlider();
        slider.setPaintTicks(true); //显示刻度
        slider.setMajorTickSpacing(20); //每20显示一个大刻度
        slider.setMinorTickSpacing(5); //每5显示一个小刻度
        addSlider(slider, "Ticks");

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true); //是否在调整滑块时自动黏着到最近的刻度
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "Snap to ticks");

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTrack(false); //隐藏滑块轴
        addSlider(slider, "No track");

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true); //显示刻度标签
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTrack(false);
        addSlider(slider, "Labels");

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setInverted(true); //滑块逆向
        addSlider(slider, "Inverted");

        slider = new JSlider(SwingConstants.VERTICAL, 0, 100, 0); //构造竖向滑块
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "Vertical");


        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true); //显示刻度标签
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);

        //为滑块刻度添加自定义标签
        Dictionary<Integer, Component> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("A"));
        labelTable.put(20, new JLabel("B"));
        labelTable.put(40, new JLabel("C"));
        labelTable.put(60, new JLabel("D"));
        labelTable.put(80, new JLabel("E"));
        labelTable.put(100, new JLabel("F"));

        slider.setLabelTable(labelTable);

        addSlider(slider, "Custom labels");

        textField = new JTextField();
        this.add(sliderPanel, BorderLayout.CENTER);
        this.add(textField, BorderLayout.SOUTH);
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SliderFrame frame = new SliderFrame();
            frame.setTitle("SliderFrame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public void addSlider(JSlider s, String description) {
        s.addChangeListener(listener);
        JPanel panel = new JPanel();
        panel.add(s);
        panel.add(new JLabel(description));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = sliderPanel.getComponentCount();
        gbc.anchor = GridBagConstraints.WEST;
        sliderPanel.add(panel, gbc);
    }
}
