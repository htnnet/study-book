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
 * Mit dieser Klasse kann man festlegen, wie einzelne Tabellenzellen auszusehen
 * haben, während man sie editiert.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBTableEditor extends AbstractCellEditor implements TableCellEditor {

    private JTextArea textArea;
    private SBFieldDocument cellDocument;
    private SBFieldDocument timeDocument;
    private JScrollPane scrollPane;
    private Font font;

    /**
     * Konstruktor, der die darzustellenden Zellenkomponenten initialisert.
     */
    public SBTableEditor() {
        cellDocument = new SBFieldDocument(100);
        timeDocument = new SBFieldDocument(50);
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);

    }

    /**
     * Überschriebene Methode von TableCellEditor mit der JTextAreas in Zellen
     * eingefügt werden können, wenn sie editiert werden.
     *
     * @param table die Tabelle
     * @param value der Wert
     * @param isSelected true-> die Zelle ist ausgewählt, sonst nicht
     * @param hasFocus true-> Zelle hat den Fokus
     * @param row Zeile der Zelle
     * @param column Spalte der Zelle
     * @return die neue darzustellende Zellkomponente (JTextArea)
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // der Text der ersten Spalte des Stundenplans (Uhrzeit) soll beim Editieren
        // fett angezeigt werden
        if (column == 0) {
            textArea.setDocument(timeDocument);
            String fontName = textArea.getFont().getFontName();
            font = new Font(fontName, Font.BOLD, 14);
            textArea.setFont(font);
            // der Text der letzten Spalte des Stundenplans (Sonntag) soll beim Editieren
            // rot angezeigt werden
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

    /**
     * Mit dieser überschriebenen Methode gewährleisten wir, dass wir den Wert
     * der JTextArea zurückgeben.
     *
     * @return der Text der JTextArea
     */
    @Override
    public Object getCellEditorValue() {
        return textArea.getText();
    }
}
