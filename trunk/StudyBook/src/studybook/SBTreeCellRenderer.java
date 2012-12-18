package studybook;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Klasse, die zur Darstellung von eigenen Icons dient, die je nach
 * Verschachtelungstiefe des Baumes gesetzt werden.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBTreeCellRenderer extends DefaultTreeCellRenderer {

    private DefaultMutableTreeNode currentTreeNode;
    private int pathLength;
    private ImageIcon studyIcon;
    private ImageIcon semesterIcon;
    private ImageIcon moduleIcon;

    /**
     * Überschriebene Methode der Oberklasse, um eigene Icons setzen zu können.
     *
     * @param tree der Baum
     * @param value der Wert des Baumelements
     * @param sel true -> das Baumelemnt ist ausgewählt, sonst nicht
     * @param expanded true -> die Unterblätter dieses Baumes liegen offen
     * @param leaf true -> Blattelement ohne Unterelement
     * @param row die Zeile des Baumelements
     * @param hasFocus das Baumelement hat den Fokus
     * @return die Komponente, die der Renderer benutzt
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree,
            Object value, boolean sel, boolean expanded, boolean leaf,
            int row, boolean hasFocus) {

        studyIcon = new ImageIcon(getClass().getResource("/pics/study16x16.png"));
        semesterIcon = new ImageIcon(getClass().getResource("/pics/semester16x16.png"));
        moduleIcon = new ImageIcon(getClass().getResource("/pics/module16x16.png"));

        currentTreeNode = (DefaultMutableTreeNode) value;
        pathLength = currentTreeNode.getPath().length;

        // in Abhängigkeit von der Verschachtelungstiefe des Baumelements
        // entsprechend mit Icons versehen
        switch (pathLength) {
            case 2:
                this.setLeafIcon(studyIcon);
                this.setOpenIcon(studyIcon);
                this.setClosedIcon(studyIcon);
                break;
            case 3:
                this.setLeafIcon(semesterIcon);
                this.setOpenIcon(semesterIcon);
                this.setClosedIcon(semesterIcon);
                break;
            case 4:
                this.setLeafIcon(moduleIcon);
                this.setOpenIcon(moduleIcon);
                this.setClosedIcon(moduleIcon);
                break;
        }
        // gleichnamige Methode in Oberklasse aufrufen, um Änderungen wirksam zu machen
        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }
}
