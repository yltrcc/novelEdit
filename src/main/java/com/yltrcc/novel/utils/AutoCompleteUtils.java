package com.yltrcc.novel.utils;

import com.yltrcc.novel.custom.StyledComboBox;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutoCompleteUtils {

    //判断JComboBox 显示还是隐藏
    private static boolean isShow = false;

    private static Rectangle rectangle;

    public static void setup(final JTextArea txtInput,
                             final ArrayList<String> items) throws BadLocationException {
        final DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(items.toArray());
        final DefaultComboBoxModel<Object> modelDetail = new DefaultComboBoxModel<>(items.toArray());
        final StyledComboBox<Object> cbInput = new StyledComboBox<Object>(model);
        setAdjusting(cbInput, false);

        final StyledComboBox<Object> scbDetail = new StyledComboBox<Object>(modelDetail) ;

        cbInput.setSelectedItem(null);
        cbInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAdjusting(cbInput)) {
                    if (cbInput.getSelectedItem() != null) {
                        String text = txtInput.getText();
                        text = TextUtils.joint(text, cbInput.getSelectedItem().toString());
                        txtInput.setText(text);
                    }
                }
            }
        });
        // 添加按键监听器
        txtInput.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                setAdjusting(cbInput, true);
                //空格
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (cbInput.isPopupVisible()) {
                        e.setKeyCode(KeyEvent.VK_ENTER);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER
                        || e.getKeyCode() == KeyEvent.VK_UP
                        || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (cbInput.isPopupVisible()) {
                        e.setSource(cbInput);
                        cbInput.dispatchEvent(e);
                        scbDetail.setPopupVisible(false);
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            //判断是换行还是选择关键字
                            String text = txtInput.getText();
                            if (cbInput.getSelectedItem() != null && isShow) {

                                text = TextUtils.joint(text, cbInput.getSelectedItem().toString());
                                txtInput.setText(text);
                                cbInput.setPopupVisible(false);
                                isShow = false;
                            } else {
                                //换行
                                txtInput.setText(text + "\r\n");
                            }

                        }
                    }
                    if (scbDetail.isPopupVisible()) {
                        e.setSource(scbDetail);
                        scbDetail.dispatchEvent(e);
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            //判断是换行还是选择关键字
                            String text = txtInput.getText();
                            if (scbDetail.getSelectedItem() != null) {
                                text = TextUtils.joint(text, scbDetail.getSelectedItem().toString());
                                txtInput.setText(text);
                                scbDetail.setPopupVisible(false);
                            } else {
                                //换行
                                txtInput.setText(text + "\r\n");
                            }

                        }
                    }
                }
                //左右键
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    e.setSource(cbInput);
                    cbInput.dispatchEvent(e);
                    cbInput.setPopupVisible(true);
                    scbDetail.setPopupVisible(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (cbInput.isPopupVisible()) {
                        e.setSource(scbDetail);
                        scbDetail.dispatchEvent(e);
                        modelDetail.removeAllElements();
                        //读取数据
                        List<String> strings = null;
                        try {
                            strings = FileUtils.readFile("E:\\BaiduSyncdisk\\小说\\测试\\" + cbInput.getSelectedItem() + ".txt");
                        } catch (IOException exception) {
                            System.out.println(exception.getMessage());
                        }
                        //TODO 还可以优化 前缀 可以采用字段树进行优化
                        if (strings != null && !strings.isEmpty()) {
                            for (String s : strings) {
                                modelDetail.addElement(s);
                            }

                            int width = 1;
                            for (int i = 0; i < model.getSize(); i++) {
                                String elementAt = (String) model.getElementAt(i);
                                width = Math.max(elementAt.length(), width);
                            }
                            rectangle.x += width * 16;
                            scbDetail.setBounds(rectangle);
                            scbDetail.setFocusable(true);
                            scbDetail.setPopupVisible(true);
                        }
                    }

                }
                //ESC
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cbInput.setPopupVisible(false);
                    isShow = false;
                }
                setAdjusting(cbInput, false);
            }
        });
        // 添加文本框内的 文本改变 监听器
        txtInput.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                //获取光标位置
                Rectangle r = null;
                try {
                    r = txtInput.modelToView(txtInput.getCaretPosition());
                } catch (BadLocationException badLocationException) {
                    r = new Rectangle();
                }
                rectangle = r;
                cbInput.setBounds(r);
                updateList();

            }
            public void removeUpdate(DocumentEvent e) {
            }
            public void changedUpdate(DocumentEvent e) {
            }

            private void updateList() {
                setAdjusting(cbInput, true);
                model.removeAllElements();
                String input = txtInput.getText();

                if (!input.isEmpty()) {
                    for (String item : items) {
                        //第一种智能判断
                        String s = TextUtils.getText(input);
                        TextUtils.setIsActive(false);
                        if (s.equals("")) {
                            break;
                        }
                        if (item.toLowerCase().startsWith(s.toLowerCase()) && !item.equalsIgnoreCase(s)) {
                            model.addElement(item);
                            TextUtils.setIsActive(true);
                        }
                        //第二种最近一位判断。
                        String s1 = input.substring(input.length() - 1);
                        if (!s.equals(s1) && item.toLowerCase().startsWith(s1.toLowerCase()) && !item.equalsIgnoreCase(s1)) {
                            model.addElement(item);
                        }
                    }
                }
                if (model.getSize() > 0) {
                    cbInput.setPopupVisible(true);

                    isShow = true;
                } else {
                    cbInput.setPopupVisible(false);
                    isShow = false;
                }

                setAdjusting(cbInput, false);
            }
        });
        txtInput.setLayout(new BorderLayout());
        txtInput.add(cbInput, BorderLayout.SOUTH);
        txtInput.add(scbDetail, BorderLayout.SOUTH);
    }

    private static void setAdjusting(JComboBox<Object> cbInput, boolean adjusting) {
        cbInput.putClientProperty("is_adjusting", adjusting);
    }

    private static boolean isAdjusting(JComboBox<Object> cbInput) {
        if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
            return (Boolean) cbInput.getClientProperty("is_adjusting");
        }
        return false;
    }

}
