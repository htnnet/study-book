package studybook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Enthält Methoden mit denen eine SQL-Datenbankverbindung hergestellt, Daten
 * verändert und ausgelesern werden können.
 *
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBSQL {

    private Connection conn = null;
    private Statement stat = null;
    private ResultSet resultSet;

    public SBSQL() {
    }

    /**
     * Stellt eine Verbindung zur angegebenen Profildatei mittels ihres Pfades
     * her.
     *
     * @param profilePath der Pfad zur Profildatei
     */
    public void connect(String profilePath) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + profilePath);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Schließt die Verbindung zur Profildatei/Datebankdatei.
     */
    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Führt die gewünschten Änderungen an einer Profildatei durch.
     * @param query der SQLite-Befehl zur Modifikation der Datenbank
     */
    public void query(String query) {
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Liefert mittels eines SQLite-Befehls den gewünschten Datenbanksatz
     * als ResultSet zurück.
     * @param query SQLite-Befehl zur Anforderung von Datensätzen
     * @return der durch den Befehl geholte Datensatz
     */
    public ResultSet getResultSet(String query) {
        try {
            stat = conn.createStatement();
            resultSet = stat.executeQuery(query);
        } catch (SQLException e) {
            System.err.println(e);
        }
        return resultSet;

    }
}
