package studybook;

import java.awt.BorderLayout;
import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * Hierin befindet sich das Panel für die Semesterverwaltung.
 */
public class SBSemesterPanel extends JPanel {
    private Border margin;
    private Border timetabletborder;
    private Border timetablecborder;
    private DefaultTableModel timetablemodel;
    private JTable timetable;


    public SBSemesterPanel() {
        this.createSemesterPanel();
        this.layoutSemesterPanel();
    }


    /**
     * Hier wird das Panel erstellt, über das der Benutzer seinen Semester-
     * stundenplan einsehen und verwalten kann.
     */
    private void createSemesterPanel() {
        timetablemodel = new DefaultTableModel(5, 6);
        timetable = new JTable(timetablemodel);


        // alle Zellen des Stundenplans quadratisch darstellen
        timetable.setRowHeight(100);
        for (int i = 0; i < timetable.getColumnCount(); i++) {
            TableColumn column = timetable.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
        }

        margin = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        timetabletborder = BorderFactory.createTitledBorder("Stundenplan");
        timetablecborder = BorderFactory.createCompoundBorder(timetabletborder, margin);

        this.setBorder(timetablecborder);

    }

    /**
     * Der Semesterstundenplan in Form in einer JTable wird an seinen rechten
     * Platz verpflanzt.
     */
    private void layoutSemesterPanel() {
        this.setLayout(new BorderLayout());
        this.add(timetable);
    }
}
