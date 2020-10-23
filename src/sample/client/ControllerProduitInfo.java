package sample.client;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Main;
import sample.vendeur.MainVendeur;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerProduitInfo implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println(Main.produit);
        nom.setText(Main.produit.getNom());
        date.setText(Main.produit.getDate());
        typeObjet.setText(Main.produit.getTypeObjet());
        prix.setText(Main.produit.getPrix());

        String sql = "SELECT * FROM caracteristique_produit where produit = '"+ Main.produit.getNom()+"';";
        try (Connection conn = MainVendeur.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                    vboxContainer.getChildren().add(createPaneCarac(rs.getString(1),rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private Pane createPaneCarac(String caracteristique, String valeur) {
        Pane pane = new Pane();
        pane.setPrefHeight(83);
        pane.setPrefWidth(677);

        Label label = new Label();
        label.setText(caracteristique+" : "+valeur);
        label.setPrefWidth(545);
        label.setPrefHeight(35);
        label.setStyle("-fx-background-color: #4B4A50;");
        label.setTextFill(Paint.valueOf("#fbfaff"));
        label.setEffect(new DropShadow());
        label.setCursor(Cursor.HAND);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font(18));
        label.setLayoutX(69);
        label.setLayoutY(22);

        pane.getChildren().add(label);
        return pane;

    }


    @FXML
    private Label nom;

    @FXML
    private Label prix;

    @FXML
    private Label date;

    @FXML
    private Label typeObjet;

    @FXML
    private VBox vboxContainer;
    
    @FXML
    void commander(ActionEvent event) throws IOException {

        String sql = "INSERT INTO commande(date,livrer,client,produit) VALUES(?,?,?,?)";
        try (Connection conn = Main.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            Date date = cal.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/YYYY");
            String dateString = format1.format(date);

            pstmt.setString(1, dateString);
            pstmt.setBoolean(2,false );
            pstmt.setString(3, Main.client.getTel());
            pstmt.setString(4, Main.produit.getNom());
            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Parent HomePageParent = FXMLLoader.load(getClass().getResource("demande_envoyee.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    @FXML
    void produit(ActionEvent event) throws IOException {
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("accueil.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }
}
