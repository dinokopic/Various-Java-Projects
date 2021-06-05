package ba.unsa.etf.rpr.t7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public Button imgKorisnik;


    private KorisniciModel model;

    public KorisnikController(KorisniciModel model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            model.setTrenutniKorisnik(newKorisnik);
            listKorisnici.refresh();
        });
        ImageView slika = new ImageView("/img/face-smile.png");
        slika.setFitWidth(128);
        slika.setFitHeight(128);
        imgKorisnik.setGraphic(slika);

        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty());
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty());
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty());
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty());
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty());
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
            } else {
                fldIme.textProperty().bindBidirectional(newKorisnik.imeProperty());
                fldPrezime.textProperty().bindBidirectional(newKorisnik.prezimeProperty());
                fldEmail.textProperty().bindBidirectional(newKorisnik.emailProperty());
                fldUsername.textProperty().bindBidirectional(newKorisnik.usernameProperty());
                fldPassword.textProperty().bindBidirectional(newKorisnik.passwordProperty());
                if (newKorisnik.getSlika().equals("")) {
                    ImageView image = new ImageView("/img/face-smile.png");
                    image.setFitWidth(128);
                    image.setFitHeight(128);
                    imgKorisnik.setGraphic(image);
                }
                else {
                    ImageView image = new ImageView(newKorisnik.getSlika());
                    image.setFitWidth(128);
                    image.setFitHeight(128);
                    imgKorisnik.setGraphic(image);
                }
            }
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });

    }

    public void obrisiAction(ActionEvent actionEvent) {
        if (model.getKorisnici().size() != 0)
            model.obrisiKorisnika(model.getTrenutniKorisnik());
    }

    public void dodajAction(ActionEvent actionEvent) {
        Korisnik k = new Korisnik("", "", "", "", "");
        if (model.getKorisnici().size() != 0)
            k.setId(model.getKorisnici().stream().map(Korisnik::getId).max(Integer::compare).get()+1);
        else
            k.setId(1);
        model.addKorisnika(k);
        listKorisnici.getSelectionModel().selectLast();
    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void aboutAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Information:");
        Hyperlink hl = new Hyperlink("https://github.com/dkopic1");
        hl.setOnAction(e -> {
            try {
                new ProcessBuilder("x-www-browser", "https://github.com/dkopic1").start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        ImageView img = new ImageView("/img/japaimg.jpg");
        img.setFitWidth(200);
        img.setFitHeight(200);
        TextFlow tf = new TextFlow(new Text(" Version: 1.0 \n Github profile: "),
                hl,
                new Text("\n Author: Dino Kopić\n"),
                img
        );
        alert.getDialogPane().setContent(tf);
        alert.showAndWait();
    }

    public void saveAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = new Stage();

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);
        if (file != null)
            model.zapisiDatoteku(file);
    }

    public void printAction(ActionEvent actionEvent) {
        try {
            new PrintReport().showReport(model.dajKonekciju());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void jezikBosanski(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        Stage primaryStage = (Stage) listKorisnici.getScene().getWindow();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/korisnici.fxml" ), bundle);
        loader.setController(this);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ignored) {}
        primaryStage.setTitle("Korisnici");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
    }

    public void jezikEngleski(ActionEvent actionEvent) {
        Locale.setDefault(Locale.US);
        Stage primaryStage = (Stage) listKorisnici.getScene().getWindow();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/korisnici.fxml" ), bundle);
        loader.setController(this);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ignored) {}
        primaryStage.setTitle("Users");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
    }

    public void promjenaSlike(ActionEvent actionEvent) throws IOException {
        if (model.getTrenutniKorisnik() == null) return;
        PretragaController pretragaController = new PretragaController();
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pretraga.fxml"), bundle);
        loader.setController(pretragaController);
        Parent root = loader.load();
        stage.setTitle("Pretraga slike");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setWidth(650);
        stage.show();
        stage.setOnHiding(w -> {
            if (pretragaController.getIzabranaSlika() != null) {
                ImageView slika = pretragaController.getIzabranaSlika();
                model.getTrenutniKorisnik().setSlika(slika.getImage().getUrl());
                imgKorisnik.setGraphic(slika);
            }
        });
    }

}
