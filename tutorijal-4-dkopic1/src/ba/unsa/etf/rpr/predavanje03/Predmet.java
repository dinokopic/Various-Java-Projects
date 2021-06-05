package ba.unsa.etf.rpr.predavanje03;

public abstract class Predmet {
    private String naziv;
    private int ects;

    abstract public String getNaziv();
    abstract public int getEcts();
}
