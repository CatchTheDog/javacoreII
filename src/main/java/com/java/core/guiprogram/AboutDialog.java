package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    public AboutDialog(JFrame owner) {
        super(owner, "About DialogTest", true);
        String htmlStr =
                "<html>" +
                        "<h1>" +
                        "<i>" +
                        "Core Java" +
                        "</i>" +
                        "</h1>" +
                        "<hr>" +
                        "By Cay Horstmann" +
                        "</htm>";
        this.add(new JLabel(htmlStr), BorderLayout.CENTER);
        JButton ok = new JButton("OK");
        ok.addActionListener(event -> setVisible(false));
        JPanel panel = new JPanel();
        panel.add(ok);
        this.add(panel, BorderLayout.SOUTH);
        this.pack();
    }
}
