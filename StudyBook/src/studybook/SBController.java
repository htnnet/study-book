package studybook;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Alex
 */
public class SBController {
    private SBModel model;
    private SBView view;

    public SBController(SBModel model) {
        this.model = model;
        view = new SBView(this);
        view.createMainFrame();
        view.createMenuBar();
        view.layoutMainFrame();
        this.neues_profil("test"); //Testaufruf neues_profil
    }

    private void neues_profil(String name) {
        SBModel sqltest = this.model;
        sqltest.connect(name + ".profile"); //Speichere Datenbank unter name.profile
        sqltest.query("CREATE TABLE IF NOT EXISTS studiengaenge (name);"); //Erstelle Tabelle fuer Studiengaenge
        sqltest.query("INSERT INTO studiengaenge (name) VALUES ('testname')"); //Fuege Teststudiengang hinzu
        try {
            ResultSet rs = sqltest.get("SELECT * FROM studiengaenge"); //Alles von der Tabelle Studiengaenge holen
            while (rs.next()) {
                System.out.println("name = " + rs.getString("name")); //Und alle Werte "name" nacheinander auflisten
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}
