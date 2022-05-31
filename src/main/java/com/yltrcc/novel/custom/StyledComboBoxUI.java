package com.yltrcc.novel.custom;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

class StyledComboBoxUI<E> extends BasicComboBoxUI {
  private final JComboBox<E> comboBox;

  public StyledComboBoxUI(JComboBox<E> comboBox) {
    this.comboBox = comboBox;
  }

  protected ComboPopup createPopup() {
    BasicComboPopup popup = new BasicComboPopup(this.comboBox) {
      @Override
      protected Rectangle computePopupBounds(int px, int py, int pw, int ph) {
        //根据结果结算高度。
        ComboBoxModel model = comboBox.getModel();
        int size = Math.min(model.getSize(), 16);
        int width = 1;
        for (int i=0; i<size; i++) {
          String elementAt = (String) model.getElementAt(i);
          width = Math.max(elementAt.length(), width);
        }
        return super.computePopupBounds(
            px,py,(width+1) * 16,size * 18
        );
      }
    };
    popup.getAccessibleContext().setAccessibleParent(this.comboBox);
    return popup;
  }


}