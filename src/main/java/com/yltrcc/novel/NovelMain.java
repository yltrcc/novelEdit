package com.yltrcc.novel;
import com.yltrcc.novel.views.MainNotepadView;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.io.FileNotFoundException;

public class NovelMain
{
    public static void main(String[] agrs)
    {
        try {
            MainNotepadView mainNotepadView = new MainNotepadView();
            mainNotepadView.setVisible(true);
            //设置最大化
            mainNotepadView.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } catch (Exception e) {
            //TODO 弹出提示
            System.out.println(e.getMessage());
        }
    }
}