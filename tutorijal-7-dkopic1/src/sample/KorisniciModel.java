package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KorisniciModel {
    private ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
    private SimpleObjectProperty<Korisnik> trenutni = new SimpleObjectProperty<>();

    public ObservableList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ObservableList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik getTrenutni() {
        return trenutni.get();
    }

    public SimpleObjectProperty<Korisnik> trenutniProperty() {
        return trenutni;
    }

    public void setTrenutni(Korisnik trenutni) {
        this.trenutni.set(trenutni);
    }

    public void napuni() {
        korisnici.add(new Korisnik("Dino", "Kopic", "dkopic1@etf.unsa.ba", "dkopic1", "udrimujo"));
        korisnici.add(new Korisnik("Elmir", "Kokic", "ekokic1@etf.unsa.ba", "ekokic1", "mitrovicu"));
        korisnici.add(new Korisnik("Tarik", "Japalak", "tjapalak1@etf.unsa.ba", "tjapalak1", "postosutidrva"));
        trenutni.set(null);
    }
}
