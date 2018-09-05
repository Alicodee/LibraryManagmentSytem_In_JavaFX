package sample.displayBooks;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.database.DataBaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;

public class displayBooksController implements Initializable {

    @FXML
    private TableView<Book> tableview;

    @FXML
    private TableColumn<Book, String> idCol;

    @FXML
    private TableColumn<Book, String> titleCol;

    @FXML
    private TableColumn<Book, String> autherCol;

    @FXML
    private TableColumn<Book, String> pubCol;

    @FXML
    private TableColumn<Book, Boolean> availabilityCol;

    ObservableList<Book> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
           initialCol();
           loadData();

    }

    //it is the method to load the data into columns
    private void loadData() {
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
        ResultSet rs;
        String cquery = "SELECT * FROM BOOK";
        rs = dataBaseHandler.execQuery(cquery);
        try {
            while (rs.next())
            {
                String id = rs.getString("id");
                String title1= rs.getString("title");
                String auther = rs.getString("auther");
                String publisher = rs.getString("publisher");
                Boolean avail = rs.getBoolean("isAvail");

                list.add(new Book(id,title1,auther,publisher,avail));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            tableview.getItems().setAll(list);

    }

    //it is the method to intialize the columns and relate with Book class
    private void initialCol() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        autherCol.setCellValueFactory(new PropertyValueFactory<>("auther"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        pubCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availabilty"));
        tableview.setEditable(true);
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());


    }
}
