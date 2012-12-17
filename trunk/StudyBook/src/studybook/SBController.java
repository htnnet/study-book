package studybook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JPanel;

/**
 * Ist für die reibungslose Interkommunikation zwischen der grafischen
 * Oberfläche und der Logik verantwortlich.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBController {

    private SBModel model;
    private SBView view;
    private String profilname = null;
    private JPanel emptyPanel = new JPanel();
    private SBStudyPanel studyPanel = new SBStudyPanel();
    private SBHelpPanel helpPanel = new SBHelpPanel();
    private SBSemesterPanel semesterPanel = new SBSemesterPanel();
    private SBModulePanel modulePanel = new SBModulePanel();
    private String activePanel = ""; //Startpanel festlegen
    private boolean initialized = true;
    private boolean profile_changed = false;
    private int activeStudyID = 0;
    private int activeModuleID = 0;
    private int activeSemesterID = 0;

    /**
     * Konstruktor des Controllers
     *
     * @param model Beim Programmstart erstelltes Model
     */
    public SBController(SBModel model) {
        this.model = model;
        this.initialize();
    }

    /**
     * Methode zum Initialisieren des Programms bei Programmstart
     */
    private void initialize() {
        view = new SBView(this);
        view.createView();
        view.layoutView();
        this.loadSettings();
        view.reloadTree(model.getTreeVector(view));

        view.setEditMenuEnabled(false, false, false, false, false, false);
        if (this.checkInstance()) {
            view.showStatusError("StudyBook bereits geöffnet! Bitte alle StudyBook Fenster schließen und neu starten!");
        }


        initialized = false;
    }

    /**
     * Methode zum Prüfen, ob bereits eine Instanc läuft. Ansonsten wird eine Instanzprüfdatei erstellt.
     *
     * @return true bei schon existierender Instanz, false sonst
     */
    private boolean checkInstance() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("lock.sblock"));
            in.close();
            File lock = new File("lock.sblock");
            return true;
        } catch (FileNotFoundException e) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("lock.sblock"));
                out.flush();
                out.close();
            } catch (IOException e2) {
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private void removeInstance() {
        File lock = new File("lock.sblock");
        if (lock.exists()) {
            lock.delete();
        }
    }

    /**
     * Methode zum Erstellen eines neuen Profils
     *
     * @param name Pfad zur neuen Profildatei
     */
    public void newProfile(String name) {
        model.createProfile(name);
        this.changeProfile(name + ".sbprofile");
    }

    /**
     * Methode zum Wechseln des Profils
     *
     * @param path Pfad zum neuen Profil
     */
    public void changeProfile(String path) {
        this.save();
        model.changeProfile(path.substring(0, path.length() - 10));
        profile_changed = true;
        view.reloadTree(model.getTreeVector(view));
        view.setRightPanel(new JPanel());
        view.setEditable(true);
        view.setEditMenuEnabled(true, true, false, false, false, false);
    }

    /**
     * Methode zum Laden eines Profils
     *
     * @param path Pfad zur Profildatei
     */
    private void loadProfile(String path) {
        model.setProfile(path);
        view.reloadTree(model.getTreeVector(view));
        view.setEditable(true);
        view.setEditMenuEnabled(true, true, false, false, false, false);
    }

    /**
     * Methode zum Anzeigen des Study Panels
     *
     * @param studyID ID des Studiengangs, dessen StudyPanel angezeigt werden soll
     */


    public void showStudyPanel(int studyID) {
        view.setEditMenuEnabled(true, false, true, false, true, true);
        if (!initialized && !profile_changed) {
            this.save();
        }
        studyPanel.getGradeTable().emptyCells();
        studyPanel.setFields(model.getStudyPanelValues(studyID, view));

        profile_changed = false;
        view.setRightPanel(studyPanel);

        activePanel = "studyPanel";
        activeStudyID = studyID;

        studyPanel.getGradeTable().populateTable(model.getGradeTable(studyID, view));
    }

    /**
     * Methode zum Anzeigen des Semester Panels
     *
     * @param semesterID ID des Semesters, dessen Semester Panel angezeigt werden soll
     */
    public void showSemesterPanel(int semesterID) {
        if (semesterPanel.getTimeTable().isEditing()) {
            semesterPanel.getTimeTable().getCellEditor().stopCellEditing();
        }

        view.setEditMenuEnabled(true, false, false, true, true, true);
        this.save();

        semesterPanel.getTimeTable().emptyCells();
        semesterPanel.getTimeTable().populateTable(model.getSemesterPanelValues(semesterID, view));

        semesterPanel.getTimeTable().emptyCells();
        semesterPanel.getTimeTable().populateTable(model.getSemesterPanelValues(semesterID, view));

        view.setRightPanel(semesterPanel);
        activePanel = "semesterPanel";
        activeSemesterID = semesterID;
    }

    /**
     * Methode zum Anzeigen des Module Panels
     *
     * @param moduleID ID des Moduls, dessen Module Panel angezeigt werden soll
     */
    public void showModulePanel(int moduleID) {
        view.setEditMenuEnabled(false, false, false, false, true, true);
        this.save();

        modulePanel.setFields(model.getModulePanelValues(moduleID, view));

        view.setRightPanel(modulePanel);
        activePanel = "modulePanel";
        activeModuleID = moduleID;
    }

    /**
     * Methode zum Anzeigen des Hilfe Panels
     */
    public void showHelpPanel() {
        view.setEditMenuEnabled(true, true, false, false, false, false);
        this.save();
        view.getTree().setSelectionRow(-1);
        view.setRightPanel(helpPanel);
        activePanel = "helpPanel";
    }

    /**
     * Methode zum Anzeigen des Über-Dialogs
     */
    public void showAboutDialog() {
        System.out.println("About");
    }

    /**
     * Methode zum Erstellen eines neuen Studiengangs
     *
     * @return ID des neu erstellten Studiengangs
     */
    public int addStudy() {
        int studyID = model.addStudy(view);
        return studyID;
    }

    /**
     * Methode zum Erstellen eines neuen Semesters
     *
     * @param studyID ID des Studiengangs, zu dem das Semester hinzugefügt werden soll
     * @return ID des neu erstellten Semesters
     */
    public int addSemester(int studyID) {
        int semesterID = model.addSemester(studyID, view);
        return semesterID;
    }

    /**
     * Methode zum Erstellen eines neuen Moduls
     *
     * @param semesterID ID des Semesters, zu dem das Modul hinzugefügt werden soll
     * @return ID des neu erstellten Moduls
     */
    public int addModule(int semesterID) {
        int moduleID = model.addModule(semesterID, view);
        return moduleID;
    }

    /**
     * Methode zum Umbennen eines Studiengangs
     *
     * @param studyID ID des umzubenennenden Studiengangs
     * @param studyName Neuer Name des Studiengangs
     */
    public void renameStudy(int studyID, String studyName) {
        model.renameStudy(studyID, studyName, view);
    }

    /**
     * Methode zum Umbennen eines Semesters
     *
     * @param semesterID ID des umzubenennenden Semesters
     * @param semesterName Neuer Name des Semesters
     */
    public void renameSemester(int semesterID, String semesterName) {
        model.renameSemester(semesterID, semesterName, view);
    }

    /**
     * Methode zum Umbennen eines Moduls
     *
     * @param moduleID ID des umzubenennenden Moduls
     * @param moduleName Neuer Name des Moduls
     */
    public void renameModule(int moduleID, String moduleName) {
        model.renameModule(moduleID, moduleName, view);
    }

    /**
     * Methode zum Löschen eines Studiengangs
     *
     * @param studyID ID des zu löschenden Studiengangs
     */
    public void deleteStudy(int studyID) {
        model.deleteStudy(studyID, view);
        view.setRightPanel(emptyPanel);
        view.setEditMenuEnabled(true, true, false, false, false, false);
    }

    /**
     * Methode zum Löschen eines Semesters
     * @param semesterID ID des zu löschenden Semesters
     */
    public void deleteSemester(int semesterID) {
        model.deleteSemester(semesterID, view);
        view.setRightPanel(emptyPanel);
        view.setEditMenuEnabled(true, true, false, false, false, false);
    }

    /**
     * Methode zum Löschen eines Moduls
     *
     * @param moduleID ID des zu löschenden Moduls
     */
    public void deleteModule(int moduleID) {
        model.deleteModule(moduleID, view);
        view.setRightPanel(emptyPanel);
        view.setEditMenuEnabled(true, true, false, false, false, false);
    }

    /**
     * Methode zum Speichern der aktuellen Anzeige
     */
    public void save() {
        this.checkInstance();

        switch (activePanel) {
            case "studyPanel":
                model.saveStudyPanel(studyPanel.getFields(), activeStudyID, view);
                break;
            case "modulePanel":
                model.saveModulePanel(modulePanel.getFields(), activeModuleID, view);
                break;
            case "semesterPanel":
                model.saveSemesterPanel(semesterPanel.getTimeTable().getTimeTableValues(), activeSemesterID, view);
                break;
        }
    }

    /**
     * Methode zum Speichern des zuletzt geladenen Profils
     */
    public void saveSettings() {
        if (model.getProfile() != null) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("settings.sbc"));
                out.write(model.getProfile());
                out.flush();
                out.close();
            } catch (IOException e) {
            } catch (NullPointerException e) {
                System.err.println(e);
            }
        }

    }

    /**
     * Methode zum Laden der letzten Einstellungen.
     * Beinhaltet Lesen des zuletzt geladenen Profils.
     */
    private void loadSettings() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("settings.sbc"));
            String line = null;
            while ((line = in.readLine()) != null) {
                File profileFile = new File(line + ".sbprofile");
                if (profileFile.exists()) {
                    this.loadProfile(line);
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            view.setRightPanel(helpPanel);
            activePanel = "sbhelppanel";
        } catch (IOException e) {
        }
    }

    /**
     * Methode zum Holen des aktiven Panels
     *
     * @return Name des Aktiven Panels
     */
    public String getActivePanel() {
        return activePanel;
    }

    /**
     * Methode zum sauberen Beenden des Programms inklusive Speicherung der letzten Anzeige
     */
    public void exit() {
        this.saveSettings();
        this.save();
        this.removeInstance();
        System.exit(0);
    }
}
