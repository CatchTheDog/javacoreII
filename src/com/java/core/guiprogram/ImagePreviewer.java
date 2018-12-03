package com.java.core.guiprogram;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Mr.X
 * @version 1.0.0
 * 为选择的图片设置预览器
 * @since 2018/12/3
 */
public class ImagePreviewer extends JLabel {
    public ImagePreviewer(JFileChooser chooser) {
        this.setPreferredSize(new Dimension(100, 100));
        this.setBorder(BorderFactory.createEtchedBorder());
        //使用JavaBeans机制，当bean的属性变化时，文件选择器就会通知相关的监听器（通过安装PropertyChangeListener属性来实现）
        chooser.addPropertyChangeListener(event -> {
            if (event.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
                //获取选中的图片文件路径
                File f = (File) event.getNewValue();
                if (f == null) {
                    this.setIcon(null);
                    return;
                }
                ImageIcon icon = new ImageIcon(f.getPath());
                //为图片生成缩略图
                if (icon.getIconWidth() > this.getWidth()) {
                    icon = new ImageIcon(icon.getImage().getScaledInstance(this.getWidth(), -1, Image.SCALE_DEFAULT));
                }
                this.setIcon(icon);
            }
        });
    }
}
