/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studybook;

/**
 *
 * @author Admin
 */
public class Dozent extends Person {
    private String raumNr,titel;
    public Dozent(String name, String vorname, String email,int telefon) {
        this.name = name;
        this.vorname = vorname;
        this.email = email;
        this.telefon = telefon;
    }
}
