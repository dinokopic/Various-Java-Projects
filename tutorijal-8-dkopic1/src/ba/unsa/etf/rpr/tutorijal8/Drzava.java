package ba.unsa.etf.rpr.tutorijal8;

public class Drzava {
    private String naziv, jedinicaZaPovrsinu;
    private int brojStanovnika;
    private double povrsina;
    private Grad glavniGrad;

    public Drzava(String naziv, String jedinicaZaPovrsinu, int brojStanovnika, double povrsina, Grad glavniGrad) {
        this.naziv = naziv;
        this.jedinicaZaPovrsinu = jedinicaZaPovrsinu;
        this.brojStanovnika = brojStanovnika;
        this.povrsina = povrsina;
        this.glavniGrad = glavniGrad;
    }

    public Drzava() {
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getJedinicaZaPovrsinu() {
        return jedinicaZaPovrsinu;
    }

    public void setJedinicaZaPovrsinu(String jedinicaZaPovrsinu) {
        this.jedinicaZaPovrsinu = jedinicaZaPovrsinu;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public double getPovrsina() {
        return povrsina;
    }

    public void setPovrsina(double povrsina) {
        this.povrsina = povrsina;
    }

    public Grad getGlavniGrad() {
        return glavniGrad;
    }

    public void setGlavniGrad(Grad glavniGrad) {
        this.glavniGrad = glavniGrad;
    }
}
