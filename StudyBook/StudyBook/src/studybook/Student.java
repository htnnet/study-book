/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studybook;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class Student extends Person {
    private int aktSem,matNr;
    private Date studBeginn,studEnde;
    public Student(String name, String vorname, String email,int telefon) {
        this.name = name;
        this.vorname = vorname;
        this.email = email;
        this.telefon = telefon;
    }
}
