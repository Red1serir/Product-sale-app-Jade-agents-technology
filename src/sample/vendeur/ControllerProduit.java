package sample.vendeur;

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerProduit implements Initializable  {



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (tProduit!=null) {
            initialiserProduits();
        }


    }


    @FXML
    private BorderPane borderPaneContaine;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vboxProduit;
    @FXML
    private JFXTextField tProduit;

    @FXML
    void presseKey(ActionEvent event) {

    }

    @FXML
    void accueil(ActionEvent event) throws IOException {
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("accueil.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    @FXML
    void ajouterProduit(ActionEvent event) {
            loadUI("ajouter_objet_type_nom");
    }
    @FXML
    void produit(ActionEvent event) throws IOException {

        Parent HomePageParent = FXMLLoader.load(getClass().getResource("Produit.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    private void initialiserProduits(){

        System.out.println("hello");
        String sql = "SELECT * FROM produit where proprietaire ='"+ MainVendeur.vendeur.getTel()+"';";
        try (Connection conn = MainVendeur.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            HBox currentHBox = creatHboxContainer();
            int i = 1;
            System.out.println("ererkjljflkq jlkfqj");
            while (rs.next()) {
                System.out.println("hello dakhel while ");
                currentHBox.getChildren().add(createPaneProduit(rs.getString(1),rs.getString(3),rs.getString(4)));
                if (i%3 == 0){
                    vboxProduit.getChildren().add(currentHBox);
                    currentHBox = creatHboxContainer();
                }
                i++;
            }
            if(i<=3)
            {
                vboxProduit.getChildren().add(currentHBox);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    private Pane createPaneProduit(String nomProduit,String prix, String date){
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

        Label labelNom = new Label();
        labelNom.setLayoutX(14);
        labelNom.setLayoutY(177);
        labelNom.setPrefWidth(188);
        labelNom.setText(nomProduit);
        labelNom.setFont(Font.font(17));
        labelNom.setWrapText(false);

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

        pane.getChildren().addAll(imageView,labelNom,labelDate,labelPrix);
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



    private void loadUI(String ui) {
        Parent root = null;
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(ui+".fxml"));
            root = loader.load();

            // root = FXMLLoader.load(getClass().getResource("/sample/"+ui+".fxml"));

        }catch (IOException ioe){

            Logger.getLogger(ControllerProduit.class.getName()).log(Level.SEVERE, null , ioe);

        }

        borderPaneContaine.setCenter(root);

    }

}
