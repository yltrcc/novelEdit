package com.yltrcc.novel.custom;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class StyledComboBox<E> extends JComboBox<E> {
    public StyledComboBox(ComboBoxModel<E> aModel) {
        setUI(new StyledComboBoxUI<>(this));
        setModel(aModel);
    }
}