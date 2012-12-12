package studybook;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Enthält die Logik des Programms und die von der grafischen Oberfläche
 * darzustellenden Daten, die mittels einer SQL-Datenbankverbindung angefordert
 * werden können.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBModel {

    private Connection conn = null;
    private SBSQL db;
    private String profilname;

    public SBModel() {
        this.db = new SBSQL();
    }

    public void setProfile(String profilname) {
        this.profilname = profilname;
    }

    public String getProfile() {
        return this.profilname;
    }

    public void addModule(int semesterID) {
        db.query("INSERT INTO module (semesterID)"
                + "VALUES (" + semesterID + ");");
    }

    public boolean dbconnect(SBView view) {
        if (!new File(this.profilname + ".sbprofile").exists()) {
            return false;
        } else {
            db.connect(profilname + ".sbprofile");
            view.setFrameTitle("StudyBook - " + this.profilname + ".sbprofile");
            return true;
        }
    }

    public void saveStudyPanel(String fields[], SBView view) {
        if (!this.dbconnect(view)) {
            view.showStatusError("Profil konnte nicht geladen werden!");
        } else {
            db.query("UPDATE allgemeindaten SET studentName = '" + fields[0] + "',"
                    + "studentMatnum='" + fields[1] + "',"
                    + "studentBirth='" + fields[2] + "',"
                    + "studyName='" + fields[3] + "',"
                    + "studyAcad='" + fields[4] + "',"
                    + "studyStart='" + fields[5] + "';");
        }
    }

    public void createProfile(String name) {
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
    }

    public String[] getStudyPanelValues(SBView view) {
        if (!this.dbconnect(view)) {
            view.showStatusError("Profil konnte nicht geladen werden!");
            return null;
        } else {
            try {
                ResultSet rs = db.getResultSet("SELECT * FROM allgemeindaten"); //Alles von der Tabelle allgemeindaten holen
                while (rs.next()) {
                    String fields[] = {rs.getString("studentname"), rs.getString("studentmatnum"), rs.getString("studentbirth"),
                        rs.getString("studyname"), rs.getString("studyacad"), rs.getString("studystart")};
                    return fields;
                }
                return null;
            } catch (SQLException e) {
                System.err.println(e);
                return null;
            }
        }
    }
}
