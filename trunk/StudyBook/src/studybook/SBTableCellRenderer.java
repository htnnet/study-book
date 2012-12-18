package studybook;

import java.awt.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Diese Klasse realisiert einen TableCellRenderer, mit dem man die Zellen des
 * Stundenplans mit JTextAreas versehen kann.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
class SBTableCellRenderer extends DefaultTableCellRenderer {

    private JTextArea textArea;

    /**
     * Konstruktor der Klasse SBTableCellRenderer, der eine JTextarea erstellt.
     */
    public SBTableCellRenderer() {
        textArea = new JTextArea();
    }

    /**
     * Überschriebene Methode von DefaultTableCellRenderer mit der JTextAreas in
     * Zellen eingefügt werden können.
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
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setText(value.toString());
        return textArea;
    }
}
