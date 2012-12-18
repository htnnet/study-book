package studybook;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * Mit dieser Klasse wird sowohl die Notenübersicht in der Studiengangverwaltung
 * als auch der Stundenplan in der Semesterverwaltung realisiert.
 *
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBTable extends JTable {

    DefaultTableModel tableModel;
    int rowCount;
    int colCount;

    /**
     * Der Konstruktor der Klasse SBTable, der den Konstruktor der Oberklasse
     * aufruft.
     *
     * @param tableModel das überreichte und zu überreichende tableModel
     */
    public SBTable(DefaultTableModel tableModel) {
        super(tableModel);
        this.tableModel = tableModel;
    }

    /**
     * Methode mit der der Stundenplan und die Notenübersicht gefüllt werden
     * kann.
     *
     * @param cells die einzutragenden Werte
     */
    public void populateTable(String[][] cells) {
        rowCount = tableModel.getRowCount();

        for (int i = 0; i < cells.length; i++) {

            // Falls die einzutragenden Daten zu groß sind, neue Tabellenzeile erstellen
            if (i == (rowCount - 1)) {
                this.tableModel.addRow(new String[]{"", "", ""});
            }
            for (int j = 0; j < cells[i].length; j++) {
                this.setValueAt(cells[i][j], i, j);
            }
        }
    }

    /**
     * Methode mit der die Einträge in dem Stundenplan entnommen werden können,
     * sodass sie in die Datenbank eingespeist werden können.
     *
     * @return die eingetragenen Tabellenwerte
     */
    public String[][] getTimeTableValues() {
        rowCount = tableModel.getRowCount();
        colCount = tableModel.getColumnCount();
        String[][] tableData = new String[rowCount * colCount][3];
        String value;
        int index = 0;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                value = (String) tableModel.getValueAt(i, j);
                tableData[index][0] = String.valueOf(i);
                tableData[index][1] = String.valueOf(j);
                tableData[index][2] = value;
                index++;
            }
        }
        return tableData;
    }

    /**
     * Mittels dieser Methode können die Tabellen (Stundenplan, Notenübersicht)
     * geleert werden.
     */
    public void emptyCells() {
        rowCount = tableModel.getRowCount();
        colCount = tableModel.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                this.setValueAt("", i, j);
            }
        }
    }

    /**
     * Dient dazu, um den Tabellen ein besonderes Aussehen zu verpassen.
     *
     * @param renderer der verwendete Renderer
     * @param Index_row die Zeile
     * @param Index_col die Spalte
     * @return die veränderte und nun darzustellende Komponente
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
        Font font;
        Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
        // erste Spalte des Stundenplans (Uhrzeit) grau einfärben
        if (Index_col == 0 && comp instanceof JTextArea) {
            comp.setBackground(new Color(245, 245, 250));
            String fontName = comp.getFont().getFontName();
            font = new Font(fontName, Font.BOLD, 14);
            comp.setFont(font);

            // vorletzte Spalte des Stundenplans (Samstag) bläulich einfärben
        } else if (Index_col == 6) {
            comp.setBackground(new Color(238, 243, 250));

            // letzte Spalte des Stundenplans (Sonntag) bläulich einfärben
            // eingegebener Text wird rot dargestellt
        } else if (Index_col == 7) {
            comp.setBackground(new Color(238, 243, 250));
            comp.setForeground(Color.RED);

            // jede ungerade Zeile der Notenübersicht leicht gräulich einfärben
        } else if (Index_row % 2 == 1 && comp instanceof JLabel) {
            comp.setBackground(new Color(245, 245, 250));

            // erste Zeile der Notenübersicht (Gesamt) fett darstellen
        } else if (Index_row == 0 && comp instanceof JLabel) {
            font = comp.getFont();
            comp.setFont(font.deriveFont(Font.BOLD));
        } else {
            comp.setBackground(Color.white);
        }
        return comp;
    }
}
