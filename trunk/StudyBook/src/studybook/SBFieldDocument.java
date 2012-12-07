package studybook;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * Enthält eine Abwandlung der Klasse PlainDocument, mit der das Verhalten von
 * JTextFields und JTextAreas festgelegt werden kann.
 *
 * @author StudyBook-Crew
 * @version 0.1
 * @since 2012-10-14
 */
public class SBFieldDocument extends PlainDocument {
    private int max;

    /**
     * Konstruktor der Klasse SBFieldDocument.
     *
     * @param maxLength Die maximale Anzahl an einzugebenden Zeichen
     */
    public SBFieldDocument(int maxLength) {
        max = maxLength;
    }


    /**
     * Überschriebene Methode von PlainDocument, mit der man das Eingabe-
     * verhalten steuern kann.
     * @param offset
     * @param str
     * @param a
     * @throws BadLocationException
     */
    @Override
    public void insertString(int offset, String str, AttributeSet a)
            throws BadLocationException {
        if (getLength() + str.length() > max) {
        } else {
            super.insertString(offset, str, a);
        }
    }
}

