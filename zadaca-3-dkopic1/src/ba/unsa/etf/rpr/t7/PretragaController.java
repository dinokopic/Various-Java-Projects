package ba.unsa.etf.rpr.t7;

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.giphy.GiphyData;
import at.mukprojects.giphy4j.entity.search.SearchFeed;
import at.mukprojects.giphy4j.exception.GiphyException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PretragaController {
    public ScrollPane paneSlike;
    public TextField pretragaTextField;
    public TilePane tilePane;

    private ImageView izabranaSlika;

    @FXML
    public void initialize() {
        tilePane = new TilePane();
        paneSlike.setContent(tilePane);
        paneSlike.setFitToWidth(true);
        izabranaSlika = null;
    }

    public void cancelAction(ActionEvent actionEvent) {
        izabranaSlika = null;
        Stage stage = (Stage) paneSlike.getScene().getWindow();
        stage.close();
    }

    public void searchAction(ActionEvent actionEvent) {
        if (pretragaTextField.getText().equals("")) {
            System.out.println("Nije ti lako");
            //ovdje ide alert
        }
        else {
            tilePane.getChildren().clear();
            new Thread(() -> {
                ImageView slika = new ImageView("/img/loading.gif");
                slika.setFitWidth(128);
                slika.setFitHeight(128);
                Giphy giphy = new Giphy("XeI6xK9gsJiRMFgzSK8DCG3TiotlIoor");
                try {
                    SearchFeed feed = giphy.search(pretragaTextField.getText(), 25, 0);
                    for (GiphyData giphyData : feed.getDataList()) {
                        System.out.println(giphyData.getImages().getOriginalStill().getUrl());
                        //giphyData.setRating("R");
                        Button button = new Button();
                        button.setGraphic(slika);
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent e) {
                                izabranaSlika = (ImageView) button.getGraphic();
                            }
                        });
                        Platform.runLater(() -> {
                            tilePane.getChildren().add(button);
                        });
                        String url = giphyData.getImages().getOriginalStill().getUrl();
                        int i = 13; //na ovoj poziciji se nalazi broj iza media koji treba ukloniti
                        while (Character.isDigit(url.charAt(i))) i++;
                        url = url.substring(0, 8) + "i" + url.substring(i);
                        ImageView img = new ImageView(url);
                        img.setFitHeight(128);
                        img.setFitWidth(128);
                        Platform.runLater(() -> button.setGraphic(img));
                    }
                } catch (GiphyException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void okAction(ActionEvent actionEvent) {
        if (izabranaSlika != null) {
            Stage stage = (Stage) paneSlike.getScene().getWindow();
            stage.close();
        }
    }

    public ImageView getIzabranaSlika() {
        return izabranaSlika;
    }

}
