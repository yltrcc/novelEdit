package com.yltrcc.novel;

import javax.swing.*;

public class StyledComboBox extends JComboBox {
  public StyledComboBox() {
    setUI(new StyledComboBoxUI());
  }
}