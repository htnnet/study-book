/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studybook;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Admin
 */
public class StudyBook {

    private JFrame mainframe = new JFrame("StudyBook"); //Hauptfenster
    private JMenuBar menubar = new JMenuBar(); //Menue
    private JMenu menubar_datei = new JMenu("Datei"); //Datei
    private JMenuItem menubar_datei_neues_profil = new JMenuItem("Neues Profil"); //Datei --> Neues Profil
    private JMenuItem menubar_datei_beenden = new JMenuItem("Beenden"); //Datei --> Beenden
    private JMenu menubar_bearbeiten = new JMenu("Bearbeiten"); //Bearbeiten
    private JMenu menubar_hilfe = new JMenu("Hilfe"); //Hilfe
    private JMenuItem menubar_hilfe_ueber = new JMenuItem("Über"); //Hilfe --> Ueber
    private JSplitPane splitpane;
    private JTree baum; //Baum, linke Seite
    private TreeNode wurzel; //Wurzel des JTree's !!!Muss datenbankbasiert werden, da mehrere Studiengaenge moeglich
    private JPanel main = new JPanel(); //Hauptpanel, rechte Seite
    private ActionListener alistener; //ActionListener fuer Fenster

    public StudyBook() {
        alistener = new SBActionListener(this); //ActionListener initialisieren
        neues_profil("test"); //Testaufruf neues_profil
    }

    private void neues_profil(String name) {
        SBModel sqltest = new SBModel();
        sqltest.connect(name + ".profile"); //Speichere Datenbank unter name.profile
        sqltest.query("CREATE TABLE IF NOT EXISTS studiengaenge (name);"); //Erstelle Tabelle fuer Studiengaenge
        sqltest.query("INSERT INTO studiengaenge (name) VALUES ('testname')"); //Fuege Teststudiengang hinzu
        try {
            ResultSet rs = sqltest.get("SELECT * FROM studiengaenge"); //Alles von der Tabelle Studiengaenge holen
            while (rs.next()) {
                System.out.println("name = " + rs.getString("name")); //Und alle Werte "name" nacheinander auflisten
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

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

    public void setMainFrame(JLabel label) { //Rechte Seite Label hinzufuegen (zB bei Hilfe->Ueber)
        main.add(label);
        main.revalidate();
    }

    public void initialize() {
        mainframe.setSize(800, 600); //Groesse des Hauptfensters auf 800x600 setzen
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Bei Klick auf X, Programm beenden
        mainframe.setLayout(new BorderLayout()); //BorderLayout zur Anordnung

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

        mainframe.add(menubar, BorderLayout.NORTH);

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

        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, baum, main);
        splitpane.setContinuousLayout(true);
        splitpane.setDividerLocation(200);

        mainframe.add(splitpane, BorderLayout.CENTER);
        mainframe.setVisible(true);

        try {
            Image img = ImageIO.read(getClass().getResource("/pics/sb_icon.gif"));
            mainframe.setIconImage(img);
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (System.getProperty("os.name").indexOf("Windows") != -1) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        StudyBook sb = new StudyBook();
        sb.initialize();
    }
}