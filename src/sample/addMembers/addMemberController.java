package sample.addMembers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.JFXFillTransition;
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

public class addMemberController implements Initializable{
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
    private JFXButton savebutton;

    @FXML
    private JFXButton cancelbutton;
    @FXML
     private AnchorPane rootpane;

    @FXML
    void cancelbuttonHandler(ActionEvent event) {
        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage.close();

    }

//method to add members data into MEMBER TABLE
    @FXML
    void savebuttonHandler(ActionEvent event) {
        String MemberID = id.getText();
        String MemberNAme = name.getText();
        String Mobile = mobile.getText();
        String Email = email.getText();
        if(MemberID.isEmpty()|| MemberNAme.isEmpty()||Mobile.isEmpty()|| Email.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All the Fields ");
            alert.showAndWait();
        }
        String str = "INSERT INTO MEMBER VALUES("
                + "'" + MemberID +"',"
                + "'" + MemberNAme + "',"
                + "'" + Mobile + "',"
                + "'" + Email + "'"
                + ")";

        if(handler.execAction(str))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Succuss ");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("ERROR! Query Failed ");
            alert.showAndWait();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = DataBaseHandler.getInstance();

    }

//method to print names of all the members
    private void checkMembers() {
        ResultSet title2;
        String cquery = "SELECT name FROM MEMBER";
        title2 = handler.execQuery(cquery);
        try {
            while(title2.next())
            {
                String titlex = title2.getString("name");
                System.out.println(titlex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
