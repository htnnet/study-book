package studybook;

import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.tree.*;

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
    private JScrollPane treePane;
    private SBMouseTreeListener mouseTreeListener;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode treeRoot;
    private JFrame mainFrame;
    private JSplitPane splitPane;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JMenuBar menuBar;
    private JPopupMenu popupMenu;
    private JMenu menu;
    private JMenu editMenu;
    private JMenu submenu;
    private JMenuItem moduleBarMenuItem;
    private JMenuItem modulePopupMenuItem;
    private ImageIcon itemIcon;
    private JMenuItem menuItem;
    private JLabel statusBar;
    private SBFieldDocument nodeDocument;
    private JTextField nodeField;

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

        leftPanel = new JPanel();
        leftPanel.setMinimumSize(new Dimension(200, 200));
        rightPanel = new JPanel();
        rightPanel.setMinimumSize(new Dimension(500, 500));

        this.createMenuBar();
        this.createPopupMenu();
        this.createTree();
        this.createStatusBar();

        mainFrame.setContentPane(mainPanel);


    }

    /**
     * Setzt den Titel des Frames, um den Benutzer zu zeigen, welche Profildatei
     * er gerade geöffnet hat und wo diese liegt.
     *
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
        leftPanel.add(treePane);

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
     * Baumelement und Unterblätter entfernen.
     */
    public void removeNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                this.treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }
    }

    /**
     * Befüllt das JTree mit neuen Daten.
     * @param nodes
     */
    public void reloadTree(Vector<SBNodeStruct> nodes) {
        //TreePath lastSelected
        treeRoot = this.addTreeNodes(nodes, nodes.get(0), 1);
        treeModel = new DefaultTreeModel(treeRoot) {
            @Override
            public void valueForPathChanged(final TreePath path, final Object newValue) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                SBNodeStruct oldValue = (SBNodeStruct) node.getUserObject();
                oldValue.setName(newValue.toString());

                super.valueForPathChanged(path, oldValue);
        }
        };



        tree.setModel(treeModel);

        // alles aufklappen
        for (int i = 0; i < tree.getRowCount() ; i++) {
            tree.expandRow( i );
        }
        treeModel.addTreeModelListener(mouseTreeListener);
    }

    /**
     * Methode zum Erstellen eines Baumes, der zur Navigation zwischen der
     * Studiengang-,Semester- und Modulverwatlung dient.
     */
    private void createTree() { //Baum erstellen
        //treeRoot = new DefaultMutableTreeNode("root");
        tree = new JTree();
        tree.setEditable(true);
        treePane = new JScrollPane(tree);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        // Damit nur ein Element gleichzeitig ausgewählt werden kann.
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Render erstellen für die Icons
        DefaultTreeCellRenderer treeRenderer = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree,
                    Object value, boolean sel, boolean expanded, boolean leaf,
                    int row, boolean hasFocus) {
                DefaultMutableTreeNode currentTreeNode = (DefaultMutableTreeNode) value;
                int pathLength = currentTreeNode.getPath().length;

                SBNodeStruct userObject = (SBNodeStruct) currentTreeNode.getUserObject();
                ImageIcon studyIcon = new ImageIcon(getClass().getResource("/pics/sb_icon.gif"));
                ImageIcon semesterIcon = new ImageIcon(getClass().getResource("/pics/semester16x16.png"));
                ImageIcon moduleIcon = new ImageIcon(getClass().getResource("/pics/module16x16.png"));

                int level = userObject.getLevel();
                //System.out.println(userObject.toString() + ": \nPfadlänge: " + pathLength + " | Level. " + level );

                switch (pathLength) {
                    case 2:
                        setLeafIcon(studyIcon);
                        setOpenIcon(studyIcon);
                        setClosedIcon(studyIcon);
                        break;
                    case 3:
                        setLeafIcon(semesterIcon);
                        setOpenIcon(semesterIcon);
                        setClosedIcon(semesterIcon);
                        break;
                    case 4:
                        setLeafIcon(moduleIcon);
                        break;


                }
                return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            }
        };

        nodeDocument = new SBFieldDocument(50);

        nodeField = new JTextField(nodeDocument, "", 0);

        tree.setCellRenderer(treeRenderer); //Renderer dem Baum hinzufuegen

        TreeCellEditor fieldEditor = new DefaultCellEditor(nodeField);
        TreeCellEditor editor = new DefaultTreeCellEditor(tree, treeRenderer, fieldEditor);

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setInvokesStopCellEditing(true);      // Fokus-Verlust bedeutet das Ändern des Namens
        tree.setCellEditor(editor);
        mouseTreeListener = new SBMouseTreeListener(controller, tree, popupMenu);
        tree.addMouseListener(mouseTreeListener); // SBMouseTreeListener dem Baum hinzufuegen
        tree.addTreeSelectionListener(mouseTreeListener);
    }

    /**
     * Fügt dem JTree Elemente in Form von SBNodeStruct-Objekten aus einem
     * Vector hinzu.
     * @param nodes Vector mit den SBNodeStruct-Objekten
     * @param parent parent-element
     * @param start gibt die Stelle an, an der der Vector nodes abgearbeitet wird
     * @return
     */
    private DefaultMutableTreeNode addTreeNodes(Vector<SBNodeStruct> nodes, SBNodeStruct parent, int start) {

        DefaultMutableTreeNode node = new DefaultMutableTreeNode(parent);

        // durch den Vektor iterieren
        for (int i = start; i < nodes.size(); i++) {

            // nur Einträge einfügen, deren Verschachtelungstiefe eine höher ist als die des parents
            if (nodes.get(i).getLevel() == parent.getLevel() + 1) {

                // Unterbaum des childs rekursiv anhängen
                node.add(addTreeNodes(nodes, nodes.get(i), i + 1));
            } else {

                // Schleife abbrechen, wenn auf ein Element gestoßen wird, dessen
                // Verschachtelungstiefe
                if (nodes.get(i).getLevel() <= parent.getLevel()) {
                    break;
                }
            }
        }
        return node;

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
        editMenu = new JMenu("Bearbeiten");
        editMenu.setMnemonic('B');

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

        moduleBarMenuItem = new JMenuItem("Modul", 'M');
        itemIcon = new ImageIcon(getClass().getResource("/pics/module16x16.png"));
        moduleBarMenuItem.setIcon(itemIcon);
        moduleBarMenuItem.setActionCommand("module");
        moduleBarMenuItem.addActionListener(actionListener);
        submenu.add(moduleBarMenuItem);
        editMenu.add(submenu);

        menuItem = new JMenuItem("Löschen", 'L');
        itemIcon = new ImageIcon(getClass().getResource("/pics/delete16x16.png"));
        KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke("DELETE");
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(deleteKeyStroke);
        menuItem.setActionCommand("delete");
        menuItem.addActionListener(actionListener);
        editMenu.add(menuItem);

        menuItem = new JMenuItem("Umbenennen", 'U');
        itemIcon = new ImageIcon(getClass().getResource("/pics/rename16x16.png"));
        KeyStroke f2KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        menuItem.setIcon(itemIcon);
        menuItem.setAccelerator(f2KeyStroke);
        menuItem.setActionCommand("rename");
        menuItem.addActionListener(actionListener);
        editMenu.add(menuItem);

        editMenu.setEnabled(false);
        menuBar.add(editMenu);

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

        modulePopupMenuItem = new JMenuItem("Modul", 'M');
        itemIcon = new ImageIcon(getClass().getResource("/pics/module16x16.png"));
        modulePopupMenuItem.setIcon(itemIcon);
        modulePopupMenuItem.setActionCommand("module");
        modulePopupMenuItem.addActionListener(actionListener);
        submenu.add(modulePopupMenuItem);
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

        popupMenu.setEnabled(false);
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
     *
     * @param errorMessage die auszugebende Fehlermeldung
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
     * Wenn kein Baumelement markiert ist, kann mittels dieser Methode das
     * Popup-Menü und das Bearbeiten-Menü aktiviert und deaktiviert werden.
     *
     * @param enabled Menü aktivieren oder deaktivieren
     */
    public void setEditMenuEnabled(boolean enabled) {
        editMenu.setEnabled(enabled);
        popupMenu.setEnabled(enabled);
    }

    /**
     * Wenn ein Studiengang im Baum markiert ist, kann mittels dieser Methode
     * das MenuItem zum Hinzufügen von Modulen deaktiviert werden.
     *
     * @param enabled Menü aktivieren oder deaktivieren
     */
    public void setModuleMenuItemEnabled(boolean enabled) {
        moduleBarMenuItem.setEnabled(enabled);
        modulePopupMenuItem.setEnabled(enabled);

    }

    /**
     * Mithilfe dieser Methode kann das Panel auf der rechten Seite des Frames
     * mit einem beliebigen Panel versehen werden.
     *
     * @param panel
     */
    public void setRightPanel(JPanel rightPanel) {
        // damit beim Panel-Wechsel die alte Position des Dividers gewahrt wird
        int dividerLocation = splitPane.getDividerLocation();
        rightPanel.setMinimumSize(new Dimension(500, 500));
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(dividerLocation);
    }

    /**
     * Gibt das HelpPanel zurück.
     * @return das HelpPanel
     */
    public SBHelpPanel getHelpPanel() {
        return this.helpPanel;
    }

    /**
     * Gibt das StudyPanel zurück.
     * @return das HelpPanel
     */
    public SBStudyPanel getStudyPanel() {
        return this.studyPanel;
    }
     /**
     * Gibt das SemesterPanel zurück.
     * @return das SemesterPanel
     */
    public SBSemesterPanel getSemesterPanel() {
        return this.semesterPanel;
    }
        /**
     * Gibt das ModulePanel zurück.
     * @return das ModulePanel
     */
    public SBModulePanel getModulePanel() {
        return this.modulePanel;
    }

    /**
     * Gibt den Tree zurück.
     * @return der Tree
     */
    public JTree getTree() {
        return this.tree;
    }
}