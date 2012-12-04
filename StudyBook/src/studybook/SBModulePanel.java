package studybook;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.border.Border;
import javax.swing.*;
import java.awt.Font;

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
    private JTextField academicNameField;
    private JTextField academicRoomField;
    private JTextField academicTelField;
    private JTextField academicMailField;
    private JTextField examOneTypeField;
    private JTextField examOneRoomField;
    private JTextField examOneDateField;
    private JTextField examOneTimeField;
    private JTextField examOneCreditsField;
    private JTextField examOneGradeField;
    private JTextField examTwoTypeField;
    private JTextField examTwoRoomField;
    private JTextField examTwoDateField;
    private JTextField examTwoTimeField;
    private JTextField examTwoCreditsField;
    private JTextField examTwoGradeField;
    private JTextArea noteArea;
    private Font font;

    /**
     * Konstruktor der Klasse SBModulePanel.
     */
    public SBModulePanel() {
        this.createModulePanel();
        this.layoutModulePanel();
    }

    /**
     * Es wird ein Panel erzeugt, das dem Benutzer die Eingabe seiner
     * modulrelevanten Daten ermöglicht.
     */
    public void createModulePanel() {
        centerPanel = new JPanel();
        southPanel = new JPanel();

        margin = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        academicPanel = new JPanel();
        academicTitledBorder = BorderFactory.createTitledBorder("Dozent");
        academicCompBorder = BorderFactory.createCompoundBorder(margin, academicTitledBorder);
        academicPanel.setBorder(academicCompBorder);

        examOnePanel = new JPanel();
        examOneTitledBorder = BorderFactory.createTitledBorder("1. Leistungsnachweis");
        examOneCompBorder = BorderFactory.createCompoundBorder(margin, examOneTitledBorder);
        examOnePanel.setBorder(examOneCompBorder);

        examTwoPanel = new JPanel();
        examTwoTitledBorder = BorderFactory.createTitledBorder("2. Leistungsnachweis");
        examTwoCompBorder = BorderFactory.createCompoundBorder(margin, examTwoTitledBorder);
        examTwoPanel.setBorder(examTwoCompBorder);

        notePanel = new JPanel();
        noteTitledBorder = BorderFactory.createTitledBorder("Notizen");
        noteCompBorder = BorderFactory.createCompoundBorder(margin, noteTitledBorder);
        notePanel.setBorder(noteCompBorder);

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

        examOneLabelPanel = new JPanel();
        examOneLabelPanel.setBorder(margin);
        examOneFieldPanel = new JPanel();
        examOneFieldPanel.setBorder(margin);

        examTwoLabelPanel = new JPanel();
        examTwoLabelPanel.setBorder(margin);
        examTwoFieldPanel = new JPanel();
        examTwoFieldPanel.setBorder(margin);

        academicNameLabel = new JLabel("Name:");
        academicRoomLabel = new JLabel("Raum:");
        academicTelLabel = new JLabel("Telefon:");
        academicMailLabel = new JLabel("E-Mail:");
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

        academicNameField = new JTextField();
        academicRoomField = new JTextField();
        academicTelField = new JTextField();
        academicMailField = new JTextField();
        examOneTypeField = new JTextField();
        examOneRoomField = new JTextField();
        examOneDateField = new JTextField();
        examOneTimeField = new JTextField();
        examOneCreditsField = new JTextField();
        examOneGradeField = new JTextField();
        examTwoTypeField = new JTextField();
        examTwoRoomField = new JTextField();
        examTwoDateField = new JTextField();
        examTwoTimeField = new JTextField();
        examTwoCreditsField = new JTextField();
        examTwoGradeField = new JTextField();
        noteArea = new JTextArea();
        noteArea.setLineWrap(true);
    }

    /**
     * Die GUI-Komponenten der Modulverwaltung kriegen hier einen Platz
     * zugeteilt.
     */
    public void layoutModulePanel() {
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

        academicPanel.setLayout(new GridLayout(1, 2, 5, 5));
        academicPanel.add(academicLeftPanel);
        academicPanel.add(academicRightPanel);

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
        examOneFieldPanel.add(examOneDateField);
        examOneFieldPanel.add(examOneTimeField);
        examOneFieldPanel.add(examOneCreditsField);
        examOneFieldPanel.add(examOneGradeField);

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
        examTwoFieldPanel.add(examTwoDateField);
        examTwoFieldPanel.add(examTwoTimeField);
        examTwoFieldPanel.add(examTwoCreditsField);
        examTwoFieldPanel.add(examTwoGradeField);

        examOnePanel.setLayout(new BorderLayout());
        examOnePanel.add(examOneLabelPanel, BorderLayout.WEST);
        examOnePanel.add(examOneFieldPanel, BorderLayout.CENTER);

        examTwoPanel.setLayout(new BorderLayout());
        examTwoPanel.add(examTwoLabelPanel, BorderLayout.WEST);
        examTwoPanel.add(examTwoFieldPanel, BorderLayout.CENTER);

        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanel.add(examOnePanel);
        centerPanel.add(examTwoPanel);

        notePanel.setLayout(new BorderLayout());
        notePanel.add(noteArea);

        southPanel.setLayout(new BorderLayout());
        southPanel.add(centerPanel, BorderLayout.NORTH);
        southPanel.add(notePanel, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(academicPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.CENTER);

    }

     /**
     * Getter-Methode für das Holen der Textfeldwerte.
     *
     * @return die eingetragenen Textfeldwerte
     */
    public String[] getFields() {
        String[] fields = {
            this.academicNameField.getText(),
            this.academicRoomField.getText(),
            this.academicTelField.getText(),
            this.academicMailField.getText(),
            this.examOneTypeField.getText(),
            this.examOneRoomField.getText(),
            this.examOneDateField.getText(),
            this.examOneTimeField.getText(),
            this.examOneCreditsField.getText(),
            this.examOneGradeField.getText(),
            this.examTwoTypeField.getText(),
            this.examTwoRoomField.getText(),
            this.examTwoDateField.getText(),
            this.examTwoTimeField.getText(),
            this.examTwoCreditsField.getText(),
            this.examTwoGradeField.getText(),
            this.noteArea.getText()
        };
        return fields;
    }

    /**
     * Setter-Methode für das Setzen der Textfelwerte.
     *
     * @param fields die einzutragenen Textfeldwerte
     */
    public void setFields(String[] fields) {
        this.academicNameField.setText(fields[0]);
        this.academicRoomField.setText(fields[1]);
        this.academicTelField.setText(fields[2]);
        this.academicMailField.setText(fields[3]);
        this.examOneTypeField.setText(fields[4]);
        this.examOneRoomField.setText(fields[5]);
        this.examOneDateField.setText(fields[6]);
        this.examOneTimeField.setText(fields[7]);
        this.examOneCreditsField.setText(fields[8]);
        this.examOneGradeField.setText(fields[9]);
        this.examTwoTypeField.setText(fields[10]);
        this.examTwoRoomField.setText(fields[11]);
        this.examTwoDateField.setText(fields[12]);
        this.examTwoTimeField.setText(fields[13]);
        this.examTwoCreditsField.setText(fields[14]);
        this.examTwoGradeField.setText(fields[15]);
        this.noteArea.setText(fields[16]);
    }
}
