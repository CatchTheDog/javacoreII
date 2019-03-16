package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ToolBarFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private JPanel panel;

    public ToolBarFrame() {
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        panel = new JPanel();
        this.add(panel, BorderLayout.CENTER);

        Action blueAction = new ColorAction("Blue", new ImageIcon("blue-ball.gif"), Color.BLUE);
        Action yellowAction = new ColorAction("Yellow", new ImageIcon("yellow-ball.gif"), Color.YELLOW);
        Action redAction = new ColorAction("Red", new ImageIcon("red-ball.gif"), Color.RED);
        Action exitAction = new AbstractAction("Exit", new ImageIcon("exit.gif")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit");

        JToolBar bar = new JToolBar();
        bar.add(blueAction);
        bar.add(yellowAction);
        bar.add(redAction);
        bar.add(exitAction);
        this.add(bar, BorderLayout.NORTH);

        JMenu menu = new JMenu("Color");
        menu.add(yellowAction);
        menu.add(blueAction);
        menu.add(redAction);
        menu.add(exitAction);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ToolBarFrame toolBarFrame = new ToolBarFrame();
            toolBarFrame.setTitle("ToolBarFrame");
            toolBarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            toolBarFrame.setVisible(true);
        });
    }

    class ColorAction extends AbstractAction {

        public ColorAction(String name, Icon icon, Color c) {
            this.putValue(Action.NAME, name);
            this.putValue(Action.SMALL_ICON, icon);
            this.putValue(Action.SHORT_DESCRIPTION, name + "background");
            this.putValue("Color", c);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = (Color) this.getValue("Color");
            panel.setBackground(c);
        }
    }
}
