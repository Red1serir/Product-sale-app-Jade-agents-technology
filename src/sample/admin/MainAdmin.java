package sample.admin;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;
import sample.outils.object.ObjetCommercial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class MainAdmin extends Application {
    //C:\Users\al bassair\Desktop\SE-AgInt\database\database.db


    public static ObjetCommercial objetCommercial;
    public static HashMap<String,JFXComboBox<String>> hashMap;

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
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("Log_in.fxml"));
        Scene scene=new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        Main.stage = primaryStage;
        new FadeIn(root).play();
    }




    public static void main(String[] args) {
       // Regle regle = new Regle("etiq : IF vehicleType=cycle AND num_wheels=2 AND motor=no THEN vehicle=Bicycle");
        //System.out.println(regle.getEtiq());
        launch(args);
    }
}
