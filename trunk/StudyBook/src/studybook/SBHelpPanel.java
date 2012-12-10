package studybook;

import java.awt.BorderLayout;
import javax.swing.border.Border;
import javax.swing.*;

/**
 * Das Hilfspanel.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBHelpPanel extends JPanel {
    private JLabel hilfe;

    public SBHelpPanel() {
        this.createHelpPanel();
        this.layoutHelpPanel();
    }


    /**
    * Erstellt das Hilfspanel, das dem Benutzer das Programm näher bringen soll.
    */
    public void createHelpPanel() {
        hilfe = new JLabel("Hilfe!!!");
        hilfe.setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Setzt die GUI-Komponenten des Hilfspanels an die richtige Stelle.
     */
    public void layoutHelpPanel() {
        this.setLayout(new BorderLayout());
        this.add(hilfe, BorderLayout.CENTER);
    }
}
