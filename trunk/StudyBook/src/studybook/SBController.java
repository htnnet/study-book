package studybook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
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
    private SBStudyPanel sbstudypanel = new SBStudyPanel();
    private SBHelpPanel sbhelppanel = new SBHelpPanel();
    private SBModulePanel sbmodulepanel = new SBModulePanel();
    private SBSemesterPanel sbsemesterpanel = new SBSemesterPanel();
    private String activePanel = "sbstudypanel"; //Startpanel festlegen
    private boolean initialized = true;
    private boolean profile_changed = false;
    private boolean profileSaveAs = false;
    private int activeStudyID = 0;
    private int activeModuleID = 0;
    private int activeSemesterID = 0;

    public SBController(SBModel model) {
        this.model = model;
        this.initialize();
    }

    private void initialize() {

        this.loadSettings();

        view = new SBView(this);
        view.createView();
        view.reloadTree(model.getTreeVector(view));
        view.layoutView();

        initialized = false;
    }


    public void showStudyPanel(int studyID) {
        view.setEditMenuEnabled(true, false, true, false, true, true);
        if (!initialized && !profile_changed) {
            this.save();
        }
        sbstudypanel.setFields(model.getStudyPanelValues(studyID,view));

        profile_changed = false;
        view.setRightPanel(sbstudypanel);

        activePanel = "sbstudypanel";
        activeStudyID = studyID;

        // TESTDATEN AUS DER DATENBANK
        /*String[][] cells = {{"GELEK2", "6", "2.5"},
                            {"Klausur", "3", "3.0"},
                            {"Labor", "3", "2.0"}
                            };*/

        sbstudypanel.getGradeTable().populateTable(model.getGradeTable(studyID, view));
    }

    public void showSemesterPanel(int semesterID) {
        view.setEditMenuEnabled(true, false, false, true, true, true);
        this.save();
        

        sbsemesterpanel.getTimeTable().populateTable(model.getSemesterPanelValues(semesterID, view));

        // DATEN AUS DEM STUNDENPLAN HOLEN UND AUSGEBEN
        sbsemesterpanel.getTimeTable().getTimeTableValues();
        view.setRightPanel(sbsemesterpanel);
        activePanel = "sbsemesterpanel";
        activeSemesterID = semesterID;
    }

    public void showModulePanel(int moduleID) {
        view.setEditMenuEnabled(false, false, false, false, true, true);
        this.save();
        sbmodulepanel.setFields(model.getModulePanelValues(moduleID,view));
        view.setRightPanel(sbmodulepanel);
        //sbmodulepanel.getTable().populateGradeTable(cellvalues);
        activePanel = "sbmodulepanel";
        activeModuleID = moduleID;
    }

    public void showHelpPanel() {
        this.save();
        view.setRightPanel(sbhelppanel);
        activePanel = "sbhelppanel";
    }

    public void showAboutPanel() {
        System.out.println("About");
    }

    private void loadSettings() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("settings.sbc"));
            String line = null;
            while ((line = in.readLine()) != null) {
                model.setProfile(line);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void saveSettings() {
        if (model.getProfile() != null) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("settings.sbc"));
                out.write(model.getProfile());
                out.flush();
                out.close();
            } catch (IOException e) {
            }
        }

    }

    public int addStudy() {
        int studyID = model.addStudy();
        return studyID;
    }

    public int addSemester(int studyID) {
        int semesterID = model.addSemester(studyID);
        return semesterID;
    }

    public int addModule(int semesterID) {
        int moduleID = model.addModule(semesterID);
        return moduleID;
    }

    public void deleteStudy(int studyID) {
        model.deleteStudy(studyID,view);
    }

    public void deleteSemester(int semesterID) {
        model.deleteSemester(semesterID,view);
    }

    public void deleteModule(int moduleID) {
        model.deleteModule(moduleID,view);
    }


    public void renameStudy(int studyID, String studyName) {
        model.renameStudy(studyID, studyName, view);
    }

    public void renameSemester(int semesterID, String semesterName) {
        model.renameSemester(semesterID, semesterName, view);
    }

    public void renameModule(int moduleID, String moduleName) {
        model.renameModule(moduleID, moduleName, view);
    }

    public void save() {
        switch (activePanel) {
            case "sbstudypanel":
                System.err.println("save studyPanel");
                model.saveStudyPanel(sbstudypanel.getFields(), activeStudyID, view);
                break;
            case "sbmodulepanel":
                System.err.println("save modulePanel");
                model.saveModulePanel(sbmodulepanel.getFields(), activeModuleID, view);
                break;
            case "sbsemesterpanel":
                System.err.println("save semesterPanel");
                model.saveSemesterPanel(sbsemesterpanel.getTimeTable().getTimeTableValues(), activeSemesterID, view);
                break;
        }
    }

    public void exit() {
        this.saveSettings();
        this.save();
        System.exit(0);
    }

    public void saveProfile(String path) {
        profileSaveAs = true;
        if (path.substring(path.length() - 10, path.length()).equals(".sbprofile")) {
            path = path.substring(0, path.length() - 10);
        }
        model.setProfile(path + ".sbprofile");
        this.newProfile(path);
    }

    public String getActivePanel() {
        return activePanel;
    }

    public void changeProfile(String path) {
        System.out.println("changeprofile " + path);
        this.save();
        model.changeProfile(path.substring(0, path.length() - 10));
        profile_changed = true;
        view.reloadTree(model.getTreeVector(view));
        view.setRightPanel(new JPanel());
    }

    public void newProfile(String name) {
        model.createProfile(name);
        this.changeProfile(name + ".sbprofile");
    }


}
