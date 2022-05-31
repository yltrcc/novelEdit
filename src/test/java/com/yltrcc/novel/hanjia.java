package com.yltrcc.novel;
//由JComboBox组成组合框级联，两种方法都行添加

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class hanjia extends JFrame {
    private JPanel p;
    private JLabel lblProvince, lblCity;
    private JComboBox cmbProvince, cmbCity;

    public hanjia() {
        super("组合框联动");
        p = new JPanel();
        lblProvince = new JLabel("省份");//标签
        lblCity = new JLabel("城市"); //标签
        // 创建组合框，并使用字符串数组初始化其选项列表，创建指定的数组元素
        cmbProvince = new JComboBox(new String[]{"广东", "江苏", "浙江"});
        // 创建一个没有选项的组合框
        cmbCity = new JComboBox();
        cmbCity.addItem("广州"); //addItem（）：添加新的选项内容
        cmbCity.addItem("深圳");
        cmbCity.addItem("珠海");
        cmbCity.addItem("南京");
        cmbCity.addItem("苏州");
        cmbCity.addItem("无锡");
        cmbCity.addItem("杭州");
        cmbCity.addItem("宁波");
        cmbCity.addItem("义乌");

        cmbCity.removeItem("义乌");//移除该项
        //cmbCity.removeAllItems();  移除所有项
        p.add(lblProvince);
        p.add(cmbProvince);
        p.add(lblCity);
        p.add(cmbCity);

        // 将面板添加到窗体中
        this.add(p);

        // 设置窗口大小
        this.setSize(300, 200);
        // 设置窗口左上角坐标
        this.setLocation(200, 100);
        // 设置窗口默认关闭方式为退出应用程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口可视（显示）
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new hanjia();
    }
}

