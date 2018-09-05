package sample.displayMember;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;
        import sample.database.DataBaseHandler;

        import java.net.URL;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ResourceBundle;

public class displayMembersController implements Initializable {
    ObservableList<Members> list= FXCollections.observableArrayList();

    @FXML
    private TableView<Members> tableview;

    @FXML
    private TableColumn<Members,String> nameCol;

    @FXML
    private TableColumn<Members,String> idCol;

    @FXML
    private TableColumn<Members,String> mobileCol;

    @FXML
    private TableColumn<Members,String> emailCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialCol();
        loadData();
    }

//it is the method to load the data into columns
    private void loadData() {
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
        ResultSet rs ;
        String qu = "SELECT * FROM MEMBER";
        rs = dataBaseHandler.execQuery(qu);
        try {
            while(rs.next())
            {
                String ID = rs.getString("id");
                String Name = rs.getString("name");
                String Mobile = rs.getString("mobile");
                String Email = rs.getString("email");
                list.add(new Members(ID,Name,Mobile,Email));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableview.getItems().setAll(list);
    }

//it is the method to intialize the columns and relate with Member class
    private void initialCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
}

