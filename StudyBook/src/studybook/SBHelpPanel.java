package studybook;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.*;

/**
 * Das Hilfspanel hilft dem Benutzer sich mit dem Programm zurechtzufinden,
 * indem es ihn mit Informationen über die Bedienung versorgt.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBHelpPanel extends JPanel {

    private JLabel hilfe;
    private JEditorPane editorPane;
    private JScrollPane editorScrollPane;

    /**
     * Konstruktor der Klasse SBHelpPanel.
     */
    public SBHelpPanel() {
        this.createHelpPanel();
        this.layoutHelpPanel();
    }

    /**
     * Erstellt das Hilfspanel, das dem Benutzer das Programm näher bringen
     * soll.
     */
    public void createHelpPanel() {
        this.createEditorPane();
        editorScrollPane = new JScrollPane(this.editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    /**
     * Erzeugt ein EditorPane mit dem man Text mithilfe von HTML unterbringen
     * kann.
     */
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
