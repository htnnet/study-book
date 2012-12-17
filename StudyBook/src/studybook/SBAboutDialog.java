package studybook;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Enthält
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBAboutDialog extends JDialog {

    private JPanel aboutPanel;
    private JLabel aboutIcon;
    private JLabel aboutLabel;
    private ImageIcon icon;
    private Border margin;

    public SBAboutDialog(JFrame parent) {
        super(parent, "Über StudyBook", true);
        this.createAboutDialog();
        this.layoutAboutDialog();
    }

    private void createAboutDialog() {
        aboutPanel = new JPanel();

        margin = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        aboutPanel.setBorder(margin);

        icon = new ImageIcon(getClass().getResource("/pics/about.gif"));
        aboutIcon = new JLabel(icon);
        aboutIcon.setText("<HTML><b>Version 1.0<b></HTML>");
        aboutIcon.setHorizontalTextPosition(JLabel.CENTER);
        aboutIcon.setVerticalTextPosition(JLabel.BOTTOM);

        String about = "<HTML><hr><h3>Autoren:</h3>"
                + "Andre Müller, Alexander Keller,<br>"
                + "Bernd Hitzelberger, Michael Dazjuk,<br>"
                + "Robert Heimsoth, Thomas Matern, Thomas Wolscht</HTML>";
        aboutLabel = new JLabel(about);
        aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Fenster-Icon setzen
        try {
            Image img = ImageIO.read(getClass().getResource("/pics/study16x16.png"));
            this.setIconImage(img);
        } catch (IOException e) {
            System.err.println(e.toString());
        }


    }

    private void layoutAboutDialog() {
        aboutPanel.setLayout(new BorderLayout());
        aboutPanel.add(aboutIcon, BorderLayout.NORTH);
        aboutPanel.add(aboutLabel, BorderLayout.CENTER);
        this.add(aboutPanel);
        this.pack();
    }
}