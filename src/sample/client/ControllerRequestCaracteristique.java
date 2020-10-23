package sample.client;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sample.Main;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerRequestCaracteristique implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.hashMap = new HashMap<>();

        String sql = "SELECT caracteristique,valeur FROM objet where nom='"+ Main.request.get("category")+"';";
        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                if (Main.hashMap.containsKey(rs.getString("caracteristique"))){
                    Main.hashMap.get(rs.getString("caracteristique")).getItems().add(rs.getString("valeur"));
                }else {
                    JFXComboBox<String> jfxComboBox = createComboBox(rs.getString("valeur"));
                    Main.hashMap.put(rs.getString("caracteristique"), jfxComboBox);
                    Pane pane = createPane(rs.getString("caracteristique"), jfxComboBox);
                    vboxContainer.getChildren().add(pane);
                }
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private VBox vboxContainer;


    private JFXComboBox<String> createComboBox(String valeur){

        JFXComboBox<String> jfxComboBox = new JFXComboBox<>();
        jfxComboBox.setPrefHeight(25);
        jfxComboBox.setPrefWidth(384);
        jfxComboBox.setLayoutX(257);
        jfxComboBox.setLayoutY(39);
        jfxComboBox.setOpaqueInsets(new Insets(20,0,20,0));
        jfxComboBox.getItems().add(valeur);

        return jfxComboBox;
    }

    private Pane createPane(String caracteristique, JFXComboBox<String> jfxComboBox){
        Pane pane = new Pane();
        pane.setPrefHeight(115);
        pane.setPrefWidth(723);

        Label label = new Label(caracteristique+" :");
        label.setLayoutX(99);
        label.setLayoutY(39);
        label.setFont(Font.font(16));

        pane.getChildren().addAll(label,jfxComboBox);

        return pane;
    }

}
