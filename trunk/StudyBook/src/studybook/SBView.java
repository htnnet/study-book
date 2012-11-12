package studybook;

import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;


/**
 * Die grafischen Oberfläche bildet das Herzstück des Programms. Hierüber
 * kann der Benutzer sein Studium verwalten.
 */
public class SBView extends JFrame {

    private SBController controller;
    private JFrame frame;
    private JPanel cpanel;
    private JPanel lpanel;
    private JPanel rpanel;
    private JLabel statusbar;
    private JSplitPane splitpane;

    /**
     * Konstruktor der Klasse "SBView"
     *
     * @param controller das Controller-Objekt
     */
    public SBView(SBController controller) {
        this.controller = controller;
        // Falls möglich, Windows Look and Feel setzen, sonst Standard
        if (System.getProperty("os.name").indexOf("Windows") != -1) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }

    /**
     * Erstellt das Haupfenster mit ihren grafischen Komponenten.
     */
    public void createMainFrame() {
        frame = new JFrame("StudyBook");
        // Fenster-Icon setzen
        try {
            Image img = ImageIO.read(getClass().getResource("/pics/sb_icon.gif"));
            frame.setIconImage(img);
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        cpanel = new JPanel();
        splitpane = new JSplitPane();
        splitpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        lpanel = new JPanel();

        // Hier wird das Aussehen des rechten Panels mit folgenden Befehlen bestimmt
        rpanel = new SBStudyPanel();
        //rpanel = new SBSemesterPanel();
        //rpanel = new SBModulePanel();
        //rpanel = new SBHelpPanel();
        // Später soll es über SBController wahlweise bestimmt werden

        splitpane.setDividerLocation(200);

        frame.setContentPane(cpanel);

        statusbar = new JLabel("Ich bin eine Statusbar, die rot werden kann, wenn Fehler auftreten");
        statusbar.setOpaque(true);  // für das Setzen der Hintergrundfarbe
        statusbar.setBackground(Color.red);
        statusbar.setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Sorgt dafür, dass jede GUI-Komponente des Hauptfensters an ihren rechten
     * Platz kommt.
     */
    public void layoutMainFrame() {
        frame.setLayout(new BorderLayout());

        splitpane.setLeftComponent(lpanel);
        splitpane.setRightComponent(rpanel);

        frame.add(statusbar, BorderLayout.SOUTH);
        frame.add(splitpane, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);  // Zentrieren
    }
}