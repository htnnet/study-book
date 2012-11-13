package studybook;

import javax.swing.UIManager;

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
        // Falls möglich, Windows Look and Feel setzen, sonst Standard
        if (System.getProperty("os.name").indexOf("Windows") != -1) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }

        SBModel model = new SBModel();
        new SBController(model);
    }
}
