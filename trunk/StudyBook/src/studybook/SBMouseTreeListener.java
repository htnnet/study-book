package studybook;


import java.awt.Menu;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 * Empfängt die vom Nutzer getätigten Mauseingaben in dem JTree und reagiert
 * entsprechend.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBMouseTreeListener extends MouseAdapter implements TreeSelectionListener {

    private SBController controller;
    private ActionListener actionListener;
    private JTree tree;
    private JPopupMenu popupMenu;

     /**
     * Konstruktor der Klasse "SBMouseTreeListener".
     *
     * @param controller das Controller-Objekt
     * @param tree das Baum-Objekt, auf dem Ereignisse stattfinden
     */
    public SBMouseTreeListener(SBController controller, JTree tree, JPopupMenu popupMenu) {
        this.controller = controller;
        this.tree = tree;
        this.popupMenu = popupMenu;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (SwingUtilities.isRightMouseButton(event)) {
            int row = tree.getClosestRowForLocation(event.getX(), event.getY());
            tree.setSelectionRow(row);
            popupMenu.show(tree, event.getX(), event.getY());
        }
    }

    /**
     * Bei jedem Klick auf ein neues Baumelement wird ein Event ausgelöst
     * mittels dieses sich der TreePath und somit die aufzurufendende Verwaltung
     * bestimmen lässt.
     * @param event das TreeSelection-Event
     */

    @Override
    public void valueChanged(TreeSelectionEvent event) {
        int pathLength = event.getPath().getPathCount();

        switch (pathLength) {
                case 2:
                    controller.showStudyPanel();
                    break;

                case 3:
                    controller.showSemesterPanel();
                    break;

                case 4:
                    controller.showModulePanel();
                    break;
        }

    }
}