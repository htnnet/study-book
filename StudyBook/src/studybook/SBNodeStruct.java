package studybook;

/**
 * Die Klasse SBNodeStruct soll eine Datenstruktur repräsentieren, die den
 * Austausch von Daten zwischen dem JTree und der Datenbank gewährleisten soll.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBNodeStruct {

    private String name;
    private int id;
    private int level;

    /**
     * Standardkonstruktor zum Erzeugen eines Stringobjekts.
     */
    public SBNodeStruct(String name, int id, int level) {      //
        this.name = new String(name);
        this.id = id;
        this.level = level;
    }

    /**
     * Gibt die String-Repräsentation des Objekts wieder.
     * @return die String-Repräsentation des Objekts
     */
    @Override
    public String toString() {
        return name;
    }

     /**
     * Zum Holen der ID.
     * @return die ID
     */
    public int getId() {
        return this.id;
    }

     /**
     * Zum Holen der Verschachtelungstiefe.
     * @return die Verschachtelungstiefe
     */
    public int getLevel() {
        return this.level;
    }
}
