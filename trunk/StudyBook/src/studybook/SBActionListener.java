package studybook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * Empfängt die vom Nutzer getätigten Eingaben in der MenuBar und reagiert
 * entsprechend.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBActionListener implements ActionListener {

    private SBView view;
    private SBController controller;
    FileFilter ff = null;

    public SBActionListener(SBView view, SBController controller) {
        this.view = view;
        this.controller = controller;
        ff = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".sbprofile") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "StudyBook Profile (*.sbprofile)";
            }
        };
    }

    /**
     * Reagiert auf das Anklicken eines MenuItems in der MenuBar.
     *
     * @param event das Action-Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        view.hideStatusBar();
        if (obj instanceof JMenuItem) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                //---Datei---
                // Neues Profil
                case "new":
                    String str = JOptionPane.showInputDialog(null, "Profilname: ", "Neues Profil anlegen", 1);
                    if (str != null) {
                        controller.createProfile(str);
                    }
                    break;

                // Profil öffnen
                case "open":
                    JFileChooser chooser = new JFileChooser();

                    chooser.setFileFilter(ff);
                    chooser.setAcceptAllFileFilterUsed(false);
                    int rueckgabeWert = chooser.showOpenDialog(null);
                    if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
                        controller.changeProfile(chooser.getSelectedFile().getPath());
                    }
                    break;

                // Profil speichern
                case "save":

                    break;

                // Profils speichern unter...
                case "saveas":
                    JFileChooser saver = new JFileChooser();
                    saver.setFileFilter(ff);
                    saver.setAcceptAllFileFilterUsed(false);
                    rueckgabeWert = saver.showSaveDialog(null);
                    if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
                        controller.saveProfile(saver.getSelectedFile().getPath());
                    }
                    break;

                // Beenden
                case "exit":
                    controller.exit();
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
                    controller.showModulePanel();
                    break;
            }
        }
    }
}
