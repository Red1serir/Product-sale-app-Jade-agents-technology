package sample.admin;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.vendeur.MainVendeur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerObjet implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hashMap = new HashMap<>();
        //initialisation de produits
        String sql = "SELECT distinct nom FROM objet";
        try (Connection conn = MainAdmin.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                if (!cbProduit.getItems().contains(rs.getString("nom"))) {
                    cbProduit.getItems().add(rs.getString("nom"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private JFXComboBox<String> cbProduit;

    @FXML
    private VBox vbCaracteristique;

    private HashMap<String, HBox> hashMap;

    @FXML
    void changeProduit(ActionEvent event) {
        if (!cbProduit.getSelectionModel().isEmpty()) {
            vbCaracteristique.getChildren().clear();
            String sql = "SELECT * FROM objet where nom = '" + cbProduit.getSelectionModel().getSelectedItem() + "';";
            try (Connection conn = MainVendeur.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                while (rs.next()) {
                    if (hashMap.containsKey(rs.getString("caracteristique"))){
                        hashMap.get(rs.getString("caracteristique")).getChildren().add(createPane(rs.getString("valeur"),1));
                    }else {
                        HBox hBox = createHBox(createPane(rs.getString("caracteristique"),0));
                        hBox.getChildren().add(createPane(rs.getString("valeur"),1));
                        hashMap.put(rs.getString("caracteristique"), hBox);
                        vbCaracteristique.getChildren().add(hBox);
                    }

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void accueil(ActionEvent event) throws IOException {
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("accueil.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    private HBox createHBox(Pane pane){
        HBox hBox =new HBox();
        hBox.setPrefHeight(100.0);
        hBox.getChildren().add(pane);
        hBox.setStyle("-fx-border-color: #000000; -fx-border-width: 1;");
        return hBox;
    }

    private Pane createPane(String charString,int type){
        Pane pane = new Pane();
        pane.setPrefWidth(196);
        pane.setPrefHeight(100);

        Label label = new Label();
        label.setFont(new Font(20));
        label.setAlignment(Pos.CENTER);
        label.setContentDisplay(ContentDisplay.TOP);
        label.setLayoutX(10.0);
        label.setLayoutY(28.0);
        label.setPrefHeight(45.0);
        label.setPrefWidth(176.0);
        if (type == 0) {
            label.setStyle("-fx-background-color: #8502f0;");
        }else {
            label.setStyle("-fx-background-color: #30eeff;");
        }
        label.setText(charString);
        label.setTextFill(Paint.valueOf("WHITE"));
        pane.getChildren().add(label);
        return pane;
    }

}

