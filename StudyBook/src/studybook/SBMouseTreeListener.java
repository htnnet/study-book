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
    private SBView view;
    private JTree tree;
    private JPopupMenu popupMenu;
    private int currentId;
    private String oldName;

    /**
     * Konstruktor der Klasse "SBMouseTreeListener".
     *
     * @param controller das Controller-Objekt
     * @param tree das Baum-Objekt, auf dem Ereignisse stattfinden
     */
    public SBMouseTreeListener(SBController controller, SBView view, JTree tree, JPopupMenu popupMenu) {
        this.controller = controller;
        this.view = view;
        this.tree = tree;
        this.popupMenu = popupMenu;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (SwingUtilities.isRightMouseButton(event)) {
            int row = tree.getRowForLocation(event.getX(), event.getY());
            if (row == -1) {  // Wenn ins "leere" Feld unter dem Baum geklickt wird
                tree.clearSelection();
                view.setEditMenuEnabled(true, true, false, false, false, false);

            } else {
                tree.setSelectionRow(row);
            }
            popupMenu.show(tree, event.getX(), event.getY());
        }
    }

    /**
     * Bei jedem Klick auf ein neues Baumelement wird ein Event ausgelöst
     * mittels dieses sich der TreePath und somit die aufzurufendende Verwaltung
     * bestimmen lässt.
     *
     * @param event das TreeSelection-Event
     */
    @Override
    public void valueChanged(TreeSelectionEvent event) {
        int pathLength = event.getPath().getPathCount();

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        // bei nicht ausgewähltem Baumelement
        if (node == null) {
            return;
        }



        SBNodeStruct nodeInfo = (SBNodeStruct) node.getUserObject();
        System.out.println(nodeInfo.toString());

        currentId = nodeInfo.getId();
        oldName = nodeInfo.toString();

        // In Abhängigkeit von der Länge von Pathlength Panel aufrufen.
        switch (pathLength) {
            case 2:
                controller.showStudyPanel(nodeInfo.getId());
                break;
            case 3:
                controller.showSemesterPanel(nodeInfo.getId());
                break;
            case 4:
                controller.showModulePanel(nodeInfo.getId());
                break;
        }
    }

    /**
     * Wird ausgelöst, wenn der Name eines Baumelements verändert wird
     *
     * @param event das TreeModel-Event
     */
    @Override
    public void treeNodesChanged(TreeModelEvent event) {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode) (event.getTreePath().getLastPathComponent());


        try {
            int index = event.getChildIndices()[0];
            node = (DefaultMutableTreeNode) (node.getChildAt(index));
        } catch (NullPointerException exc) {
        }

        SBNodeStruct struct = (SBNodeStruct) node.getUserObject();
        int level = struct.getLevel();
        int id = struct.getId();
        String newName = struct.toString();

        // Sicherstellen, dass der eingegebene Name nicht leer ist
        if (newName.trim().equals("") || newName == null) {
            struct.setName(oldName);
            return;
        }

        // Damit beim Verbleib beim selben Baumelement nicht der Ursprungsnname verwendet wird
        oldName = newName;

        switch (level) {
            case 1:
                this.controller.renameStudy(id, newName);
                break;
            case 2:
                this.controller.renameSemester(id, newName);
                break;
            case 3:
                this.controller.renameModule(id, newName);
                break;
        }
    }

    /**
     * Wird aufgerufen, wenn eine neues Baumelement hinzugefügt wird.
     *
     * @param event das TreeModel-Event
     */
    @Override
    public void treeNodesInserted(TreeModelEvent event) {
        int id = 0;
        SBNodeStruct struct;
        SBNodeStruct parentStruct;
        DefaultMutableTreeNode node;
        DefaultMutableTreeNode parentNode;
        node = (DefaultMutableTreeNode) (event.getTreePath().getLastPathComponent());

        try {
            int index = event.getChildIndices()[0];
            node = (DefaultMutableTreeNode) (node.getChildAt(index));
        } catch (NullPointerException exc) {
        }

        parentNode = (DefaultMutableTreeNode) node.getParent();
        struct = (SBNodeStruct) node.getUserObject();
        int level = struct.getLevel();
        switch (level) {
            case 1:
                id = this.controller.addStudy();
                break;
            case 2:
                parentStruct = (SBNodeStruct) parentNode.getUserObject();
                id = this.controller.addSemester(parentStruct.getId());
                break;
            case 3:
                parentStruct = (SBNodeStruct) parentNode.getUserObject();
                id = this.controller.addModule(parentStruct.getId());
                break;
        }

        struct.setId(id);
    }

    @Override
    public void treeNodesRemoved(TreeModelEvent event) {
        int pathLength = event.getTreePath().getPathCount();

        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode) (event.getTreePath().getLastPathComponent());

        try {
            int index = event.getChildIndices()[0];
            node = (DefaultMutableTreeNode) (node.getChildAt(index));
        } catch (NullPointerException exception) {
        } catch (ArrayIndexOutOfBoundsException exception) {
        }


        switch (pathLength) {
            case 1:
                System.out.println("studiengang: " + currentId);
                this.controller.deleteStudy(currentId);
                break;
            case 2:
                System.out.println("semester: " + currentId);
                this.controller.deleteSemester(currentId);
                break;
            case 3:
                System.out.println("modul: " + currentId);
                this.controller.deleteModule(currentId);
                break;
        }

    }

    @Override
    public void treeStructureChanged(TreeModelEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
