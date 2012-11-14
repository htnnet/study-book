package studybook;


import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;


/**
 * Empfängt die vom Nutzer getätigten Mauseingaben in dem JTree und reagiert
 * entsprechend.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBMouseListener extends MouseAdapter {

    private SBController controller;
    private JTree tree;

      /**
     * Konstruktor der Klasse "SBMouseListener".
     *
     * @param controller das Controller-Objekt
     * @param tree das Baum-Objekt, auf dem Ereignisse stattfinden
     */
    public SBMouseListener(SBController controller, JTree tree) {
        this.controller = controller;
        this.tree = tree;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            int row = tree.getClosestRowForLocation(e.getX(), e.getY());
            tree.setSelectionRow(row);
            System.out.println(row + "" + e.getComponent() + "" + e.getX() + e.getY());
            JPopupMenu popup = new JPopupMenu();
            popup.add(new JMenuItem("popup: " + row));

            popup.show(tree, e.getX(), e.getY()); //Problem: Wie Blatt von Baum eindeutig identifizieren??
            //row aendert sich, jenachdem, wie weit der Baum ausgeklappt ist
        }
    }
}
