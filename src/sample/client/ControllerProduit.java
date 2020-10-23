package sample.client;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Main;
import sample.outils.object.Produit;
import sample.vendeur.MainVendeur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerProduit implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String sql = "SELECT * FROM produit ;";
        try (Connection conn = MainVendeur.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            HBox currentHBox = creatHboxContainer();
            int i = 1;
            while (rs.next()) {
                currentHBox.getChildren().add(createPaneProduit(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                if (i%3 == 0){
                    vboxProduit.getChildren().add(currentHBox);
                    currentHBox = creatHboxContainer();
                }
                i++;
            }
            if(i<=3){
                vboxProduit.getChildren().add(currentHBox);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    private VBox vboxProduit;



    private Pane createPaneProduit(String nomProduit,String typeObjet,String prix, String date){
        Pane pane = new Pane();
        pane.setPrefHeight(240.0);
        pane.setPrefWidth(199.0);
        pane.setStyle("-fx-background-color: #FFFFFF;");
        pane.setEffect(new DropShadow());
        pane.setCursor(Cursor.HAND);


        ImageView imageView =new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(200);
        imageView.setLayoutX(14);
        imageView.setLayoutY(16);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image("sample/images/Produit.png"));


        JFXButton jfxButtonNom = new JFXButton();
        jfxButtonNom.setLayoutX(14);
        jfxButtonNom.setLayoutY(177);
        jfxButtonNom.setPrefWidth(188);
        jfxButtonNom.setText(nomProduit);
        jfxButtonNom.setFont(Font.font(17));
        jfxButtonNom.setWrapText(false);
        jfxButtonNom.setAlignment(Pos.BASELINE_LEFT);
        jfxButtonNom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent HomePageParent = null;
                try {
                    Main.produit = new Produit(nomProduit, typeObjet, date, prix);
                    HomePageParent = FXMLLoader.load(getClass().getResource("info_produit.fxml"));
                    Scene HomePageScene = new Scene(HomePageParent);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    appStage.setScene(HomePageScene);
                    appStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        Label labelDate = new Label();
        labelDate.setLayoutX(14);
        labelDate.setLayoutY(208);
        labelDate.setPrefWidth(100);
        labelDate.setText(date);
        labelDate.setWrapText(false);

        Label labelPrix = new Label();
        labelPrix.setLayoutX(100);
        labelPrix.setLayoutY(208);
        labelPrix.setPrefWidth(94);
        labelPrix.setText(prix+" DA");
        labelPrix.setWrapText(false);
        labelPrix.setFont(Font.font(17));
        labelPrix.setTextFill(Color.valueOf("#ff8521"));

        pane.getChildren().addAll(imageView,jfxButtonNom,labelDate,labelPrix);

        return pane;
    }

    private HBox creatHboxContainer(){
        HBox hBox = new HBox();

        hBox.setOpaqueInsets(new Insets(10,10,10,10));
        hBox.setPadding(new Insets(10,0,10,0));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefHeight(260.0);
        hBox.setPrefWidth(727.0);
        hBox.setSpacing(20.0);


        return hBox;
    }
}
