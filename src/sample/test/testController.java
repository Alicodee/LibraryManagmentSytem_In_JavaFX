package sample.test;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ali Hamza on 7/25/2017.
 */
public class testController implements Initializable {
    @FXML
    private AnchorPane rootpane;
    @FXML
    private JFXTextField nDays;

    @FXML
    private JFXTextField finePerDay;

    @FXML
    private JFXTextField admin;

    @FXML
    private JFXPasswordField password;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         initiallizeDefaultValues();

    }

    private void initiallizeDefaultValues() {
        Prefrences preferences = Prefrences.getPreferences();
        nDays.setText(String.valueOf(preferences.getnDAysWithoutFine()));
        finePerDay.setText(String.valueOf(preferences.getFinePerDay()));
        admin.setText(String.valueOf(preferences.getUsername()));
        password.setText(String.valueOf(preferences.getPassword()));

        //Prefrences.writePreferencesToFile(preferences);
    }


    @FXML
    void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage.close();

    }

    @FXML
    void saveButtonAction(ActionEvent event) {
        int no_Days = Integer.parseInt(nDays.getText());
        int fine = Integer.parseInt(finePerDay.getText());
        Prefrences preferencesObj = Prefrences.getPreferences();
        preferencesObj.setnDAysWithoutFine(no_Days);
        preferencesObj.setFinePerDay(fine);
        preferencesObj.setUsername(admin.getText());
        preferencesObj.setPassword(password.getText());


        Prefrences.writePreferencesToFile(preferencesObj);
    }
}
