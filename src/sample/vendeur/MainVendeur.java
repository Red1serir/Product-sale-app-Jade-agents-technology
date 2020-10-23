package sample.vendeur;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.outils.object.ObjetCommercial;
import sample.outils.utilisateur.Vendeur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class MainVendeur extends Application {
    //C:\Users\al bassair\Desktop\SE-AgInt\database\database.db


    public static ObjetCommercial objetCommercial;
    public static HashMap<String,JFXComboBox<String>> hashMap;
    public static Vendeur vendeur;
    public static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connection etablie !!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Vendeur");
        //primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setScene(new Scene(root, 975, 634));
        primaryStage.show();
    }




    public static void main(String[] args) {
       // Regle regle = new Regle("etiq : IF vehicleType=cycle AND num_wheels=2 AND motor=no THEN vehicle=Bicycle");
        //System.out.println(regle.getEtiq());
        launch(args);
    }
}
