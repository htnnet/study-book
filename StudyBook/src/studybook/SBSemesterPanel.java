package studybook;

import java.awt.BorderLayout;
import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        //timeTableModel = new DefaultTableModel(columns, 20);


        timeTableModel = new DefaultTableModel(columns, 10) {
            public Class getColumnClass(int columnIndex) {
                return String.class;
            }
        };

        timeTable = new SBTable(timeTableModel);
        // alle Zellen des Stundenplans quadratisch darstellen
        timeTable.setRowHeight(50);
        for (int i = 0; i < timeTable.getColumnCount(); i++) {
            TableColumn column = timeTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(50);
        }
        timeTable.setDefaultRenderer(String.class, new SBTableRenderer());
        timeTable.getTableHeader().setReorderingAllowed(false);
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
