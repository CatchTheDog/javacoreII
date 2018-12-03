package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;

public class PasswordChooser extends JPanel {
    private JTextField username;
    private JPasswordField password;
    private JButton okButton;
    private boolean ok;
    private JDialog dialog;

    public PasswordChooser() {
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("UserName:"));
        panel.add(username = new JTextField(""));
        panel.add(new JLabel("Password:"));
        panel.add(password = new JPasswordField(""));
        this.add(panel, BorderLayout.CENTER);

        okButton = new JButton("Ok");
        okButton.addActionListener(event -> {
            ok = true;
            dialog.setVisible(false);
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> dialog.setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * 交换数据
     *
     * @return
     */
    public User getUser() {
        return new User(username.getText(), password.getPassword());
    }

    /**
     * 交换数据
     *
     * @param u
     */
    public void setUser(User u) {
        username.setText(u.getName());
    }

    /**
     * 显示对话框
     *
     * @param parent
     * @param title
     * @return
     */
    public boolean showDialog(Component parent, String title) {
        ok = false;
        Frame owner = null;
        if (parent instanceof Frame) {
            owner = (Frame) parent;
        } else {
            owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
        }

        if (dialog == null || dialog.getOwner() != owner) {
            dialog = new JDialog(owner, true);
            dialog.add(this);
            dialog.getRootPane().setDefaultButton(okButton);
            dialog.pack();
        }

        dialog.setTitle(title);
        dialog.setVisible(true); //模态框，在执行到此处时进行阻塞调用；在用户对弹窗处理（输入提交或者选择提交）后，再向下执行。所以在用户点击ok之后，ok = true,visibale = false.
        return ok;
    }
}
