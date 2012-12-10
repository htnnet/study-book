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
        SBModel db = this.dbconnect();
        if (db != null) {
            if (!initialize && !profile_changed) {
                this.save();
            }
            try {
                ResultSet rs = db.get("SELECT * FROM allgemeindaten"); //Alles von der Tabelle allgemeindaten holen
                while (rs.next()) {
                    String fields[] = {rs.getString("studentname"),rs.getString("studentmatnum"),rs.getString("studentbirth"),
                        rs.getString("studyname"),rs.getString("studyacad"),rs.getString("studystart")};
                    sbstudypanel.setFields(fields);
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        } else {
            view.showStatusError("Fehler bei dem Lesen des Profils! Profil erstellt?");
            view.setFrameTitle("StudyBook");
        }
        profile_changed = false;
        System.out.println(profilname);
        activePanel = "sbstudypanel";
        view.setRightPanel(sbstudypanel);
    }

    public void showSemesterPanel() {
        this.save();
        view.setRightPanel(sbsemesterpanel);
        activePanel = "sbsemesterpanel";
    }

    public void showModulePanel() {
        this.save();
        view.setRightPanel(sbmodulepanel);
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
        this.save();
    }

    private void initialize() {
        this.loadSettings();
        view = new SBView(this);
        view.createView();
        view.layoutView();
        this.showStudyPanel();
        initialize = false;
    }



    public void save() {
        switch (activePanel) {
            case "sbstudypanel":
                System.err.println("save studyPanel");
                SBModel db = this.dbconnect();
                if (db != null) {
                    String fields[] = sbstudypanel.getFields();
                    db.query("UPDATE allgemeindaten SET studentName = '" + fields[0] + "',"
                            + "studentMatnum='" + fields[1] + "',"
                            + "studentBirth='" + fields[2] + "',"
                            + "studyName='" + fields[3] + "',"
                            + "studyAcad='" + fields[4] + "',"
                            + "studyStart='" + fields[5] + "';");
                }
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
        profilname = path + ".sbprofile";
        this.createProfile(path);
    }





    public String getActivePanel() {
        return activePanel;
    }

    public void changeProfile(String path) {
        System.out.println("changeprofile " + path);
        this.save();
        profilname = path.substring(0, path.length() - 10);
        profile_changed = true;
        this.dbconnect();
        this.showStudyPanel();
    }

    public SBModel dbconnect() {
        if (!new File(profilname + ".sbprofile").exists()) {
            view.showStatusError("Noch kein Profil erstellt!");
            return null;
        } else {
            profileSaveAs = false;
            SBModel sqltest = this.model;
            sqltest.connect(profilname + ".sbprofile");
            view.setFrameTitle("StudyBook - " + profilname + ".sbprofile");
            System.out.println("dbconnect " + profilname);
            return sqltest;
        }
    }

    public void createProfile(String name) {
        SBModel db = this.model;
        db.connect(name + ".sbprofile");
        db.query("CREATE TABLE IF NOT EXISTS 'allgemeindaten' ("
                + "'studentName' varchar(100) NOT NULL,"
                + "'studentBirth' varchar(100) NOT NULL,"
                + "'studentMatnum' varchar(100) NOT NULL,"
                + "'studyName' varchar(100) NOT NULL,"
                + "'studyAcad' varchar(100) NOT NULL,"
                + "'studyStart' varchar(20) NOT NULL);");
        db.query("CREATE TABLE IF NOT EXISTS 'module' ("
                + "'semesterID' int(1000) NOT NULL,"
                + "'academicName' varchar(100) NOT NULL,"
                + "'academicRoom' varchar(100) NOT NULL,"
                + "'academicTel' varchar(100) NOT NULL,"
                + "'academicMail' varchar(100) NOT NULL,"
                + "'examOneType' varchar(100) NOT NULL,"
                + "'examOneRoom' varchar(20) NOT NULL"
                + "'examOneDate' varchar(100) NOT NULL,"
                + "'examOneTime' varchar(100) NOT NULL,"
                + "'examOneCredits' varchar(100) NOT NULL,"
                + "'examOneGrade' varchar(100) NOT NULL,"
                + "'examTwoType' varchar(100) NOT NULL,"
                + "'examTwoRoom' varchar(20) NOT NULL"
                + "'examTwoDate' varchar(100) NOT NULL,"
                + "'examTwoTime' varchar(100) NOT NULL,"
                + "'examTwoCredits' varchar(100) NOT NULL,"
                + "'examTwoGrade' varchar(100) NOT NULL,"
                + "'note' varchar(10000) NOT NULL);");
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
        //!!!!!!!!!ACHTUNG MUSS HIER ENTFERNT WERDEN BEI ERSTELLUNG VON MODULEN UEBER BAUM
        this.addModule(1);
        this.changeProfile(name + ".sbprofile");
    }

    public void addModule(int semesterID) {
        ////////////Nicht dynamisch, da Semester noch nicht implementiert!
        if(semesterID == 1) {
            //Erstelle neues Modul
            SBModel db = this.dbconnect();
            db.query("INSERT INTO module (semesterID)"
                + "VALUES ("+semesterID+");");
        }
    }
}