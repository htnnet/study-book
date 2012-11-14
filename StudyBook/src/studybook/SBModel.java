package studybook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public SBModel() {
    }

    public void connect(String profile) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:"+profile);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void query(String query) {
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate(query);
        } catch(SQLException e) {
            System.err.println(e);
        }
    }

    public ResultSet get(String query) throws SQLException {
        Statement stat = conn.createStatement();
        return stat.executeQuery(query);
    }
}
