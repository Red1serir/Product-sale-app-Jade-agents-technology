package sample.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.outils.md5.HashPassword;
import sample.outils.utilisateur.Client;
import sample.vendeur.MainVendeur;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerLogin {

    @FXML
    private TextField tel;

    @FXML
    private PasswordField password;

    @FXML
    private Label lableErreur;

    @FXML
    void Laodpageinscription(ActionEvent event) throws IOException {
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("pageinscription.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    @FXML
    void seConnecter(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        if (!tel.getText().isEmpty() && !password.getText().isEmpty()){
            String sql = "SELECT * FROM client where tel='"+tel.getText()+"';";
            try (Connection conn = MainVendeur.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                if (rs.next()) {
                    String mtp = new HashPassword().hashPassword(password.getText());
                    if (rs.getString("motpasse").equals(mtp)) {
                        Main.client = new Client(
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
}
