package studybook;

/**
 * Enthält die main-Funktion des Programms.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBStart {

    /**
     * Erstellt ein Backend-Objekt und übergibt es zur weiteren Verarbeitung
     * dem Controller-Objekt.
     *
     * @param args Kommandozeilenparameter
     */
    public static void main(String[] args) {
        SBModel model = new SBModel();
        new SBController(model);
    }
}
