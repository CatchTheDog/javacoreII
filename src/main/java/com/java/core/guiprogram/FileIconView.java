package com.java.core.guiprogram;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.io.File;

/**
 * @author Mr.X
 * @version 1.0.0
 * 为满足过滤器条件的文件设置显示图标
 * @since 2018/12/3 16:28
 */
public class FileIconView extends FileView {
    private FileFilter fileFilter;
    private Icon icon;

    public FileIconView(FileFilter aFilter, Icon icon) {
        this.fileFilter = aFilter;
        this.icon = icon;
    }

    @Override
    public Icon getIcon(File f) {
        if (!f.isDirectory() && fileFilter.accept(f)) return icon;
        else return null;
    }
}
