package studybook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

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
    private JTree tree;
    private JFileChooser fileChooser;
    private int check;

    public SBActionListener(SBView view, SBController controller) {
        this.view = view;

        this.controller = controller;
        this.fileChooser = fileChooser;
        this.setupFileChooser();

    }

    /**
     * Erstellt einen JFileChosser mit dessen Hilfe Dateien zum Öffnen und
     * Zielorte zum Abspeichern ausgewählt werden können.
     */
    private void setupFileChooser() {
        // FileFilter erstellen, umungewollte Dateien in der
        // Verzeichnisauflistung herauszufiltern
        FileFilter fileFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.getName().toLowerCase().endsWith(".sbprofile") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return "StudyBook Profile (*.sbprofile)";
            }
        };
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    /**
     * Reagiert auf das Anklicken eines MenuItems in der MenuBar.
     *
     * @param event das Action-Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.tree = view.getTree();
        Object obj = e.getSource();
        DefaultMutableTreeNode parentNode;
        DefaultMutableTreeNode node;
        SBNodeStruct nodeInfo;
        view.hideStatusError();
        if (obj instanceof JMenuItem) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                //---Datei---
                // Neues Profil
                case "new":
                    String name = JOptionPane.showInputDialog(null, "Profilname: ", "Neues Profil anlegen", 1);
                    if (name != null) {
                        controller.newProfile(name);
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

                // Profil speichern unter...
                case "saveas":
                    check = fileChooser.showSaveDialog(null);
                    if (check == JFileChooser.APPROVE_OPTION) {
                        controller.saveProfile(fileChooser.getSelectedFile().getPath());
                    }
                    break;

                // Beenden
                case "exit":
                    controller.exit();
                    break;

                //---Bearbeiten---
                // -Hinzufügen-
                // Studiengang
                case "study":
                    System.out.println("neuer Studiengang");
                    //controller.addStudy();
                    break;

                // Semester
                case "semester":
                    node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    nodeInfo = (SBNodeStruct) node.getUserObject();
                    controller.addSemester(nodeInfo.getId());
                    break;

                // Modul
                case "module":
                    node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    nodeInfo = (SBNodeStruct) node.getUserObject();
                    controller.addSemester(nodeInfo.getId());
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
                    controller.showAboutPanel();
                    break;
            }
        }
    }
}
