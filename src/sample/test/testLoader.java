package sample.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.database.DataBaseHandler;

public class testLoader extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(new Scene(root, 680, 470));
        primaryStage.show();

        new Thread( () ->
        {
            DataBaseHandler.getInstance();
        }).start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
