package studybook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JTree;

/**
 * Empfängt die vom Nutzer getätigten Eingaben in der MenuBar und dem
 * Kontextmenü und reagiert entsprechend.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBActionListener implements ActionListener {

    private SBView view;
    private SBController controller;
    private JTree tree;
    private JFileChooser fileChooser;
    private int check;

    /**
     * Konstruktor der Klasse SBActionListener, der die View erhält, um
     * Fehlermeldungen in der StatusBar ausgeben zu können und den Controller
     * erhält, um auf Eingaben entsprechend reagieren zu können.
     *
     * @param view das View-Objekt
     * @param controller das Controller-Objekt
     */
    public SBActionListener(SBView view, SBController controller) {
        this.view = view;
        this.controller = controller;
        fileChooser = view.getFileChooser();
    }

    /**
     * Reagiert auf das Anklicken eines MenuItems in der MenuBar und in dem
     * Popup-Menu.
     *
     * @param event das Action-Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.tree = view.getTree();
        Object obj = e.getSource();
        view.hideStatusError();
        if (obj instanceof JMenuItem) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                //---Datei---
                // Neues Profil
                case "new":
                    check = fileChooser.showSaveDialog(null);
                    if (check == JFileChooser.APPROVE_OPTION) {
                        File profileFile = new File(fileChooser.getSelectedFile().getPath() + ".sbprofile");
                        if (profileFile.exists()) { // falls die Datei schon existiert
                            view.showStatusError("Es existiert bereits ein Profil mit diesem Namen!");
                        } else {
                            controller.newProfile(fileChooser.getSelectedFile().getPath());
                        }
                    }
                    break;

                // Profil öffnen
                case "open":
                    check = fileChooser.showOpenDialog(null);
                    if (check == JFileChooser.APPROVE_OPTION) {
                        controller.changeProfile(fileChooser.getSelectedFile().getPath());
                    }
                    break;

                // Profil speichern
                case "save":
                    controller.save();
                    break;

                // Beenden
                case "exit":
                    controller.exit();
                    break;

                //---Bearbeiten---
                // -Hinzufügen-
                // Studiengang
                case "study":
                    view.addTreeNode(new SBNodeStruct("Neuer Studiengang", 1));
                    break;

                // Semester
                case "semester":
                    view.addTreeNode(new SBNodeStruct("Neues Semester", 2));
                    break;

                // Modul
                case "module":
                    view.addTreeNode(new SBNodeStruct("Neues Modul", 3));
                    break;

                // Löschen
                case "delete":
                    view.removeNode();
                    break;

                // Umbenennen
                case "rename":
                    tree.startEditingAtPath(tree.getSelectionPath());
                    break;
                //---Hilfe---
                // Hilfe
                case "help":
                    controller.showHelpPanel();
                    break;

                // Über
                case "about":
                    controller.showAboutDialog();
                    break;
            }
        }
    }
}
