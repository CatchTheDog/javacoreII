package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * 模式对话框：在结束对对话框的处理前，不允许用户与应用程序的其余窗口进行交互。
 * 无模式对话框：在结束对对话框的处理前，允许用户与应用程序的其余窗口交互。
 * JOptionPanel:
 * showMessageDialog:显示一条消息并等待用户点击ok
 * showConfirmDialog:显示一条小时并等待用户确认（ok/cancel）
 * showOptionDialog:显示一条消息并获得yoghurt在一组选项中的选择
 * showInputDialog:显示一条消息并获得用户输入的一行文本
 */
public class OptionDialogFrame extends JFrame {
    private ButtonPanel typePanel;
    private ButtonPanel messagePanel;
    private ButtonPanel messageTypePanel;
    private ButtonPanel optionTypePanel;
    private ButtonPanel optionsPanel;
    private ButtonPanel inputPanel;

    private String messageString = "Message";
    private Icon messageIcon = new ImageIcon("blue-ball.gif");
    private Object messageObject = new Date();
    private Component messageComponent = new SampleComponent();

    public OptionDialogFrame() {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2, 3));
        typePanel = new ButtonPanel("Type", "Message", "Confirm", "Option", "Input");
        messageTypePanel = new ButtonPanel("Message Type", "ERROR_MESSAGE", "INFORMATION_MESSAGE", "WARNING_MESSAGE", "QUESTION_MESSAGE", "PLAIN_MESSAGE");
        messagePanel = new ButtonPanel("Message", "String", "Icon", "Component", "Other", "Object[]");
        optionTypePanel = new ButtonPanel("Confirm", "DEFAULT_OPTION", "YES_NO_OPTION", "YES_NO_CANCEL_OPTION", "OK_CANCEL_OPTION");
        optionsPanel = new ButtonPanel("Option", "String[]", "Icon[]", "Object");
        inputPanel = new ButtonPanel("Input", "Text field", "Combo box");

        gridPanel.add(typePanel);
        gridPanel.add(messageTypePanel);
        gridPanel.add(messagePanel);
        gridPanel.add(optionTypePanel);
        gridPanel.add(optionsPanel);
        gridPanel.add(inputPanel);

        JPanel showPanel = new JPanel();
        JButton showButton = new JButton("Show");
        showButton.addActionListener(new ShowAction());
        showPanel.add(showButton);

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(showPanel, BorderLayout.SOUTH);
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            OptionDialogFrame frame = new OptionDialogFrame();
            frame.setTitle("OptionDialogFrame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public Object getMessage() {
        String s = messagePanel.getSelection();
        if (s.equals("String")) return messageString;
        else if (s.equals("Icon")) return messageIcon;
        else if (s.equals("Component")) return messageComponent;
        else if (s.equals("Object[]")) return new Object[]{messageString, messageIcon, messageComponent, messageObject};
        else if (s.equals("Other")) return messageObject;
        else return null;
    }

    public Object[] getOptions() {
        String s = optionsPanel.getSelection();
        if (s.equals("String[]")) return new String[]{"Yellow", "Blue", "Red"};
        else if (s.equals("Icon[]"))
            return new Icon[]{new ImageIcon("yellow-ball.gif"), new ImageIcon("blue-ball.gif"), new ImageIcon("red-ball.gif")};
        else if (s.equals("Object[]")) return new Object[]{messageString, messageIcon, messageComponent, messageObject};
        else return null;

    }

    public int getType(ButtonPanel panel) {
        String s = panel.getSelection();
        try {
            return JOptionPane.class.getField(s).getInt(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return -1;
        }
    }

    /**
     * showMessageDialog(Component parent,Object message,String title,int messageType,Icon icon)
     * parent:父组件，可以为null
     * message:显示在对话框中的消息，可以使字符串、图标、组件或者一个这些类型的数组
     * title:对话框标题栏中的字符串——对话框标题
     * messageType:取值为ERROR_MESSAGE,INFORMATION_MESSAGE,WARNING_MESSAGE,QUESTION_MESSAGE,PLAIN_MESSAGE之一
     * icon:用于替代标准图标的图标
     * showConfirmDialog(Component parent, Object message,String title, int optionType, int messageType, Icon Icon)
     * optionType 取值为 DEFAULT_OPTION、YES_NO_0PTI0N、 YES_NO_CANCEL_OPTION、 OK_CANCEL_OPTION 之一
     */
    public class ShowAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (typePanel.getSelection().equals("Confirm"))
                JOptionPane.showConfirmDialog(OptionDialogFrame.this, getMessage(), "Titile", getType(optionTypePanel), getType(messageTypePanel));
            else if (typePanel.getSelection().equals("Input")) {
                if (inputPanel.getSelection().equals("Test field"))
                    JOptionPane.showInputDialog(OptionDialogFrame.this, getMessage(), "Title", getType(messageTypePanel));
                else
                    JOptionPane.showInputDialog(OptionDialogFrame.this, getMessage(), "Title", getType(messageTypePanel), null, new String[]{"Yellow", "Blue", "Red"}, "Blue");
            } else if (typePanel.getSelection().equals("Message"))
                JOptionPane.showMessageDialog(OptionDialogFrame.this, getMessage(), "Title", getType(messageTypePanel));
            else if (typePanel.getSelection().equals("Option"))
                JOptionPane.showOptionDialog(OptionDialogFrame.this, getMessage(), "Title", getType(optionTypePanel), getType(messageTypePanel), null, getOptions(), getOptions()[0]);
        }
    }
}
