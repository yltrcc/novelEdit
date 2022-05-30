package com.yltrcc.novel.custom;

import javax.swing.*;

public class StyledComboBox<E> extends JComboBox<E> {
  public StyledComboBox(ComboBoxModel<E> aModel) {
    setUI(new StyledComboBoxUI<>(this));
    setModel(aModel);
  }
}