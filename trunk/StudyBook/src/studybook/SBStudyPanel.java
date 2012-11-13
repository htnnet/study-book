package studybook;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;
/**
 * Enthält das Panel für die Studiengangverwaltung.
 *
 */
public class SBStudyPanel extends JPanel{
    private JPanel studentstudypanel;
    private JPanel studentpanel;
    private JPanel studypanel;
    private JPanel progresspanel;
    private Border margin;
    private Border studenttborder;
    private Border studentcborder;
    private Border studytborder;
    private Border studycborder;
    private Border progresstborder;
    private Border progresscborder;
    private JLabel studentnamel;
    private JLabel studentbirthdl;
    private JLabel studentmatnuml;
    private JLabel studynamel;
    private JLabel studyacadl;
    private JLabel studystartl;
    private JTextField studentnamet;
    private JTextField studentbirthdt;
    private JTextField studentmatnumt;
    private JTextField studynamet;
    private JTextField studyacadt;
    private JTextField studystartt;


    public SBStudyPanel() {
        this.createStudyPanel();
        this.layoutStudyPanel();
    }


    /**
    * Erstellt das Panel zur Studiengangverwaltung, das dem Benutzer unter
    * anderem einen Einblick in seinen Studienfortschritt gewährt.
    */
    private void createStudyPanel() {
        studentstudypanel = new JPanel();

        margin = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        studentpanel = new JPanel();
        studenttborder = BorderFactory.createTitledBorder("Student");
        studentcborder = BorderFactory.createCompoundBorder(studenttborder, margin);
        studentpanel.setBorder(studentcborder);

        studypanel = new JPanel();
        studytborder = BorderFactory.createTitledBorder("Studiengang");
        studycborder = BorderFactory.createCompoundBorder(studytborder, margin);
        studypanel.setBorder(studycborder);

        progresspanel = new JPanel();
        progresstborder = BorderFactory.createTitledBorder("Fortschritt");
        progresscborder = BorderFactory.createCompoundBorder(progresstborder, margin);
        progresspanel.setBorder(progresscborder);

        studentnamel = new JLabel("Name:");
        studentbirthdl = new JLabel("Geburtsdatum:");
        studentmatnuml = new JLabel("Matrikelnummer:");

        studynamel = new JLabel("Name:");
        studyacadl = new JLabel("Hochschule:");
        studystartl = new JLabel("Studienbeginn:");

        studentnamet = new JTextField();
        studentbirthdt = new JTextField();
        studentmatnumt = new JTextField();

        studynamet = new JTextField();
        studyacadt = new JTextField();
        studystartt = new JTextField();
    }

    /**
     * Damit die GUI-Komponenten an die richtige Stelle kommen.
     */
    private void layoutStudyPanel() {
        // momentan noch GridLayout mit 5 Pixeln zum nächsten GUI-Element hin
        studentpanel.setLayout(new GridLayout(3, 2, 5, 5));
        studentpanel.add(studentnamel);
        studentpanel.add(studentnamet);
        studentpanel.add(studentbirthdl);
        studentpanel.add(studentbirthdt);
        studentpanel.add(studentmatnuml);
        studentpanel.add(studentmatnumt);

        studypanel.setLayout(new GridLayout(3, 2, 5, 5));
        studypanel.add(studynamel);
        studypanel.add(studynamet);
        studypanel.add(studyacadl);
        studypanel.add(studyacadt);
        studypanel.add(studystartl);
        studypanel.add(studystartt);

        studentstudypanel.setLayout(new GridLayout(1,2));
        studentstudypanel.add(studentpanel);
        studentstudypanel.add(studypanel);

        this.setLayout(new BorderLayout());
        this.add(studentstudypanel, BorderLayout.NORTH);
        this.add(progresspanel, BorderLayout.CENTER);

    }
}
