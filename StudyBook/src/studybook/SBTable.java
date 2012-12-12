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
    public SBTable(DefaultTableModel tableModel) {
        super(tableModel);
    }

    public void populateTimeTable(String[][] values) {

    }

    public void populateGradeTable(String[][] values) {

    }

    public String[][] getTimeTableValues() {
        return null;
    }

    private void colorizeOddRows() {

    }
}
