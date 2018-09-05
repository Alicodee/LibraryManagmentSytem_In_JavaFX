package sample.addBooks;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DataBaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addBooksController implements Initializable {

    @FXML
    private AnchorPane rootpane;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField auther;

    @FXML
    private JFXTextField publisher;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    DataBaseHandler handler;

    @FXML
    void canceButtonHandler(ActionEvent event) {
        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage.close();
    }

//method to add books into BOOK TABLE
    @FXML
    void saveButtonHandler(ActionEvent event) {
        String BookID = id.getText();
        String BookName = name.getText();
        String BookAuther = auther.getText();
        String BookPublisher = publisher.getText();
        if(BookID.isEmpty()||BookAuther.isEmpty()||BookName.isEmpty()||BookPublisher.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All the Fields ");
            alert.showAndWait();
        }
        else {
            String qu = "INSERT INTO BOOK VALUES(" +
                    "'" + BookID + "'," +
                    "'" + BookName + "'," +
                    "'" + BookAuther + "'," +
                    "'" + BookPublisher + "'," +
                    "'" + true + "'" +
                    ")";
            if (handler.execAction(qu)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Succuss ");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Query Failed");
                alert.showAndWait();

            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = DataBaseHandler.getInstance();
    }

//method to print names of all the book  stored in  BOOK TABLE
    private void checkBooks() {
        ResultSet title;
        String cquery = "SELECT title FROM BOOK";
       title = handler.execQuery(cquery);
        try {
            while(title.next())
            {
                String title1 = title.getString("title");
                System.out.println(title1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
