package studybook;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Enthält eine Abwandlung der Klasse PlainDocument, mit der das Verhalten von
 * JTextFields und JTextAreas festgelegt werden kann.
 *
 * @author StudyBook-Crew
 * @version 1.0
 * @since 2012-12-18
 */
public class SBFieldDocument extends PlainDocument {

    private int maxLength;
    private String acceptedChars;

    /**
     * 1. Konstruktor der Klasse SBFieldDocument.
     *
     * @param maxLength die maximale Anzahl an einzugebenden Zeichen
     */
    public SBFieldDocument(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * 2. Konstruktor der Klasse SBFieldDocument.
     *
     * @param maxLength die maximale Anzahl an einzugebenden Zeichen
     * @param acceptedChars die erlaubten Zeichen für diese Instanz
     */
    public SBFieldDocument(int maxLength, String acceptedChars) {
        this.maxLength = maxLength;
        this.acceptedChars = acceptedChars;
    }

    /**
     * Überschriebene Methode von PlainDocument, mit der man das Eingabe-
     * verhalten steuern kann.
     *
     * @param offset der startende Offset
     * @param str der vom Benutzer eingegebene Text
     * @param a Attribute des eingegebenen Textes
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
        if ((this.getLength() + str.length() > maxLength) || this.getLength() + str.length() < 1) {
        } else {
            super.insertString(offset, str, a);
        }
    }
}
