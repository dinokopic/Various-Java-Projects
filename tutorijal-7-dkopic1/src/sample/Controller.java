package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public PasswordField fldPass;
    public ListView<Korisnik> listKorisnici;

    private KorisniciModel model;

    public Controller(KorisniciModel model) {
        this.model = model;
    }

    public void zatvori(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void dodaj(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik(fldIme.getText(), fldPrezime.getText(), fldEmail.getText(),
                fldUsername.getText(), fldPass.getText()));
    }

    private void bindaj() {
        fldIme.textProperty().bindBidirectional(model.getTrenutni().imeProperty());
        fldPrezime.textProperty().bindBidirectional(model.getTrenutni().prezimeProperty());
        fldEmail.textProperty().bindBidirectional(model.getTrenutni().emailProperty());
        fldUsername.textProperty().bindBidirectional(model.getTrenutni().usernameProperty());
        fldPass.textProperty().bindBidirectional(model.getTrenutni().passwordProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, stari, novi) ->{
                    if (stari == null) {
                        if (model.getTrenutni() == null)
                            model.setTrenutni(novi);
                    }
                    else {
                        fldIme.textProperty().unbindBidirectional(model.getTrenutni().imeProperty());
                        fldPrezime.textProperty().unbindBidirectional(model.getTrenutni().prezimeProperty());
                        fldEmail.textProperty().unbindBidirectional(model.getTrenutni().emailProperty());
                        fldUsername.textProperty().unbindBidirectional(model.getTrenutni().usernameProperty());
                        fldPass.textProperty().unbindBidirectional(model.getTrenutni().passwordProperty());
                        model.setTrenutni(novi);
                    }
                    bindaj();
                }
        );
    }

}
