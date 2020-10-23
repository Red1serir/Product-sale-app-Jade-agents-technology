package sample.vendeur;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.outils.object.CaracteristiqueObjet;
import sample.outils.object.ObjetCommercial;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class ControllerAjouterProduit implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        i=1;
        if (nomProduit!=null){
            String sql = "SELECT distinct nom FROM objet";
            try (Connection conn = MainVendeur.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                while (rs.next()) {
                    if (!typeProduit.getItems().contains(rs.getString("nom"))) {
                        typeProduit.getItems().add(rs.getString("nom"));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        if (vboxContainer != null) {

            MainVendeur.hashMap = new HashMap<>();

            String sql = "SELECT caracteristique,valeur FROM objet where nom='"+ MainVendeur.objetCommercial.getNom()+"';";
            try (Connection conn = MainVendeur.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    if (MainVendeur.hashMap.containsKey(rs.getString("caracteristique"))) {
                        MainVendeur.hashMap.get(rs.getString("caracteristique")).getItems().add(rs.getString("valeur"));
                    } else {
                        JFXComboBox<String> jfxComboBox = createComboBox(rs.getString("valeur"));
                        MainVendeur.hashMap.put(rs.getString("caracteristique"), jfxComboBox);
                        Pane pane = createPane(rs.getString("caracteristique"), jfxComboBox);
                        vboxContainer.getChildren().add(pane);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    int i;
    @FXML
    private JFXButton buttonSuivant;
    @FXML
    private JFXTextField nomProduit;
    @FXML
    private JFXComboBox<String> typeProduit;
    @FXML
    private BorderPane borderPaneContaine;
    @FXML
    private JFXTextField prixProduit;

    @FXML
    private Pane paneFirst;

    @FXML
    private VBox vboxContainer;

    @FXML
    void suivant(ActionEvent event) {
        if (i==1){
            if (!nomProduit.getText().isEmpty() && !typeProduit.getSelectionModel().isEmpty()){
                MainVendeur.objetCommercial = new ObjetCommercial(typeProduit.getSelectionModel().getSelectedItem());
                MainVendeur.objetCommercial.setNomProduit(nomProduit.getText());
                try {
                    MainVendeur.objetCommercial.setPrixProduit(Float.parseFloat(prixProduit.getText()));
                    i=2;
                    loadUI("ajouter_objet_caracteristique");
                    buttonSuivant.setText("Enregistrer");
                }catch (Exception e){

                }

            }
        }else if (i==2){
             for (String key : MainVendeur.hashMap.keySet()){
                if (MainVendeur.hashMap.get(key).getSelectionModel().isEmpty()){
                    return;
                }else {
                    MainVendeur.objetCommercial.add(new CaracteristiqueObjet(key, MainVendeur.hashMap.get(key).getSelectionModel().getSelectedItem()));
                }
            }

            String sql = "INSERT INTO produit(nom,type_objet,prix,date,proprietaire) VALUES(?,?,?,?,?)";

            try (Connection conn = MainVendeur.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, MainVendeur.objetCommercial.getNomProduit());
                    pstmt.setString(2, MainVendeur.objetCommercial.getNom());
                    pstmt.setFloat(3, MainVendeur.objetCommercial.getPrixProduit());

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, 1);
                    Date date = cal.getTime();
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/YYYY");
                    String dateString = format1.format(date);

                    pstmt.setString(4,dateString);
                    pstmt.setString(5, MainVendeur.vendeur.getTel());
                    pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            sql = "INSERT INTO caracteristique_produit(nom,valeur,produit) VALUES(?,?,?)";

            try (Connection conn = MainVendeur.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (CaracteristiqueObjet caracteristiqueObjet : MainVendeur.objetCommercial.getListCaracteristique()) {
                    pstmt.setString(1, caracteristiqueObjet.getCaracteristique());
                    pstmt.setString(2, caracteristiqueObjet.getValeur());
                    pstmt.setString(3, MainVendeur.objetCommercial.getNomProduit());
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            i=3;
            buttonSuivant.setVisible(false);
            loadUI("ProduitAjouter");
        }else if (i==3){
            i=1;
            loadUI("AjouterObjet_pane");
        }

    }


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

    private void loadUI(String ui) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
        }catch (IOException ioe){
            Logger.getLogger(ControllerProduit.class.getName()).log(Level.SEVERE, null , ioe);
        }
        borderPaneContaine.setCenter(root);
    }

    @FXML
    void ajouterAutreProduit(ActionEvent event) throws IOException {
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("Produit.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }


}
