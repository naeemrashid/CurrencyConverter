package application.main;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private Label header;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label topLabel;
    @FXML
    private JFXComboBox<?> fromSelector;

    @FXML
    private JFXComboBox<?> toSelector;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("application/resources/img/currency-unfold.png");
        ImageView imageView = new ImageView(image);
        header.setGraphic(imageView);
//        header.setStyle("currency.jpg'");


    }


}
