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
 * BESCHREIBUNG!!!!!!!!!!!!!!!!
 *
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBTable extends JTable {

    DefaultTableModel tableModel;

    public SBTable(DefaultTableModel tableModel) {
        super(tableModel);
        this.tableModel = tableModel;
    }

    public void emptyCells() {
        int rowCount = tableModel.getRowCount();
        int colCount = tableModel.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                this.setValueAt("", i, j);
            }
        }
    }

    public void populateTable(String[][] cells) {
        int rowCount = tableModel.getRowCount();

        for (int i = 0; i < cells.length; i++) {

            //
            if (i == (rowCount - 1)) {
                this.tableModel.addRow(new String[]{"", "", ""});
            }
            for (int j = 0; j < cells[i].length; j++) {
                this.setValueAt(cells[i][j], i, j);
            }
        }
    }

    public String[][] getTimeTableValues() {
        int rowCount = tableModel.getRowCount();
        int colCount = tableModel.getColumnCount();
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
     * Zum Einfärben von jeder zweiten Zeile.
     *
     * @param renderer
     * @param Index_row
     * @param Index_col
     * @return
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
        Font font;
        Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
        if (Index_col == 0 && comp instanceof JTextArea) {
            comp.setBackground(new Color(245, 245, 250));
            String fontName = comp.getFont().getFontName();
            font = new Font(fontName, Font.BOLD, 14);
            comp.setFont(font);

        } else if (Index_col == 6) {
            comp.setBackground(new Color(238, 243, 250));

        } else if (Index_col == 7) {
            comp.setBackground(new Color(238, 243, 250));
            comp.setForeground(Color.RED);

        } else if (Index_row % 2 == 1 && comp instanceof JLabel) {
            comp.setBackground(new Color(245, 245, 250));

        } else if (Index_row == 0 && comp instanceof JLabel) {
            font = comp.getFont();
            comp.setFont(font.deriveFont(Font.BOLD));
        } else {
            comp.setBackground(Color.white);
        }
        return comp;
    }
}
