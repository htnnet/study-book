package studybook;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;

/**
 * Klasse, die dafür sorgt, dass beim Umbenennen von Baumelementen die Icons,
 * die vorher von uns gesetzt wurden, auch da bleiben.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBTreeCellEditor extends DefaultTreeCellEditor {

    private JLabel label;

    /*
     * Konstruktor der Klasse SBTreeCellEditor, der den Konstruktor der
     * Oberklasse aufruft.
     */
    public SBTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer, TreeCellEditor editor) {
        super(tree, renderer, editor);
    }

    /**
     * Sorgt für das richtige Icon beim Umbenennen von Baumelementen
     *
     * @param tree der Baum
     * @param value der Wert des Baumelements
     * @param selected true -> das Baumelemnt ist ausgewählt, sonst nicht
     * @param expanded true -> die Unterblätter dieses Baumes liegen offen
     * @param leaf true -> Blattelement ohne Unterelement
     * @param row die Zeile des Baumelements
     */
    @Override
    protected void determineOffset(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row) {

        if (renderer != null) {
            label = (JLabel) renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf,
                    row, tree.hasFocus() && tree.getLeadSelectionRow() == row);
            editingIcon = label.getIcon();

            if (editingIcon != null) {
                offset = renderer.getIconTextGap() + editingIcon.getIconWidth();
            } else {
                offset = renderer.getIconTextGap();
            }
        } else {
            editingIcon = null;
            offset = 0;
        }
    }
}
