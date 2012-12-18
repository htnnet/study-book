package studybook;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Ein Dialog, das Informationen über das Programm "StudyBook" preisgibt.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBAboutDialog extends JDialog {

    private JPanel aboutPanel;
    private JLabel aboutIcon;
    private JLabel aboutLabel;
    private ImageIcon icon;
    private Border margin;
    private String about;
    final int EMPTYSPACE = 5;

    /**
     * Der Konstruktor der Klasse SBAboutDialog, der den Super-Konstruktor der
     * Klasse JDialog aufruft.
     *
     * @param parent das Parent-Frame zu dem dieser Dialog gehören soll
     */
    public SBAboutDialog(JFrame parent) {
        super(parent, "Über StudyBook", true);
        this.createAboutDialog();
        this.layoutAboutDialog();
    }

    /**
     * Die grafischen Komponenten, die diesen Dialog ausmachen, werden hier
     * erzeugt.
     */
    private void createAboutDialog() {
        margin = BorderFactory.createEmptyBorder(EMPTYSPACE, EMPTYSPACE, EMPTYSPACE, EMPTYSPACE);

        aboutPanel = new JPanel();
        aboutPanel.setBorder(margin);

        // das Logo mit dem Versionstext
        icon = new ImageIcon(getClass().getResource("/pics/about.gif"));
        aboutIcon = new JLabel(icon);
        aboutIcon.setText("<HTML><b>Version 1.0<b></HTML>");
        aboutIcon.setHorizontalTextPosition(JLabel.CENTER);
        aboutIcon.setVerticalTextPosition(JLabel.BOTTOM);


        // der Text, der die Autoren in einem JLabel mit HTML darstellt
        about = "<HTML><hr><h3>Autoren:</h3>"
                + "Andre Müller, Alexander Keller,<br>"
                + "Bernd Hitzelberger, Michael Dazjuk,<br>"
                + "Robert Heimsoth, Thomas Matern, Thomas Wolscht</HTML>";
        aboutLabel = new JLabel(about);
        aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Fenster-Icon setzen
        try {
            Image img = ImageIO.read(getClass().getResource("/pics/study32x32.png"));
            this.setIconImage(img);
        } catch (IOException e) {
            System.err.println(e.toString());
        }

    }

    /**
     * Bringt die grafischen Komponenten dieses Dialogs an die richtige Stelle.
     */
    private void layoutAboutDialog() {
        aboutPanel.setLayout(new BorderLayout());
        aboutPanel.add(aboutIcon, BorderLayout.NORTH);
        aboutPanel.add(aboutLabel, BorderLayout.CENTER);
        this.add(aboutPanel);
        this.pack();
    }
}