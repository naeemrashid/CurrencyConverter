package application.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private Label image;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        image.setGraphic(new ImageView(new Image("application/resources/img/currency-unfold.png")));
//        topLabel.setId("label");
        Image image = new Image("application/resources/img/currency-unfold.png");
        ImageView imageView = new ImageView(image);
//        header.setGraphic(imageView);
//        ObservableList<String> list =getCountries();
//        fromSelector.setItems(list);
//        fromSelector.getSelectionModel().select(0);
//        Label label = new Label("testing");
//        label.setGraphic(new ImageView(new Image("application/resources/icons/flags/4x3/ae.svg")));
//        borderPane.setBottom(label);
//        refresh.setGraphic(new ImageView(new Image("application/resources/img/refresh36x36.png")));
//



    }



    public ObservableList<String> getCountries(){
        String[] locales = Locale.getISOCountries();
        ObservableList<String> countries = FXCollections.observableArrayList();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countries.add(obj.getDisplayCountry());
//            System.out.println("Country Code = " + obj.getCountry()
//                    + ", Country Name = " + obj.getDisplayCountry());

        }
        return countries;

    }



}
