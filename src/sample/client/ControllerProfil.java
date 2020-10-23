package sample.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerProfil implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tNom.setText(Main.client.getNom());
        tPrenom.setText(Main.client.getPrenom());
        cbWilaya.getItems().add(Main.client.getWilaya());
        tAdresse.setText(Main.client.getAdresse());
        tNTel.setText(Main.client.getTel());
        tPassword.setText("");
        tNewPassword.setText("");
    }

    @FXML
    private TextField tNom;

    @FXML
    private TextField tPrenom;

    @FXML
    private ComboBox<String> cbWilaya;

    @FXML
    private TextField tAdresse;

    @FXML
    private TextField tNTel;

    @FXML
    private PasswordField tPassword;

    @FXML
    private PasswordField tNewPassword;


    @FXML
    void enregistrer(ActionEvent event) {

    }

}
