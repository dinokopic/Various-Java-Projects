package ba.unsa.etf.rpr.predavanje03;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String imePrezime;
    private int indeks;
    private Semestar semestar;
    private List<Predmet> predmeti;

    public Student(String imePrezime, int indeks, Semestar semestar, List<Predmet> predmeti) {
        this.imePrezime = imePrezime;
        this.indeks = indeks;
        this.setSemestar(semestar);
        this.setPredmeti(predmeti);
    }
    public String getImePrezime() { return imePrezime; }
    public int getIndeks() { return indeks; }
    public Semestar getSemestar() { return semestar; }
    public List<Predmet> getPredmeti() { return predmeti; }
    public void ispisi() {}
    public void setSemestar(Semestar s) {
        semestar = s;
    }
    public void setPredmeti(List<Predmet> predmeti) {
        this.predmeti = new ArrayList<>(predmeti);
    }

}
