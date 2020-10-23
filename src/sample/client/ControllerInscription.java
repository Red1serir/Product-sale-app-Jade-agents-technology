package sample.client;

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
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.outils.md5.HashPassword;
import sample.outils.utilisateur.Client;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerInscription implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        wilaya.getItems().addAll("Alger","Tissemsilt","Boumerdès","Illizi","Ouargla");
    }

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField adresse;

    @FXML
    private TextField tel;

    @FXML
    private PasswordField password;

    @FXML
    private Label lableErreur;

    @FXML
    private PasswordField password1;

    @FXML
    private ComboBox<String> wilaya;

    @FXML
    void Loadpadeprincipale(ActionEvent event) throws IOException {
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    @FXML
    void goinscri(ActionEvent event) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (    !nom.getText().isEmpty()
                && !prenom.getText().isEmpty()
                && !password.getText().isEmpty()
                && !password1.getText().isEmpty()
                && !wilaya.getSelectionModel().isEmpty()
                && !wilaya.getSelectionModel().getSelectedItem().isEmpty() && !adresse.getText().isEmpty()
                && !tel.getText().isEmpty()
        ){
            if (password1.getText().equals(password.getText())){
                String hash = new HashPassword().hashPassword(password.getText());
                String sql = "INSERT INTO client(tel,nom,prenom,wilaya,adresse,motpasse) VALUES(?,?,?,?,?,?)";
                try (Connection conn = Main.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, tel.getText());
                        pstmt.setString(2,nom.getText() );
                        pstmt.setString(3, prenom.getText());
                        pstmt.setString(4, wilaya.getSelectionModel().getSelectedItem());
                        pstmt.setString(5, adresse.getText());
                        pstmt.setString(6, hash);
                        pstmt.executeUpdate();

                        Main.client = new Client(
                                nom.getText(),
                                prenom.getText(),
                                hash,
                                wilaya.getSelectionModel().getSelectedItem(),
                                adresse.getText(),
                                tel.getText()
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
