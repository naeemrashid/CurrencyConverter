package application.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private JFXTextField inputField;

    @FXML
    private JFXComboBox<Label> from;

    @FXML
    private JFXComboBox<Label> to;

    @FXML
    private JFXButton convertBtn;

    @FXML
    private Label result;

    private ObservableList<Label> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list=getCountries();
        from.setItems(list);
        to.setItems(list);
//        image.setGraphic(new ImageView(new Image("application/resources/img/currency-unfold.png")));
//        topLabel.setId("label");
//        Image image = new Image("application/resources/img/currency-unfold.png");
//        ImageView imageView = new ImageView(image);
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



    public ObservableList<Label> getCountries(){
        String[] locales = Locale.getISOCountries();
        ObservableList<Label> countries = FXCollections.observableArrayList();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            Label country = new Label(obj.getCountry().toUpperCase());
                if (!obj.getCountry().toLowerCase().equals("an")){
                    Image icon = new Image("application/resources/icons/flags/4x3/"+obj.getCountry().toLowerCase()+".png");
                    country.setGraphic(new ImageView(icon));
                    countries.add(country);
                }


//            System.out.println("Country Code = " + obj.getCountry()
//                    + ", Country Name = " + obj.getDisplayCountry());

        }
        return countries;

    }



}
