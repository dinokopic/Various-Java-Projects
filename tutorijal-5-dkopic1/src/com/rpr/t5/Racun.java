package com.rpr.t5;

public class Racun {
    private final Long brojRacuna;
    private final Osoba korisnikRacuna;
    private Double stanjeRacuna;
    private boolean odobrenjePrekoracenja;

    public Racun(Long brojRacuna, Osoba korisnikRacuna) {
        this.brojRacuna = brojRacuna;
        this.korisnikRacuna = korisnikRacuna;
        this.stanjeRacuna = 0d;
        this.odobrenjePrekoracenja = false;
    }

    private boolean provjeriOdobrenjePrekoracenja(Double d) {
        return (d <= stanjeRacuna || odobrenjePrekoracenja);
    }

    public boolean izvrsiUplatu(Double u) {
        if (u < 0) return false;
        stanjeRacuna += u;
        return true;
    }

    public boolean izvrsiIsplatu(Double i) {
        if (stanjeRacuna < i && !odobrenjePrekoracenja) return false;
        stanjeRacuna -= i;
        return true;
    }

    public Number getStanjeRacuna() {
        return stanjeRacuna;
    }

    public String getBrojRacuna() {
        return brojRacuna.toString();
    }

    void odobriPrekoracenje() {
        odobrenjePrekoracenja = true;
    }
}
