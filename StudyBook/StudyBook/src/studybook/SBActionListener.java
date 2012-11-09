/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studybook;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

/**
 *
 * @author Admin
 */
public class SBActionListener implements ActionListener {
    StudyBook mainframe;
    public SBActionListener(StudyBook mainframe) {
        this.mainframe = mainframe;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        //Menuebuttons
        if (obj instanceof JMenuItem) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                //---Datei---
                //      Beenden
                case "beenden":
                    System.exit(0);
                    break;
                //---Hilfe---
                //      Über
                case "ueber":
                    JLabel mainlabel = new JLabel(new ImageIcon(getClass().getResource("/bilder/transp.gif")));
                    mainlabel.setMinimumSize(new Dimension(20,20));
                    mainframe.setMainFrame(mainlabel);
                    break;
            }
        }
    }
}
