package studybook;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Alex
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
        for (int i = 0; i < cells.length; i++) {
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

    private void colorizeOddRows() {
    }
}
