package com.yltrcc.novel.utils;

import com.yltrcc.novel.custom.StyledComboBox;
import com.yltrcc.novel.entity.HintEntity;
import com.yltrcc.novel.test.Trie;

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

    private static Trie trie1 = null;

    private static String s = null;

    private static final Integer MAX_SHOW = 20;

    public static void setup(final JTextArea txtInput,
                             final ArrayList<String> items) throws BadLocationException {
        final DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(items.toArray());
        final StyledComboBox<Object> cbInput = new StyledComboBox<Object>(model);
        setAdjusting(cbInput, false);
        Trie trie = new Trie();
        for (String s : items) {
            trie.add(s);
        }
        cbInput.setSelectedItem(null);
        cbInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAdjusting(cbInput)) {
                    if (cbInput.getSelectedItem() != null) {
                        String text = txtInput.getText();
                        HintEntity selectedItem = (HintEntity) cbInput.getSelectedItem();
                        txtInput.setText(text.substring(0, text.lastIndexOf(selectedItem.getOriginalStr()))
                                + selectedItem.getReplaceStr());
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
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            //判断是换行还是选择关键字
                            String text = txtInput.getText();
                            if (cbInput.getSelectedItem() != null && isShow) {
                                String elementAt = "";
                                if (cbInput.getSelectedItem() instanceof String) {
                                    elementAt = (String) cbInput.getSelectedItem();
                                }
                                if (cbInput.getSelectedItem() instanceof HintEntity) {
                                    elementAt = (String) cbInput.getSelectedItem().toString();
                                }
                                txtInput.setText(text.substring(0, text.lastIndexOf(s))
                                        + elementAt);
                                cbInput.setPopupVisible(false);
                                isShow = false;
                            } else {
                                //换行
                                txtInput.setText(text + "\r\n");
                            }
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
                try {
                    updateList();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }

            public void removeUpdate(DocumentEvent e) {
                //获取光标位置
                Rectangle r = null;
                try {
                    r = txtInput.modelToView(txtInput.getCaretPosition());
                } catch (BadLocationException badLocationException) {
                    r = new Rectangle();
                }
                rectangle = r;
                cbInput.setBounds(r);
                try {
                    updateList();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            public void changedUpdate(DocumentEvent e) {
                cbInput.setPopupVisible(false);
                isShow = false;
            }

            private void updateList() throws IOException {
                setAdjusting(cbInput, true);
                model.removeAllElements();
                String input = txtInput.getText();

                if (!input.isEmpty()) {
                    //第一种智能判断
                    s = TextUtils.getText(input);
                    if (s.equals("")) {
                        cbInput.setPopupVisible(false);
                        isShow = false;
                        setAdjusting(cbInput, false);
                        return;
                    }
                    //s的长度为 1 的时候添加到字典树中
                    if (s.length() <= 3) {
                        List<String> wordsByPrefix = trie.getWordsByPrefix(s);
                        int count = 0;
                        TextUtils.setIsActive(false);
                        if (trie1 == null) {
                            trie1 = new Trie();
                        }
                        if (trie1.size() > 0) {
                            for (String str : trie1.getWordsByPrefix(s)) {
                                if (count < MAX_SHOW) {
                                    HintEntity hint = new HintEntity();
                                    hint.setOriginalStr(s);
                                    hint.setReplaceStr(str);
                                    model.addElement(hint);
                                    TextUtils.setIsActive(true);
                                    count++;
                                } else {
                                    break;
                                }
                            }
                        }
                        if (wordsByPrefix.size() > 0) {
                            //两步 先展示 然后异步构建字典树
                            if (count == 0) {
                                List<String> strings = FileUtils.readFile("E:\\BaiduSyncdisk\\小说\\前缀测试\\" + wordsByPrefix.get(0) + ".txt");

                                for (String str : strings) {
                                    if (count < MAX_SHOW) {
                                        HintEntity hint = new HintEntity();
                                        hint.setOriginalStr(s);
                                        hint.setReplaceStr(str);
                                        model.addElement(hint);
                                        TextUtils.setIsActive(true);
                                        count++;
                                    } else {
                                        break;
                                    }
                                }
                            }
                            //异步构建字典树
                            Thread t = new Thread(){
                                @Override
                                public void run() {
                                    for (String item : wordsByPrefix) {
                                        List<String> strings = new ArrayList<>();
                                        try {
                                            strings = FileUtils.readFile("E:\\BaiduSyncdisk\\小说\\前缀测试\\" + item + ".txt");
                                        } catch (IOException e) {
                                            System.out.println(e.getMessage());
                                        }
                                        if (strings.size() > 0) {
                                            for (String str : strings) {
                                                trie1.add(str);
                                            }
                                        }
                                    }
                                }
                            };
                            t.start();
                        }
                    } else {
                        int count = 0;
                        for (String str : trie1.getWordsByPrefix(s)) {
                            if (count < MAX_SHOW) {
                                HintEntity hint = new HintEntity();
                                hint.setOriginalStr(s);
                                hint.setReplaceStr(str);
                                model.addElement(hint);
                                TextUtils.setIsActive(true);
                                count++;
                            } else {
                                break;
                            }
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
