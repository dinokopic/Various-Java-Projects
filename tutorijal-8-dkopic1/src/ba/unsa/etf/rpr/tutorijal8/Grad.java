package ba.unsa.etf.rpr.tutorijal8;

public class Grad {
    private String naziv;
    private int brojStanovnika;
    private double[] temperature;

    public Grad() {
        temperature = new double[1000];
    }

    public Grad(String naziv, int brojStanovnika, double[] temperature) {
        this.naziv = naziv;
        this.brojStanovnika = brojStanovnika;
        this.temperature = temperature;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public double[] getTemperature() {
        return temperature;
    }

    public void setTemperature(double[] temperature) {
        this.temperature = temperature;
    }
}
