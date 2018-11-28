package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ActionFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private JPanel buttonPanel;

    public ActionFrame() {
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        buttonPanel = new JPanel();

        //define actions
        Action yellowAction = new ColorAction("Yellow", new ImageIcon(""), Color.YELLOW);
        Action blueAction = new ColorAction("Blue", new ImageIcon(""), Color.BLUE);
        Action redAction = new ColorAction("Red", new ImageIcon(""), Color.RED);

        buttonPanel.add(new JButton(yellowAction));
        buttonPanel.add(new JButton(blueAction));
        buttonPanel.add(new JButton(redAction));

        this.add(buttonPanel);

        //将按键事件与 action 映射
        InputMap imap = buttonPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        imap.put(KeyStroke.getKeyStroke("ctrl Y"), "panel.yellow");
        imap.put(KeyStroke.getKeyStroke("ctrl B"), "panel.blue");
        imap.put(KeyStroke.getKeyStroke("ctrl R"), "panel.red");

        ActionMap amap = buttonPanel.getActionMap();
        amap.put("panel.yellow", yellowAction);
        amap.put("panel.blue", blueAction);
        amap.put("panel.red", redAction);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ActionFrame actionFrame = new ActionFrame();
            actionFrame.setTitle("ActionFrame");
            actionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            actionFrame.setVisible(true);
        });
    }

    public class ColorAction extends AbstractAction {
        public ColorAction(String name, Icon icon, Color color) {
            this.putValue(Action.NAME, name);
            this.putValue(Action.SMALL_ICON, icon);
            this.putValue(Action.SHORT_DESCRIPTION, "Set panel color to " + name.toLowerCase());
            putValue("color", color);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color color = (Color) this.getValue("color");
            buttonPanel.setBackground(color);
        }
    }
}
