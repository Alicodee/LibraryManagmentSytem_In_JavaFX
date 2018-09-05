package sample.test;


import com.google.gson.Gson;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ali Hamza on 7/24/2017.
 */
public class Prefrences implements Initializable {
    public  static final String Config_File = "config.txt";
    int nDAysWithoutFine;
    int finePerDay;
    String username;
    String password;

    Prefrences()
    {
        nDAysWithoutFine=30;
        finePerDay =1;
        username = "admin";
        setPassword("admin");
    }

    public int getnDAysWithoutFine() {
        return nDAysWithoutFine;
    }

    public void setnDAysWithoutFine(int nDAysWithoutFine) {
        this.nDAysWithoutFine = nDAysWithoutFine;
    }

    public int getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(int finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.length()<16)
        {
            this.password = DigestUtils.shaHex(password);
        }
           else
               this.password=password;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

      //  intializeConfigFile();
    }

    public static void intializeConfigFile() {
        Writer writer=null;
        Prefrences preferences = new Prefrences();
        Gson gson = new Gson();
        try {
            writer = new FileWriter(Config_File);
            gson.toJson(preferences,writer);
        } catch (IOException e) {

            System.out.println(e.getLocalizedMessage());;
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static Prefrences getPreferences()
    {
        Gson gson = new Gson();
        Prefrences prefrences = new Prefrences();
        try {
            prefrences = gson.fromJson(new FileReader(Config_File), Prefrences.class);
        } catch (FileNotFoundException e) {
            intializeConfigFile();
            e.printStackTrace();
        }
        return prefrences;

    }

   public static void writePreferencesToFile(Prefrences preferencesObj)
    {
        Writer writer=null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(Config_File);
            gson.toJson(preferencesObj,writer);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucuss");
            alert.setHeaderText(null);
            alert.setContentText("Saved ");
            alert.show();


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

