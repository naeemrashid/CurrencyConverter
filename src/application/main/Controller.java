package application.main;

import com.google.gson.Gson;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import jdk.nashorn.internal.parser.JSONParser;

import java.net.URL;
import java.util.*;

/**
 *
 * @author Naeem Rashid
 *
 */
public class Controller implements Initializable{
    @FXML
    private JFXTextField inputField;
    @FXML
    private Label error;
    @FXML
    private JFXComboBox<Label> from;

    @FXML
    private JFXComboBox<Label> to;
    @FXML
    private JFXButton sync;
    @FXML
    private JFXButton convertBtn;

    @FXML
    private StackPane pane;

    private ObservableList<Label> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list=getCountries();
        from.setItems(list);
        to.setItems(list);


        from.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            if (e.getCode().isLetterKey()){
                System.out.println(e.getCode().getName());
                for (Label item : list){
                    if(item.getText().contains(e.getCode().getName())){
                        from.getSelectionModel().select(item);
                        break;
                    }
                }

            }

        });
        sync.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e->{
            System.out.println("Here goes the data fetch code.");
            if(error.getText().isEmpty())
            error.setText("unable to fetch latest data. check your internet connection.");
        });
        convertBtn.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED , e->{
            showDialog("Currency Conversion Information \n","Converted Amount from "+from.getSelectionModel().getSelectedItem().getText()+
                    "\n to "+to.getSelectionModel().getSelectedItem().getText()+" \n amount ",pane);
        });

    }



    public ObservableList<Label> getCountries(){
        String[] locales = Locale.getISOCountries();
        ObservableList<Label> countries = FXCollections.observableArrayList();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            Label country = new Label(obj.getDisplayCountry());
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

    public void showDialog(String heading,String body,StackPane stackPane){
        JFXButton cancel = new JFXButton("Cancel");
        cancel.setPrefSize(112,35);
        cancel.setStyle("-fx-background-color:#9D7052; -fx-text-fill:#ffff");
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(heading));
        content.setBody(new Text(body));
        content.setActions(cancel);
        JFXDialog dialog= new JFXDialog(stackPane,content, JFXDialog.DialogTransition.CENTER);
        dialog.show();
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();

            }
        });
    }

}
