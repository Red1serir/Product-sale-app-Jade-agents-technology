package sample.vendeur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.outils.md5.HashPassword;
import sample.outils.utilisateur.Vendeur;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wilayaInscrit.getItems().addAll("Alger","Tissemsilt","Boumerdès","Illizi","Ouargla");
    }

    @FXML
    private TextField nomInscrit;

    @FXML
    private TextField prenomInscrit;

    @FXML
    private TextField telInscrit;

    @FXML
    private ComboBox<String> wilayaInscrit;

    @FXML
    private TextField adresseInscrit;

    @FXML
    private PasswordField motPasseInscrit;

    @FXML
    private PasswordField motPasseInscrit1;

    @FXML
    private TextField telLogin;

    @FXML
    private PasswordField motPasseLogin;


    @FXML
    private Label lableErreur;

    @FXML
    void connecter(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        if (!telLogin.getText().isEmpty() && !motPasseLogin.getText().isEmpty()){
            String sql = "SELECT * FROM vendeur where tel='"+telLogin.getText()+"';";
            try (Connection conn = MainVendeur.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                if (rs.next()) {
                    String mtp = new HashPassword().hashPassword(motPasseLogin.getText());
                    if (rs.getString("motpasse").equals(mtp)) {
                        MainVendeur.vendeur = new Vendeur(
                                rs.getString("nom"),
                                rs.getString("prenom"),
                                rs.getString("motpasse"),
                                rs.getString("wilaya"),
                                rs.getString("adresse"),
                                rs.getString("tel")
                        );

                        Parent HomePageParent = FXMLLoader.load(getClass().getResource("accueil.fxml"));
                        Scene HomePageScene = new Scene(HomePageParent);

                        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        appStage.setScene(HomePageScene);
                        appStage.show();
                    }else {
                        lableErreur.setText("Mot de passe erroné !!");
                    }
                }else {
                    lableErreur.setText("ce numero de téléphone n'existe pas");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }else {
            lableErreur.setText("remplir tout les champs svp");
        }

    }


    @FXML
    void inscrit(ActionEvent event) throws IOException, NoSuchAlgorithmException {

        if (    !nomInscrit.getText().isEmpty()
                && !prenomInscrit.getText().isEmpty()
                && !motPasseInscrit.getText().isEmpty()
                && !motPasseInscrit1.getText().isEmpty()
                && !wilayaInscrit.getSelectionModel().isEmpty()
                && !wilayaInscrit.getSelectionModel().getSelectedItem().isEmpty()
                && !adresseInscrit.getText().isEmpty()
                && !telInscrit.getText().isEmpty()
        ){
            if (motPasseInscrit1.getText().equals(motPasseInscrit.getText())){
                String hash = new HashPassword().hashPassword(motPasseInscrit.getText());
                String sql = "INSERT INTO vendeur(tel,nom,prenom,wilaya,adresse,motpasse) VALUES(?,?,?,?,?,?)";
                try (Connection conn = Main.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, telInscrit.getText());
                    pstmt.setString(2,nomInscrit.getText() );
                    pstmt.setString(3, prenomInscrit.getText());
                    pstmt.setString(4, wilayaInscrit.getSelectionModel().getSelectedItem());
                    pstmt.setString(5, adresseInscrit.getText());
                    pstmt.setString(6, hash);
                    pstmt.executeUpdate();

                    MainVendeur.vendeur = new Vendeur(
                            nomInscrit.getText(),
                            prenomInscrit.getText(),
                            hash,
                            wilayaInscrit.getSelectionModel().getSelectedItem(),
                            adresseInscrit.getText(),
                            telInscrit.getText()
                    );

                    Parent HomePageParent = FXMLLoader.load(getClass().getResource("accueil.fxml"));
                    Scene HomePageScene = new Scene(HomePageParent);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    appStage.setScene(HomePageScene);
                    appStage.show();

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            }else {
                lableErreur.setText("les mots de passes n'ont pas compatible !!");
            }

        }else {
            lableErreur.setText("rempli touts les champs svp !!!");
        }
    }

}
