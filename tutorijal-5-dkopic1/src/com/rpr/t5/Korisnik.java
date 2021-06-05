package com.rpr.t5;

public class Korisnik extends Osoba {
    private Racun racun;

    public boolean dodajRacun(Racun racun1) {
        if (racun == null) {
            racun = racun1;
            return true;
        }
        return false;
    }

    public Korisnik(String ime, String prezime) {
        super(ime, prezime);
    }

    public Racun getRacun() {
        return racun;
    }
}
