package ba.unsa.etf.rpr.t7;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class KorisniciModel {
    private ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
    private SimpleObjectProperty<Korisnik> trenutniKorisnik = new SimpleObjectProperty<>();
    private Connection conn;
    private PreparedStatement sviKorisniciUpit = null;
    private PreparedStatement updateKorisnikUpit = null;
    private PreparedStatement obrisiKorisnikaUpit = null;
    private PreparedStatement dodajKorisnikaUpit = null;

    public KorisniciModel() {
        if (!Files.exists(Paths.get("korisnici.db"))) {
            regenerisiBazu();
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:korisnici.db");
            sviKorisniciUpit = conn.prepareStatement("SELECT * FROM korisnik");
            sviKorisniciUpit = conn.prepareStatement("SELECT * FROM korisnik");
            updateKorisnikUpit = conn.prepareStatement("UPDATE korisnik SET ime=?, prezime=?, email=?, username=?, password=?, slika =? WHERE id=?");
            obrisiKorisnikaUpit = conn.prepareStatement("DELETE FROM korisnik WHERE id=?");
            dodajKorisnikaUpit = conn.prepareStatement("INSERT INTO korisnik VALUES (?,?,?,?,?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void napuni() {
        korisnici.addAll(dajKorisnike());
        trenutniKorisnik.set(null);
    }

    public ObservableList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ObservableList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public SimpleObjectProperty<Korisnik> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(Korisnik trenutniKorisnik) {
        if (this.trenutniKorisnik != null && this.trenutniKorisnik.getValue() != null) {
            updateKorisnik(this.trenutniKorisnik.getValue());
        }
        if (this.trenutniKorisnik == null)
            this.trenutniKorisnik = new SimpleObjectProperty<>();
        this.trenutniKorisnik.set(trenutniKorisnik);
    }

    public void setTrenutniKorisnik(int i) {
        this.trenutniKorisnik.set(korisnici.get(i));
    }

    public void diskonektuj() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiKorisnika (Korisnik k) {
        try {
            obrisiKorisnikaUpit.setInt(1,k.getId());
            obrisiKorisnikaUpit.execute();
            korisnici.removeIf(korisnik -> korisnik.getId() == k.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        korisnici.removeIf(korisnik -> korisnik.getId() == k.getId());
        if (korisnici.size() != 0)
            trenutniKorisnik.set(korisnici.get(0));
        else
            trenutniKorisnik = null;
    }

    public void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:korisnici.db");
            ulaz = new Scanner(new FileInputStream("korisnici.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException | SQLException e) {
            System.out.println("Ne postoji SQL datoteka… nastavljam sa praznom bazom");
        }
    }

    public void updateKorisnik(Korisnik k) {
        try {
            updateKorisnikUpit.setString(1, k.getIme());
            updateKorisnikUpit.setString(2, k.getPrezime());
            updateKorisnikUpit.setString(3, k.getEmail());
            updateKorisnikUpit.setString(4, k.getUsername());
            updateKorisnikUpit.setString(5, k.getPassword());
            updateKorisnikUpit.setString(6, k.getSlika());
            updateKorisnikUpit.setInt(7, k.getId());
            updateKorisnikUpit.execute();
            korisnici.stream().forEach((Korisnik korisnik) -> {if (korisnik.getId() == k.getId()) korisnik = k;});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addKorisnika(Korisnik k) {
        dodajKorisnika(k);
        korisnici.add(k);
    }

    public void dodajKorisnika(Korisnik k) {
        try {
            dodajKorisnikaUpit.setNull(1, k.getId());
            dodajKorisnikaUpit.setString(2, k.getIme());
            dodajKorisnikaUpit.setString(3, k.getPrezime());
            dodajKorisnikaUpit.setString(4, k.getEmail());
            dodajKorisnikaUpit.setString(5, k.getUsername());
            dodajKorisnikaUpit.setString(6, k.getPassword());
            dodajKorisnikaUpit.setString(7, k.getPassword());
            dodajKorisnikaUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Korisnik> dajKorisnike() {
        ArrayList<Korisnik> korisnici = new ArrayList<>();
        Korisnik k = null;
        ResultSet rs = null;
        try {
            rs = sviKorisniciUpit.executeQuery();
            while (rs.next()) {
                k = new Korisnik(rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                k.setSlika(rs.getString(7));
                k.setId(rs.getInt(1));
                korisnici.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return korisnici;
    }

    public void zapisiDatoteku(File datoteka) {
        if (datoteka != null) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(datoteka, "UTF-8");
                for (Korisnik k : korisnici)
                    writer.println(k.getUsername() + ":" + k.getPassword() + ":"
                            + k.getId() + ":" + k.getId() + ":" + k.getIme()
                            + " " + k.getPrezime() + "::");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection dajKonekciju() {
        return conn;
    }

}