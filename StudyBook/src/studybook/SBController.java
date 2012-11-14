package studybook;

import java.io.File;
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
    private String profilname;
    private SBStudyPanel sbstudypanel = new SBStudyPanel();
    private SBHelpPanel sbhelppanel = new SBHelpPanel();
    private String activePanel = "sbstudypanel"; //Startpanel festlegen
    private boolean initialize = true;

    public SBController(SBModel model) {
        this.model = model;
        profilname = "test4";
        this.createProfile(profilname); //Testaufruf createProfile
        this.initialize();
    }

    private void initialize() {
        view = new SBView(this,sbstudypanel,sbhelppanel);
        view.createMainFrame();
        view.layoutMainFrame();
        this.setStudyPanel();
        initialize = false;
    }

    public void setHelpPanel() {
        view.save();
        view.setRightPanel(sbhelppanel);
        activePanel = "sbhelppanel";
    }

    public void setStudyPanel() {
        SBModel db = this.dbconnect();
        if(db != null) {
            if(!initialize) view.save();
            try {
                ResultSet rs = db.get("SELECT * FROM allgemeindaten"); //Alles von der Tabelle Studiengaenge holen
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
            view.setRightPanel(sbstudypanel);
            activePanel = "sbstudypanel";
        } else {
            view.showError("Fehler bei dem Lesen des Profils! Profil erstellt?");
        }
    }

    public String getActivePanel() {
        return activePanel;
    }

    public SBModel dbconnect() {
        if(!new File(profilname+".sb").exists()) {
            view.showError("Noch kein Profil erstellt!");
            return null;
        } else {
            SBModel sqltest = this.model;
            sqltest.connect(profilname + ".sb");
            return sqltest;
        }
    }

    private void createProfile(String name) {
        SBModel db = this.model;
        db.connect(name + ".profile");
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
    }
}
