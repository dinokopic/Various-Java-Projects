package ba.unsa.etf.rpr;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavnaController {

    private GeografijaDAO dao = GeografijaDAO.getInstance();

    public TableView<Grad> tableViewGradovi;
    public ObservableList<Grad> gradovi = FXCollections.observableArrayList();
    public TableColumn<Grad, Integer> colGradId;
    public TableColumn<Grad, String> colGradNaziv;
    public TableColumn<Grad, Integer> colGradStanovnika;
    public TableColumn<Grad, Drzava> colGradDrzava;

    public void initialize() {
        gradovi.addAll(GeografijaDAO.getInstance().gradovi());
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"));
        loader.setController(drzavaController);
        Parent root = loader.load();
        stage.setTitle("Država");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.setOnHiding(w -> {
            Drzava d = drzavaController.getDrzava();
            if (d != null) {
                dao.dodajDrzavu(d);
            }
        });
    }

    public void addGrad () throws IOException {
        Stage stage = new Stage();
        GradController gradController = new GradController(null, dao.drzave());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        loader.setController(gradController);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.setOnHiding(w -> {
            Grad g = gradController.getGrad();
            if (g != null) {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
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

}















