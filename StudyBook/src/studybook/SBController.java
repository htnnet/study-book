package studybook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.tree.TreePath;

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
    private boolean initialize = true;
    private boolean profile_changed = false;
    private boolean profileSaveAs = false;

    public SBController(SBModel model) {
        this.model = model;
        this.initialize();
    }

    public void showStudyPanel() {
        view.setEditMenuEnabled(true);
        view.setModuleMenuItemEnabled(false);

        if (!initialize && !profile_changed) {
            this.save();
        }

        sbstudypanel.setFields(model.getStudyPanelValues(view));

        profile_changed = false;
        activePanel = "sbstudypanel";
        view.setRightPanel(sbstudypanel);
    }

    public void showSemesterPanel() {
        view.setEditMenuEnabled(true);
        view.setModuleMenuItemEnabled(true);
        this.save();
        view.setRightPanel(sbsemesterpanel);
        //sbsemesterpanel.getTable().populateTimeTable(cellvalues);
        activePanel = "sbsemesterpanel";
    }

    public void showModulePanel() {
        view.setEditMenuEnabled(true);
        view.setModuleMenuItemEnabled(true);
        this.save();
        view.setRightPanel(sbmodulepanel);
        //sbmodulepanel.getTable().populateGradeTable(cellvalues);
        activePanel = "sbmodulepanel";
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
        this.save();
    }

    public void reloadTree() {
        Vector<SBNodeStruct> v = new Vector<SBNodeStruct>();
        v.add(new SBNodeStruct("root", 0, 0));
        v.add(new SBNodeStruct("Technische Informatik B.Sc.", 0, 1));
        v.add(new SBNodeStruct("1. Semester", 0, 2));
        v.add(new SBNodeStruct("MATHE2", 0, 3));
        v.add(new SBNodeStruct("ENGL", 1, 3));
        v.add(new SBNodeStruct("GELEK1", 2, 3));
        v.add(new SBNodeStruct("PROG1", 3, 3));
        v.add(new SBNodeStruct("INFORM", 4, 3));
        v.add(new SBNodeStruct("2. Semester", 1, 2));
        v.add(new SBNodeStruct("3. Semester", 2, 2));
        v.add(new SBNodeStruct("Informatik M.Sc.", 1, 1));
        v.add(new SBNodeStruct("1. Semester", 0, 2));
        v.add(new SBNodeStruct("Informatik M.Sc.", 1, 1));

        view.reloadTree(v);
    }

    private void initialize() {
        Vector<SBNodeStruct> v = new Vector<SBNodeStruct>();
        v.add(new SBNodeStruct("root", 0, 0));
        v.add(new SBNodeStruct("Technische Informatik B.Sc.", 0, 1));
        v.add(new SBNodeStruct("1. Semester", 0, 2));
        v.add(new SBNodeStruct("MATHE2", 0, 3));
        v.add(new SBNodeStruct("ENGL", 1, 3));
        v.add(new SBNodeStruct("GELEK1", 2, 3));
        v.add(new SBNodeStruct("PROG1", 3, 3));
        v.add(new SBNodeStruct("INFORM", 4, 3));
        v.add(new SBNodeStruct("2. Semester", 1, 2));
        v.add(new SBNodeStruct("3. Semester", 2, 2));

        this.loadSettings();
        view = new SBView(this);
        view.createView();
        view.reloadTree(v);

        view.reloadTree(v);
        view.layoutView();
        this.showStudyPanel();

        initialize = false;
    }

    public void save() {
        switch (activePanel) {
            case "sbstudypanel":
                System.err.println("save studyPanel");
                model.saveStudyPanel(sbstudypanel.getFields(), view);
                break;
            case "sbmodulepanel":
                break;
        }
    }

    public void exit() {
        this.saveSettings();
        System.exit(0);
    }

    public void saveProfile(String path) {
        profileSaveAs = true;
        if (path.substring(path.length() - 10, path.length()).equals(".sbprofile")) {
            path = path.substring(0, path.length() - 10);
        }
        model.setProfile(path + ".sbprofile");
        this.createProfile(path);
    }

    public String getActivePanel() {
        return activePanel;
    }

    public void changeProfile(String path) {
        System.out.println("changeprofile " + path);
        this.save();
        model.setProfile(path.substring(0, path.length() - 10));
        profile_changed = true;
        this.showStudyPanel();
    }

    public void showStatusError(String error) {
        view.showStatusError(error);
    }

    public void createProfile(String name) {
        model.createProfile(name);
        //!!!!!!!!!ACHTUNG MUSS HIER ENTFERNT WERDEN BEI ERSTELLUNG VON MODULEN UEBER BAUM
        this.addModule(1);
        this.changeProfile(name + ".sbprofile");
    }

    public void addModule(int semesterID) {
        ////////////Nicht dynamisch, da Semester noch nicht implementiert!
        if (semesterID == 1) {
            //Erstelle neues Modul
            model.addModule(semesterID);
        }
    }
}
