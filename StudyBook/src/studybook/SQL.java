/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studybook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class SQL {
    
    private Connection conn = null;
    
    public SQL(String profile) {
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
