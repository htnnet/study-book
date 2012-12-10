package studybook;

import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * Die grafischen Oberfläche bildet das Herzstück des Programms. Hierüber kann
 * der Benutzer sein Studium verwalten.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBView {

    private SBController controller;
    private SBHelpPanel helpPanel;
    private SBStudyPanel studyPanel;
    private SBSemesterPanel semesterPanel;
    private SBModulePanel modulePanel;
    private ActionListener actionListener;
    private SBMouseTreeListener mouseTreeListener;
    private JTree tree;
    private DefaultMutableTreeNode treeRoot;
    private JFrame mainFrame;
    private JSplitPane splitPane;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JMenuBar menuBar;
    private JPopupMenu popupMenu;
    private JMenu menu;
    private JMenu submenu;
    private ImageIcon itemIcon;
    private JMenuItem menuItem;
    private JLabel statusBar;

    /**
     * Konstruktor der Klasse "SBView"
     *
     * @param controller das Controller-Objekt
     */
    public SBView(SBController controller) {
        actionListener = new SBActionListener(this, controller); //ActionListener initialisieren
        this.controller = controller;
    }

    /**
     * Erstellt das Haupfenster mit ihren grafischen Komponenten.
     */
    public void createView() {
        mainFrame = new JFrame("StudyBook");
        // Fenster-Icon setzen
        try {
            Image img = ImageIO.read(getClass().getResource("/pics/sb_icon.gif"));
            mainFrame.setIconImage(img);
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.exit();
            }
        });
        mainFrame.setResizable(false);

        mainPanel = new JPanel();
        splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(200);

        leftPanel = new JPanel();
        this.createMenuBar();
        this.createPopupMenu();
        this.createTree();
        this.createStatusBar();

        mainFrame.setContentPane(mainPanel);

    }

    /**
     * Setzt den Titel des Frames, um den Benutzer zu zeigen, welche Profildatei
     * er gerade geöffnet hat und wo diese liegt.
     * @param title der neue Titel des Frames
     */
    public void setFrameTitle(String title) {
        mainFrame.setTitle(title);
        mainFrame.revalidate();
    }

    /**
     * Sorgt dafür, dass jede GUI-Komponente des Hauptfensters an ihren rechten
     * Platz kommt.
     */
    public void layoutView() {
        mainFrame.setLayout(new BorderLayout());

        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(tree);

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        mainFrame.add(statusBar, BorderLayout.SOUTH);
        mainFrame.add(splitPane, BorderLayout.CENTER);

        mainFrame.setSize(800, 600);
        mainFrame.setMinimumSize(new Dimension(800, 600));
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);  // Zentrieren
    }

    /**
     * Methode zum Erstellen eines Baumes, der zur Navigation zwischen der
     * Studiengang-,Semester- und Modulverwatlung dient.
     */

    private void createTree() { //Baum erstellen
        treeRoot = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode studiengang1 = new DefaultMutableTreeNode("Technische Informatik B.Sc.");
        DefaultMutableTreeNode studiengang2 = new DefaultMutableTreeNode("Informatik M.Sc.");

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
            studiengang1.add(semester);
        }

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
            studiengang2.add(semester);
        }

        treeRoot.add(studiengang1);
        treeRoot.add(studiengang2);
        tree = new JTree(treeRoot);

        // Damit nur ein Element gleichzeitig ausgewählt werden kann.
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        DefaultTreeCellRenderer tree_renderer = new DefaultTreeCellRenderer() {
            {
                setLeafIcon(new ImageIcon(getClass().getResource("/pics/module16x16.png"))); //Icon von Blaettern
                setOpenIcon(new ImageIcon(getClass().getResource("/pics/sb_icon.gif"))); //Icon, wenn aufgeklappt
                setClosedIcon(new ImageIcon(getClass().getResource("/pics/sb_icon.gif"))); //Icon, wenn zugeklappt
            }
        };
        tree.setRootVisible(false);
        tree.setCellRenderer(tree_renderer); //Renderer dem Baum hinzufuegen
        mouseTreeListener = new SBMouseTreeListener(controller, tree, popupMenu);
        tree.addMouseListener(mouseTreeListener); // SBMouseTreeListener dem Baum hinzufuegen
        tree.addTreeSelectionListener(mouseTreeListener);
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
        itemIcon = new ImageIcon(getClass().getResource("/pics/new_profile16x16.png"));
        KeyStroke ctrlNKeyStroke = KeyStroke.getKeyStroke("control N");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(ctrlNKeyStroke);
        menuItem.setActionCommand("new");
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Profil öffnen", 'ö');
        itemIcon = new ImageIcon(getClass().getResource("/pics/open16x16.png"));
        KeyStroke ctrlOKeyStroke = KeyStroke.getKeyStroke("control O");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(ctrlOKeyStroke);
        menuItem.setActionCommand("open");
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Profil speichern", 's');
        itemIcon = new ImageIcon(getClass().getResource("/pics/save16x16.png"));
        KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(ctrlSKeyStroke);
        menuItem.setActionCommand("save");
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);

        menuItem = new JMenuItem("Profil speichern unter...", 'u');
        itemIcon = new ImageIcon(getClass().getResource("/pics/saveas16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("saveas");
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Beenden", 'e');
        itemIcon = new ImageIcon(getClass().getResource("/pics/exit16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("exit");
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);

        menuBar.add(menu);

        // Bearbeiten
        menu = new JMenu("Bearbeiten");
        menu.setMnemonic('B');

        submenu = new JMenu("Hinzufügen");
        submenu.setMnemonic('H');
        itemIcon = new ImageIcon(getClass().getResource("/pics/add16x16.png"));
        submenu.setIcon(itemIcon);

        menuItem = new JMenuItem("Studiengang", 'S');
        itemIcon = new ImageIcon(getClass().getResource("/pics/sb_icon.gif"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("study");
        menuItem.addActionListener(actionListener);
        submenu.add(menuItem);

        menuItem = new JMenuItem("Semester", 'e');
        itemIcon = new ImageIcon(getClass().getResource("/pics/semester16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("semester");
        menuItem.addActionListener(actionListener);
        submenu.add(menuItem);

        menuItem = new JMenuItem("Modul", 'M');
        itemIcon = new ImageIcon(getClass().getResource("/pics/module16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("module");
        menuItem.addActionListener(actionListener);
        submenu.add(menuItem);
        menu.add(submenu);

        menuItem = new JMenuItem("Löschen", 'L');
        itemIcon = new ImageIcon(getClass().getResource("/pics/delete16x16.png"));
        KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke("DELETE");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(deleteKeyStroke);
        menuItem.setActionCommand("delete");
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);

        menuItem = new JMenuItem("Umbenennen", 'U');
        itemIcon = new ImageIcon(getClass().getResource("/pics/rename16x16.png"));
        KeyStroke f2KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(f2KeyStroke);
        menuItem.setActionCommand("rename");
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);

        menuBar.add(menu);

        // Hilfe
        menu = new JMenu("Hilfe");
        menu.setMnemonic('H');

        menuItem = new JMenuItem("Hilfe", 'H');
        itemIcon = new ImageIcon(getClass().getResource("/pics/help16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("help");
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);

        menuItem = new JMenuItem("Über", 'Ü');
        itemIcon = new ImageIcon(getClass().getResource("/pics/about16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("about");
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);

        menuBar.add(menu);
        mainFrame.setJMenuBar(menuBar);
    }

    /**
     * Erstelt ein JPopupMenu, das bei einem Rechtsklick auf den Tree erscheint.
     */
    private void createPopupMenu() {
        popupMenu = new JPopupMenu();
        submenu = new JMenu("Hinzufügen");
        submenu.setMnemonic('H');
        itemIcon = new ImageIcon(getClass().getResource("/pics/add16x16.png"));
        submenu.setIcon(itemIcon);

        menuItem = new JMenuItem("Studiengang", 'S');
        itemIcon = new ImageIcon(getClass().getResource("/pics/sb_icon.gif"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("study");
        menuItem.addActionListener(actionListener);
        submenu.add(menuItem);

        menuItem = new JMenuItem("Semester", 'e');
        itemIcon = new ImageIcon(getClass().getResource("/pics/semester16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("semester");
        menuItem.addActionListener(actionListener);
        submenu.add(menuItem);

        menuItem = new JMenuItem("Modul", 'M');
        itemIcon = new ImageIcon(getClass().getResource("/pics/module16x16.png"));
        menuItem.setIcon(itemIcon);
        menuItem.setActionCommand("module");
        menuItem.addActionListener(actionListener);
        submenu.add(menuItem);
        popupMenu.add(submenu);

        menuItem = new JMenuItem("Löschen", 'L');
        itemIcon = new ImageIcon(getClass().getResource("/pics/delete16x16.png"));
        KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke("DELETE");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(deleteKeyStroke);
        menuItem.setActionCommand("delete");
        menuItem.addActionListener(actionListener);
        popupMenu.add(menuItem);

        menuItem = new JMenuItem("Umbenennen", 'U');
        itemIcon = new ImageIcon(getClass().getResource("/pics/rename16x16.png"));
        KeyStroke f2KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(f2KeyStroke);
        menuItem.setActionCommand("rename");
        menuItem.addActionListener(actionListener);
        popupMenu.add(menuItem);
    }
    /**
     * Die StatusBar fungiert in erster Linie dazu dem Benutzer über
     * aufgetretene Fehler zu unterrichten. Hierbei wird sie rot, um die Blicke
     * des Benutzers auf sich zu lenken.
     */
    private void createStatusBar() {
        statusBar = new JLabel(" ");
        statusBar.setOpaque(true);  // für das Setzen der Hintergrundfarbe
        statusBar.setBackground(Color.red);
        statusBar.setForeground(Color.white);
        statusBar.setHorizontalAlignment(SwingConstants.CENTER);
        this.hideStatusError();
    }

    /**
     * Lässt eine rot aufleuchtende Statusbar erscheinen, die dem Benutzer über
     * eventuell auftretende Fehler unterrichtet.
     * @param errorMessage
     */
    public void showStatusError(String errorMessage) {
        statusBar.setText(errorMessage);
        statusBar.setVisible(true);
    }

     /**
     * Ist der Fehler behoben, so wird mittels dieser Methode die StatusBar
     * ausgeblendet.
     */
    public void hideStatusError() {
        statusBar.setVisible(false);
    }

    /**
     * Mithilfe dieser Methode kann das Panel auf der rechten Seite des
     * Frames mit einem beliebigen Panel versehen werden.
     * @param panel
     */
    public void setRightPanel(JPanel panel) {
        rightPanel = panel;
        this.layoutView();
    }
}