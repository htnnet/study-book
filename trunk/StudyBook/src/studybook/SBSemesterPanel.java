package studybook;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Hierin befindet sich das Panel für die Semesterverwaltung.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBSemesterPanel extends JPanel {

    private Border margin;
    private Border timeTitledBorder;
    private Border timeCompBorder;
    private JScrollPane timeScrollPane;
    private DefaultTableModel timeTableModel;
    private SBTable timeTable;

    public SBSemesterPanel() {
        this.createSemesterPanel();
        this.layoutSemesterPanel();
    }

    /**
     * Hier wird das Panel erstellt, über das der Benutzer seinen Semester-
     * stundenplan einsehen und verwalten kann.
     */
    private void createSemesterPanel() {
        String[] columns = {"Uhrzeit", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};

        timeTableModel = new DefaultTableModel(columns, 10);
        timeTable = new SBTable(timeTableModel);

        // alle Zellen des Stundenplans quadratisch darstellen und Renderer und Editor setzen
        timeTable.setRowHeight(100);
        for (int i = 0; i < timeTable.getColumnCount(); i++) {
            TableColumn column = timeTable.getColumnModel().getColumn(i);
            column.setCellRenderer(new SBTableCellRenderer());
            column.setCellEditor(new SBTableEditor());
        }

        // Table Header
        JTableHeader timeTableHeader = timeTable.getTableHeader();
        timeTableHeader.setReorderingAllowed(false);
        timeTableHeader.setResizingAllowed(false);
        TableCellRenderer rendererFromHeader = timeTable.getTableHeader().getDefaultRenderer();
	JLabel headerLabel = (JLabel) rendererFromHeader;
	headerLabel.setHorizontalAlignment(JLabel.CENTER);

        String fontName = timeTableHeader.getFont().getName();
        Font font = new Font(fontName, Font.BOLD, 11);
        timeTableHeader.setFont(font);


        timeScrollPane = new JScrollPane(timeTable);
        margin = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        timeTitledBorder = BorderFactory.createTitledBorder("Stundenplan");
        timeCompBorder = BorderFactory.createCompoundBorder(timeTitledBorder, margin);

        this.setBorder(timeCompBorder);
    }

    /**
     * Der Semesterstundenplan in Form in einer JTable wird an seinen rechten
     * Platz verpflanzt.
     */
    private void layoutSemesterPanel() {
        this.setLayout(new BorderLayout());
        this.add(timeScrollPane);
    }

    /**
     * Gibt den Stundenplan in Form einer JTable zurück.
     *
     * @return der Stundenplan
     */
    public SBTable getTimeTable() {
        return this.timeTable;
    }
}
