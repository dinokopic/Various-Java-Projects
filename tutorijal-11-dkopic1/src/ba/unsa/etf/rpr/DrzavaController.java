package ba.unsa.etf.rpr;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;


public class DrzavaController {

    private ObservableList<Grad> gradovi = FXCollections.observableArrayList();
    private Drzava drzava;

    public Button btnCancel;
    public Button btnOk;
    public TextField fieldNaziv;
    public ChoiceBox<Grad> choiceGrad;

    public DrzavaController() {}

    public DrzavaController(Drzava d, ArrayList<Grad> gradovi) {
        drzava = d;
        this.gradovi.addAll(gradovi);
    }

    @FXML
    public void initialize() {
        choiceGrad.setItems(gradovi);
        choiceGrad.getSelectionModel().selectFirst();
        drzava = new Drzava();
    }

    public void onOk() {
        if (fieldNaziv.getText().isEmpty()) {
            fieldNaziv.getStyleClass().remove("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
        }
        else {
            fieldNaziv.getStyleClass().remove("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");

            drzava.setNaziv(fieldNaziv.getText());
            drzava.setGlavniGrad(choiceGrad.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) btnOk.getScene().getWindow();
            stage.close();
        }
    }

    public void onCancel() {
        drzava = null;
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public Drzava getDrzava() {
        return drzava;
    }

}
