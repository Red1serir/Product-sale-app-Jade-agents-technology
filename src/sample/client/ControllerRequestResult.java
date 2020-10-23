package sample.client;

import com.jfoenix.controls.JFXButton;
import jade.wrapper.AgentController;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.Main;
import sample.outils.object.Produit;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import sample.outils.systemMultiAgent.*;

public class ControllerRequestResult implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            initialiserProduits();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private VBox vboxResulata;

    private CentralAgent centralAgent;

    public CentralAgent getCentralAgent() {
        return centralAgent;
    }

    public void setCentralAgent(CentralAgent centralAgent) {
        this.centralAgent = centralAgent;
    }

    private void initialiserProduits() throws SQLException {
        BuildAgents buildAgents = new BuildAgents();
        System.out.println("----------------->"+ Main.request.get("category"));
        AgentController centralAgent =buildAgents.buildCentralAgent(new Object[]{this, Main.request});
        try {
            Thread.sleep((long) 1000.0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




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



    public void reciveResult(String category,String queryCondition){

        String sql = "SELECT * from produit where type_objet='"+ category +"' AND nom in ("+queryCondition+");";
        System.out.println(sql);
        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            HBox currentHBox = creatHboxContainer();
            int i = 1;
            while (rs.next()) {
                currentHBox.getChildren().add(createPaneProduit(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                if (i%3 == 0){
                    vboxResulata.getChildren().add(currentHBox);
                    currentHBox = creatHboxContainer();
                }
                i++;
            }
            if (i<=3 && currentHBox.getChildren().size()>0){
                vboxResulata.getChildren().add(currentHBox);
            }
            if (vboxResulata.getChildren().size()==0){
                Pane pane =createPane_info("Aucune recommandation trouvee");
                vboxResulata.getChildren().add(pane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }

    private Pane createPane_info(String information){
        Pane pane = new Pane();
        pane.setPrefHeight(115);
        pane.setPrefWidth(723);
        Label label = new Label(information);
        label.setLayoutX(137);
        label.setLayoutY(96);
        label.setPrefWidth(350);
        label.setPrefHeight(45);
        label.setFont(Font.font(20));
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setStyle("-fx-background-color: #30eeff;");
        pane.getChildren().addAll(label);
        return pane;
    }

}
