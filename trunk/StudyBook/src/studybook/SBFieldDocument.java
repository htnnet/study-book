package studybook;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
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
        this.maxLength = maxLength;
        this.acceptedChars = acceptedChars;
    }

    public void remove(int offset, int length) throws BadLocationException {
        int currentLength = getLength();
        String currentContent = getText(0, currentLength);
        System.out.println(currentContent);

        super.remove(offset, length);

    }

    /**
     * Überschriebene Methode von PlainDocument, mit der man das Eingabe-
     * verhalten steuern kann.
     *
     * @param offset Der startende Offset
     * @param str Der vom Benutzer eingegebene Text
     * @param a Attribute des eingegebenen Textes
     * @throws BadLocationException
     */
    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {

        //System.out.println(this.getText(0, this.getLength()));
        /*
         * if ((this.getLength() < minLength) && (str.trim().length() == 0 ||
         * str.equals(""))){ System.out.println("verboten");
         *
         * }
         */
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
