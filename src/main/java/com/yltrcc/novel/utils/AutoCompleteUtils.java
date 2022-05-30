package com.yltrcc.novel.utils;

import com.yltrcc.novel.custom.StyledComboBox;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class AutoCompleteUtils {

    //判断JComboBox 显示还是隐藏
    private static boolean isShow = false;

    public static void setup(final JTextArea txtInput,
                                         final ArrayList<String> items) throws BadLocationException {
        final DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(items.toArray());
        final StyledComboBox<Object> cbInput = new StyledComboBox<Object>(model) {
            public Dimension getPreferredSize() {
                return new Dimension(0, 0);
            }
        };
        setAdjusting(cbInput, false);


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
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (cbInput.isPopupVisible()) {
                        e.setKeyCode(KeyEvent.VK_ENTER);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER
                        || e.getKeyCode() == KeyEvent.VK_UP
                        || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    e.setSource(cbInput);
                    cbInput.dispatchEvent(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        //判断是换行还是选择关键字
                        String text = txtInput.getText();
                        if (cbInput.getSelectedItem() != null && isShow) {

                            text = TextUtils.joint(text, cbInput.getSelectedItem().toString());
                            txtInput.setText(text);
                            cbInput.setPopupVisible(false);
                            isShow = false;
                        }else {
                            //换行
                            txtInput.setText(text + "\r\n");
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
                r.width = 400;
                cbInput.setBounds(r);
                Point location = cbInput.getLocation();

                System.out.println("光标位置：" +
                        r.getX() + ","  + r.getY());
                System.out.println("位置坐标：" +
                        location.getX() + ","  + location.getY());
                updateList();

            }


            public void removeUpdate(DocumentEvent e) {
                //获取光标位置
                Rectangle r = null;
                try {
                    r = txtInput.modelToView(txtInput.getCaretPosition());
                } catch (BadLocationException badLocationException) {
                    r = new Rectangle();
                }
                cbInput.setBounds((int) (r.getX() + 12), (int) r.getY(), 40, 30);
                updateList();
            }

            public void changedUpdate(DocumentEvent e) {
                //获取光标位置
                Rectangle r = null;
                try {
                    r = txtInput.modelToView(txtInput.getCaretPosition());
                } catch (BadLocationException badLocationException) {
                    r = new Rectangle();
                }
                cbInput.setBounds((int) (r.getX() + 12), (int) r.getY(), 40, 30);
                updateList();
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
                        String s1 = input.substring(input.length()-1);
                        if (!s.equals(s1) && item.toLowerCase().startsWith(s1.toLowerCase()) && !item.equalsIgnoreCase(s1)) {
                            model.addElement(item);
                        }
                    }
                }
                if (model.getSize() > 0) {
                    cbInput.setPopupVisible(true);

                    isShow = true;
                }else {
                    cbInput.setPopupVisible(false);
                    isShow = false;
                }

                setAdjusting(cbInput, false);
            }
        });
        txtInput.setLayout(new BorderLayout());
        txtInput.add(cbInput, BorderLayout.SOUTH);
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
