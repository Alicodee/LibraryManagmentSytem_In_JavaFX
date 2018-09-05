package sample.deleteBook;

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


public class deleteBookController implements Initializable {
    DataBaseHandler handler;

    @FXML
    private AnchorPane rootpane;

    @FXML
    private JFXTextField id;

    @FXML
    void cancelbuttonHandler(ActionEvent event) {

        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage.close();
    }

//method to delete the book of given ID
    @FXML
    void deletebuttonHandler(ActionEvent event) {
        String deleteID = id.getText();
        if(deleteID.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All the Fields ");
            alert.showAndWait();
        }
        else
        {
            String qu = "SELECT * FROM BOOK WHERE id='" + deleteID + "'";
            ResultSet rs;
            Boolean flag =false;
            rs = handler.execQuery(qu);
            try {
                if(rs.next())
                {
                    flag =true;
                   String query = "DELETE FROM BOOK WHERE id='" + deleteID + "'";
                   if(handler.execAction(query))
                   {
                       Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                       alert1.setTitle("Succus");
                       alert1.setHeaderText(null);
                       alert1.setContentText("Book Deleted Succussfully ");
                       alert1.show();

                   }
                   else
                   {
                       Alert alert1 = new Alert(Alert.AlertType.ERROR);
                       alert1.setTitle("Error");
                       alert1.setHeaderText(null);
                       alert1.setContentText("Book has not deleted");
                       alert1.show();
                   }
                }
                if(!flag)
                {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setHeaderText(null);
                    alert1.setContentText("No Such Book Available");
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
