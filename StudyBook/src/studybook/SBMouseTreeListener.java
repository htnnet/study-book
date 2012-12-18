package studybook;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Empfängt die vom Nutzer getätigten Mauseingaben in dem JTree und
 * Veränderungen in der Baumstrukur und reagiert entsprechend.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
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

        currentId = nodeInfo.getId();
        oldName = nodeInfo.toString();

        // In Abhängigkeit der Länge von Pathlength Panel aufrufen.
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
     * Wird ausgelöst, wenn der Name eines Baumelements verändert wird.
     *
     * @param event das TreeModel-Event
     */
    @Override
    public void treeNodesChanged(TreeModelEvent event) {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode) (event.getTreePath().getLastPathComponent());

        // Sicherstellen, dass wir das richtige Baumelement haben
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
     * Wird aufgerufen, wenn ein neues Baumelement hinzugefügt wird.
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

        // Sicherstellen, dass wir das richtige Baumelement haben
        try {
            int index = event.getChildIndices()[0];
            node = (DefaultMutableTreeNode) (node.getChildAt(index));
        } catch (NullPointerException exc) {
        }

        // für das Hinzufügen von Baumelement brauchen wir stets das Oberlement
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

    /**
     * Wird aufgerufen, wenn ein Baumelement gelöscht wurde.
     *
     * @param event das TreeModel-Event
     */
    @Override
    public void treeNodesRemoved(TreeModelEvent event) {
        int pathLength = event.getTreePath().getPathCount();

        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode) (event.getTreePath().getLastPathComponent());

        // Sicherstellen, dass wir das richtige Baumelement haben
        try {
            int index = event.getChildIndices()[0];
            node = (DefaultMutableTreeNode) (node.getChildAt(index));
        } catch (NullPointerException exception) {
        } catch (ArrayIndexOutOfBoundsException exception) {
        }

        // Mithilfe der Verschachtelungstiefe bestimmen, welche Methode aufgerufen
        // und welches Element letztlich gelöscht werden muss
        switch (pathLength) {
            case 1:
                this.controller.deleteStudy(currentId);
                break;
            case 2:
                this.controller.deleteSemester(currentId);
                break;
            case 3:
                this.controller.deleteModule(currentId);
                break;
        }

    }

    /**
     * Wird aufgerufen, wenn mehrere Baumelement gleichzeitig gelöscht wurden.
     * Diese Methode musste aufgrund des Interfaces implementiert werden, wird
     * hier jedoch nicht verwendet, weil man nur ein Baumelement auswählen kann.
     *
     * @param event das TreeModel-Event
     */
    @Override
    public void treeStructureChanged(TreeModelEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
