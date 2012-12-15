package studybook;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

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
    private boolean connected = false;

    public SBModel() {
        this.db = new SBSQL();
    }

    public void setProfile(String profilname) {
        this.profilname = profilname;
    }

    public String getProfile() {
        return this.profilname;
    }

    public int addStudy() {
        db.query("INSERT INTO studiengaenge (name)"
                + "VALUES ('Neuer Studiengang');");
        int id = 0;
        ResultSet idRS = db.getResultSet("SELECT MAX(id) AS id FROM studiengaenge");
        try {
            while (idRS.next()) {
                id = Integer.parseInt(idRS.getString("id"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return id;
    }

    public int addSemester(int studyID) {
        db.query("INSERT INTO semester (studyID,name)"
                + "VALUES (" + studyID + ",'Neues Semester');");
        int id = 0;
        ResultSet idRS = db.getResultSet("SELECT MAX(id) AS id FROM semester");
        try {
            while (idRS.next()) {
                id = Integer.parseInt(idRS.getString("id"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return id;
    }

    public int addModule(int semesterID) {
        db.query("INSERT INTO module (semesterID,name)"
                + "VALUES (" + semesterID + ",'Neues Modul');");
        int id = 0;
        ResultSet idRS = db.getResultSet("SELECT MAX(id) AS id FROM module");
        try {
            while (idRS.next()) {
                id = Integer.parseInt(idRS.getString("id"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return id;
    }

    public void dbconnect(SBView view) {
        if (!new File(this.profilname + ".sbprofile").exists()) {
            connected = false;
        } else {
            db.connect(profilname + ".sbprofile");
            view.setFrameTitle("StudyBook - " + this.profilname + ".sbprofile");
            connected = true;
        }

    }

    public void changeProfile(String profilePath) {
        this.setProfile(profilePath);
        db.close();
        connected = false;
    }

    public void saveStudyPanel(String fields[], int studyID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("UPDATE studiengaenge SET studentName='" + fields[0] + "',"
                + "studentMatnum='" + fields[1] + "',"
                + "studentBirth='" + fields[2] + "',"
                + "studyName='" + fields[3] + "',"
                + "studyAcad='" + fields[4] + "',"
                + "studyStart='" + fields[5] + "' WHERE id=" + studyID + ";");
    }

    public void saveModulePanel(String fields[], int moduleID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("UPDATE module SET academicName='" + fields[0] + "', academicRoom='" + fields[1] + "', academicTel='" + fields[2] + "',"
                + "academicMail='" + fields[3] + "', examOneType='" + fields[4] + "', examOneRoom='" + fields[5] + "',"
                + "examOneDate='" + fields[6] + "', examOneTime='" + fields[7] + "', examOneCredits='" + fields[8] + "',"
                + "examOneGrade='" + fields[9] + "', examTwoType='" + fields[10] + "', examTwoRoom='" + fields[11] + "',"
                + "examTwoDate='" + fields[12] + "', examTwoTime='" + fields[13] + "', examTwoCredits='" + fields[14] + "',"
                + "examTwoGrade='" + fields[15] + "', note='" + fields[16] + "'"
                + " WHERE id=" + moduleID + ";");
    }

    public void createProfile(String name) {
        db.connect(name + ".sbprofile");
        db.query("CREATE TABLE IF NOT EXISTS 'studiengaenge' ("
                + "'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "'name' varchar(100) NOT NULL,"
                + "'studentName' varchar(100) DEFAULT '' NOT NULL,"
                + "'studentBirth' varchar(100) DEFAULT '' NOT NULL,"
                + "'studentMatnum' varchar(100) DEFAULT '' NOT NULL,"
                + "'studyName' varchar(100) DEFAULT '' NOT NULL,"
                + "'studyAcad' varchar(100) DEFAULT '' NOT NULL,"
                + "'studyStart' varchar(20) DEFAULT '' NOT NULL);");
        db.query("CREATE TABLE IF NOT EXISTS 'semester' ("
                + "'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "'studyID' int(1000) NOT NULL,"
                + "'name' varchar(100) NOT NULL,"
                + "'zeile0' varchar(100000) DEFAULT '' NOT NULL,"
                + "'zeile1' varchar(100000) DEFAULT '' NOT NULL,"
                + "'zeile2' varchar(100000) DEFAULT '' NOT NULL,"
                + "'zeile3' varchar(100000) DEFAULT '' NOT NULL,"
                + "'zeile4' varchar(100000) DEFAULT '' NOT NULL,"
                + "'zeile5' varchar(100000) DEFAULT '' NOT NULL,"
                + "'zeile6' varchar(100000) DEFAULT '' NOT NULL,"
                + "'zeile7' varchar(100000) DEFAULT '' NOT NULL,"
                + "'zeile8' varchar(100000) DEFAULT '' NOT NULL,"
                + "'zeile9' varchar(100000) DEFAULT '' NOT NULL);");
        db.query("CREATE TABLE IF NOT EXISTS 'module' ("
                + "'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "'semesterID' int(1000) NOT NULL,"
                + "'name' varchar(100) NOT NULL,"
                + "'academicName' varchar(100) DEFAULT '' NOT NULL,"
                + "'academicRoom' varchar(100) DEFAULT '' NOT NULL,"
                + "'academicTel' varchar(100) DEFAULT '' NOT NULL,"
                + "'academicMail' varchar(100) DEFAULT '' NOT NULL,"
                + "'examOneType' varchar(100) DEFAULT '' NOT NULL,"
                + "'examOneRoom' varchar(20) DEFAULT '' NOT NULL,"
                + "'examOneDate' varchar(100) DEFAULT '' NOT NULL,"
                + "'examOneTime' varchar(100) DEFAULT '' NOT NULL,"
                + "'examOneCredits' varchar(100) DEFAULT '' NOT NULL,"
                + "'examOneGrade' varchar(100) DEFAULT '' NOT NULL,"
                + "'examTwoType' varchar(100) DEFAULT '' NOT NULL,"
                + "'examTwoRoom' varchar(20) DEFAULT '' NOT NULL,"
                + "'examTwoDate' varchar(100) DEFAULT '' NOT NULL,"
                + "'examTwoTime' varchar(100) DEFAULT '' NOT NULL,"
                + "'examTwoCredits' varchar(100) DEFAULT '' NOT NULL,"
                + "'examTwoGrade' varchar(100) DEFAULT '' NOT NULL,"
                + "'note' varchar(10000) DEFAULT '' NOT NULL);");
    }

    public Vector<SBNodeStruct> getTreeVector(SBView view) {
        Vector<SBNodeStruct> v = new Vector<>();
        v.add(new SBNodeStruct("root", 0, 0));
        if (!connected) {
            this.dbconnect(view);
        }
        if (!connected) {
            view.showStatusError("Profil konnte nicht geladen werden!");
        } else {
            try {
                //Studiengaenge
                ResultSet studyRS = db.getResultSet("SELECT id,name FROM studiengaenge");
                ArrayList<String> study_arr = new ArrayList<>();
                while (studyRS.next()) {
                    study_arr.add(studyRS.getString("name") + "::::" + studyRS.getString("id"));
                }
                for (int i = 0; i < study_arr.size(); i++) {
                    String[] study_arr_split = study_arr.get(i).split("::::");
                    v.add(new SBNodeStruct(study_arr_split[0], Integer.parseInt(study_arr_split[1]), 1));
                    //Semester
                    ResultSet semesterRS = db.getResultSet("SELECT id,name FROM semester WHERE studyID ='" + Integer.parseInt(study_arr_split[1]) + "'");
                    ArrayList<String> semester_arr = new ArrayList<>();
                    while (semesterRS.next()) {
                        semester_arr.add(semesterRS.getString("name") + "::::" + semesterRS.getString("id"));
                    }
                    for (int j = 0; j < semester_arr.size(); j++) {
                        String[] semester_arr_split = semester_arr.get(j).split("::::");
                        v.add(new SBNodeStruct(semester_arr_split[0], Integer.parseInt(semester_arr_split[1]), 2));
                        //Module
                        ResultSet moduleRS = db.getResultSet("SELECT id,name FROM module WHERE semesterID ='" + Integer.parseInt(semester_arr_split[1]) + "'");
                        ArrayList<String> module_arr = new ArrayList<>();
                        while (moduleRS.next()) {
                            module_arr.add(moduleRS.getString("name") + "::::" + moduleRS.getString("id"));
                        }
                        for (int k = 0; k < module_arr.size(); k++) {
                            String[] module_arr_split = module_arr.get(k).split("::::");
                            v.add(new SBNodeStruct(module_arr_split[0], Integer.parseInt(module_arr_split[1]), 3));
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return v;
    }

    public void renameStudy(int studyID, String studyName, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("UPDATE studiengaenge SET name='" + studyName + "' WHERE id=" + studyID + ";");
    }

    public void renameSemester(int semesterID, String semesterName, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("UPDATE semester SET name='" + semesterName + "' WHERE id=" + semesterID + ";");
    }

    public void renameModule(int moduleID, String moduleName, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("UPDATE module SET name='" + moduleName + "' WHERE id=" + moduleID + ";");
    }

    public void deleteStudy(int studyID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("DELETE FROM studiengaenge WHERE id=" + studyID + ";");
    }

    public void deleteSemester(int semesterID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("DELETE FROM semester WHERE id=" + semesterID + ";");
    }

    public void deleteModule(int moduleID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("DELETE FROM module WHERE id=" + moduleID + ";");
    }

    public String[] getStudyPanelValues(int studyID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        if (!connected) {
            view.showStatusError("Profil konnte nicht geladen werden!");
            return null;
        } else {
            try {
                ResultSet rs = db.getResultSet("SELECT * FROM studiengaenge WHERE id=" + studyID + ";"); //Alles von der Tabelle studiengaenge holen
                while (rs.next()) {
                    String fields[] = {rs.getString("studentName"), rs.getString("studentMatnum"), rs.getString("studentBirth"),
                        rs.getString("studyAcad"), rs.getString("studyName"), rs.getString("studyStart")};
                    return fields;

                }
                return null;
            } catch (SQLException e) {
                System.err.println(e);
                return null;
            }
        }
    }

    public String[] getModulePanelValues(int moduleID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        if (!connected) {
            view.showStatusError("Profil konnte nicht geladen werden!");
            return null;
        } else {
            try {
                ResultSet rs = db.getResultSet("SELECT * FROM module WHERE id=" + moduleID + ";"); //Alles von der Tabelle module holen
                while (rs.next()) {
                    String fields[] = {rs.getString("academicName"), rs.getString("academicRoom"), rs.getString("academicTel"),
                        rs.getString("academicMail"), rs.getString("examOneType"), rs.getString("examOneRoom"),
                        rs.getString("examOneDate"), rs.getString("examOneTime"), rs.getString("examOneCredits"),
                        rs.getString("examOneGrade"), rs.getString("examTwoType"), rs.getString("examTwoRoom"),
                        rs.getString("examTwoDate"), rs.getString("examTwoTime"), rs.getString("examTwoCredits"),
                        rs.getString("examTwoGrade"), rs.getString("note")};
                    return fields;
                }
                return null;
            } catch (SQLException e) {
                System.err.println(e);
                return null;
            }
        }
    }
    
    public String[][] getSemesterPanelValues(int semesterID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
            try {
                ResultSet rs = db.getResultSet("SELECT zeile0,zeile1,zeile2,zeile3,zeile4,zeile5,zeile6,zeile7,zeile8,zeile9 FROM semester WHERE id=" + semesterID + ";"); //Alles von der Tabelle semester holen
                while (rs.next()) {
                    String[] zeile0 = rs.getString("zeile0").split("::::");
                    String[] zeile1 = rs.getString("zeile1").split("::::");
                    String[] zeile2 = rs.getString("zeile2").split("::::");
                    String[] zeile3 = rs.getString("zeile3").split("::::");
                    String[] zeile4 = rs.getString("zeile4").split("::::");
                    String[] zeile5 = rs.getString("zeile5").split("::::");
                    String[] zeile6 = rs.getString("zeile6").split("::::");
                    String[] zeile7 = rs.getString("zeile7").split("::::");
                    String[] zeile8 = rs.getString("zeile8").split("::::");
                    String[] zeile9 = rs.getString("zeile9").split("::::");
                    String fields[][] = {zeile0, zeile1,zeile2,zeile3,zeile4,zeile5,zeile6,zeile7,zeile8,zeile9};
                    System.out.println(fields[1].toString());
                    return fields;
                }
                return null;
            } catch (SQLException e) {
                System.err.println(e);
                return null;
            }
        
    }
}
