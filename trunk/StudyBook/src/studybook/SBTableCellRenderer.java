/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studybook;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 *
 * @author Andre Müller & Alexander Keller
 * @version 1.0
 * @since 2012-06-20
 */
class SBTableCellRenderer extends DefaultTableCellRenderer  {

    private JTextArea textArea;

    public SBTableCellRenderer() {
        textArea = new JTextArea();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setText(value.toString());
        return textArea;
    }
}
