package sample.splash;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ali Hamza on 7/15/2017.
 */
public class splashController implements Initializable {
    @FXML
    private AnchorPane splashRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new SplashScreen().start();


    }
    class SplashScreen extends Thread
    {
        @Override
        public void run()
        {
            try {
                Thread.sleep(5000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                             root = FXMLLoader.load(getClass().getResource("/sample/login/login.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Login ");
                        stage.getIcons().add(new Image("/sample/icon.png"));
                        stage.setScene(scene);
                        stage.show();

                        Stage stage1 = (Stage) splashRoot.getScene().getWindow();
                        stage1.close();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
