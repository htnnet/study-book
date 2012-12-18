package studybook;

/**
 * Die Klasse SBNodeStruct repräsentiert eine Datenstruktur, die den Austausch
 * von Daten zwischen dem JTree und der Datenbank gewährleisten soll.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBNodeStruct {

    private String name;
    private int id;
    private int level;

    /**
     * Standardkonstruktor zum Erzeugen eines Stringobjekts mit Datenbank-Id und
     * Verschachtelungstiefe.
     *
     * @param name der Name des Baumelements
     * @param id die Id des Baumelements, die auch die Id in der Datenbank ist
     * @param level die Verschachtelungstiefe
     */
    public SBNodeStruct(String name, int id, int level) {
        this.name = new String(name);
        this.id = id;
        this.level = level;
    }

    /**
     * Konstruktor zum Hinzufügen eines neuen Elements, dessen ID noch nicht
     * bekannt ist und noch von der Datenbank vergeben werden muss.
     *
     * @param name der Name des Baumelements
     * @param level die Verschachtelungstiefe
     */
    public SBNodeStruct(String name, int level) {
        this.name = new String(name);
        this.level = level;
    }

    /**
     * Gibt die String-Repräsentation des Objekts wieder.
     *
     * @return die String-Repräsentation des Objekts
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Zum Holen der Id.
     *
     * @return die Id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Hiermit kann die Verschachtelungstiefe des Baumelements zurückgeliefert
     * werden.
     *
     * @param level die Verschachtelungstiefe des Baumelements
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Zum Setzen des Namens nach Änderung im Tree.
     *
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Zum Setzen der Id des Baumelements nachdem er hinzugefügt wurde.
     *
     * @param id die Id des Baumelements.
     */
    public void setId(int id) {
        this.id = id;
    }
}
