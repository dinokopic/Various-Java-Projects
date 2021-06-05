package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

/*
    public static void main(String[] args) {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        GeografijaDAO geografijaDAO = GeografijaDAO.getInstance();

        glavniGrad();

        System.out.println(ispisiGradove());
        Grad g = geografijaDAO.glavniGrad("Austrija");
        System.out.println(g.getNaziv());

        //System.out.println("Gradovi su:\n" + ispisiGradove());
        //glavniGrad();
*/

    public static String ispisiGradove() {
        //
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        //
        GeografijaDAO geo = GeografijaDAO.getInstance();
        ArrayList<Grad> gradovi = geo.gradovi();
        String ispis = "";
        for (Grad g : gradovi) {
            ispis += g.getNaziv() + " (" + g.getDrzava().getNaziv() + ") - " + g.getBrojStanovnika() + "\n";
        }
        return ispis;
    }

    public static void glavniGrad() {
        Scanner ulaz = new Scanner(System.in);
        System.out.println("Unesite naziv države: ");
        String naziv = ulaz.next();
        GeografijaDAO geo = GeografijaDAO.getInstance();
        Grad glavni = geo.glavniGrad(naziv);
        if (glavni == null)
            System.out.println("Nepostojeća država");
        else
            System.out.println("Glavni grad države " + naziv + " je " + glavni.getNaziv());
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        GlavnaController glavnaController = new GlavnaController();
        glavnaController.resetujBazu();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource(
                "/fxml/glavna.fxml" ), bundle);
        loader.setController(glavnaController);
        Parent root = loader.load();
        primaryStage.setTitle("Glavna");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
        //primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);

    }
}
