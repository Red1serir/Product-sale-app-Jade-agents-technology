package sample;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.outils.object.ObjetCommercial;
import sample.outils.object.Produit;
import sample.outils.utilisateur.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class Main extends Application {
    public static Stage stage;
    static Connection connection;
    public static Client client;
    public static HashMap<String,String> request;
    public static ObjetCommercial objetCommercial;
    public static HashMap<String,JFXComboBox<String>> hashMap;
    public static Produit produit;
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
    public static String join(byte[] tab){
        String tem = "";
        for (byte b : tab){
            tem += b;
        }
        return tem;
    }

    public static String byteToString(byte[] tab){
        String s = "";
        for (byte b : tab){
            s += ""+"$";
        }
        return s;
    }

    public static byte[] stringToByte(String s){
        int i = 0;
        String[] tab = s.split("[$]");
        byte[] tabByte = new byte[tab.length];
        for (String t : tab){
            tabByte[i] = Byte.parseByte(t);
            i++;
        }
        return tabByte;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("sample1.fxml"));
        Scene scene=new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        Main.stage = primaryStage;
        stage.setTitle("Accueil");
        new FadeIn(root).play();
    }

    public static void main(String[] args){
        launch(args);
    }
}
