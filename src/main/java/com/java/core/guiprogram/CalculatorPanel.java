package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorPanel extends JPanel {
    private JButton display; //显示输入输出
    private JPanel panel;
    private double result;  //计算结果展示
    private String lastCommand;
    private boolean start;  //true：前一次输入不是数字（或者无输入）  fasle:前一次输入是数字

    public CalculatorPanel() {
        //使用边框布局管理器
        this.setLayout(new BorderLayout());
        result = 0;
        lastCommand = "=";
        start = true;

        display = new JButton("0");
        display.setEnabled(false);
        this.add(display, BorderLayout.NORTH);

        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("/", command);

        addButton("4", insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("*", command);

        addButton("1", insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("-", command);

        addButton("0", insert);
        addButton(".", insert);
        addButton("=", command);
        addButton("+", command);

        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            CalculatorPanel calculatorPanel = new CalculatorPanel();
            frame.add(calculatorPanel);
            frame.setTitle("Calculator");
            frame.setSize(new Dimension(200, 200));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    /**
     * 为按钮绑定监听器并将其添加到面板
     *
     * @param label
     * @param listener
     */
    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        panel.add(button);
    }

    /**
     * 计算并显示运算结果
     *
     * @param x
     */
    public void calculate(double x) {
        if (lastCommand.equals("+")) result += x;
        else if (lastCommand.equals("-")) result -= x;
        else if (lastCommand.equals("*")) result *= x;
        else if (lastCommand.equals("/")) result /= x;
        else if (lastCommand.equals("=")) result = x;
        display.setText("" + result);
    }

    /**
     * 输入数值
     * 如果前一个输入的是数字，本次继续输入数字，则需要连续展示上次输入和本次输入
     * 如果上一次输入的是运算符或者无输入，则本次仅需展示本次输入数字即可
     */
    private class InsertAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();
            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }
    }

    /**
     * 输入运算符
     * start true：前一次输入不是数字（或者无输入）  fasle:前一次输入是数字
     * 如果前一次输入的是非数字，则“-”为负号,同数字处理
     */
    private class CommandAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (start) {
                if (command.equals("-")) {
                    display.setText(command);
                    start = false;
                } else
                    lastCommand = command;
            } else {
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start = true;
            }
        }
    }
}
