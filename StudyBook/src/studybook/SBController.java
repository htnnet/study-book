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
 * Oberfläche (View) und den Daten (Model) verantwortlich.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBController {

    private SBModel model;
    private SBView view;
    private SBHelpPanel helpPanel;
    private SBStudyPanel studyPanel;
    private SBSemesterPanel semesterPanel;
    private SBModulePanel modulePanel;
    private JPanel emptyPanel;
    private String activePanel = ""; //Startpanel festlegen
    private boolean initialized = true;
    private boolean profileChanged = false;
    private int activeStudyId = 0;
    private int activeModuleId = 0;
    private int activeSemesterId = 0;

    /**
     * Konstruktor des Controllers bei dem das Programm initialisiert wird.
     *
     * @param model beim Programmstart erstelltes Model
     */
    public SBController(SBModel model) {
        this.model = model;
        this.initialize();
    }

    /**
     * Methode zum Initialisieren des Programms bei Programmstart.
     */
    private void initialize() {
        view = new SBView(this);
        view.createView();
        view.layoutView();

        // Panels aus der grafische Oberfläche holen
        helpPanel = view.getHelpPanel();
        studyPanel = view.getStudyPanel();
        semesterPanel = view.getSemesterPanel();
        modulePanel = view.getModulePanel();
        emptyPanel = view.getEmptyPanel();

        this.loadSettings();

        view.setEditMenuEnabled(false, false, false, false, false, false);
        if (this.checkInstance()) {
            view.showStatusError("StudyBook bereits geöffnet! Bitte alle StudyBook Fenster schließen und neu starten!");
        }
        initialized = false;
    }

    /**
     * Methode zum Prüfen, ob bereits eine Instanz läuft. Falls nicht, wird eine
     * Instanzprüfdatei erstellt.
     *
     * @return true bei schon existierender Instanz, sonst false
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

    /**
     * Methode, die den Lock auf die jetzige Instanz wieder freigibt.
     */
    private void removeInstance() {
        File lock = new File("lock.sblock");
        if (lock.exists()) {
            lock.delete();
        }
    }

    /**
     * Methode zum Erstellen eines neuen Profils.
     *
     * @param name Pfad zur neuen Profildatei
     */
    public void newProfile(String name) {
        model.createProfile(name);
        this.changeProfile(name + ".sbprofile");
    }

    /**
     * Methode zum Wechseln des Profils.
     *
     * @param path Pfad zum neuen Profil
     */
    public void changeProfile(String path) {
        this.save();
        model.changeProfile(path.substring(0, path.length() - 10));
        profileChanged = true;
        view.reloadTree(model.getTreeVector(view));
        view.setRightPanel(new JPanel());
        view.setEditable(true);
        view.setEditMenuEnabled(true, true, false, false, false, false);
    }

    /**
     * Methode zum Laden eines Profils.
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
     * Methode zum Anzeigen des Study-Panels
     *
     * @param studyId Id des Studiengangs, dessen StudyPanel angezeigt werden
     * soll
     */
    public void showStudyPanel(int studyId) {
        view.setEditMenuEnabled(true, false, true, false, true, true);
        if (!initialized && !profileChanged) {
            this.save();
        }
        studyPanel.getGradeTable().emptyCells();
        studyPanel.setFields(model.getStudyPanelValues(studyId, view));

        profileChanged = false;
        view.setRightPanel(studyPanel);

        activePanel = "studyPanel";
        activeStudyId = studyId;

        studyPanel.getGradeTable().populateTable(model.getGradeTable(studyId, view));
    }

    /**
     * Methode zum Anzeigen des Semester-Panels.
     *
     * @param semesterId Id des Semesters, dessen Semester-Panel angezeigt
     * werden soll.
     */
    public void showSemesterPanel(int semesterId) {
        // Sicherstellen bei einem Wechsel von einem Semester-Panel zum
        // anderen das Editieren von Zellen abgeschaltet wird
        if (semesterPanel.getTimeTable().isEditing()) {
            semesterPanel.getTimeTable().getCellEditor().stopCellEditing();
        }

        view.setEditMenuEnabled(true, false, false, true, true, true);
        this.save();

        semesterPanel.getTimeTable().emptyCells();
        semesterPanel.getTimeTable().populateTable(model.getSemesterPanelValues(semesterId, view));

        semesterPanel.getTimeTable().emptyCells();
        semesterPanel.getTimeTable().populateTable(model.getSemesterPanelValues(semesterId, view));

        view.setRightPanel(semesterPanel);
        activePanel = "semesterPanel";
        activeSemesterId = semesterId;
    }

    /**
     * Methode zum Anzeigen des Modul-Panels
     *
     * @param moduleId Id des Moduls, dessen Modul-Panel angezeigt werden soll.
     */
    public void showModulePanel(int moduleId) {
        view.setEditMenuEnabled(false, false, false, false, true, true);
        this.save();

        modulePanel.setFields(model.getModulePanelValues(moduleId, view));

        view.setRightPanel(modulePanel);
        activePanel = "modulePanel";
        activeModuleId = moduleId;
    }

    /**
     * Methode zum Anzeigen des Hilfe-Panels
     */
    public void showHelpPanel() {
        view.setEditMenuEnabled(true, true, false, false, false, false);
        this.save();
        view.getTree().setSelectionRow(-1);
        view.setRightPanel(helpPanel);
        activePanel = "helpPanel";
    }

    /**
     * Methode zum Anzeigen des Über-Dialogs.
     */
    public void showAboutDialog() {
        view.showAboutDialog();
    }

    /**
     * Methode zum Erstellen eines neuen Studiengangs
     *
     * @return Id des neu erstellten Studiengangs
     */
    public int addStudy() {
        int studyId = model.addStudy(view);
        return studyId;
    }

    /**
     * Methode zum Erstellen eines neuen Semesters.
     *
     * @param studyId Id des Studiengangs, zu dem das Semester hinzugefügt
     * werden soll
     * @return Id des neu erstellten Semesters
     */
    public int addSemester(int studyId) {
        int semesterId = model.addSemester(studyId, view);
        return semesterId;
    }

    /**
     * Methode zum Erstellen eines neuen Moduls.
     *
     * @param semesterId Id des Semesters, zu dem das Modul hinzugefügt werden
     * soll.
     * @return Id des neu erstellten Moduls
     */
    public int addModule(int semesterId) {
        int moduleId = model.addModule(semesterId, view);
        return moduleId;
    }

    /**
     * Methode zum Umbennen eines Studiengangs.
     *
     * @param studyId Id des umzubenennenden Studiengangs
     * @param studyName Neuer Name des Studiengangs
     */
    public void renameStudy(int studyId, String studyName) {
        model.renameStudy(studyId, studyName, view);
    }

    /**
     * Methode zum Umbennen eines Semesters.
     *
     * @param semesterId Id des umzubenennenden Semesters
     * @param semesterName neuer Name des Semesters
     */
    public void renameSemester(int semesterId, String semesterName) {
        model.renameSemester(semesterId, semesterName, view);
    }

    /**
     * Methode zum Umbennen eines Moduls.
     *
     * @param moduleId Id des umzubenennenden Moduls
     * @param moduleName neuer Name des Moduls
     */
    public void renameModule(int moduleId, String moduleName) {
        model.renameModule(moduleId, moduleName, view);
    }

    /**
     * Methode zum Löschen eines Studiengangs.
     *
     * @param studyId Id des zu löschenden Studiengangs.
     */
    public void deleteStudy(int studyId) {
        model.deleteStudy(studyId, view);
        view.setRightPanel(emptyPanel);
        view.setEditMenuEnabled(true, true, false, false, false, false);
    }

    /**
     * Methode zum Löschen eines Semesters.
     *
     * @param semesterId Id des zu löschenden Semesters
     */
    public void deleteSemester(int semesterId) {
        model.deleteSemester(semesterId, view);
        view.setRightPanel(emptyPanel);
        view.setEditMenuEnabled(true, true, false, false, false, false);
    }

    /**
     * Methode zum Löschen eines Moduls.
     *
     * @param moduleId Id des zu löschenden Moduls
     */
    public void deleteModule(int moduleId) {
        model.deleteModule(moduleId, view);
        view.setRightPanel(emptyPanel);
        view.setEditMenuEnabled(true, true, false, false, false, false);
    }

    /**
     * Methode zum Speichern der aktuellen Anzeige.
     */
    public void save() {
        this.checkInstance();

        switch (activePanel) {
            case "studyPanel":
                model.saveStudyPanel(studyPanel.getFields(), activeStudyId, view);
                break;
            case "modulePanel":
                model.saveModulePanel(modulePanel.getFields(), activeModuleId, view);
                break;
            case "semesterPanel":
                model.saveSemesterPanel(semesterPanel.getTimeTable().getTimeTableValues(), activeSemesterId, view);
                break;
        }
    }

    /**
     * Methode zum Speichern des zuletzt geladenen Profils.
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
     * Methode zum Laden der letzten Einstellungen. Beinhaltet das zuletzt
     * geladene Profil.
     */
    private void loadSettings() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("settings.sbc"));
            String line = null;
            while ((line = in.readLine()) != null) {
                File profileFile = new File(line + ".sbprofile");
                if (profileFile.exists()) {
                    this.loadProfile(line);
                    view.reloadTree(model.getTreeVector(view));
                } else {
                    view.showStatusError("Profil \""+  line + ".sbprofile\" konnte nicht geladen werden!");
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            view.setRightPanel(helpPanel);
            activePanel = "helpPanel";

        } catch (IOException e) {
        }
    }

    /**
     * Methode zum Holen des aktiven Panels.
     *
     * @return Name des aktiven Panels
     */
    public String getActivePanel() {
        return activePanel;
    }

    /**
     * Methode zum sauberen Beenden des Programms inklusive Speicherung der
     * letzten Anzeige
     */
    public void exit() {
        this.saveSettings();
        this.save();
        this.removeInstance();
        System.exit(0);
    }
}
