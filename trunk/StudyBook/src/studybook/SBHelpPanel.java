package studybook;

import java.awt.BorderLayout;
import java.io.IOException;
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
    private JEditorPane editorPane;
    private JScrollPane editorScrollPane;

    public SBHelpPanel() {
        this.createHelpPanel();
        this.layoutHelpPanel();
    }

    /**
     * Erstellt das Hilfspanel, das dem Benutzer das Programm näher bringen
     * soll.
     */
    public void createHelpPanel() {
        hilfe = new JLabel("Hilfe!!!");
        this.createEditorPane();
        editorScrollPane = new JScrollPane(this.editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void createEditorPane() {
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        java.net.URL helpURL = this.getClass().getResource("/doc/help.html");
        if (helpURL != null) {
            try {
                editorPane.setPage(helpURL);
            } catch (IOException exception) {
                System.err.println("Konnte URL nicht finden " + helpURL);
            }
        } else {
            System.err.println("Die Datei \"help.html\" konnte nicht gefunden werden");
        }
    }

    /**
     * Setzt die GUI-Komponenten des Hilfspanels an die richtige Stelle.
     */
    public void layoutHelpPanel() {
        this.setLayout(new BorderLayout());
        this.add(editorScrollPane, BorderLayout.CENTER);
    }
}
