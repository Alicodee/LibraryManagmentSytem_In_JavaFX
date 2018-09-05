package sample.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;
import sample.test.Prefrences;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML
    private AnchorPane rootpane;

    @FXML
    private Label label;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    Prefrences prefrences;

    @FXML
    void CancelOperation(ActionEvent event) {
        closeStage();

    }

//It is the method for login purpose it checks the username and password then compare it with username and password
//stored in database if matches then it open the main window
    @FXML
    void loginOperation(ActionEvent event) {
        String name = username.getText();
        String pass = DigestUtils.shaHex(password.getText());

        if(name.isEmpty() || pass.isEmpty())
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All the required Fields");
            alert.show();
        }

        else
        {
            if(name.equals(prefrences.getUsername()) && pass.equals(prefrences.getPassword()))
            {
                closeStage();
                loadMain();
            }
            else
            {

                label.setText("Invailed Username or Password");
                label.setStyle("-fx-background-color: #212121; -fx-text-fill: white");
            }
        }
        System.out.println("user: "+prefrences.getUsername() +"  PAs: " + prefrences.getPassword());

    }

//method to close the login window
    private void closeStage() {
        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage.close();
    }

//it is method to load main Window. it is invoked in login operation if username&password  matches
    void loadMain()
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/MainPage/sample.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.getIcons().add(new Image("/sample/icon.png"));
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root,850,700));
            stage.show();
        } catch (IOException e) {
            System.out.println("File not found " + e.getLocalizedMessage() );
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXDepthManager.setDepth(rootpane,5);
         prefrences = Prefrences.getPreferences();
    }
}
