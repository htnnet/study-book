package studybook;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 * Enthält das Panel für die Studiengangverwaltung.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBStudyPanel extends JPanel {

    private SBTable gradeTable;
    private DefaultTableModel tableModel;
    private JPanel northPanel;
    private JPanel studentPanel;
    private JPanel studentLabelPanel;
    private JPanel studentFieldPanel;
    private JPanel studyPanel;
    private JPanel studyLabelPanel;
    private JPanel studyFieldPanel;
    private JPanel progressPanel;
    private Border studentCompBorder;
    private Border studentTitledBorder;
    private Border studyCompBorder;
    private Border studyTitledBorder;
    private Border progressCompBorder;
    private Border progressTitledBorder;
    private Border margin;
    private JLabel studentNameLabel;
    private JLabel studentBirthLabel;
    private JLabel studentMatLabel;
    private JLabel studyNameLabel;
    private JLabel studyAcadLabel;
    private JLabel studyStartLabel;
    private JTextField studentNameField;
    private JTextField studentBirthField;
    private JTextField studentMatField;
    private JTextField studyNameField;
    private JTextField studyAcadField;
    private JTextField studyStartField;

    /**
     * Konstruktor der Klasse SBStudyPanel.
     */
    public SBStudyPanel() {
        this.createStudyPanel();
        this.layoutStudyPanel();
    }

    /**
     * Erstellt das Panel zur Studiengangverwaltung, das dem Benutzer unter
     * anderem einen Einblick in seinen Studienfortschritt gewährt.
     */
    private void createStudyPanel() {
        northPanel = new JPanel();

        margin = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        studentPanel = new JPanel();
        studentTitledBorder = BorderFactory.createTitledBorder("Student");
        studentCompBorder = BorderFactory.createCompoundBorder(margin, studentTitledBorder);
        studentPanel.setBorder(studentCompBorder);

        studyPanel = new JPanel();
        studyTitledBorder = BorderFactory.createTitledBorder("Studiengang");
        studyCompBorder = BorderFactory.createCompoundBorder(margin, studyTitledBorder);
        studyPanel.setBorder(studyCompBorder);

        progressPanel = new JPanel();
        progressTitledBorder = BorderFactory.createTitledBorder("Fortschritt");
        progressCompBorder = BorderFactory.createCompoundBorder(margin, progressTitledBorder);
        progressPanel.setBorder(progressCompBorder);

        studentLabelPanel = new JPanel();
        studentLabelPanel.setBorder(margin);

        studentFieldPanel = new JPanel();
        studentFieldPanel.setBorder(margin);

        studyLabelPanel = new JPanel();
        studyLabelPanel.setBorder(margin);

        studyFieldPanel = new JPanel();
        studyFieldPanel.setBorder(margin);

        studentNameLabel = new JLabel("Name:");
        studentBirthLabel = new JLabel("Geburtsdatum:");
        studentMatLabel = new JLabel("Matrikelnummer:");

        studyNameLabel = new JLabel("Name:");
        studyAcadLabel = new JLabel("Hochschule:");
        studyStartLabel = new JLabel("Studienbeginn:");

        studentNameField = new JTextField();
        studentBirthField = new JTextField();
        studentMatField = new JTextField();

        studyNameField = new JTextField();
        studyAcadField = new JTextField();
        studyStartField = new JTextField();
    }

    /**
     * Damit die GUI-Komponenten an die richtige Stelle kommen.
     */
    private void layoutStudyPanel() {
        studentLabelPanel.setLayout(new GridLayout(3, 1, 5, 5));
        studentLabelPanel.add(studentNameLabel);
        studentLabelPanel.add(studentBirthLabel);
        studentLabelPanel.add(studentMatLabel);

        studentFieldPanel.setLayout(new GridLayout(3, 1, 5, 5));
        studentFieldPanel.add(studentNameField);
        studentFieldPanel.add(studentBirthField);
        studentFieldPanel.add(studentMatField);

        studyLabelPanel.setLayout(new GridLayout(3, 1, 5, 5));
        studyLabelPanel.add(studyNameLabel);
        studyLabelPanel.add(studyAcadLabel);
        studyLabelPanel.add(studyStartLabel);

        studyFieldPanel.setLayout(new GridLayout(3, 1, 5, 5));
        studyFieldPanel.add(studyNameField);
        studyFieldPanel.add(studyAcadField);
        studyFieldPanel.add(studyStartField);

        studentPanel.setLayout(new BorderLayout());
        studentPanel.add(studentLabelPanel, BorderLayout.WEST);
        studentPanel.add(studentFieldPanel, BorderLayout.CENTER);

        studyPanel.setLayout(new BorderLayout());
        studyPanel.add(studyLabelPanel, BorderLayout.WEST);
        studyPanel.add(studyFieldPanel, BorderLayout.CENTER);

        northPanel.setLayout(new GridLayout(1, 2));
        northPanel.add(studentPanel);
        northPanel.add(studyPanel);

        this.setLayout(new BorderLayout());
        this.add(northPanel, BorderLayout.NORTH);
        this.add(progressPanel, BorderLayout.CENTER);
    }

    /**
     * Getter-Methode für die Tabelle, die den Notenüberblick beinhaltet.
     *
     * @return die Notentabelle
     */
    public SBTable getTable() {
        return this.gradeTable;
    }

    /**
     * Getter-Methode für das Holen der Textfeldwerte.
     *
     * @return die eingetragenen Textfeldwerte
     */
    public String[] getFields() {
        String[] fields = {
            this.studentNameField.getText(),
            this.studentBirthField.getText(),
            this.studentMatField.getText(),
            this.studyNameField.getText(),
            this.studyAcadField.getText(),
            this.studyStartField.getText()
        };
        return fields;
    }

    /**
     * Setter-Methode für das Setzen der Textfelwerte.
     *
     * @param fields die einzutragenen Textfeldwerte
     */
    public void setFields(String[] fields) {
        this.studentNameField.setText(fields[0]);
        this.studentBirthField.setText(fields[1]);
        this.studentMatField.setText(fields[2]);

        this.studyNameField.setText(fields[3]);
        this.studyAcadField.setText(fields[4]);
        this.studyStartField.setText(fields[5]);
    }

    public void save(SBController controller) {
        System.err.println("save studyPanel");
        SBModel db = controller.dbconnect();
        if (db != null) {
            db.query("UPDATE allgemeindaten SET studentname = '" + studentNameField.getText() + "',"
                    + "studentbirth='" + studentBirthField.getText() + "',"
                    + "studentmatnum='" + studentMatField.getText() + "',"
                    + "studyname='" + studyNameField.getText() + "',"
                    + "studyacad='" + studyAcadField.getText() + "',"
                    + "studystart='" + studyStartField.getText() + "';");
        }
    }

    public void setFields(String studentname, String studentbirth, String studentmatnum, String studyname, String studyacad, String studystart) {
        this.studentNameField.setText(studentname);
        this.studentBirthField.setText(studentbirth);
        this.studentMatField.setText(studentmatnum);

        this.studyNameField.setText(studyname);
        this.studyAcadField.setText(studyacad);
        this.studyStartField.setText(studystart);
    }
}
