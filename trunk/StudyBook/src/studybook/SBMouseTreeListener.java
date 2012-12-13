package studybook;


import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


/**
 * Empfängt die vom Nutzer getätigten Mauseingaben in dem JTree und reagiert
 * entsprechend.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBMouseTreeListener extends MouseAdapter implements TreeSelectionListener, TreeModelListener {

    private SBController controller;
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
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        SBNodeStruct nodeInfo = (SBNodeStruct) node.getUserObject();

        // In Abhängigkeit von der Länge von Pathlength Panel aufrufen.
        switch (pathLength) {
                case 2:

                    controller.showStudyPanel();
                    //controller.showStudyPanel(nodeInfo.getId());
                    break;

                case 3:
                    controller.showSemesterPanel();
                    //controller.showSemesterPanel(nodeInfo.getId());
                    break;

                case 4:
                    controller.showModulePanel();
                    //controller.showModulePanel(nodeInfo.getId());
                    break;
        }

    }

    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

        try {
            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode)
                   (node.getChildAt(index));
        } catch (NullPointerException exc) {}

        SBNodeStruct struct = (SBNodeStruct) node.getUserObject();
        controller.renameNode(struct.getId());
        System.out.println("Neuer Name: " + struct);
    }

    @Override
    public void treeNodesInserted(TreeModelEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void treeNodesRemoved(TreeModelEvent e) {
        /*
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

        try {
            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode)
                   (node.getChildAt(index));
        } catch (NullPointerException exc) {}

        SBNodeStruct struct = (SBNodeStruct) node.getUserObject();
        controller.removeNode(struct.getId());
        System.out.println("Gelöscht: " + struct);
        *
        */
    }

    @Override
    public void treeStructureChanged(TreeModelEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
