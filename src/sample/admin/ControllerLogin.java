package sample.admin;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.outils.md5.HashPassword;
import sample.vendeur.MainVendeur;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    @FXML
    private JFXTextField tel;

    @FXML
    private JFXPasswordField motPasse;

    @FXML
    private JFXSnackbar snackbar;
    @FXML
    Pane pane;



    @FXML
    void connecter(ActionEvent event) {
        if (!tel.getText().isEmpty() && !motPasse.getText().isEmpty()){
            String sql = "SELECT * FROM admin where tel='"+tel.getText()+"';";
            try (Connection conn = MainVendeur.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                if (rs.next()) {
                    String mtp = new HashPassword().hashPassword(motPasse.getText());
                    if (rs.getString("password").equals(mtp)) {

                        Parent HomePageParent = FXMLLoader.load(getClass().getResource("accueil.fxml"));
                        Scene HomePageScene = new Scene(HomePageParent);

                        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        appStage.setScene(HomePageScene);
                        appStage.show();
                    }else {
                        snackbar.show("Mot de passe errone !!",3000);
                    }
                }else {
                    snackbar.show("ce numero de téléphone n'existe pas",3000);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            snackbar.show("veuillez remplir tout les champs",3000);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        snackbar=new JFXSnackbar(pane);
    }
}
