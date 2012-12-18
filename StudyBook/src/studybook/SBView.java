package studybook;

import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.*;

/**
 * Die grafischen Oberfläche bildet das Herzstück des Programms. Hierüber kann
 * der Benutzer sein Studium verwalten.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBView {

    private SBController controller;
    private SBHelpPanel helpPanel;
    private SBStudyPanel studyPanel;
    private SBSemesterPanel semesterPanel;
    private SBModulePanel modulePanel;
    private JPanel emptyPanel;
    private ActionListener actionListener;
    private SBMouseTreeListener mouseTreeListener;
    private JFileChooser fileChooser;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode treeRoot;
    private SBTreeCellRenderer treeCellRenderer;
    private TreeCellEditor fieldEditor;
    private SBTreeCellEditor treeCelleditor;
    private SBFieldDocument nodeDocument;
    private JTextField nodeField;
    private JScrollPane treePane;
    private JFrame mainFrame;
    private JSplitPane splitPane;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JMenuBar menuBar;
    private JPopupMenu popupMenu;
    private JMenu menu;
    private JMenu addBarMenu;
    private JMenu addPopupMenu;
    private JMenuItem studyBarMenuItem;
    private JMenuItem semesterBarMenuItem;
    private JMenuItem moduleBarMenuItem;
    private JMenuItem deleteBarMenuItem;
    private JMenuItem renameBarMenuItem;
    private JMenuItem studyPopupMenuItem;
    private JMenuItem semesterPopupMenuItem;
    private JMenuItem modulePopupMenuItem;
    private JMenuItem deletePopupMenuItem;
    private JMenuItem renamePopupMenuItem;
    private ImageIcon itemIcon;
    private JMenuItem menuItem;
    private JLabel statusBar;
    private boolean editable = false;

    /**
     * Konstruktor der Klasse "SBView"
     *
     * @param controller das Controller-Objekt
     */
    public SBView(SBController controller) {
        this.controller = controller;
    }

    /**
     * Erstellt das Haupfenster mit ihren grafischen Komponenten.
     */
    public void createView() {
        mainFrame = new JFrame("StudyBook");
        // Fenster-Icon setzen
        try {
            Image img = ImageIO.read(getClass().getResource("/pics/study32x32.png"));
            mainFrame.setIconImage(img);
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        // Beim Schließen des Programms wird abgespeichert
        mainFrame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                controller.exit();
            }
        });

        mainFrame.setResizable(false);

        mainPanel = new JPanel();
        splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        // Panel mit Baum
        leftPanel = new JPanel();
        leftPanel.setMinimumSize(new Dimension(200, 200));

        // Panel mit HilfsPanel, Studiengangverwaltung, Semesterverwaltung und
        // Moduleverwaltung
        rightPanel = new JPanel();
        rightPanel.setMinimumSize(new Dimension(570, 500));

        this.createFileChooser();
        actionListener = new SBActionListener(this, controller); //ActionListener initialisieren
        this.createMenuBar();
        this.createPopupMenu();
        this.createTree();
        this.createStatusBar();
        this.createPanels();

        mainFrame.setContentPane(mainPanel);
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

        mainFrame.setSize(900, 600);
        mainFrame.setMinimumSize(new Dimension(870, 600));
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);  // Zentrieren
    }

    /**
     * Erzeugt die Panels, die auf der rechten Seite des Programms angezeigt
     * werden.
     */
    private void createPanels() {
        helpPanel = new SBHelpPanel();
        studyPanel = new SBStudyPanel();
        semesterPanel = new SBSemesterPanel();
        modulePanel = new SBModulePanel();
        emptyPanel = new JPanel();
    }

    /**
     * Erstellt einen JFileChosser mit dessen Hilfe Dateien zum Öffnen und
     * Zielorte zum Abspeichern ausgewählt werden können.
     */
    private void createFileChooser() {
        // FileFilter erstellen, um ungewollte Dateien in der
        // Verzeichnisauflistung herauszufiltern
        FileFilter fileFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.getName().toLowerCase().endsWith(".sbprofile") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return "StudyBook Profil (*.sbprofile)";
            }
        };
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
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

        addBarMenu = new JMenu("Hinzufügen");
        addBarMenu.setMnemonic('H');
        itemIcon = new ImageIcon(getClass().getResource("/pics/add16x16.png"));
        addBarMenu.setIcon(itemIcon);

        studyBarMenuItem = new JMenuItem("Studiengang", 'S');
        itemIcon = new ImageIcon(getClass().getResource("/pics/study16x16.png"));
        studyBarMenuItem.setIcon(itemIcon);
        studyBarMenuItem.setActionCommand("study");
        studyBarMenuItem.addActionListener(actionListener);
        addBarMenu.add(studyBarMenuItem);

        semesterBarMenuItem = new JMenuItem("Semester", 'e');
        itemIcon = new ImageIcon(getClass().getResource("/pics/semester16x16.png"));
        semesterBarMenuItem.setIcon(itemIcon);
        semesterBarMenuItem.setActionCommand("semester");
        semesterBarMenuItem.addActionListener(actionListener);
        addBarMenu.add(semesterBarMenuItem);

        moduleBarMenuItem = new JMenuItem("Modul", 'M');
        itemIcon = new ImageIcon(getClass().getResource("/pics/module16x16.png"));
        moduleBarMenuItem.setIcon(itemIcon);
        moduleBarMenuItem.setActionCommand("module");
        moduleBarMenuItem.addActionListener(actionListener);
        addBarMenu.add(moduleBarMenuItem);
        menu.add(addBarMenu);

        deleteBarMenuItem = new JMenuItem("Löschen", 'L');
        itemIcon = new ImageIcon(getClass().getResource("/pics/delete16x16.png"));
        KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke("DELETE");
        deleteBarMenuItem.setIcon(itemIcon);
        deleteBarMenuItem.setAccelerator(deleteKeyStroke);
        deleteBarMenuItem.setActionCommand("delete");
        deleteBarMenuItem.addActionListener(actionListener);
        menu.add(deleteBarMenuItem);

        renameBarMenuItem = new JMenuItem("Umbenennen", 'U');
        itemIcon = new ImageIcon(getClass().getResource("/pics/rename16x16.png"));
        KeyStroke f2KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        renameBarMenuItem.setIcon(itemIcon);
        renameBarMenuItem.setAccelerator(f2KeyStroke);
        renameBarMenuItem.setActionCommand("rename");
        renameBarMenuItem.addActionListener(actionListener);
        menu.add(renameBarMenuItem);

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

        menuItem = new JMenuItem("Über StudyBook", 'Ü');
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
        // Hinzufügen
        popupMenu = new JPopupMenu();
        addPopupMenu = new JMenu("Hinzufügen");
        addPopupMenu.setMnemonic('H');
        itemIcon = new ImageIcon(getClass().getResource("/pics/add16x16.png"));
        addPopupMenu.setIcon(itemIcon);

        studyPopupMenuItem = new JMenuItem("Studiengang", 'S');
        itemIcon = new ImageIcon(getClass().getResource("/pics/study16x16.png"));
        studyPopupMenuItem.setIcon(itemIcon);
        studyPopupMenuItem.setActionCommand("study");
        studyPopupMenuItem.addActionListener(actionListener);
        addPopupMenu.add(studyPopupMenuItem);

        semesterPopupMenuItem = new JMenuItem("Semester", 'e');
        itemIcon = new ImageIcon(getClass().getResource("/pics/semester16x16.png"));
        semesterPopupMenuItem.setIcon(itemIcon);
        semesterPopupMenuItem.setActionCommand("semester");
        semesterPopupMenuItem.addActionListener(actionListener);
        addPopupMenu.add(semesterPopupMenuItem);

        modulePopupMenuItem = new JMenuItem("Modul", 'M');
        itemIcon = new ImageIcon(getClass().getResource("/pics/module16x16.png"));
        modulePopupMenuItem.setIcon(itemIcon);
        modulePopupMenuItem.setActionCommand("module");
        modulePopupMenuItem.addActionListener(actionListener);
        addPopupMenu.add(modulePopupMenuItem);
        popupMenu.add(addPopupMenu);

        // Löschen
        deletePopupMenuItem = new JMenuItem("Löschen", 'L');
        itemIcon = new ImageIcon(getClass().getResource("/pics/delete16x16.png"));
        KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke("DELETE");
        deletePopupMenuItem.setIcon(itemIcon);
        deletePopupMenuItem.setAccelerator(deleteKeyStroke);
        deletePopupMenuItem.setActionCommand("delete");
        deletePopupMenuItem.addActionListener(actionListener);
        popupMenu.add(deletePopupMenuItem);

        // Umbenennen
        renamePopupMenuItem = new JMenuItem("Umbenennen", 'U');
        itemIcon = new ImageIcon(getClass().getResource("/pics/rename16x16.png"));
        KeyStroke f2KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        renamePopupMenuItem.setIcon(itemIcon);
        renamePopupMenuItem.setAccelerator(f2KeyStroke);
        renamePopupMenuItem.setActionCommand("rename");
        renamePopupMenuItem.addActionListener(actionListener);
        popupMenu.add(renamePopupMenuItem);
    }

    /**
     * Hiermit wird gewährleistet, dass die Menüs ungewollt ausgegraut werden.
     *
     * @param editable Menüs ausgraubar oder nicht
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Wenn kein Baumelement markiert ist, kann mittels dieser Methode MenuItems
     * des Popup-Menüs und des Bearbeiten-Menüs aktiviert und deaktiviert
     * werden.
     *
     * @param study Studiengang-MenuItem aktivieren oder deaktivieren
     * @param semester Semester-MenuItem aktivieren oder deaktivieren
     * @param module Modul-MenuItem aktivieren oder deaktivieren
     * @param delete Löschen-MenuItem aktivieren oder deaktivieren
     * @param rename Umbenennen-MenuItem aktivieren oder deaktivieren
     */
    public void setEditMenuEnabled(boolean add, boolean study, boolean semester, boolean module, boolean delete, boolean rename) {
        if (this.editable) {
            addBarMenu.setEnabled(add);
            addPopupMenu.setEnabled(add);
            studyBarMenuItem.setEnabled(study);
            studyPopupMenuItem.setEnabled(study);
            semesterBarMenuItem.setEnabled(semester);
            semesterPopupMenuItem.setEnabled(semester);
            moduleBarMenuItem.setEnabled(module);
            modulePopupMenuItem.setEnabled(module);
            deleteBarMenuItem.setEnabled(delete);
            deletePopupMenuItem.setEnabled(delete);
            renameBarMenuItem.setEnabled(rename);
            renamePopupMenuItem.setEnabled(rename);
        } else {
            addBarMenu.setEnabled(false);
            addPopupMenu.setEnabled(false);
            studyBarMenuItem.setEnabled(false);
            studyPopupMenuItem.setEnabled(false);
            semesterBarMenuItem.setEnabled(false);
            semesterPopupMenuItem.setEnabled(false);
            moduleBarMenuItem.setEnabled(false);
            modulePopupMenuItem.setEnabled(false);
            deleteBarMenuItem.setEnabled(false);
            deletePopupMenuItem.setEnabled(false);
            renameBarMenuItem.setEnabled(false);
            renamePopupMenuItem.setEnabled(false);
        }
    }

    /**
     * Methode zum Erstellen eines Baumes, der zur Navigation zwischen der
     * Studiengang-,Semester- und Modulverwatlung dient.
     */
    private void createTree() {
        treeRoot = new DefaultMutableTreeNode("root");
        tree = new JTree(treeRoot);
        tree.setEditable(true);     // Baumelemente dürfen umbenannt werden
        treePane = new JScrollPane(tree);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        // Damit nur ein Element gleichzeitig ausgewählt werden kann.
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        // Fokus-Verlust bedeutet das Ändern des Namens
        tree.setInvokesStopCellEditing(true);

        // Zeichenlänge eines Baumelements auf 50 beschränken
        nodeDocument = new SBFieldDocument(50);
        nodeField = new JTextField(nodeDocument, "", 0);

        treeCellRenderer = new SBTreeCellRenderer();
        tree.setCellRenderer(treeCellRenderer);

        fieldEditor = new DefaultCellEditor(nodeField);
        treeCelleditor = new SBTreeCellEditor(tree, treeCellRenderer, fieldEditor);
        tree.setCellEditor(treeCelleditor);

        // Listener
        mouseTreeListener = new SBMouseTreeListener(controller, this, tree, popupMenu);
        tree.addMouseListener(mouseTreeListener); // SBMouseTreeListener dem Baum hinzufuegen
        tree.addTreeSelectionListener(mouseTreeListener);
    }

    /**
     * Befüllt den JTree mit neuen Daten in Form eines speziellen Vektors.
     *
     * @param nodes Vektor, der die hineinzufügenden Elemente enthält
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
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
        treeModel.addTreeModelListener(mouseTreeListener);
    }

    /**
     * Fügt dem JTree rekursiv Elemente in Form von SBNodeStruct-Objekten aus
     * einem Vector hinzu.
     *
     * @param nodes Vector mit den SBNodeStruct-Objekten
     * @param parent parent-element
     * @param start Stelle an, an der der Vector nodes abgearbeitet wird
     * @return die anzuhängende node
     */
    private DefaultMutableTreeNode addTreeNodes(Vector<SBNodeStruct> nodes, SBNodeStruct parent, int start) {

        DefaultMutableTreeNode node = new DefaultMutableTreeNode(parent);

        // durch den Vektor iterieren
        for (int i = start; i < nodes.size(); i++) {

            // nur Einträge einfügen, deren Verschachtelungstiefe eine höher ist, als die des parents
            if (nodes.get(i).getLevel() == parent.getLevel() + 1) {

                // Unterbaum des childs rekursiv anhängen
                node.add(addTreeNodes(nodes, nodes.get(i), i + 1));
            } else {

                // Schleife abbrechen, wenn auf ein Element gestoßen wird, das
                // keine Unterelemente hat
                if (nodes.get(i).getLevel() <= parent.getLevel()) {
                    break;
                }
            }
        }
        return node;
    }

    /**
     * Methode zum Hinzufügen eines Baumelements.
     *
     * @param child das hinzuzufügende Kind-Objekt
     * @return das hinzuzufügende Baumelement, das aus dem Objekt gewonnen wurde
     */
    public DefaultMutableTreeNode addTreeNode(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = treeRoot;
        } else {
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }

        return addTreeNode(parentNode, child, true);
    }

    /**
     * Methode zum Hinzufügen eines Baumelements, dessen Oberelement bekannt
     * ist.
     *
     * @param parent das Oberlement an das das Element angefügt wird
     * @param child das hinzuzufügende Kind-Objekt
     * @param shouldBeVisible damit das hinzugefügte Baumelement gesehen wird
     * @return das hinzuzufügende Baumelement
     */
    public DefaultMutableTreeNode addTreeNode(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = treeRoot;
        }

        // Änderungen vornhemen
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        // Falls das neue Baumelement nicht mehr zu sehen ist, hinscrollen
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    /**
     * Mit dieser Methode kann ein Baumelement und seine Unterelemente entfernt
     * werden.
     */
    public void removeNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                this.treeModel.removeNodeFromParent(currentNode);
            }
        }
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
     * Mithilfe dieser Methode kann das Panel auf der rechten Seite des Frames
     * mit einem beliebigen Panel versehen werden.
     *
     * @param panel das Panel, das auf der rechten Seite dargestellt werden soll
     */
    public void setRightPanel(JPanel rightPanel) {
        // damit beim Panel-Wechsel die alte Position des Dividers gewahrt wird
        int dividerLocation = splitPane.getDividerLocation();
        rightPanel.setMinimumSize(new Dimension(500, 500));
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(dividerLocation);
    }

    /**
     * Dient zum Anzeigen eines Panels, das Informationen über das Programm
     * preisgibt.
     */
    public void showAboutDialog() {
        JDialog aboutDialog = new SBAboutDialog(this.mainFrame);
        aboutDialog.setResizable(false);
        aboutDialog.setLocationRelativeTo(null);
        aboutDialog.setVisible(true);
    }

    /**
     * Gibt den erzeugten FileChooser zurück, der vom ActionListener verwendet
     * wird, um entsprechende FileChooser-Dialog anzuzeigen.
     *
     * @return
     */
    public JFileChooser getFileChooser() {
        return this.fileChooser;
    }

    /**
     * Gibt das HelpPanel zurück.
     *
     * @return das HelpPanel
     */
    public SBHelpPanel getHelpPanel() {
        return this.helpPanel;
    }

    /**
     * Gibt das StudyPanel zurück.
     *
     * @return das StudyPanel
     */
    public SBStudyPanel getStudyPanel() {
        return this.studyPanel;
    }

    /**
     * Gibt das SemesterPanel zurück.
     *
     * @return das SemesterPanel
     */
    public SBSemesterPanel getSemesterPanel() {
        return this.semesterPanel;
    }

    /**
     * Gibt das ModulePanel zurück.
     *
     * @return das ModulePanel
     */
    public SBModulePanel getModulePanel() {
        return this.modulePanel;
    }

    /**
     * Gibt ein leeres Panel zurück, das bei nicht ausgewählten Baumelement
     * benötigt wird.
     *
     * @return eine leeres Panel
     */
    public JPanel getEmptyPanel() {
        return this.emptyPanel;
    }

    /**
     * Gibt den Tree zurück.
     *
     * @return der Tree
     */
    public JTree getTree() {
        return this.tree;
    }
}