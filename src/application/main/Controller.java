package application.main;

import com.google.gson.Gson;
import com.jfoenix.controls.*;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private  String s="";
    @FXML
    private Label autoFill;
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
        CurrencyConvert convert = new CurrencyConvert(this);
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }
        autoFill.setVisible(false);
        autoFill.getStyleClass().add("auto-fill");
        list=getCountries();
        from.setItems(list);
        to.setItems(list);
        from.getStyleClass().add("combo-box");
        to.getStyleClass().add("combo-box");
        inputField.getStyleClass().add("input-field");
        sync.setTooltip(new Tooltip("Fetch latest data from fixer.io"));

        RequiredFieldValidator validator = new RequiredFieldValidator();
        DoubleValidator doubleValidator = new DoubleValidator();
        validator.setMessage("Enter numeric value. Field is empty");
        doubleValidator.setMessage("Enter Numeric value");
        inputField.getValidators().addAll(validator,doubleValidator);


//        inputField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                if (!newValue){
//                    inputField.validate();
//                }
//            }
//        });


        from.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            autoFill.setVisible(true);
            if( e.getCode() == KeyCode.BACK_SPACE) {
                if (s.length() > 0) {
                    s = s.substring( 0, s.length() - 1 );
                }
            }
            else s += e.getText();
            autoFill.setText(s);
            System.out.println(s);
            for( Label item: list ) {
                if( item.getText().startsWith(s) )
                    from.getSelectionModel().select( item );
            }

        });
        to.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            autoFill.setVisible(true);
            if( e.getCode() == KeyCode.BACK_SPACE){
                if (s.length()>0)
                s = s.substring( 0, s.length() - 1 );
            }

            else s += e.getText();
            autoFill.setText(s);
            for( Label item: list ) {
                if( item.getText().startsWith(s) )
                    to.getSelectionModel().select( item );
            }

        });
        to.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            s="";

        });
        from.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            s="";
        });

        sync.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e->{
            error.setText("");
            autoFill.setVisible(false);
            System.out.println("Here goes the data fetch code.");
            if (from.getSelectionModel().getSelectedIndex()!=-1){
                String  fromCurrency=Currency.getInstance(new Locale("",countries.get(from.getSelectionModel().getSelectedItem().getText()))).getCurrencyCode();
                convert.refresh(fromCurrency);
            }else {
                error.setText("Please select the from country.");
            }

        });


        convertBtn.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED , e->{
            error.setText("");
            autoFill.setVisible(false);
            if (validateInput()){
                String fromCountry = from.getSelectionModel().getSelectedItem().getText();
                String toCountry = to.getSelectionModel().getSelectedItem().getText();
                String fromCurrency = Currency.getInstance(new Locale("",countries.get(fromCountry))).getCurrencyCode();
                String toCurrency  = Currency.getInstance(new Locale("",countries.get(toCountry))).getCurrencyCode();
                Platform.runLater( new Runnable() {
                    @Override
                    public void run() {
                        double rate = convert.convert(fromCurrency,toCurrency);
                        rate = rate * Double.parseDouble(inputField.getText());
                        showDialog("Currency Conversion Information \n","Converted Amount from "+from.getSelectionModel().getSelectedItem().getText()+
                                "\n to "+to.getSelectionModel().getSelectedItem().getText()+" \n amount "+rate,pane);
                    }
                });
            } else {
                error.setText("Something went wrong fill the input correctly.");

                }


        });


    }



    public ObservableList<Label> getCountries(){
        String[] locales = Locale.getISOCountries();
        ObservableList<Label> countries = FXCollections.observableArrayList();
        for (String countryCode : locales) {
            Locale obj = new Locale("EN", countryCode);
            Label country = new Label(obj.getDisplayCountry());
                if (!obj.getCountry().toLowerCase().equals("an")){
                    Image icon = new Image("application/resources/icons/flags/4x3/"+obj.getCountry().toLowerCase()+".png");
                    country.setGraphic(new ImageView(icon));
                    countries.add(country);
//                    try {
//                        System.out.println("Country Currency "+Currency.getInstance(obj).getCurrencyCode());
//                    }catch (NullPointerException ex){
////                        System.out.println("Country "+obj.getDisplayCountry());
//                        if (obj.getDisplayCountry().equals("Antarctica")){
//                            System.out.println("Country Currency EDD");
//                        }
//
//                    }

                }


//            System.out.println("Country Code = " + obj.getCountry()
//                    + ", Country Name = " + obj.getDisplayCountry());

        }
        return countries;

    }

    public void showDialog(String heading,String body,StackPane stackPane){
        JFXButton cancel = new JFXButton("Cancel");
        cancel.setPrefSize(112,35);
        cancel.getStyleClass().add("cancel-btn");
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


    private boolean validateInput(){
        if (inputField.validate()){
            if (from.getSelectionModel().getSelectedIndex()!=-1 && to.getSelectionModel().getSelectedIndex()!=-1){
                return true;
            }
        }
        return false;
    }
    public Label getError() {
        return error;
    }

    public void setError(Label error) {
        this.error = error;
    }
}
