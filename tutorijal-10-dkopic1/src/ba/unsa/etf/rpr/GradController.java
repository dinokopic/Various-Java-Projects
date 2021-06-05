package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GradController {

    private ObservableList<Drzava> drzave = FXCollections.observableArrayList();
    private Grad grad = null;

    public Button btnCancel;
    public Button btnOk;
    public TextField fieldNaziv;
    public TextField fieldBrojStanovnika;
    public ChoiceBox<Drzava> choiceDrzava = new ChoiceBox<>();

    public GradController() {

    }

    public GradController(Grad g, ArrayList<Drzava> drzave) {
        grad = g;
        this.drzave.addAll(drzave);
    }

    @FXML
    public void initialize() {
        choiceDrzava.setItems(drzave);
        if (grad != null) {
            fieldNaziv.setText(grad.getNaziv());
            fieldBrojStanovnika.setText(Integer.toString(grad.getBrojStanovnika()));
            choiceDrzava.getSelectionModel().select(grad.getDrzava());
        }
        else
            grad = new Grad();
    }

    public void onCancel() {
        grad = null;
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void onOk() {
        if (fieldNaziv.getText().isEmpty()) {
            fieldNaziv.getStyleClass().remove("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
        }
        else {
            fieldNaziv.getStyleClass().remove("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }
        if (ispravanBrojStanovnika(fieldBrojStanovnika.getText())) {
            fieldBrojStanovnika.getStyleClass().remove("poljeNijeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeIspravno");
        }
        else {
            fieldBrojStanovnika.getStyleClass().remove("poljeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
        }

        if (fieldNaziv.getStyleClass().contains("poljeIspravno")
                && fieldBrojStanovnika.getStyleClass().contains("poljeIspravno")) {
            postaviGrad();
            Stage stage = (Stage) btnOk.getScene().getWindow();
            stage.close();
        }
    }


    public boolean ispravanBrojStanovnika(String string) {
        if (string.isEmpty()) return false;
        for (char c : string.toCharArray()) {
            if (c < '0' || c > '9') return false;
        }

        return true;
    }

    public Grad getGrad() {
        return grad;
    }

    void postaviGrad() {
        grad.setNaziv(fieldNaziv.getText());
        grad.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
        grad.setDrzava(choiceDrzava.getSelectionModel().getSelectedItem());
    }

}
