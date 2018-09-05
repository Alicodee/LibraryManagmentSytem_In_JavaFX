package sample.MainPage;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DataBaseHandler;
import sun.util.calendar.JulianCalendar;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller extends DataBaseHandler implements Initializable {
    DataBaseHandler handler;





    @FXML
    private Text memberNameText;

    @FXML
    private Text mobiletext;


    @FXML
    private JFXTextField bookIDtextfield;

    @FXML
    private StackPane rootpane;

    @FXML
    private Text bookNameText;

    @FXML
    private Text autherText;
    @FXML
    private Text plainText;

    @FXML
    private JFXTextField memberIDTextField;

    @FXML
    private HBox hbox1;

    @FXML
    private HBox hbox2;

    @FXML
    private JFXTextField bookIdTextField2;

    @FXML
    private JFXListView<String > listView;



    //loading diffrent windows on buuton click
    @FXML
    void loadAddBook(ActionEvent event) {
        loadWindow("/sample/addBooks/addBooks.fxml", "Add New Book");

    }

    @FXML
    void loadAddMember(ActionEvent event) {
        loadWindow("/sample/addMembers/addMembers.fxml", "Add New Member");
    }

    @FXML
    void loadDisplayBook(ActionEvent event) {
        loadWindow("/sample/displayBooks/displayBooks.fxml", "View Books");

    }

    @FXML
    void loadDisplayMember(ActionEvent event) {
        loadWindow("/sample/displayMember/displayMembers.fxml", "view Member");

    }

    @FXML
    void loadFullScreen(ActionEvent event) {

        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    void LoadUpdateBook(ActionEvent event) {
            loadWindow("/sample/updateBook/updateBook.fxml","Update Book");
    }

    @FXML
    void loadDeleteBook(ActionEvent event) {
             loadWindow("/sample/deleteBook/deleteBook.fxml","Delete Book");
    }

    @FXML
    void loadDeleteMember(ActionEvent event) {

        loadWindow("/sample/deleteMember/deleteMember.fxml","Delete Member");
    }

    @FXML
    void loadUpdateMember(ActionEvent event) {
        loadWindow("/sample/updateMember/updateMember.fxml","Update Member");
    }

    @FXML
    void loadSettings(ActionEvent event) {
       loadWindow("/sample/test/test.fxml","Settings");
    }

    @FXML
    void loadAbout(ActionEvent event) {

        loadWindow("/sample/about/about.fxml","About");
    }


//method of loading member's iformation using member ID written in text field
    @FXML
    void loadMemberInfo(ActionEvent event) {
        ResultSet res;
        String Mid = memberIDTextField.getText();
        String qury = "SELECT * FROM MEMBER WHERE id=" + "'" + Mid + "'";
        res = handler.execQuery(qury);
        Boolean flag = false;
        try {
            while(res.next())
            {
                String Mname = res.getString("name");
                String Mauther = res.getString("mobile");

                memberNameText.setText(Mname);
                mobiletext.setText(Mauther);
                flag = true;
            }
            if(!flag)
            {
                memberNameText.setText("No Such Person Registered");
                mobiletext.setText("");

            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //method of loading book iformation using book ID written in text field
    @FXML
    void setBookDetails(ActionEvent event) {
        ResultSet res;
        String ID = bookIDtextfield.getText();
        String qu = "SELECT * FROM BOOK WHERE id=" + "'" + ID + "'";
        res = handler.execQuery(qu);
        Boolean flag = false;
        try {
            while(res.next())
            {
                String tname = res.getString("title");
                String tauther = res.getString("auther");
                Boolean bstatus = res.getBoolean("isAvail");
                bookNameText.setText(tname);
                autherText.setText(tauther);
                String status = (bstatus)?"Available":"Not Available";
                plainText.setText(status);
                flag = true;
            }
            if(!flag)
            {
                bookNameText.setText("No Such Book Available");
                autherText.setText("");
                plainText.setText("");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //method to load graph
    @FXML
    void loadGraph(ActionEvent event)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/displayChart/displayChart.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Pie Chart");
            stage.setScene(new Scene(root,420,350));
            stage.getIcons().add(new Image("/sample/icon.png"));
            stage.show();
        } catch (IOException e) {
            System.out.println("File not found " + e.getLocalizedMessage() );
        }

    }

//Method of loading any fxml file passsing location and title as parameters
    void loadWindow(String location, String title)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/sample/icon.png"));
            stage.show();
        } catch (IOException e) {
            System.out.println("File not found " + e.getLocalizedMessage() );
        }
    }

//In Menu section there is a close button inside file menu to close the main window
    @FXML
    void closeMenuItemHandler(ActionEvent event) {

        Stage stage = (Stage) rootpane.getScene().getWindow();
       stage.close();
    }

    //handler to create backup of database
    @FXML
    void backupMenutiemhandler(ActionEvent event) {


        Connection connection;
        String DB_URL = "jdbc:derby:database;create=true";

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            connection = DriverManager.getConnection(DB_URL);
           String backupdirectory ="D:/db_Backups/"+ Calendar.DATE;
            CallableStatement cs = connection.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
            cs.setString(1, backupdirectory);
            cs.execute();
            cs.close();
            System.out.println("backed up database to "+backupdirectory);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //handler to restore created backup
    @FXML
    void restoreDatabaseHandlr(ActionEvent event) {

        try {
            restoreDatabase();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //method to restore created backup
    public static void restoreDatabase()throws SQLException
    {

         String DB_URL1 = "jdbc:derby:new_database;restoreFrom="+"D:/db_Backups/5/database";
        System.out.println("restore method invoked");
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            Connection connection ;
            connection = DriverManager.getConnection(DB_URL1);
            connection.close();
            DB_URL = "jdbc:derby:new_database;create=true";

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


 //Method which is invoked 1st when the class is compiled
    @Override
    public void initialize(URL location, ResourceBundle resources) {
             handler = DataBaseHandler.getInstance();

    }

 //these are some methods to create depth
    @FXML
    void loadDepth1(MouseEvent event) {
        JFXDepthManager.setDepth(hbox1,5);
    }

    @FXML
    void loadDepth2(MouseEvent event) {
        JFXDepthManager.setDepth(hbox2,3);
    }
    @FXML
    void exitDepth1(MouseEvent event) {

        JFXDepthManager.setDepth(hbox1,0);
    }

    @FXML
    void exitDepth2(MouseEvent event) {
        JFXDepthManager.setDepth(hbox2,0);
    }

//this is method to for book issue operation
    @FXML
     void issueOperation(ActionEvent event) {
        String memberId = memberIDTextField.getText();
        String bookId = bookIDtextfield.getText();
        if(memberId.isEmpty()|| bookId.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fill the boxes");
            alert.setHeaderText(null);
            alert.setContentText("Please First fill all the boxes");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Issue Opera tion");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to issue the book " + bookNameText.getText() + "\n to " + memberNameText.getText());

            Optional<ButtonType> response = alert.showAndWait();
            if (response.get() == ButtonType.OK) {
                String str = "INSERT INTO ISSUE(bookId,memberId) VALUES("
                        + "'" + bookId + "',"
                        + "'" + memberId + "')";
                String str2 = "UPDATE BOOK SET isAvail = false WHERE id ='" + bookId + "'";

                if (handler.execAction(str) && handler.execAction(str2)) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Succus");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Issued operation Completed");
                    alert1.showAndWait();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Failure");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Issued operation Failed");
                    alert1.showAndWait();
                }

            } else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Canceled");
                alert1.setHeaderText(null);
                alert1.setContentText("Issued operation Canceled");
                alert1.showAndWait();

            }
        }
    }

 //this is method for viewing details of  issued book
    @FXML
    void loadBookInfo2(ActionEvent event) {
        ObservableList<String> list = FXCollections.observableArrayList();

        String id = bookIdTextField2.getText();
        ResultSet rs;
        String qu = "SELECT * FROM ISSUE WHERE bookId = '" + id + "'";
        rs = handler.execQuery(qu);
        try {
            while(rs.next())
            {
                Timestamp date = rs.getTimestamp("issueTime");
                String  mMemberName = rs.getString("memberId");
               list.add("Time & Date : " + date.toGMTString());
                list.add(" Renew Count: " + rs.getString("renew_count"));
                String  qu1 ="SELECT * FROM BOOK WHERE id = '"+ id +"'";
                ResultSet rs1 =handler.execQuery(qu1);
                list.add("Book Info: ");
                while (rs1.next()){
                    list.add("Book Name: " + rs1.getString("title"));
                    list.add("Book Auther: " + rs1.getString("auther"));
                    list.add("Book Publisher: " + rs1.getString("publisher"));
                }

                String  qu2 ="SELECT * FROM MEMBER WHERE id = '"+ mMemberName +"'";
                ResultSet rs2 =handler.execQuery(qu2);
                list.add("Member Info: ");
                while (rs2.next()){
                    list.add("Member Name: " + rs2.getString("name"));
                    list.add("Mobile Number: " + rs2.getString("mobile"));
                    list.add("Email Address: " + rs2.getString("email"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listView.getItems().setAll(list);
    }

//method to renew the book
    @FXML
    void renewBook(ActionEvent event) {
        String id = bookIdTextField2.getText();
        ResultSet rs ;
        if(id.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Book ID! ");
            alert.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Renew Operation");
            alert.setHeaderText(null);
            alert.setContentText("Are You sure to renew the book ?");

            Optional<ButtonType> response = alert.showAndWait();
            if(response.get() == ButtonType.OK)
            {
                String qu = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP , renew_count = renew_count+1 WHERE bookId ='" + id + "'";
                if(handler.execAction(qu))
                {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Succus");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book Renewed Succussfully ");
                    alert1.show();
                }
                else
                {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book Renew Failed ");
                    alert1.show();
                }

            }
            else
            {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setTitle("Failure");
                alert3.setHeaderText(null);
                alert3.setContentText("Book Renewed Canceled ");
                alert3.show();
            }


        }

    }

//method to return the book
    @FXML
    void submitOperation(ActionEvent event) {
        String id = bookIdTextField2.getText();
        ResultSet rs ;
        if(id.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Please enter Valid Book id");
            alert.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Renew Operation");
            alert.setHeaderText(null);
            alert.setContentText("Are You sure to Submit the book ?");

            Optional<ButtonType> response = alert.showAndWait();
            if(response.get() == ButtonType.OK)
            {
                String ac = "DELETE FROM ISSUE WHERE bookId = '" + id + "'";
                String ac1 ="UPDATE BOOK SET isAvail = true WHERE id = '" + id + "'";
                if(handler.execAction(ac) && handler.execAction(ac1))
                {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Succus");
                    alert1.setHeaderText(null);
                    alert1.setContentText("you have succusfully submitted the book");
                    alert1.show();
                }
                else
                {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Failed");
                    alert1.setHeaderText(null);
                    alert1.setContentText("your book submission has been failed");
                    alert1.show();
                }
            }
            else
            {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Canceled");
                alert1.setHeaderText(null);
                alert1.setContentText("your book submission has been canceled");
                alert1.show();
            }
        }

    }





}
