package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GeografijaDAO {
    private static GeografijaDAO instance;
    private Connection connection;

    private static PreparedStatement sviGradoviUpit;
    private static PreparedStatement glavniGradUpit;
    private static PreparedStatement dajDrzavuIzNazivaUpit;
    private static PreparedStatement obrisiGradoveIzDrzave, obrisiDrzavu;
    private static PreparedStatement odrediIDGradaUpit, odrediIDDrzaveUpit;
    private static PreparedStatement dodajGradUpit, dodajDrzavuUpit;
    private static PreparedStatement izmijeniGradUpit;

    private GeografijaDAO() {
        if (instance == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
                try {
                    glavniGradUpit = connection.prepareStatement("SELECT g.id, g.naziv, g.broj_stanovnika, g.drzava FROM grad g, drzava d WHERE g.id = d.glavni_grad AND d.naziv = ?");
                } catch (Exception e) {
                    regenerisiBazu();
                    try {
                        glavniGradUpit = connection.prepareStatement("SELECT g.id, g.naziv, g.broj_stanovnika, g.drzava FROM grad g, drzava d WHERE g.id = d.glavni_grad AND d.naziv = ?");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                dajDrzavuIzNazivaUpit = connection.prepareStatement("SELECT d.id, d.naziv, d.glavni_grad FROM drzava d WHERE d.naziv = ?");

                dodajGradUpit = connection.prepareStatement("INSERT INTO grad VALUES(?,?,?,?)");
                dodajDrzavuUpit = connection.prepareStatement("INSERT INTO drzava VALUES(?,?,?)");

                obrisiGradoveIzDrzave = connection.prepareStatement("DELETE FROM grad WHERE drzava = ?");
                obrisiDrzavu = connection.prepareStatement("DELETE FROM drzava WHERE id = ?");

                odrediIDGradaUpit = connection.prepareStatement("SELECT MAX(id)+1 FROM grad");
                odrediIDDrzaveUpit = connection.prepareStatement("SELECT MAX(id)+1 FROM drzava");

                izmijeniGradUpit = connection.prepareStatement("UPDATE grad SET naziv = ?, broj_stanovnika = ?, drzava = ? WHERE id = ?");

                sviGradoviUpit = connection.prepareStatement("SELECT g.id, g.naziv, g.broj_stanovnika, d.id, d.naziv, g2.id, g2.naziv, g2.broj_stanovnika FROM grad g, drzava d, grad g2 WHERE g.drzava = d.id AND g2.id = d.glavni_grad ORDER BY g.broj_stanovnika DESC");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka… nastavljam sa praznom bazom");
        }
    }

    public static GeografijaDAO getInstance() {
        if (instance == null)
            instance = new GeografijaDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance != null) {
            try {
                instance.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }

    public ArrayList<Grad> gradovi() {
        ArrayList<Grad> gradovi = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            Grad grad = null, glavniGrad = null;
            Drzava drzava = null;
            ResultSet res = sviGradoviUpit.executeQuery();
            while (res.next()) {
                grad = new Grad(res.getInt(1), res.getString(2),res.getInt(3), null);
                drzava = new Drzava(res.getInt(4), res.getString(5), null);
                glavniGrad = new Grad(res.getInt(6), res.getString(7), res.getInt(8), null);
                glavniGrad.setDrzava(drzava);
                drzava.setGlavniGrad(glavniGrad);
                grad.setDrzava(drzava);
                gradovi.add(grad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradovi;
    }

    public Grad glavniGrad(String drzava) {
        try {
            glavniGradUpit.setString(1, drzava);
            ResultSet res = glavniGradUpit.executeQuery();
            if (!res.next()) return null;
            Grad g = new Grad(res.getInt(1), res.getString(2), res.getInt(3), null);
            Drzava d = new Drzava(res.getInt(4), drzava, g);
            g.setDrzava(d);
            return g;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void obrisiDrzavu(String drzava) {
        try {
            dajDrzavuIzNazivaUpit.setString(1,drzava);
            ResultSet rs = dajDrzavuIzNazivaUpit.executeQuery();
            if (!rs.next()) return;
            obrisiGradoveIzDrzave.setInt(1,rs.getInt(1));
            obrisiDrzavu.setInt(1, rs.getInt(1));
            obrisiGradoveIzDrzave.executeUpdate();
            obrisiDrzavu.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Drzava nadjiDrzavu(String drzava) {
        try {
            dajDrzavuIzNazivaUpit.setString(1, drzava);
            ResultSet rs = dajDrzavuIzNazivaUpit.executeQuery();
            if (!rs.next()) return null;
            return new Drzava(rs.getInt(1), drzava, glavniGrad(drzava));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dodajGrad(Grad grad) {
        try {
            ResultSet rs = odrediIDGradaUpit.executeQuery();
            int id = 1;
            if (rs.next())
                id = rs.getInt(1);
            dodajGradUpit.setInt(1,id);
            dodajGradUpit.setString(2,grad.getNaziv());
            dodajGradUpit.setInt(3,grad.getBrojStanovnika());
            dodajGradUpit.setInt(4,grad.getDrzava().getId());
            dodajGradUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajDrzavu(Drzava drzava) {
        try {
            ResultSet rs = odrediIDDrzaveUpit.executeQuery();
            int id = 1;
            if (rs.next())
                id = rs.getInt(1);
            rs = odrediIDGradaUpit.executeQuery();
            int gradID = 1;
            if (rs.next())
                gradID = rs.getInt(1);
            dodajDrzavuUpit.setInt(1, id);
            dodajDrzavuUpit.setString(2, drzava.getNaziv());
            dodajDrzavuUpit.setInt(3, gradID);
            dodajDrzavuUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniGrad(Grad grad) {
        try {
            izmijeniGradUpit.setString(1,grad.getNaziv());
            izmijeniGradUpit.setInt(2,grad.getBrojStanovnika());
            izmijeniGradUpit.setInt(3,grad.getDrzava().getId());
            izmijeniGradUpit.setInt(4,grad.getId());
            izmijeniGradUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
