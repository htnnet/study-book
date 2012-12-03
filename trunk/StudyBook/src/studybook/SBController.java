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
    private String activePanel = "sbstudypanel"; //Startpanel festlegen
    private boolean initialize = true;
    private boolean profile_changed = false;
    private boolean profileSaveAs = false;

    public SBController(SBModel model) {
        this.model = model;
        this.initialize();
    }

    private void loadSettings() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("settings.sbc"));
            String line = null;
            while ((line = in.readLine()) != null) {
                profilname = line;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void saveSettings() {
        if (profilname != null) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("settings.sbc"));
                out.write(profilname);
                out.flush();
                out.close();
            } catch (IOException e) {
            }
        }
        view.save();
    }

    private void initialize() {
        this.loadSettings();
        view = new SBView(this, sbstudypanel, sbhelppanel);
        view.createMainFrame();
        view.layoutMainFrame();
        this.setStudyPanel();
        initialize = false;
    }

    public void showModulePanel() {
        view.save();
        view.setRightPanel(sbmodulepanel);
        activePanel = "sbmodulepanel";
    }

    public void setHelpPanel() {
        view.save();
        view.setRightPanel(sbhelppanel);
        activePanel = "sbhelppanel";
    }

    public void exit() {
        this.saveSettings();
        System.exit(0);
    }

    public void saveProfile(String path) {
        profileSaveAs = true;
        if(path.substring(path.length()-10,path.length()).equals(".sbprofile")) {
            path = path.substring(0,path.length()-10);
        }
        profilname = path+".sbprofile";
        this.createProfile(path);
    }

    public void setStudyPanel() {
        SBModel db = this.dbconnect();
        if (db != null) {
            if (!initialize && !profile_changed) {
                view.save();
            }
            try {
                ResultSet rs = db.get("SELECT * FROM allgemeindaten"); //Alles von der Tabelle allgemeindaten holen
                while (rs.next()) {
                    sbstudypanel.setFields(rs.getString("studentname"),
                            rs.getString("studentbirth"),
                            rs.getString("studentmatnum"),
                            rs.getString("studyname"),
                            rs.getString("studyacad"),
                            rs.getString("studystart"));
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            view.showError("Fehler bei dem Lesen des Profils! Profil erstellt?");
            view.setStandardTitle();
        }
        profile_changed = false;
        System.out.println(profilname);
        activePanel = "sbstudypanel";
        view.setRightPanel(sbstudypanel);
    }

    public String getActivePanel() {
        return activePanel;
    }

    public void changeProfile(String path) {
        System.out.println("changeprofile " +path);
        view.save();
        profilname = path.substring(0,path.length()-10);
        profile_changed = true;
        this.dbconnect();
        this.setStudyPanel();
    }

    public SBModel dbconnect() {
        if (!new File(profilname + ".sbprofile").exists()) {
            view.showError("Noch kein Profil erstellt!");
            return null;
        } else {
            profileSaveAs = false;
            SBModel sqltest = this.model;
            sqltest.connect(profilname + ".sbprofile");
            view.setFrameTitle(profilname+".sbprofile");
            System.out.println("dbconnect "+profilname);
            return sqltest;
        }
    }

    public void createProfile(String name) {
        SBModel db = this.model;
        db.connect(name + ".sbprofile");
        db.query("CREATE TABLE IF NOT EXISTS 'allgemeindaten' ("
                + "'studentname' varchar(100) NOT NULL,"
                + "'studentbirth' varchar(100) NOT NULL,"
                + "'studentmatnum' varchar(100) NOT NULL,"
                + "'studyname' varchar(100) NOT NULL,"
                + "'studyacad' varchar(100) NOT NULL,"
                + "'studystart' varchar(20) NOT NULL);");
        db.query("INSERT INTO allgemeindaten (studentname,studentbirth,studentmatnum,studyname,studyacad,studystart)"
                + "VALUES ('','','','','','');");
        db.query("CREATE TABLE IF NOT EXISTS studiengaenge (name);"); //Erstelle Tabelle fuer Studiengaenge
        db.query("INSERT INTO studiengaenge (name) VALUES ('testname')"); //Fuege Teststudiengang hinzu
        try {
            ResultSet rs = db.get("SELECT * FROM studiengaenge"); //Alles von der Tabelle Studiengaenge holen
            while (rs.next()) {
                System.out.println("name = " + rs.getString("name")); //Und alle Werte "name" nacheinander auflisten
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        this.changeProfile(name+".sbprofile");
    }
}
