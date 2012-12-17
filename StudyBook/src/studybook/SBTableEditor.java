/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studybook;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.AbstractCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Alex
 */
public class SBTableEditor extends AbstractCellEditor implements TableCellEditor {

    private JTextArea textArea;
    private SBFieldDocument cellDocument;
    private SBFieldDocument timeDocument;
    private JScrollPane scrollPane;
    private Font font;

    public SBTableEditor() {
        cellDocument = new SBFieldDocument(100);
        timeDocument = new SBFieldDocument(50);
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);

    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (column == 0) {
            textArea.setDocument(timeDocument);
            String fontName = textArea.getFont().getFontName();
            font = new Font(fontName, Font.BOLD, 14);
            textArea.setFont(font);
        } else if (column == 7) {
            textArea.setDocument(cellDocument);
            textArea.setForeground(Color.red);
        } else {
            textArea.setDocument(cellDocument);
        }

        textArea.setText(value.toString());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        return scrollPane;
    }

    public Object getCellEditorValue() {
        return textArea.getText();
    }
}
