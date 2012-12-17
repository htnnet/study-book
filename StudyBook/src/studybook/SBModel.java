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

    /**
     * Konstruktor der Klasse "SBModel"
     */
    public SBModel() {
        this.db = new SBSQL();
    }

    /**
     * Methode zum Erstellen eines Neuen Profils
     *
     * @param name Pfad zum neuen Profil
     */
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

    /**
     * Methode zum Wechseln des Profils zur Laufzeit
     *
     * @param profilePath Pfad zur neuen Profildatei
     */
    public void changeProfile(String profilePath) {
        this.setProfile(profilePath);
        if (connected) {
            db.close();
        }
        connected = false;
    }

    /**
     * Setter-Methode für das Setzen des Profilnamens
     *
     * @param profilname Profilname des zu ladenden Profils
     */
    public void setProfile(String profilname) {
        this.profilname = profilname;
    }

    /**
     * Getter-Methode zum Holen des aktuellen Profilnamens
     *
     * @return Aktueller Profilname
     */
    public String getProfile() {
        return this.profilname;
    }

    /**
     * Methode zum Herstellen der Verbindung mit der SQLite-Datenbank
     *
     * @param view SBView Objekt zur Ausgabe von Meldungen
     */
    public void dbconnect(SBView view) {
        if (!new File(this.profilname + ".sbprofile").exists()) {
            connected = false;
        } else {
            db.connect(profilname + ".sbprofile");
            view.setFrameTitle("StudyBook - " + this.profilname + ".sbprofile");
            connected = true;
        }
    }

    /**
     * Methode zum Erstellen eines neuen Studiengangs
     *
     * @param view SBView Objekt zur Ausgabe von Meldungen
     * @return ID des erstellten Datensatzes
     */
    public int addStudy(SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
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

    /**
     * Methode zum Erstellen eines neuen Semesters
     *
     * @param studyID ID des Studiengangs, zu dem das Semester gehört
     * @param view SBView Objekt zur Ausgabe von Meldungen
     * @return ID des erstellten Datensatzes
     */
    public int addSemester(int studyID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
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

    /**
     * Methode zum Erstellen eines neuen Moduls
     *
     * @param semesterID ID des Semester, zu dem das Modul gehört
     * @param view SBView Objekt zur Ausgabe von Meldungen
     * @return ID des erstellten Datensatzes
     */
    public int addModule(int semesterID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
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

    /**
     * Methode zum Umbennenen eines Studiengangs
     *
     * @param studyID ID des Studiengangs, welcher umbenannt werden soll
     * @param studyName Neuer Name
     * @param view SBView Objekt zur Ausgabe von Meldungen
     */
    public void renameStudy(int studyID, String studyName, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("UPDATE studiengaenge SET name='" + studyName + "' WHERE id=" + studyID + ";");
    }

    /**
     * Methode zum Umbennenen eines Semesters
     *
     * @param semesterID ID des Semesters, welches umbenannt werden soll
     * @param semesterName Neuer Name
     * @param view SBView Objekt zur Ausgabe von Meldungen
     */
    public void renameSemester(int semesterID, String semesterName, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("UPDATE semester SET name='" + semesterName + "' WHERE id=" + semesterID + ";");
    }

    /**
     * Methode zum Umbennenen eines Moduls
     *
     * @param moduleID ID des Moduls, welches umbenannt werden soll
     * @param moduleName Neuer Name
     * @param view SBView Objekt zur Ausgabe von Meldungen
     */
    public void renameModule(int moduleID, String moduleName, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("UPDATE module SET name='" + moduleName + "' WHERE id=" + moduleID + ";");
    }

    /**
     * Methode zum Löschen eines Studiengangs inklusive Löschung aller darunter
     * liegenden Elemente
     *
     * @param studyID ID des Studiengangs, welcher gelöscht werden soll
     * @param view SBView Objekt zur Ausgabe von Meldungen
     */
    public void deleteStudy(int studyID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        ResultSet semesterRS = db.getResultSet("SELECT id FROM semester WHERE studyID=" + studyID + ";");
        ArrayList<Integer> semesterAL = new ArrayList<>();
        try {
            while (semesterRS.next()) {
                semesterAL.add(Integer.parseInt(semesterRS.getString("id")));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        for (int i = 0; i < semesterAL.size(); i++) {
            db.query("DELETE FROM module WHERE semesterID=" + semesterAL.get(i) + ";");
        }
        db.query("DELETE FROM semester WHERE studyID=" + studyID + ";");
        db.query("DELETE FROM studiengaenge WHERE id=" + studyID + ";");
    }

    /**
     * Methode zum Löschen eines Semester inklusive Löschung aller zugehörigen
     * Module
     *
     * @param semesterID ID des Semesters, welches gelöscht werden soll
     * @param view SBView Objekt zur Ausgabe von Meldungen
     */
    public void deleteSemester(int semesterID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("DELETE FROM module WHERE semesterID=" + semesterID + ";");
        db.query("DELETE FROM semester WHERE id=" + semesterID + ";");
    }

    /**
     * Methode zum Löschen eines Moduls
     *
     * @param moduleID ID des Moduls, welches gelöscht werden soll
     * @param view SBView Objekt zur Ausgabe von Meldungen
     */
    public void deleteModule(int moduleID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        db.query("DELETE FROM module WHERE id=" + moduleID + ";");
    }

    /**
     * Methode zum Speichern aller Werte des Study Panels
     *
     * @param fields String-Array mit den Werten des Study Panels
     * @param studyID ID des Studiengangs, zu dem das Study Panel gehört
     * @param view SBView Objekt zur Ausgabe von Meldungen
     */
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

    /**
     * Methode zum Speichern des Stundenplans vom Semesterpanel
     *
     * @param fields Zwei-Dimensionales Array mit den Inhalten der Zellen vom
     * Stundenplan
     * @param semesterID ID des Semesters, zu dem das Semester Panel gehört
     * @param view SBView Objekt zur Ausgabe von Meldungen
     */
    public void saveSemesterPanel(String fields[][], int semesterID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
        StringBuilder zeile[] = new StringBuilder[10];
        for (int i = 0; i < 10; i++) {
            zeile[i] = new StringBuilder();
        }
        int aktuelle_zeile = 0;
        for (int i = 0; i < 80; i++) {
            if (Integer.parseInt(fields[i][0]) > aktuelle_zeile) {
                aktuelle_zeile++;
            }
            if (fields[i][2] == null) {
                fields[i][2] = "";
            }
            zeile[aktuelle_zeile].append(fields[i][2] + "::::");
        }
        String zeilen_string[] = new String[10];
        for (int i = 0; i < 10; i++) {
            zeilen_string[i] = zeile[i].toString().substring(0, zeile[i].toString().length() - 4);
        }
        String query = "UPDATE semester SET zeile0='" + zeilen_string[0] + "',"
                + "zeile1 = '" + zeilen_string[1].toString() + "', zeile2 = '" + zeilen_string[2].toString() + "',"
                + "zeile3 = '" + zeilen_string[3].toString() + "', zeile4 = '" + zeilen_string[4].toString() + "',"
                + "zeile5 = '" + zeilen_string[5].toString() + "', zeile6 = '" + zeilen_string[6].toString() + "',"
                + "zeile7 = '" + zeilen_string[7].toString() + "', zeile8 = '" + zeilen_string[8].toString() + "',"
                + "zeile9 = '" + zeilen_string[9].toString() + "' WHERE id=" + semesterID + ";";
        db.query(query);
    }

    /**
     * Methode zum Speichern aller Werte des Modul Panels
     *
     * @param fields String-Array mit den Werten des Modul Panels
     * @param moduleID ID des Moduls, zu dem das Modul Panel gehört
     * @param view SBView Objekt zur Ausgabe von Meldungen
     */
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

    /**
     * Getter-Methode zum Auslesen der Baumelemente
     *
     * @param view SBView Objekt zum Anzeigen von Meldungen
     * @return Vector mit den sortieren Baumelementen
     */
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

    /**
     * Getter-Methode zum Holen aller Werte des zugehörigen Studiengangs aus der
     * Datenbank
     *
     * @param studyID ID des Studiengangs, dessen Daten geholt werden sollen
     * @param view SBView Objekt zur Ausgabe von Meldungen
     * @return String Array mit den Daten für die Felder des Study Panels
     */
    public String[] getStudyPanelValues(int studyID, SBView view) {
        if (!connected) {
            this.dbconnect(view);
        }
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

    /**
     * Getter-Methode zum Holen und Erstellen der Notenübersicht im Study Panel
     *
     * @param studyID ID des Studiengangs, dessen Notenübersicht erstellt werden
     * soll
     * @param view SBView Objekt zur Ausgabe von Meldungen
     * @return Zwei dimensionales String Array mit den geholten Werten
     */
    public String[][] getGradeTable(int studyID, SBView view) {
        try {
            ResultSet studyRS = db.getResultSet("SELECT id FROM semester WHERE studyID=" + studyID + ";");
            ArrayList<Integer> moduleIDs = new ArrayList<>();
            while (studyRS.next()) {
                moduleIDs.add(Integer.parseInt(studyRS.getString("id")));
            }
            StringBuilder examTypeSB = new StringBuilder();
            StringBuilder examCreditsSB = new StringBuilder();
            StringBuilder examGradeSB = new StringBuilder();
            int allCredits = 0;
            float grade = 0;
            for (int i = 0; i < moduleIDs.size(); i++) {
                ResultSet rs = db.getResultSet("SELECT examOneType,examOneCredits,examOneGrade,examTwoType,examTwoCredits,examTwoGrade,name FROM module WHERE semesterID=" + moduleIDs.get(i) + ";");
                while (rs.next()) {
                    if (!rs.getString("examOneType").equals("") && !rs.getString("examOneCredits").equals("0") && Float.parseFloat(rs.getString("examOneGrade")) <= 4.0) {
                        examTypeSB.append(rs.getString("name")+": "+rs.getString("examOneType") + "::::");
                    }
                    if (!rs.getString("examTwoType").equals("") && !rs.getString("examTwoCredits").equals("0") && Float.parseFloat(rs.getString("examTwoGrade")) <= 4.0) {
                        examTypeSB.append(rs.getString("name")+": "+rs.getString("examTwoType") + "::::");
                    }
                    if (!rs.getString("examOneCredits").equals("") && !rs.getString("examOneType").equals("") && !rs.getString("examOneCredits").equals("0") && Float.parseFloat(rs.getString("examOneGrade")) <= 4.0) {
                        examCreditsSB.append(rs.getString("examOneCredits") + "::::");
                        int examOneCreditsInt = Integer.parseInt(rs.getString("examOneCredits"));
                        allCredits += examOneCreditsInt;
                    }
                    if (!rs.getString("examTwoCredits").equals("") && !rs.getString("examTwoType").equals("") && !rs.getString("examTwoCredits").equals("0") && Float.parseFloat(rs.getString("examTwoGrade")) <= 4.0) {
                        examCreditsSB.append(rs.getString("examTwoCredits") + "::::");
                        int examTwoCreditsInt = Integer.parseInt(rs.getString("examTwoCredits"));
                        allCredits += examTwoCreditsInt;
                    }
                    if (!rs.getString("examOneGrade").equals("") && !rs.getString("examOneType").equals("") && !rs.getString("examOneCredits").equals("0") && Float.parseFloat(rs.getString("examOneGrade")) <= 4.0) {
                        examGradeSB.append(rs.getString("examOneGrade") + "::::");
                        int examOneCreditsInt = Integer.parseInt(rs.getString("examOneCredits"));
                        double examOneGradeFloat = Float.parseFloat(rs.getString("examOneGrade"));
                        grade += (examOneCreditsInt * examOneGradeFloat);
                    }
                    if (!rs.getString("examTwoGrade").equals("") && !rs.getString("examTwoType").equals("") && !rs.getString("examTwoCredits").equals("0") && Float.parseFloat(rs.getString("examTwoGrade")) <= 4.0) {
                        examGradeSB.append(rs.getString("examTwoGrade") + "::::");
                        int examTwoCreditsInt = Integer.parseInt(rs.getString("examTwoCredits"));
                        double examTwoGradeFloat = Float.parseFloat(rs.getString("examTwoGrade"));
                        grade += (examTwoCreditsInt * examTwoGradeFloat);
                    }
                }
            }
            String examType = "";
            if (examTypeSB.toString().length() >= 4) {
                examType = examTypeSB.toString().substring(0, examTypeSB.toString().length() - 4);
            }
            String examCredits = "";
            if (examCreditsSB.toString().length() >= 4) {
                examCredits = examCreditsSB.toString().substring(0, examCreditsSB.toString().length() - 4);
            }
            String examGrade = "";
            if (examGradeSB.toString().length() >= 4) {
                examGrade = examGradeSB.toString().substring(0, examGradeSB.toString().length() - 4);
            }
            StringBuilder fieldSB = new StringBuilder();
            String[] examTypeArr = examType.split("::::");
            String[] examCreditsArr = examCredits.split("::::");
            String[] examGradeArr = examGrade.split("::::");
            for (int i = 0; i < examType.split("::::").length; i++) {
                try {
                    fieldSB.append("    " + examTypeArr[i] + ",    " + examCreditsArr[i] + ",    " + (Math.rint(Float.parseFloat(examGradeArr[i]) * 10) / 10) + "::::");
                } catch(NumberFormatException e) {
                }
            }
            String fields = "";
            if (fieldSB.toString().length() >= 4) {
                fields = fieldSB.toString().substring(0, fieldSB.toString().length() - 4);
            }
            String[] fields_alone = fields.split("::::");
            ArrayList<String[]> fieldsAL = new ArrayList<>();
            try {
                grade = grade / allCredits;
            } catch (ArithmeticException e) {
                System.err.println("Durch 0 Credits geteilt!");
            }
            if(allCredits > 0) {
                String[] firstRow = {"Gesamt:", allCredits + "", (Math.rint(grade * 10) / 10) + ""};
                fieldsAL.add(firstRow);
            }
            for (int i = 0; i < fields_alone.length; i++) {
                fieldsAL.add(fields_alone[i].split(","));
            }
            String[][] fields_arr = fieldsAL.toArray(new String[fieldsAL.size()][]);
            return fields_arr;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * Getter-Methode zum Holen des Stundenplans für ein Semester
     *
     * @param semesterID ID des Semester, dessen Stundenplan gefüllt werden soll
     * @param view SBView Objekt zur Ausgabe von Meldungen
     * @return Zwei dimensionales String Array gefüllt mit den Werten für den
     * Stundenplan
     */
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


                String fields[][] = {zeile0, zeile1, zeile2, zeile3, zeile4, zeile5, zeile6, zeile7, zeile8, zeile9};
                return fields;
            }
            return null;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }

    }

    /**
     * Getter-Methode zum Holen aller Werte für ein Modul Panel
     *
     * @param moduleID ID des Moduls, dessen Panel gefüllt werden soll
     * @param view SBView Objekt zur Ausgabe von Meldungen
     * @return Werte, mit denen das Module Panel gefüllt werden soll
     */
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
}
