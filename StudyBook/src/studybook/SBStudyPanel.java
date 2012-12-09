package studybook;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import com.michaelbaranov.microba.calendar.DatePicker;
import java.beans.PropertyVetoException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
    private JTextField studentMatField;
    private JTextField studyNameField;
    private JTextField studyAcadField;
    private SBFieldDocument studentNameDocument;
    private SBFieldDocument studentMatDocument;
    private SBFieldDocument studyNameDocument;
    private SBFieldDocument studyAcadDocument;
    private SimpleDateFormat dateFormat;
    private DatePicker studentBirthPicker;
    private DatePicker studyStartPicker;

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
        this.createChildPanel();
        this.createLabel();
        this.createTextFields();
        this.createDatePicker();
    }

    /**
     * Erstellt alle zur Modulverwaltung dazugehörigen "Child"-Panels.
     */
    private void createChildPanel() {
        margin = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        northPanel = new JPanel();

        // Studenten-Panel
        studentPanel = new JPanel();
        studentTitledBorder = BorderFactory.createTitledBorder("Student");
        studentCompBorder = BorderFactory.createCompoundBorder(margin, studentTitledBorder);
        studentPanel.setBorder(studentCompBorder);

        studentLabelPanel = new JPanel();
        studentLabelPanel.setBorder(margin);

        studentFieldPanel = new JPanel();
        studentFieldPanel.setBorder(margin);

        // Studiengang-Panel
        studyPanel = new JPanel();
        studyTitledBorder = BorderFactory.createTitledBorder("Studiengang");
        studyCompBorder = BorderFactory.createCompoundBorder(margin, studyTitledBorder);
        studyPanel.setBorder(studyCompBorder);

        studyLabelPanel = new JPanel();
        studyLabelPanel.setBorder(margin);

        studyFieldPanel = new JPanel();
        studyFieldPanel.setBorder(margin);

        // Fortschritts-Panel
        progressPanel = new JPanel();
        progressTitledBorder = BorderFactory.createTitledBorder("Fortschritt");
        progressCompBorder = BorderFactory.createCompoundBorder(margin, progressTitledBorder);
        progressPanel.setBorder(progressCompBorder);
    }

    /**
     * Erstellt alle darzustellenden Label.
     */
    private void createLabel() {
        //Student
        studentNameLabel = new JLabel("Name:");
        studentMatLabel = new JLabel("Matrikelnummer:");
        studentBirthLabel = new JLabel("Geburtsdatum:");

        // Studiengang
        studyNameLabel = new JLabel("Name:");
        studyAcadLabel = new JLabel("Hochschule:");
        studyStartLabel = new JLabel("Studienbeginn:");
    }

    /**
     * Erzeugt alle Eingabefelder und versieht sie mit Eingabebeschränkungen.
     */
    private void createTextFields() {
        // Einschränkungen für Textfelder festlegen
        studentNameDocument = new SBFieldDocument(50);
        studentMatDocument = new SBFieldDocument(50, "1234567890");
        studyNameDocument = new SBFieldDocument(50);
        studyAcadDocument = new SBFieldDocument(50);

        // Eingabefelder
        studentNameField = new JTextField(studentNameDocument, "", 0);
        studentMatField = new JTextField(studentMatDocument, "", 0);
        studyNameField = new JTextField(studyNameDocument, "", 0);
        studyAcadField = new JTextField(studyAcadDocument, "", 0);
    }

    /**
     * Erzeugt GUI-Komponenten, mit denen man ein Datum für den Geburtstag das
     * Studenten und den Studienstart festlegen kann.
     */
    private void createDatePicker() {
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

        studentBirthPicker = new DatePicker(null, dateFormat, Locale.GERMAN);
        studyStartPicker = new DatePicker(null, dateFormat, Locale.GERMAN);
    }

    /**
     * Damit die GUI-Komponenten an die richtige Stelle kommen.
     */
    private void layoutStudyPanel() {
        // Student
        studentLabelPanel.setLayout(new GridLayout(3, 1, 5, 5));
        studentLabelPanel.add(studentNameLabel);
        studentLabelPanel.add(studentMatLabel);
        studentLabelPanel.add(studentBirthLabel);

        studentFieldPanel.setLayout(new GridLayout(3, 1, 5, 5));
        studentFieldPanel.add(studentNameField);
        studentFieldPanel.add(studentMatField);
        studentFieldPanel.add(studentBirthPicker);

        studentPanel.setLayout(new BorderLayout());
        studentPanel.add(studentLabelPanel, BorderLayout.WEST);
        studentPanel.add(studentFieldPanel, BorderLayout.CENTER);

        // Studiengang
        studyLabelPanel.setLayout(new GridLayout(3, 1, 5, 5));
        studyLabelPanel.add(studyNameLabel);
        studyLabelPanel.add(studyAcadLabel);
        studyLabelPanel.add(studyStartLabel);

        studyFieldPanel.setLayout(new GridLayout(3, 1, 5, 5));
        studyFieldPanel.add(studyNameField);
        studyFieldPanel.add(studyAcadField);
        studyFieldPanel.add(studyStartPicker);

        studyPanel.setLayout(new BorderLayout());
        studyPanel.add(studyLabelPanel, BorderLayout.WEST);
        studyPanel.add(studyFieldPanel, BorderLayout.CENTER);

        // Student und Studiengang
        northPanel.setLayout(new GridLayout(1, 2));
        northPanel.add(studentPanel);
        northPanel.add(studyPanel);

        // alle Hauptpanels
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
            this.studentMatField.getText(),
            this.getStringDate(studentBirthPicker.getDate()),
            this.studyNameField.getText(),
            this.studyAcadField.getText(),
            this.getStringDate(studyStartPicker.getDate())
        };
        return fields;
    }

    /**
     * Methode, die die Datumsangaben als "Date"-Objekt einliest und zur
     * Weiterreichung an die Datenbank als String zurückliefert.
     *
     * @return Das Datum als String
     * @param date Das Datum als Date-Objekt
     */
    private String getStringDate(Date date) {
        String stringDate = "";

        // Sicherstellen, dass bei leeren Datumsangaben und auch ein leerer
        // String zurückgegeben wird
        try {
            stringDate = this.dateFormat.format(date);
        } catch (NullPointerException exception) {
        }
        return stringDate;
    }

    /**
     * Setter-Methode für das Setzen der Textfelwerte.
     *
     * @param fields die einzutragenen Textfeldwerte
     */
    public void setFields(String[] fields) {
        this.studentNameField.setText(fields[0]);
        this.studentMatField.setText(fields[1]);
        this.studyNameField.setText(fields[3]);
        this.studyAcadField.setText(fields[4]);

        // Falls das Setzen des Datums nicht funktionieren sollte
        try {
            this.studentBirthPicker.setDate(this.getDate(fields[2]));
            this.studyStartPicker.setDate(this.getDate(fields[5]));
        } catch (PropertyVetoException ex) {
        }
    }

    /**
     * Methode, die die Datumsangaben als String einliest und sie für
     * die DatePicker als Date-Objekt zurückliefert.
     *
     * @return Das Datum als Date-Objekt
     * @param stringDate Das Datum als String
     */
    private Date getDate(String stringDate) {
        Date date = null;

        // Falls das Umwandeln des Strings in ein Date-Objekt nicht
        // funktionieren sollte
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException exception) {
        }
        return date;
    }
}
