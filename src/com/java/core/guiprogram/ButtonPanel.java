package com.java.core.guiprogram;

import javax.swing.*;

/**
 * @author Mr.X
 * @version 1.0.0
 * 构建按钮组
 * @since 2018/11/30 14:33
 */
public class ButtonPanel extends JPanel {
    private ButtonGroup group;

    /**
     * 使用传入的 <code>title,options</code>构造按钮组，将每个option都构造为一个按钮
     *
     * @param title   按钮组标题
     * @param options 按钮组子项
     */
    public ButtonPanel(String title, String... options) {
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        group = new ButtonGroup();
        for (String option : options) {
            JRadioButton b = new JRadioButton(option);
            b.setActionCommand(option);
            this.add(b);
            this.group.add(b);
            b.setSelected(option == options[0]);
        }
    }

    /**
     * 返回已选按钮组中子项的值
     *
     * @return
     */
    public String getSelection() {
        return group.getSelection().getActionCommand();
    }
}
