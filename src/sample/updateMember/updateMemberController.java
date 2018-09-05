package sample.updateMember;

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

public class updateMemberController implements Initializable {

    DataBaseHandler handler;
    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField mobile;

    @FXML
    private JFXTextField email;

    @FXML
    private AnchorPane rootpane;

//method to close the update member window
    @FXML
    void cancelbuttonHandler(ActionEvent event) {

        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage.close();
    }

//method to update the member information provided in text fields
    @FXML
    void updatebuttonHandler(ActionEvent event) {
        String memberID = id.getText();
        String memberName = name.getText();
        String Mobile = mobile.getText();
        String Email = email.getText();
        if( memberID.isEmpty()||memberName.isEmpty()||Mobile.isEmpty()||Email.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All the Fields ");
            alert.showAndWait();
        }
        else
        {
            String qu ="SELECT * FROM MEMBER WHERE id ='" + memberID + "'";
            ResultSet rs ;
            rs = handler.execQuery(qu);
            Boolean flag=false;
            try {
                if (rs.next())
                {
                    flag =true;
                    String query = "UPDATE MEMBER SET name = '" + memberName +"',"
                            + "mobile = '" + Mobile + "', email = '"
                            + Email + "' WHERE id ='" + memberID + "'";
                    if(handler.execAction(query))
                    {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Succus");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Member Updated Succussfully ");
                        alert1.show();

                    }
                    else
                    {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Error");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Member Update Failed ");
                        alert1.show();
                    }
                }
                if(!flag)
                {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setHeaderText(null);
                    alert1.setContentText("No Such Member Found");
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
