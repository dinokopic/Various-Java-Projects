package com.rpr.t5;

import java.util.ArrayList;
import java.util.List;

public class Banka {
    private static Long brojRacuna = 1000000000000000L;
    private List<Uposlenik> uposleni;
    private List<Korisnik> korisnici;

    public Banka() {
        uposleni = new ArrayList<>();
        korisnici = new ArrayList<>();
    }

    public List<Uposlenik> getUposleni() {
        return uposleni;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public Korisnik kreirajNovogKorisnika(String ime, String prezime) {
        return new Korisnik(ime, prezime);
    }

    public void dodajNovogUposlenog(String ime, String prezime) {
        uposleni.add(new Uposlenik(ime, prezime));
    }

    public Racun kreirajRacun(Korisnik k1) {
        Racun racun = new Racun(brojRacuna++, k1);
        k1.dodajRacun(racun);
        return racun;
    }
}
