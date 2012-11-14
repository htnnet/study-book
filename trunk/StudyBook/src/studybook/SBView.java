package studybook;

import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;


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
    private MenuBar menuBar;
    private JMenu menu;
    //private JMenuItem newProfile;
    //private JMenuItem openProfile;
    //private JMenuItem saveProfile;
    //private JMenuItem saveAsProfile;
    //private JMenuItem exit;
    private JMenuBar menubar = new JMenuBar(); //Menue
    private JMenu menubar_datei = new JMenu("Datei"); //Datei
    private JMenuItem menubar_datei_neues_profil = new JMenuItem("Neues Profil"); //Datei --> Neues Profil
    private JMenuItem menubar_datei_beenden = new JMenuItem("Beenden"); //Datei --> Beenden
    private JMenu menubar_bearbeiten = new JMenu("Bearbeiten"); //Bearbeiten
    private JMenu menubar_hilfe = new JMenu("Hilfe"); //Hilfe
    private JMenuItem menubar_hilfe_ueber = new JMenuItem("Über"); //Hilfe --> Ueber
    private JTree baum; //Baum, linke Seite
    private TreeNode wurzel; //Wurzel des JTree's !!!Muss datenbankbasiert werden, da mehrere Studiengaenge moeglich
    private ActionListener alistener; //ActionListener fuer Fenster
    
    private SBStudyPanel sbstudypanel;
    private SBHelpPanel sbhelppanel;
    /**
     * Konstruktor der Klasse "SBView"
     *
     * @param controller das Controller-Objekt
     */
    public SBView(SBController controller,SBStudyPanel sbstudypanel,SBHelpPanel sbhelppanel) {
        alistener = new SBActionListener(this,controller); //ActionListener initialisieren
        this.controller = controller;
        this.sbstudypanel = sbstudypanel;
        this.sbhelppanel = sbhelppanel;
    }


    MouseAdapter ma = new MouseAdapter() { //MouseListener, vielleicht auch in eigene Klasse auslagern?
        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                int row = baum.getClosestRowForLocation(e.getX(), e.getY());
                baum.setSelectionRow(row);
                System.out.println(row + "" + e.getComponent() + "" + e.getX() + e.getY());
                JPopupMenu popup = new JPopupMenu();
                popup.add(new JMenuItem("popup: " + row));
                
                popup.show(baum, e.getX(), e.getY()); //Problem: Wie Blatt von Baum eindeutig identifizieren??
                //row aendert sich, jenachdem, wie weit der Baum ausgeklappt ist
            }
        }
    };

    private static TreeNode createTree() { //Baum erstellen
        DefaultMutableTreeNode wurzel = new DefaultMutableTreeNode("Technische Informatik B.Sc.");

        for (int i = 1; i < 8; i++) {
            DefaultMutableTreeNode semester = new DefaultMutableTreeNode(i + ". Semester");
            DefaultMutableTreeNode modul1 = new DefaultMutableTreeNode("MATHE1");
            DefaultMutableTreeNode modul2 = new DefaultMutableTreeNode("GELEK1");
            DefaultMutableTreeNode modul3 = new DefaultMutableTreeNode("INFORM");
            DefaultMutableTreeNode modul4 = new DefaultMutableTreeNode("PROG1");
            DefaultMutableTreeNode modul5 = new DefaultMutableTreeNode("ENGL1");
            semester.add(modul1);
            semester.add(modul2);
            semester.add(modul3);
            semester.add(modul4);
            semester.add(modul5);
            wurzel.add(semester);
        }
        return wurzel;
    }

/**

    private Object[][] menuData() {
        return {{"Datei",{"Neues Profil", }};
    }
    }
    **/

    public void createMenuBar() {
        menubar.add(menubar_datei);
        menubar_datei.add(menubar_datei_neues_profil);
        menubar_datei.add(menubar_datei_beenden);
        menubar_datei_beenden.setActionCommand("beenden");
        menubar_datei_beenden.addActionListener(alistener);

        menubar.add(menubar_bearbeiten);

        menubar.add(menubar_hilfe);
        menubar_hilfe.add(menubar_hilfe_ueber);
        menubar_hilfe_ueber.setActionCommand("ueber");
        menubar_hilfe_ueber.addActionListener(alistener);

        menubar_datei.setMnemonic('D');
        menubar_bearbeiten.setMnemonic('B');
        menubar_hilfe.setMnemonic('H');
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

        wurzel = createTree(); //Baum erstellen
        baum = new JTree(wurzel);
        DefaultTreeCellRenderer tree_renderer = new DefaultTreeCellRenderer() {
            {
                setLeafIcon(new ImageIcon(getClass().getResource("/pics/schreibblock_icon.gif"))); //Icon von Blaettern
                setOpenIcon(new ImageIcon(getClass().getResource("/pics/sb_icon.gif"))); //Icon, wenn aufgeklappt
                setClosedIcon(new ImageIcon(getClass().getResource("/pics/sb_icon.gif"))); //Icon, wenn zugeklappt
            }
        };
        baum.setCellRenderer(tree_renderer); //Renderer dem Baum hinzufuegen
        baum.addMouseListener(ma); //MouseListener dem Baum hinzufuegen


        lpanel = new JPanel();

        // Hier wird das Aussehen des rechten Panels mit folgenden Befehlen bestimmt
        //rpanel = sbstudypanel;
        //rpanel = new SBSemesterPanel();
        //rpanel = new SBModulePanel();
        //rpanel = new SBHelpPanel();
        // Später soll es über SBController wahlweise bestimmt werden

        splitpane.setDividerLocation(200);

        frame.setContentPane(cpanel);

        statusbar = new JLabel("Ich bin eine Statusbar, die rot werden kann, wenn Fehler auftreten");
        statusbar.setOpaque(true);  // für das Setzen der Hintergrundfarbe
        statusbar.setBackground(Color.red);
        statusbar.setForeground(Color.white);
        statusbar.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    public void showError(String message) {
        statusbar.setText(message);
        statusbar.setVisible(true);
    }
    
    public void safe() {
        //Speichern der veränderten Daten
        switch(controller.getActivePanel()) {
            case "sbstudypanel":
                sbstudypanel.safe(controller);
                break;
        }
    }
    
    public void setRightPanel(JPanel panel) {
        rpanel = panel;
        layoutMainFrame();
    }

    /**
     * Sorgt dafür, dass jede GUI-Komponente des Hauptfensters an ihren rechten
     * Platz kommt.
     */
    public void layoutMainFrame() {
        frame.setLayout(new BorderLayout());

        lpanel.setLayout(new BorderLayout());
        lpanel.add(baum);

        splitpane.setLeftComponent(lpanel);
        splitpane.setRightComponent(rpanel);

        frame.add(menubar, BorderLayout.NORTH);
        frame.add(statusbar, BorderLayout.SOUTH);
        statusbar.setVisible(false);
        frame.add(splitpane, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(800, 600);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);  // Zentrieren
    }
}