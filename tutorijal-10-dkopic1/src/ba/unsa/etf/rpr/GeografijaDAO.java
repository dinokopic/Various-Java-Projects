package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

public class GeografijaDAO {
    private static GeografijaDAO instance;
    private Connection connection;

    private static PreparedStatement sviGradoviUpit, sveDrzaveUpit;
    private static PreparedStatement glavniGradUpit;
    private static PreparedStatement dajDrzavuIzNazivaUpit, dajGradIzNazivaUpit, dajDrzavuGradaUpit;
    private static PreparedStatement obrisiGradoveIzDrzave, obrisiDrzavu, obrisiGradUpit;
    private static PreparedStatement odrediIDGradaUpit, odrediIDDrzaveUpit;
    private static PreparedStatement dodajGradUpit, dodajDrzavuUpit;
    private static PreparedStatement izmijeniGradUpit, izmijeniDrzavuUpit;

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
                dajGradIzNazivaUpit = connection.prepareStatement("SELECT id, naziv, broj_stanovnika, drzava FROM grad WHERE naziv = ?");

                dodajGradUpit = connection.prepareStatement("INSERT INTO grad VALUES(?,?,?,?)");
                dodajDrzavuUpit = connection.prepareStatement("INSERT INTO drzava VALUES(?,?,?)");

                obrisiGradoveIzDrzave = connection.prepareStatement("DELETE FROM grad WHERE drzava = ?");
                obrisiDrzavu = connection.prepareStatement("DELETE FROM drzava WHERE id = ?");
                obrisiGradUpit = connection.prepareStatement("DELETE FROM grad WHERE naziv = ?");

                odrediIDGradaUpit = connection.prepareStatement("SELECT MAX(id)+1 FROM grad");
                odrediIDDrzaveUpit = connection.prepareStatement("SELECT MAX(id)+1 FROM drzava");

                izmijeniGradUpit = connection.prepareStatement("UPDATE grad SET naziv = ?, broj_stanovnika = ?, drzava = ? WHERE id = ?");
                izmijeniDrzavuUpit = connection.prepareStatement("UPDATE drzava SET glavni_grad = ? WHERE id = ?");

                dajDrzavuGradaUpit = connection.prepareStatement("SELECT g.id, d.id, d.glavni_grad FROM grad g, drzava d WHERE g.drzava = d.id AND g.naziv = ?");

                sveDrzaveUpit = connection.prepareStatement("SELECT id, naziv FROM drzava");
                sviGradoviUpit = connection.prepareStatement("SELECT g.id, g.naziv, g.broj_stanovnika, d.id, d.naziv, g2.id, g2.naziv, g2.broj_stanovnika FROM grad g LEFT OUTER JOIN drzava d  on g.drzava = d.id LEFT OUTER JOIN grad g2 on d.glavni_grad = g2.id ORDER BY g.broj_stanovnika DESC");

                //sviGradoviUpit = connection.prepareStatement("SELECT g.id, g.naziv, g.broj_stanovnika, d.id, d.naziv, g2.id, g2.naziv, g2.broj_stanovnika FROM grad g, drzava d, grad g2 WHERE g.drzava = d.id AND g2.id = d.glavni_grad ORDER BY g.broj_stanovnika DESC");
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
                if (res.getString(5) != null) {
                    drzava = new Drzava(res.getInt(4), res.getString(5), null);
                    glavniGrad = new Grad(res.getInt(6), res.getString(7), res.getInt(8), null);
                    glavniGrad.setDrzava(drzava);
                    drzava.setGlavniGrad(glavniGrad);
                    grad.setDrzava(drzava);
                }
                gradovi.add(grad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradovi;
    }

    public ArrayList<Drzava> drzave() {
        ArrayList<Drzava> drzave = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = sveDrzaveUpit.executeQuery();
            while (res.next()) {
                drzave.add(new Drzava(res.getInt(1), res.getString(2), glavniGrad(res.getString(2))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drzave;
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

    public void obrisiGrad(String grad) {
        try {
            obrisiGradUpit.setString(1, grad);
            dajDrzavuGradaUpit.setString(1, grad);
            ResultSet rs1 = dajDrzavuGradaUpit.executeQuery();
            if (rs1.next() && rs1.getInt(1) == rs1.getInt(3)) {
                izmijeniDrzavuUpit.setNull(1, Types.INTEGER);
                izmijeniDrzavuUpit.setInt(2, rs1.getInt(2));
            }
            obrisiGradUpit.execute();
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

    public Grad nadjiGrad(String grad) {
        try {
            dajGradIzNazivaUpit.setString(1,grad);
            ResultSet rs = dajGradIzNazivaUpit.executeQuery();
            if (!rs.next()) return null;
            int idDrzave = rs.getInt(4);
            return new Grad(rs.getInt(1), grad, rs.getInt(3), drzave().stream()
                    .filter(a ->  a.getId() == idDrzave)
                    .findFirst()
                    .orElse(null));
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
            if (grad.getDrzava() != null)
                dodajGradUpit.setInt(4,grad.getDrzava().getId());
            else
                dodajGradUpit.setNull(4, Types.INTEGER);
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
            if (drzava.getGlavniGrad().getId() != 0)
                gradID = drzava.getGlavniGrad().getId();
            else if (rs.next())
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
