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
 * Die grafischen Oberfläche bildet das Herzstück des Programms. Hierüber kann
 * der Benutzer sein Studium verwalten.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBView extends JFrame {

    private SBController controller;
    private JFrame frame;
    private JPanel cpanel;
    private JPanel lpanel;
    private JPanel rpanel;
    private JLabel statusBar;
    private JSplitPane splitpane;
    private JMenuBar menuBar;
    private JMenu menu;
    private ImageIcon itemIcon;
    private JMenuItem menuItem;
    private JTree tree; //Baum, linke Seite
    private TreeNode root; //Wurzel des JTree's !!!Muss datenbankbasiert werden, da mehrere Studiengaenge moeglich
    private ActionListener alistener; //ActionListener fuer Fenster


    /**
     * Konstruktor der Klasse "SBView"
     *
     * @param controller das Controller-Objekt
     */
    public SBView(SBController controller) {
        alistener = new SBActionListener(this, controller); //ActionListener initialisieren
        this.controller = controller;

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
        frame.addWindowListener(new SBWindowListener(controller));
        frame.setResizable(false);

        cpanel = new JPanel();
        splitpane = new JSplitPane();
        splitpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitpane.setDividerLocation(200);

        lpanel = new JPanel();
        this.createTree();
        this.createMenuBar();
        this.createStatusBar();

        frame.setContentPane(cpanel);
    }
    
    public void setFrameTitle(String path) {
        frame.setTitle("StudyBook - "+path);
        frame.revalidate();
    }
    
    public void setStandardTitle() {
        frame.setTitle("StudyBook");
        frame.revalidate();
    }

    /**
     * Sorgt dafür, dass jede GUI-Komponente des Hauptfensters an ihren rechten
     * Platz kommt.
     */
    public void layoutMainFrame() {
        frame.setLayout(new BorderLayout());

        lpanel.setLayout(new BorderLayout());
        lpanel.add(tree);

        splitpane.setLeftComponent(lpanel);
        splitpane.setRightComponent(rpanel);

        frame.add(statusBar, BorderLayout.SOUTH);
        frame.add(splitpane, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(800, 600);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);  // Zentrieren
    }


    /**
     * Hier wird die MenuBar mitsamt ihren Menus und MenuItems erzeugt, die dem
     * Benutzer Interaktionsmögichkeiten mit dem Programm ermöglichen.
     */
    private void createMenuBar() {
        menuBar = new JMenuBar();

        // Datei
        menu = new JMenu("Datei");
        menu.setMnemonic('D');

        menuItem = new JMenuItem("Neues Profil", 'N');
        itemIcon = new ImageIcon(getClass().getResource("/pics/new16x16.png"));
        KeyStroke ctrlNKeyStroke = KeyStroke.getKeyStroke("control N");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(ctrlNKeyStroke);
        menuItem.setActionCommand("new");
        menuItem.addActionListener(alistener);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Profil öffnen", 'ö');
        itemIcon = new ImageIcon(getClass().getResource("/pics/open16x16.png"));
        KeyStroke ctrlOKeyStroke = KeyStroke.getKeyStroke("control O");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(ctrlOKeyStroke);
        menuItem.setActionCommand("open");
        menuItem.addActionListener(alistener);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Profil speichern", 's');
        itemIcon = new ImageIcon(getClass().getResource("/pics/save16x16.png"));
        KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(ctrlSKeyStroke);
        menuItem.setActionCommand("save");
        menuItem.addActionListener(alistener);
        menu.add(menuItem);

        menuItem = new JMenuItem("Profil speichern unter...", 'u');
        itemIcon = new ImageIcon(getClass().getResource("/pics/saveas16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("saveas");
        menuItem.addActionListener(alistener);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Beenden", 'e');
        itemIcon = new ImageIcon(getClass().getResource("/pics/exit16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("exit");
        menuItem.addActionListener(alistener);
        menu.add(menuItem);

        menuBar.add(menu);

        // Bearbeiten
        menu = new JMenu("Bearbeiten");
        menu.setMnemonic('B');

        menuItem = new JMenuItem("Knoten hinzufügen", 'h');
        itemIcon = new ImageIcon(getClass().getResource("/pics/add16x16.png"));
        KeyStroke ctrlAKeyStroke = KeyStroke.getKeyStroke("control A");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(ctrlAKeyStroke);
        menuItem.setActionCommand("add");
        menuItem.addActionListener(alistener);
        menu.add(menuItem);

        menuItem = new JMenuItem("Knoten entfernen", 'e');
        itemIcon = new ImageIcon(getClass().getResource("/pics/remove16x16.png"));
        KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke("control R");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(ctrlRKeyStroke);
        menuItem.setActionCommand("remove");
        menuItem.addActionListener(alistener);
        menu.add(menuItem);

        menuBar.add(menu);

        // Hilfe
        menu = new JMenu("Hilfe");
        menu.setMnemonic('H');

        menuItem = new JMenuItem("Hilfe", 'H');
        itemIcon = new ImageIcon(getClass().getResource("/pics/help16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("help");
        menuItem.addActionListener(alistener);
        menu.add(menuItem);

        menuItem = new JMenuItem("Über", 'Ü');
        itemIcon = new ImageIcon(getClass().getResource("/pics/about16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("about");
        menuItem.addActionListener(alistener);
        menu.add(menuItem);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    /**
     * Die StatusBar fungiert in erster Linie dazu dem Benutzer über
     * aufgetretene Fehler zu unterrichten. Hierbei wird sie rot, um die Blicke
     * des Benutzers auf sich zu lenken.
     */
    private void createStatusBar() {
        statusBar = new JLabel("Ich bin eine Statusbar, die rot werden kann, wenn Fehler auftreten");
        statusBar.setOpaque(true);  // für das Setzen der Hintergrundfarbe
        statusBar.setBackground(Color.red);
        statusBar.setForeground(Color.white);
        statusBar.setHorizontalAlignment(SwingConstants.CENTER);
        this.hideStatusBar();
    }

    private void createTree() { //Baum erstellen
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Technische Informatik B.Sc.");

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
            root.add(semester);
        }

        tree = new JTree(root);
        DefaultTreeCellRenderer tree_renderer = new DefaultTreeCellRenderer() {
            {
                setLeafIcon(new ImageIcon(getClass().getResource("/pics/schreibblock_icon.gif"))); //Icon von Blaettern
                setOpenIcon(new ImageIcon(getClass().getResource("/pics/sb_icon.gif"))); //Icon, wenn aufgeklappt
                setClosedIcon(new ImageIcon(getClass().getResource("/pics/sb_icon.gif"))); //Icon, wenn zugeklappt
            }
        };
        tree.setCellRenderer(tree_renderer); //Renderer dem Baum hinzufuegen
        tree.addMouseListener(new SBMouseListener(controller, tree)); //MouseListener dem Baum hinzufuegen

    }

    public void showError(String message) {
        statusBar.setText(message);
        statusBar.setVisible(true);
    }
    
    public void hideStatusBar() {
        statusBar.setVisible(false);
    }

    public void setRightPanel(JPanel panel) {
        rpanel = panel;
        layoutMainFrame();
    }
}