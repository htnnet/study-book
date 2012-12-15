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
    public SBNodeStruct(String name, int id, int level) {
        this.name = new String(name);
        this.id = id;
        this.level = level;
    }

    /**
     * Konstruktor zum Hinzufügen eines neuen Elements, dessen ID noch nicht
     * bekannt ist und noch von der Datenbank vergeben werden muss.
     * @param name der Name des Baumelements
     * @param level die Verschachtelungstiefe
     */
    public SBNodeStruct(String name, int level) {
        this.name = new String(name);
        this.level = level;
    }



    /**
     * Hiermit kann die Verschachtelungstiefe des Baumelements zurückgeliefert
     * werden.
     * @param level die Verschachtelungstiefe des Baumelements
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Zum Setzen der ID des Baumelements nachdem er hinzugefügt wurde.
     * @param id die ID des Baumelements.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zum Setzen des Namens nach Änderung im Tree.
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
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
}
