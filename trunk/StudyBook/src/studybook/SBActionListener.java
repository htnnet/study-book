package studybook;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

/**
 * Empfängt die vom Nutzer getätigten Eingaben in der MenuBar und
 * reagiert entsprechend.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBActionListener implements ActionListener {
    private SBView view;
    private SBController controller;
    public SBActionListener(SBView view, SBController controller) {
        this.view = view;
        this.controller = controller;
    }

     /**
     * Reagiert auf das Anklicken eines MenuItems in der MenuBar.
     *
     * @param event das Action-Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj instanceof JMenuItem) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                //---Datei---
                // Neues Profil
                case "new":
                    break;

                // Profil öffnen
                case "open":
                    controller.setStudyPanel();
                    break;

                // Profil speichern
                case "save":
                    break;

                // Profils speichern unter...
                case "saveas":
                    break;

                // Beenden
                case "exit":
                    System.exit(0);
                    break;

                //---Bearbeiten---
                // Knoten hinzufügen
                case "add":
                    break;

                // Knoten entfernen
                case "remove":
                    break;

                //---Hilfe---
                // Hilfe
                case "help":
                    controller.setHelpPanel();

                // Über
                case "about":
                    //JLabel mainlabel = new JLabel(new ImageIcon(getClass().getResource("/bilder/transp.gif")));
                    //mainlabel.setMinimumSize(new Dimension(20,20));
                    //mainframe.setMainFrame(mainlabel);
                    break;
            }
        }
    }
}
