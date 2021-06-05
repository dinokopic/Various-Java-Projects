package ba.unsa.etf.rpr.zadaca2;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public PasswordField fldPasswordRepeat;
    public Slider sliderGodinaRodjenja;
    public CheckBox cbAdmin;
    public RadioButton button;

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
            fldPasswordRepeat.textProperty().setValue(fldPassword.textProperty().get());
            sliderGodinaRodjenja.valueProperty().setValue(model.getTrenutniKorisnik().getGodinaRodjenja());
         });

        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty() );
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty() );
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty() );
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty() );
                sliderGodinaRodjenja.valueProperty().unbindBidirectional(oldKorisnik.godinaRodjenjaProperty());
                cbAdmin.setSelected(false);
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
                sliderGodinaRodjenja.setValue(2000);
                cbAdmin.setSelected(false);
            }
            else {
                fldIme.textProperty().bindBidirectional( newKorisnik.imeProperty() );
                fldPrezime.textProperty().bindBidirectional( newKorisnik.prezimeProperty() );
                fldEmail.textProperty().bindBidirectional( newKorisnik.emailProperty() );
                fldUsername.textProperty().bindBidirectional( newKorisnik.usernameProperty() );
                fldPassword.textProperty().bindBidirectional( newKorisnik.passwordProperty() );
                sliderGodinaRodjenja.valueProperty().bindBidirectional(newKorisnik.godinaRodjenjaProperty());
                cbAdmin.setSelected(newKorisnik instanceof Administrator);
            }
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && nameFormat(newIme)) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldPrezime, newPrezime) -> {
            if (!newPrezime.isEmpty() && nameFormat(newPrezime)) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldMail, newMail) -> {
            if (!newMail.isEmpty() && mailFormat(newMail)) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldUser, newUser) -> {
            if (!newUser.isEmpty() && usernameFormat(newUser)) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldPass, newPass) -> {
            Korisnik k = null;
            Administrator a = null;
            if (model.getTrenutniKorisnik() instanceof Administrator)
                a = new Administrator(null, null, null, null, newPass);
            else
                k = new Korisnik(null, null, null, null, newPass);
            if (!newPass.isEmpty() && model.getTrenutniKorisnik() != null
                    && (k != null && k.checkPassword() || a != null && a.checkPassword())
                    && newPass.equals(fldPasswordRepeat.getText())) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPasswordRepeat.textProperty().addListener((obs, oldPass, newPass) -> {
            Korisnik k = null;
            Administrator a = null;
            if (model.getTrenutniKorisnik() instanceof Administrator)
                a = new Administrator(null, null, null, null, newPass);
            else
                k = new Korisnik(null, null, null, null, newPass);
            if (!newPass.isEmpty() && model.getTrenutniKorisnik() != null
                    && (k != null && k.checkPassword() || a != null && a.checkPassword())
                    && newPass.equals(fldPassword.getText())) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public void dodajAction(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik("", "", "", "", ""));
        listKorisnici.getSelectionModel().selectLast();
    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void obrisiAction(ActionEvent actionEvent) {
        if (model.getTrenutniKorisnik() == null) return;
        listKorisnici.getItems().remove(listKorisnici.getSelectionModel().getSelectedIndex());
        if (listKorisnici.getItems().size() > 0)
            model.setTrenutniKorisnik(0);
        else
            model.setTrenutniKorisnik(null);
    }

    public void generisiAction(ActionEvent actionEvent) {
        Korisnik trenutni = model.getTrenutniKorisnik();
        String username = "";
        if (!trenutni.getIme().equals("") && !trenutni.getPrezime().equals("")) {
            username = (trenutni.getIme().charAt(0) + trenutni.getPrezime()).toLowerCase();
            username = username.replace('č', 'c');
            username = username.replace('ć', 'c');
            username = username.replace('đ', 'd');
            username = username.replace('š', 's');
            username = username.replace('ž', 'z');
        }
        model.getTrenutniKorisnik().setUsername(username);
        boolean isAdmin = model.getTrenutniKorisnik() instanceof Administrator;
        passwordGenerator(isAdmin);

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Password information");
        a.setHeaderText(null);
        a.setContentText("Vaša lozinka glasi " + fldPassword.getText());
        a.showAndWait();
    }

    public void cbStateChange(ActionEvent actionEvent) {
        if (model.getTrenutniKorisnik() == null) return;
        if (cbAdmin.isSelected()) {
            Korisnik trenuti = model.getTrenutniKorisnik();
            Administrator admin = new Administrator(trenuti.getIme(), trenuti.getPrezime(), trenuti.getEmail(),
                    trenuti.getUsername(), trenuti.getPassword());
            ObservableList<Korisnik> korisnici = model.getKorisnici();
            int index = korisnici.indexOf(model.getTrenutniKorisnik());
            korisnici.set(index, admin);
            model.setKorisnici(korisnici);
            model.setTrenutniKorisnik(admin);
        }
        else {
            Administrator admin = (Administrator)model.getTrenutniKorisnik();
            Korisnik trenutni = new Korisnik(admin.getIme(), admin.getPrezime(), admin.getEmail(),
                    admin.getUsername(), admin.getPassword());
            ObservableList<Korisnik> korisnici = model.getKorisnici();
            int index = korisnici.indexOf(model.getTrenutniKorisnik());
            korisnici.set(index, trenutni);
            model.setKorisnici(korisnici);
            model.setTrenutniKorisnik(trenutni);
        }
    }

    private boolean mailFormat(String mail) {
        if (mail.contains("@") && hasLetter(mail.substring(0, mail.indexOf('@')))
                && hasLetter(mail.substring(mail.indexOf('@'))))
            return true;
        return false;
    }

    private boolean hasLetter(String s) {
        if (s.length() < 1) return false;
        char[] cs = s.toCharArray();
        for (char c : cs) {
            if (Character.isLetter(c)) return true;
        }
        return false;
    }

    private boolean nameFormat(String name) {
        if (name.length() < 3) return false;
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ' && c != '-')
                return false;
        }
        return true;
    }

    private boolean usernameFormat(String uname) {
        if (uname.length() > 16 || !Character.isLetter(uname.charAt(0))) return false;
        for (char c : uname.toCharArray())
            if (!Character.isLetter(c) && !Character.isDigit(c) && c != '_')
                return false;
        return true;
    }

    private void passwordGenerator(boolean isAdmin) {
        char[] pass = new char[8];
        boolean hasUpperCase = false, hasLowerCase = false, hasDigit = false, hasSpecial = false;
        List<Integer> indexes = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7));
        String lowerCase = "abcdefghijklmonpqrstuvwxyz";
        String upperCase = lowerCase.toUpperCase();
        String digit = "0123456789";
        String special = "!@#$%^&*_=+-/";
        String values = upperCase + lowerCase + digit + special;
        Random rand = new Random();
        while (indexes.size() > 0) {
            int index = rand.nextInt(indexes.size());
            if (!hasUpperCase) {
                pass[indexes.get(index)] = upperCase.charAt(rand.nextInt(26));
                hasUpperCase = true;
            }
            else if (!hasLowerCase) {
                pass[indexes.get(index)] = lowerCase.charAt(rand.nextInt(26));
                hasLowerCase = true;
            }
            else if (!hasDigit) {
                pass[indexes.get(index)] = digit.charAt(rand.nextInt(10));
                hasDigit = true;
            }
            else if (isAdmin && !hasSpecial) {
                pass[indexes.get(index)] = special.charAt(rand.nextInt(13));
                hasSpecial = true;
            }
            else
                pass[indexes.get(index)] = values.charAt(rand.nextInt(75));
            indexes.remove(index);
        }
        fldPassword.setText(String.valueOf(pass));
        fldPasswordRepeat.setText(String.valueOf(pass));
    }
}


