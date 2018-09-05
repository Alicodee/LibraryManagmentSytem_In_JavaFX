package sample.updateBook;

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

public class updateBookController  implements Initializable{
    DataBaseHandler handler;

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

//method to close the update book window dialog
    @FXML
    void canceButtonHandler(ActionEvent event) {
        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage.close();
    }

//method to update the book information
    @FXML
    void updateButtonHandler(ActionEvent event) {
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
        else
        {
            String qu ="SELECT * FROM BOOK WHERE id ='" + BookID + "'";
            ResultSet rs ;
            rs = handler.execQuery(qu);
            Boolean flag=false;
            try {
                if (rs.next())
                {
                    flag =true;
                    String query = "UPDATE BOOK SET title = '" + BookName +"',"
                            + "auther = '" + BookAuther + "', publisher = '"
                            + BookPublisher + "' WHERE id ='" + BookID + "'";
                    if(handler.execAction(query))
                    {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Succus");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Book Updated Succussfully ");
                        alert1.show();

                    }
                    else
                    {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Error");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Book Update Failed ");
                        alert1.show();
                    }
                }
                if(!flag)
                {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setHeaderText(null);
                    alert1.setContentText("No Such Book Found");
                    alert1.show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = DataBaseHandler.getInstance();
    }
}


