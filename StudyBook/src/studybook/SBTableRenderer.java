/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studybook;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Dient zur Darstellung der Spielsteine in den Tabellenzellen.
 *
 * @author Andre Müller & Alexander Keller
 * @version 1.0
 * @since 2012-06-20
 */
public class SBTableRenderer extends DefaultTableCellRenderer {

    /**
     * Ermöglicht das Visualisieren der in der Tabelle eingetragenen
     * Spielsteine.
     *
     * @param value der darzustellende Spielstein als JLabel
     */
    @Override
    public void setValue(Object value) {
        if (value instanceof JLabel) {
            JLabel stone = (JLabel) value;
            this.setIcon(stone.getIcon());
            this.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            this.setIcon(null);
            super.setValue(value);
        }
    }
}
