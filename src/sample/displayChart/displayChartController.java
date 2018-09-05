package sample.displayChart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.database.DataBaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class displayChartController implements Initializable {
     DataBaseHandler handler;
    @FXML
    private StackPane stackpane;

    @FXML
    private PieChart pieChart;


    int availableBookCounter = 0;
    int issueCount = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = DataBaseHandler.getInstance();
        loadData();
        loadChart();
    }

    //method to load data from tables
    private void loadData() {

        ResultSet res;

    //counting isssued books

        String qu1 = "SELECT * FROM BOOK WHERE isAvail = true";
        String qu = "SELECT * FROM ISSUE";
        res =handler.execQuery(qu);
        try {

            while(res.next())
            {
                issueCount = issueCount + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

      //counting avaiable books
        res = handler.execQuery(qu1);
        try {

            while(res.next())
            {
                availableBookCounter =availableBookCounter+1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Issued Books: " + issueCount);
        System.out.println("Available Books " + availableBookCounter);
    }

    //lmethod to display loaded data in chart
    private void loadChart() {
        pieChart = new PieChart();
        pieChart.setData(getChartData());
        pieChart.setTitle("Books Information");
        pieChart.setStyle("-fx-pie-color:blue; -fx-padding: 1; -fx-label-line-length:10");
        pieChart.getStylesheets().add("/sample/displayChart/chart.css");


        stackpane.getChildren().add(pieChart);
    }

    private ObservableList<PieChart.Data> getChartData() {

        ObservableList<PieChart.Data> chart = FXCollections.observableArrayList();
        chart.addAll(new PieChart.Data("Issued Books",issueCount),
                new PieChart.Data("Available Books",availableBookCounter));

        return chart;
    }
}
