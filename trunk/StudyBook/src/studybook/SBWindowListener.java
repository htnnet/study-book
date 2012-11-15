/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studybook;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class SBWindowListener extends WindowAdapter {
    private SBController controller;
    
    public SBWindowListener(SBController controller) {
        super();
        this.controller = controller;
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        controller.exit();
    }
}
