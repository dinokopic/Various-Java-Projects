package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavnaController {

    private GeografijaDAO dao = GeografijaDAO.getInstance();

    public TableView<Grad> tableViewGradovi;
    public ObservableList<Grad> gradovi = FXCollections.observableArrayList();
    public TableColumn<Grad, Integer> colGradId;
    public TableColumn<Grad, String> colGradNaziv;
    public TableColumn<Grad, Integer> colGradStanovnika;
    public TableColumn<Grad, Drzava> colGradDrzava;
    public Button btnJezik;

    public void initialize() {
        gradovi = FXCollections.observableArrayList(GeografijaDAO.getInstance().gradovi());
        tableViewGradovi.setItems(gradovi);
        colGradId.setCellValueFactory(
                new PropertyValueFactory<Grad, Integer>("id")
        );
        colGradNaziv.setCellValueFactory(
                new PropertyValueFactory<Grad, String>("naziv")
        );
        colGradStanovnika.setCellValueFactory(
                new PropertyValueFactory<Grad, Integer>("brojStanovnika")
        );
        colGradDrzava.setCellValueFactory(
                new PropertyValueFactory<Grad, Drzava>("drzava")
        );


    }

    public void addDrzava () throws IOException {
        Stage stage = new Stage();
        DrzavaController drzavaController = new DrzavaController(null, dao.gradovi());

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"), bundle);
        loader.setController(drzavaController);
        Parent root = loader.load();
        stage.setTitle("Država");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.setOnHiding(w -> {
            Drzava d = drzavaController.getDrzava();
            if (d != null && d.getNaziv() != null) {
                dao.dodajDrzavu(d);
            }
        });
    }

    public void addGrad () throws IOException {
        Stage stage = new Stage();
        GradController gradController = new GradController(null, dao.drzave());

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"), bundle);
        loader.setController(gradController);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.setOnHiding(w -> {
            Grad g = gradController.getGrad();
            if (g != null && g.getNaziv() != null) {
                dao.dodajGrad(g);
                gradovi.clear();
                gradovi.addAll(GeografijaDAO.getInstance().gradovi());
            }
        });
    }

    public void editGrad () throws IOException {
        Grad g = tableViewGradovi.getSelectionModel().getSelectedItem();
        if (g == null) return;

        Stage stage = new Stage();
        GradController gradController = new GradController(g, dao.drzave());

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"), bundle);
        loader.setController(gradController);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.setOnHiding(w -> {
            Grad grad = gradController.getGrad();
            if (grad != null) {
                dao.izmijeniGrad(grad);
                gradovi.clear();
                gradovi.addAll(GeografijaDAO.getInstance().gradovi());
            }
        });
    }

    public void onObrisi() {
        Grad g = tableViewGradovi.getSelectionModel().getSelectedItem();
        if (g == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Da li ste sigurni da želite obrisati izabrani grad?");
        alert.showAndWait();

        if(alert.getResult() == ButtonType.OK) {
            dao.obrisiGrad(g.getNaziv());
            gradovi.clear();
            gradovi.addAll(GeografijaDAO.getInstance().gradovi());
        }
    }

    public void resetujBazu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        dao = GeografijaDAO.getInstance();
    }

    public void stampajGradove(ActionEvent actionEvent) {
        try {
            new GradoviReport().showReport(dao.getConn());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void onJezik(ActionEvent actionEvent) {
        ArrayList<Locale> locales = new ArrayList<>();

        locales.add(Locale.US);
        locales.add(new Locale("bs", "BA"));
        locales.add(Locale.GERMAN);
        locales.add(Locale.FRENCH);

        ChoiceDialog<Locale> choiceDialog = new ChoiceDialog<>(Locale.getDefault(), locales);
        choiceDialog.setTitle("Language");
        choiceDialog.setHeaderText("Change language");
        choiceDialog.setContentText("Select a language:");

        Optional<Locale> result = choiceDialog.showAndWait();
        if (result.isPresent()){
            Locale.setDefault(result.get());
            Stage primaryStage = (Stage) tableViewGradovi.getScene().getWindow();

            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource(
                    "/fxml/glavna.fxml" ), bundle);
            loader.setController(this);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ignored) {

            }
            primaryStage.setTitle("Glavna");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

        }

// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(letter -> System.out.println("Your choice: " + letter));
    }

}















