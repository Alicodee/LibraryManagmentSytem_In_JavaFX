package sample.database;

import javax.swing.*;
import java.sql.*;
import java.util.Calendar;

/**
 * Created by Ali Hamza on 7/21/2017.
 */

//talhajunaidd@gmail.com
public class DataBaseHandler {
    private static DataBaseHandler handler=null;
    protected static  String DB_URL = "jdbc:derby:" +
            "database;create=true";
    private static Connection connection = null;
    private static Statement statement = null;

//constructor that will create connection ,book table, member table, issue book table every time when program runs
    protected DataBaseHandler() {
        createConnection();
       setupBook();
        setupMember();
        setupIssueTable();
    }

//method to create Issue Table inwhich issued books will be stored
    private void setupIssueTable() {
        String Table_name2 = "ISSUE";
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet table = dbm.getTables(null, null, Table_name2.toUpperCase(), null);
            if (table.next()) {
                System.out.println("Table " + Table_name2 + " already exists ");
            } else {
                statement.execute("CREATE TABLE " + Table_name2 + "("
                        + " bookId varchar(200) primary key,\n"
                        + " memberId varchar(100) ,\n"
                        + " issueTime timestamp default CURRENT_TIMESTAMP ,\n"
                        + " renew_count  integer default 0,\n"
                        + " FOREIGN KEY (bookId) REFERENCES BOOK(id), \n"
                       + " FOREIGN KEY (memberId) REFERENCES  MEMBER(id)"
                        + ")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " .....setup database");
        }
    }

//it is method to share single database object among all the packages
    public static DataBaseHandler getInstance()
    {
        if(handler==null)
        {
            handler = new DataBaseHandler();
        }
        return handler;
    }

//method to create connection
    void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            connection = DriverManager.getConnection(DB_URL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

////method to create Book Table inwhich data of  books will be stored
    void setupBook() {
        String Table_name = "BOOK";
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet table = dbm.getTables(null, null, Table_name.toUpperCase(), null);
            if (table.next()) {
                System.out.println("Table " + Table_name + " already exists ");
            } else {
                statement.execute("CREATE TABLE " + Table_name + "("
                        + " id varchar(200) primary key,\n"
                        + " title varchar(100),\n"
                        + " auther varchar(200),\n"
                        + " publisher varchar(200),\n"
                        + " isAvail Boolean default true" +
                        " )" );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//method to create Member Table inwhich members's data will be stored
    void setupMember() {
        String Table_name1 = "MEMBER";
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbm1 = connection.getMetaData();
            ResultSet table1 = dbm1.getTables(null, null, Table_name1.toUpperCase(), null);
            if (table1.next()) {
                System.out.println("Table " + Table_name1 + " already exists ");
            } else {
                statement.execute("CREATE TABLE " + Table_name1 + "("
                        + " id varchar(100) primary key,\n"
                        + " name varchar(100),\n"
                        + " mobile varchar(200),\n"
                        + " email varchar (200)\n"+
                        " )" );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//it is the method to execute the querry
    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception at execute query:Database " + e.getLocalizedMessage());
            return null;
        }finally {

        }
        return result;
    }

//method to excute action querry i.e.. INSERT < DELETE
    public Boolean execAction(String qu)
    {
        try {
            statement =connection.createStatement();
            statement.execute(qu);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error",e.getMessage(),JOptionPane.ERROR_MESSAGE);
            System.out.println("Execption at Excute Action query:Database" + e.getLocalizedMessage());
            return false;
        }finally {

        }
    }
}

