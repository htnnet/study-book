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
    private int maxLength;
    private String acceptedChars;

    /**
     * 1. Konstruktor der Klasse SBFieldDocument.
     *
     * @param maxLength Die maximale Anzahl an einzugebenden Zeichen
     */
    public SBFieldDocument(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * 2. Konstruktor der Klasse SBFieldDocument.
     *
     * @param maxLength Die maximale Anzahl an einzugebenden Zeichen
     *
     */
    public SBFieldDocument(int maxLength, String acceptedChars) {
        this.maxLength =  maxLength;
        this.acceptedChars = acceptedChars;
    }


    /**
     * Überschriebene Methode von PlainDocument, mit der man das Eingabe-
     * verhalten steuern kann.
     * @param offset Der startende Offset
     * @param str Der vom Benutzer eingegebene Text
     * @param a Attribute des eingegebene Textes
     * @throws BadLocationException
     */
    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        // Falls es "verbotene" Zeichen gibt, werden sie hier herausgefiltert
        if (acceptedChars != null) {
            for (int i = 0; i < str.length(); i++) {
                if (acceptedChars.indexOf(str.valueOf(str.charAt(i))) == -1) {
                    return;
                }
            }
        }

        // Längenüberschreitung prüfen
        if (this.getLength() + str.length() > maxLength) {
        } else {
            super.insertString(offset, str, a);
        }


    }
}

