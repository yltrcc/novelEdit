package com.yltrcc.novel.constant;

import javax.swing.*;

public class UIConstant {

    /*窗体菜单大致由菜单栏、菜单和菜单项三部分组成，对于一个窗体，首先要添加一个JMenuBar,然后在JMenu中添加JMenultem*/
    /*菜单栏*/
    public static JMenu file = new JMenu("文件");
    public static JMenu edit = new JMenu("编辑");
    public static JMenu format = new JMenu("格式");
    public static JMenu view = new JMenu("查看");
    public static JMenu help = new JMenu("帮助");

    /*菜单项*/
    public static JMenuItem[] menuItem = {new JMenuItem("新建"), new JMenuItem("打开"),
            new JMenuItem("保存"), new JMenuItem("退出"),
            new JMenuItem("全选"), new JMenuItem("复制"),
            new JMenuItem("剪切"), new JMenuItem("粘贴"),
            new JMenuItem("查找"), new JMenuItem("替换"),
            new JMenuItem("自动换行"), new JMenuItem("字体"),
            new JMenuItem("普通"), new JMenuItem("粗体"),
            new JMenuItem("斜体"), new JMenuItem("字号"),
            new JMenuItem("12"), new JMenuItem("24"),
            new JMenuItem("36"), new JMenuItem("颜色"),
            new JMenuItem("背景"), new JMenuItem("状态栏"),
            new JMenuItem("关于")
    };
}
