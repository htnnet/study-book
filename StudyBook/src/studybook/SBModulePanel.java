package studybook;

import com.michaelbaranov.microba.calendar.DatePicker;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.border.Border;
import javax.swing.*;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.print.attribute.AttributeSet;
import javax.swing.text.*;

/**
 * Das Panel für die Modulverwaltung.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBModulePanel extends JPanel {

    private JPanel academicPanel;
    private JPanel academicLeftPanel;
    private JPanel academicLeftLabelPanel;
    private JPanel academicLeftFieldPanel;
    private JPanel academicRightPanel;
    private JPanel academicRightLabelPanel;
    private JPanel academicRightFieldPanel;
    private JPanel centerPanel;
    private JPanel southPanel;
    private JPanel examOnePanel;
    private JPanel examOneLabelPanel;
    private JPanel examOneFieldPanel;
    private JPanel examTwoPanel;
    private JPanel examTwoLabelPanel;
    private JPanel examTwoFieldPanel;
    private JPanel notePanel;
    private JScrollPane noteScrollPane;
    private Border academicCompBorder;
    private Border academicTitledBorder;
    private Border examOneCompBorder;
    private Border examOneTitledBorder;
    private Border examTwoCompBorder;
    private Border examTwoTitledBorder;
    private Border noteCompBorder;
    private Border noteTitledBorder;
    private Border margin;
    private JLabel academicNameLabel;
    private JLabel academicRoomLabel;
    private JLabel academicTelLabel;
    private JLabel academicMailLabel;
    private JLabel examOneTypeLabel;
    private JLabel examOneRoomLabel;
    private JLabel examOneDateLabel;
    private JLabel examOneTimeLabel;
    private JLabel examOneCreditsLabel;
    private JLabel examOneGradeLabel;
    private JLabel examTwoTypeLabel;
    private JLabel examTwoRoomLabel;
    private JLabel examTwoDateLabel;
    private JLabel examTwoTimeLabel;
    private JLabel examTwoCreditsLabel;
    private JLabel examTwoGradeLabel;
    private SBFieldDocument academicNameDocument;
    private SBFieldDocument academicRoomDocument;
    private SBFieldDocument academicTelDocument;
    private SBFieldDocument academicMailDocument;
    private SBFieldDocument examOneTypeDocument;
    private SBFieldDocument examOneRoomDocument;
    private SBFieldDocument examTwoTypeDocument;
    private SBFieldDocument examTwoRoomDocument;
    private SBFieldDocument noteDocument;
    private JTextField academicNameField;
    private JTextField academicRoomField;
    private JTextField academicTelField;
    private JTextField academicMailField;
    private JTextField examOneTypeField;
    private JTextField examOneRoomField;
    private JTextField examTwoTypeField;
    private JTextField examTwoRoomField;
    private JTextArea noteArea;
    private SpinnerDateModel examOneTimeSpinnerModel;
    private SpinnerNumberModel examOneCreditsSpinnerModel;
    private SpinnerNumberModel examOneGradeSpinnerModel;
    private SpinnerDateModel examTwoTimeSpinnerModel;
    private SpinnerNumberModel examTwoCreditsSpinnerModel;
    private SpinnerNumberModel examTwoGradeSpinnerModel;
    private DatePicker examOneDatePicker;
    private DatePicker examTwoDatePicker;
    private JSpinner examOneTimeSpinner;
    private JSpinner examOneCreditsSpinner;
    private JSpinner examOneGradeSpinner;
    private JSpinner examTwoTimeSpinner;
    private JSpinner examTwoCreditsSpinner;
    private JSpinner examTwoGradeSpinner;
    private JFormattedTextField formattedField;
    private Date initTime;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private DateFormatter dateFormatter;
    private NumberFormatter numberFormatter;
    private DecimalFormat decimalFormat;

    /**
     * Konstruktor der Klasse SBModulePanel.
     */
    public SBModulePanel() {
        this.createModulePanel();
        this.layoutModulePanel();


        String[] inputString = {"Risse", "231a", "7575673", "trris@bla.de",
         "Klausur", "34d", "18.02.2012", "23:00", "5", "4.0", "Labor", "57b",
         "", "", "20", "1.0", "Hallo\nWelt!"};

        this.setFields(inputString);

        String[] outputString = this.getFields();
        for (int i = 0; i < outputString.length; i++) {
            System.out.println(outputString[i]);
        }


    }

    /**
     * Es wird ein Panel erzeugt, das dem Benutzer die Eingabe seiner
     * modulrelevanten Daten ermöglicht.
     */
    public void createModulePanel() {
        this.createChildPanel();
        this.createLabel();
        this.createTextFields();
        this.createDatePicker();
        this.createSpinner();
        this.createTextArea();
    }

    /**
     * Erstellt alle zur Modulverwaltung dazugehörigen "Child"-Panels.
     */
    private void createChildPanel() {
        margin = BorderFactory.createEmptyBorder(5, 5, 5, 5); // Abstandshalter

        // Dozenten-Panel
        academicPanel = new JPanel();
        academicTitledBorder = BorderFactory.createTitledBorder("Dozent");
        academicCompBorder = BorderFactory.createCompoundBorder(margin, academicTitledBorder);
        academicPanel.setBorder(academicCompBorder);

        academicLeftPanel = new JPanel();

        academicLeftLabelPanel = new JPanel();
        academicLeftLabelPanel.setBorder(margin);
        academicLeftFieldPanel = new JPanel();
        academicLeftFieldPanel.setBorder(margin);

        academicRightPanel = new JPanel();

        academicRightLabelPanel = new JPanel();
        academicRightLabelPanel.setBorder(margin);
        academicRightFieldPanel = new JPanel();
        academicRightFieldPanel.setBorder(margin);

        // Panels für die Leistungsnachweise
        centerPanel = new JPanel();

        examOnePanel = new JPanel();
        examOneTitledBorder = BorderFactory.createTitledBorder("1. Leistungsnachweis");
        examOneCompBorder = BorderFactory.createCompoundBorder(margin, examOneTitledBorder);
        examOnePanel.setBorder(examOneCompBorder);

        examOneLabelPanel = new JPanel();
        examOneLabelPanel.setBorder(margin);
        examOneFieldPanel = new JPanel();
        examOneFieldPanel.setBorder(margin);


        examTwoPanel = new JPanel();
        examTwoTitledBorder = BorderFactory.createTitledBorder("2. Leistungsnachweis");
        examTwoCompBorder = BorderFactory.createCompoundBorder(margin, examTwoTitledBorder);
        examTwoPanel.setBorder(examTwoCompBorder);

        examTwoLabelPanel = new JPanel();
        examTwoLabelPanel.setBorder(margin);
        examTwoFieldPanel = new JPanel();
        examTwoFieldPanel.setBorder(margin);

        // Panel für die Notizen
        notePanel = new JPanel();
        noteTitledBorder = BorderFactory.createTitledBorder("Notizen");
        noteCompBorder = BorderFactory.createCompoundBorder(margin, noteTitledBorder);
        notePanel.setBorder(noteCompBorder);

        southPanel = new JPanel();

    }

    /**
     * Erstellt alle darzustellenden Label.
     */
    private void createLabel() {
        // Dozent
        academicNameLabel = new JLabel("Name:  ");
        academicRoomLabel = new JLabel("Raum:");
        academicTelLabel = new JLabel("Telefon:");
        academicMailLabel = new JLabel("E-Mail:");
        // Leistunsnachweise
        examOneTypeLabel = new JLabel("Art:");
        examOneRoomLabel = new JLabel("Raum:");
        examOneDateLabel = new JLabel("Datum:");
        examOneTimeLabel = new JLabel("Zeit:");
        examOneCreditsLabel = new JLabel("Credits:");
        examOneGradeLabel = new JLabel("Note:");
        examTwoTypeLabel = new JLabel("Art:");
        examTwoRoomLabel = new JLabel("Raum:");
        examTwoDateLabel = new JLabel("Datum:");
        examTwoTimeLabel = new JLabel("Zeit:");
        examTwoCreditsLabel = new JLabel("Credits:");
        examTwoGradeLabel = new JLabel("Note:");
    }

    /**
     * Erzeugt alle Eingabefelder und versieht sie mit Eingabebeschränkungen.
     */
    private void createTextFields() {
        // Eingabebeschränkungen
        academicNameDocument = new SBFieldDocument(50);
        academicRoomDocument = new SBFieldDocument(50);
        academicTelDocument = new SBFieldDocument(50, "+-()/1234567890");
        academicMailDocument = new SBFieldDocument(50);
        examOneTypeDocument = new SBFieldDocument(50);
        examOneRoomDocument = new SBFieldDocument(50);
        examTwoTypeDocument = new SBFieldDocument(50);
        examTwoRoomDocument = new SBFieldDocument(50);

        // Eingabefelder
        academicNameField = new JTextField(academicNameDocument, "", 0);
        academicRoomField = new JTextField(academicRoomDocument, "", 0);
        academicTelField = new JTextField(academicTelDocument, "", 0);
        academicMailField = new JTextField(academicMailDocument, "", 0);
        examOneTypeField = new JTextField(examOneTypeDocument, "", 0);
        examOneRoomField = new JTextField(examOneRoomDocument, "", 0);
        examTwoTypeField = new JTextField(examTwoTypeDocument, "", 0);
        examTwoRoomField = new JTextField(examTwoRoomDocument, "", 0);
    }

    /**
     * Erzeugt GUI-Komponenten, mit denen man ein Datum für die
     * Leistungsnachweise festlegen kann.
     */
    private void createDatePicker() {
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

        examOneDatePicker = new DatePicker(null, dateFormat, Locale.GERMAN);
        examTwoDatePicker = new DatePicker(null, dateFormat, Locale.GERMAN);
    }

    /**
     * Hier werden sogenannte "JSpinner" erzeugt über die der Benutzer die
     * Uhrzeit, die Credit-Points und die Note entweder mittles der Maus oder
     * der Tastatur einstellen kann.
     */
    private void createSpinner() {
        // Eingaberestriktionen für "Zeit", "Credits" und "Note" festlegen
        examOneTimeSpinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        examOneCreditsSpinnerModel = new SpinnerNumberModel(0, 0, 20, 1);
        examOneGradeSpinnerModel = new SpinnerNumberModel(5.0, 1.0, 5.0, 0.1);
        examTwoTimeSpinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        examTwoCreditsSpinnerModel = new SpinnerNumberModel(0, 0, 20, 1);
        examTwoGradeSpinnerModel = new SpinnerNumberModel(5.0, 1.0, 5.0, 0.1);

        // Spinner erzeugen
        examOneTimeSpinner = new JSpinner(examOneTimeSpinnerModel);
        examOneCreditsSpinner = new JSpinner(examOneCreditsSpinnerModel);
        examOneGradeSpinner = new JSpinner(examOneGradeSpinnerModel);
        examTwoTimeSpinner = new JSpinner(examTwoTimeSpinnerModel);
        examTwoCreditsSpinner = new JSpinner(examTwoCreditsSpinnerModel);
        examTwoGradeSpinner = new JSpinner(examTwoGradeSpinnerModel);

        // Den Spinner-Elementen Formate vorgeben und neben der Eingabe mittels
        // der Maus, auch Eingaben mittels Tastatur ermöglichen
        decimalFormat = new DecimalFormat("0.0");
        timeFormat = new SimpleDateFormat("HH:mm");

        formattedField = ((JSpinner.DateEditor) examOneTimeSpinner.getEditor()).getTextField();
        formattedField.setHorizontalAlignment(JTextField.LEFT);
        dateFormatter = (DateFormatter) formattedField.getFormatter();
        dateFormatter.setFormat(timeFormat);

        formattedField = ((JSpinner.NumberEditor) examOneCreditsSpinner.getEditor()).getTextField();
        formattedField.setHorizontalAlignment(JTextField.LEFT);
        numberFormatter = (NumberFormatter) formattedField.getFormatter();
        numberFormatter.setAllowsInvalid(false);

        formattedField = ((JSpinner.NumberEditor) examOneGradeSpinner.getEditor()).getTextField();
        formattedField.setHorizontalAlignment(JTextField.LEFT);
        numberFormatter = (NumberFormatter) formattedField.getFormatter();
        numberFormatter.setFormat(decimalFormat);
        numberFormatter.setAllowsInvalid(false);

        formattedField = ((JSpinner.DateEditor) examTwoTimeSpinner.getEditor()).getTextField();
        formattedField.setHorizontalAlignment(JTextField.LEFT);
        dateFormatter = (DateFormatter) formattedField.getFormatter();
        dateFormatter.setFormat(timeFormat);

        formattedField = ((JSpinner.NumberEditor) examTwoCreditsSpinner.getEditor()).getTextField();
        formattedField.setHorizontalAlignment(JTextField.LEFT);
        numberFormatter = (NumberFormatter) formattedField.getFormatter();
        numberFormatter.setAllowsInvalid(false);

        formattedField = ((JSpinner.NumberEditor) examTwoGradeSpinner.getEditor()).getTextField();
        formattedField.setHorizontalAlignment(JTextField.LEFT);
        numberFormatter = (NumberFormatter) formattedField.getFormatter();
        numberFormatter.setFormat(decimalFormat);
        numberFormatter.setAllowsInvalid(false);


        // Den Spinner-Elementen für die Zeit und die Note Startwerte mitgeben
        initTime = new Date();
        try {
            initTime = timeFormat.parse("00:00");
        } catch (ParseException exception) {
        }

        examOneTimeSpinner.setValue(initTime);
        examTwoTimeSpinner.setValue(initTime);

        examOneGradeSpinner.setValue(1.0);
        examTwoGradeSpinner.setValue(1.0);
    }

    /**
     * Erzeugt das große Eingabefeld, in das modulrelevante Notizen eingetragen
     * werden können.
     */
    private void createTextArea() {
        noteDocument = new SBFieldDocument(1000);
        noteArea = new JTextArea(noteDocument, "", 0, 0);
        noteArea.setLineWrap(true);                 // Zeilenumbruch
        noteArea.setWrapStyleWord(true);            // Zeilenumbruch für Wörter
        noteScrollPane = new JScrollPane(noteArea);
    }

    /**
     * Die GUI-Komponenten der Modulverwaltung kriegen hier ihren Platz
     * zugewiesen.
     */
    public void layoutModulePanel() {
        // Dozent
        academicLeftLabelPanel.setLayout(new GridLayout(2, 1, 5, 5));
        academicLeftLabelPanel.add(academicNameLabel);
        academicLeftLabelPanel.add(academicRoomLabel);

        academicLeftFieldPanel.setLayout(new GridLayout(2, 1, 5, 5));
        academicLeftFieldPanel.add(academicNameField);
        academicLeftFieldPanel.add(academicRoomField);

        academicRightLabelPanel.setLayout(new GridLayout(2, 1, 5, 5));
        academicRightLabelPanel.add(academicTelLabel);
        academicRightLabelPanel.add(academicMailLabel);

        academicRightFieldPanel.setLayout(new GridLayout(2, 1, 5, 5));
        academicRightFieldPanel.add(academicTelField);
        academicRightFieldPanel.add(academicMailField);

        academicLeftPanel.setLayout(new BorderLayout());
        academicLeftPanel.add(academicLeftLabelPanel, BorderLayout.WEST);
        academicLeftPanel.add(academicLeftFieldPanel, BorderLayout.CENTER);

        academicRightPanel.setLayout(new BorderLayout());
        academicRightPanel.add(academicRightLabelPanel, BorderLayout.WEST);
        academicRightPanel.add(academicRightFieldPanel, BorderLayout.CENTER);

        academicPanel.setLayout(new GridLayout(1, 2, 20, 20));
        academicPanel.add(academicLeftPanel);
        academicPanel.add(academicRightPanel);

        // Leistungsnachweise
        examOneLabelPanel.setLayout(new GridLayout(6, 1, 5, 5));
        examOneLabelPanel.add(examOneTypeLabel);
        examOneLabelPanel.add(examOneRoomLabel);
        examOneLabelPanel.add(examOneDateLabel);
        examOneLabelPanel.add(examOneTimeLabel);
        examOneLabelPanel.add(examOneCreditsLabel);
        examOneLabelPanel.add(examOneGradeLabel);

        examOneFieldPanel.setLayout(new GridLayout(6, 1, 5, 5));
        examOneFieldPanel.add(examOneTypeField);
        examOneFieldPanel.add(examOneRoomField);
        examOneFieldPanel.add(examOneDatePicker);
        examOneFieldPanel.add(examOneTimeSpinner);
        examOneFieldPanel.add(examOneCreditsSpinner);
        examOneFieldPanel.add(examOneGradeSpinner);

        examTwoLabelPanel.setLayout(new GridLayout(6, 1, 5, 5));
        examTwoLabelPanel.add(examTwoTypeLabel);
        examTwoLabelPanel.add(examTwoRoomLabel);
        examTwoLabelPanel.add(examTwoDateLabel);
        examTwoLabelPanel.add(examTwoTimeLabel);
        examTwoLabelPanel.add(examTwoCreditsLabel);
        examTwoLabelPanel.add(examTwoGradeLabel);

        examTwoFieldPanel.setLayout(new GridLayout(6, 1, 5, 5));
        examTwoFieldPanel.add(examTwoTypeField);
        examTwoFieldPanel.add(examTwoRoomField);
        examTwoFieldPanel.add(examTwoDatePicker);
        examTwoFieldPanel.add(examTwoTimeSpinner);
        examTwoFieldPanel.add(examTwoCreditsSpinner);
        examTwoFieldPanel.add(examTwoGradeSpinner);

        examOnePanel.setLayout(new BorderLayout());
        examOnePanel.add(examOneLabelPanel, BorderLayout.WEST);
        examOnePanel.add(examOneFieldPanel, BorderLayout.CENTER);

        examTwoPanel.setLayout(new BorderLayout());
        examTwoPanel.add(examTwoLabelPanel, BorderLayout.WEST);
        examTwoPanel.add(examTwoFieldPanel, BorderLayout.CENTER);

        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanel.add(examOnePanel);
        centerPanel.add(examTwoPanel);

        // Notizen
        notePanel.setLayout(new BorderLayout());
        notePanel.add(noteScrollPane);

        southPanel.setLayout(new BorderLayout());
        southPanel.add(centerPanel, BorderLayout.NORTH);
        southPanel.add(notePanel, BorderLayout.CENTER);

        // alle Hauptpanels
        this.setLayout(new BorderLayout());
        this.add(academicPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.CENTER);

    }

    /**
     * Getter-Methode für das Holen der Textfeldwerte, Datumsangaben,
     * Spinner-Eingaben.
     *
     * @return die eingetragenen Werte
     */
    public String[] getFields() {
        String[] fields = {
            this.academicNameField.getText(),
            this.academicRoomField.getText(),
            this.academicTelField.getText(),
            this.academicMailField.getText(),
            this.examOneTypeField.getText(),
            this.examOneRoomField.getText(),
            this.getStringDate(examOneDatePicker.getDate()),
            this.getStringTime((Date) examOneTimeSpinner.getValue()),
            this.examOneCreditsSpinner.getValue().toString(),
            this.examOneGradeSpinner.getValue().toString(),
            this.examTwoTypeField.getText(),
            this.examTwoRoomField.getText(),
            this.getStringDate(examTwoDatePicker.getDate()),
            this.getStringTime((Date) examTwoTimeSpinner.getValue()),
            this.examTwoCreditsSpinner.getValue().toString(),
            this.examTwoGradeSpinner.getValue().toString(),
            this.noteArea.getText()
        };
        return fields;
    }

    /**
     * Methode, die die Datumsangaben als "Date"-Objekt einliest und zur
     * Weiterreichung an die Datenbank als String zurückliefert.
     *
     * @return Das Datum als String
     * @param examDate Das Datum als Date-Objekt
     */
    private String getStringDate(Date examDate) {
        String examStringDate = "";

        // Sicherstellen, dass bei leeren Datumsangaben und auch ein leerer
        // String zurückgegeben wird
        try {
            examStringDate = this.dateFormat.format(examDate);
        } catch (NullPointerException exception) {
        }
        return examStringDate;
    }

    /**
     * Methode, die die Zeitangaben als "Date"-Objekt einliest und zur
     * Weiterreichung an die Datenbank als String zurückliefert.
     *
     * @return Die Zeit als String
     * @param examTime Die Zeit als Date-Objekt
     */
    private String getStringTime(Date examTime) {
        String examStringTime = "";

        // Sicherstellen, dass bei leeren Datumsangaben und auch ein leerer
        // String zurückgegeben wird
        try {
            examStringTime = this.timeFormat.format(examTime);
        } catch (NullPointerException exception) {
        }
        return examStringTime;
    }

    /**
     * Setter-Methode für das Setzen der Textfeldwerte, DatePicker und JSpinner.
     *
     * @param fields die einzutragenden Werte
     */
    public void setFields(String[] fields) {
        this.academicNameField.setText(fields[0]);
        this.academicRoomField.setText(fields[1]);
        this.academicTelField.setText(fields[2]);
        this.academicMailField.setText(fields[3]);
        this.examOneTypeField.setText(fields[4]);
        this.examOneRoomField.setText(fields[5]);
        this.examOneTimeSpinner.setValue(this.getTime(fields[7]));
        this.examOneCreditsSpinner.setValue(Integer.parseInt(fields[8]));
        this.examOneGradeSpinner.setValue(Float.parseFloat(fields[9]));
        this.examTwoTypeField.setText(fields[10]);
        this.examTwoRoomField.setText(fields[11]);
        this.examTwoTimeSpinner.setValue(this.getTime(fields[13]));
        this.examTwoCreditsSpinner.setValue(Integer.parseInt(fields[14]));
        this.examTwoGradeSpinner.setValue(Float.parseFloat(fields[15]));
        this.noteArea.setText(fields[16]);

        // Falls das Setzen des Datums nicht funktionieren sollte
        try {
            this.examOneDatePicker.setDate(this.getDate(fields[6]));
            this.examTwoDatePicker.setDate(this.getDate(fields[12]));
        } catch (PropertyVetoException ex) {
        }
    }

     /**
     * Methode, die die Datumsangaben als String einliest und sie für
     * die DatePicker als Date-Objekt zurückliefert.
     *
     * @return Das Datum als Date-Objekt
     * @param examStringDate Das Datum als String
     */
    private Date getDate(String examStringDate) {
        Date examDate = null;

        // Falls das Umwandeln des Strings in ein Date-Objekt nicht
        // funktionieren sollte
        try {
            examDate = dateFormat.parse(examStringDate);
        } catch (ParseException exception) {
        }
        return examDate;
    }

     /**
     * Methode, die die Zeitangaben als String einliest und sie für
     * die JSpinner als Date-Objekt zurückliefert.
     *
     * @return Die Zeit als Date-Objekt
     * @param examStringTime Die Zeit String
     */
    private Date getTime(String examStringTime) {
        Date examTime = initTime;

        // Falls das Umwandeln des Strings in ein Date-Objekt nicht
        // funktionieren sollte
        try {
            examTime = timeFormat.parse(examStringTime);
        } catch (ParseException exception) {
        }
        return examTime;
    }
}
