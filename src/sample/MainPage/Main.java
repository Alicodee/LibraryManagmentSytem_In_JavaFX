package sample.MainPage;

import com.sun.javafx.css.Style;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DataBaseHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/splash/splash.fxml"));
        Boolean isSupported = Platform.isSupported(ConditionalFeature.SCENE3D);
        System.out.println(isSupported);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root,530, 315);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/sample/icon.png"));
        stage.show();


//its for loading content instantly
        new Thread( () ->
        {
            DataBaseHandler.getInstance();
        }).start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
