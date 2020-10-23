package sample;

import animatefx.animation.Pulse;
import animatefx.animation.RollIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;


import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.outils.md5.HashPassword;
import sample.outils.utilisateur.Client;
import sample.outils.utilisateur.Vendeur;
import sample.vendeur.MainVendeur;


public class LoginController implements Initializable {
    @FXML
    JFXTextField tel;
    @FXML
    JFXTextField prenom, nom, tel2, wilaya, adresse;
    @FXML
    JFXPasswordField password, password2, password3;
    @FXML
    JFXPasswordField pass2;
    @FXML
    JFXComboBox<String> type1;
    @FXML
    JFXComboBox<String> type2;
    @FXML
    AnchorPane LoginForme, ConnexionForm, InscriptionForm;
    @FXML
    Text texttype;

    JFXSnackbar snackbar;

    @FXML
    void seconnecter(ActionEvent event) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        if (type1.getValue().equals("Client")) {
            seConnecterclient();
        } else {
            seconnecterVendeur();
        }
    }

    @FXML
    void inscrire(ActionEvent event) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        if (type2.getValue().equals("Vendeur"))
            inscrirevendeur();
        else
            inscrireClient();
    }

    private void inscrireClient() throws NoSuchAlgorithmException {
        if (!nom.getText().isEmpty()
                && !prenom.getText().isEmpty()
                && !password2.getText().isEmpty()
                && !password3.getText().isEmpty()
                && !wilaya.getText().isEmpty()

                && !adresse.getText().isEmpty()
                && !tel2.getText().isEmpty()
        )
        {if (password2.getText().equals(password3.getText())){
            String hash = new HashPassword().hashPassword(password2.getText());
            String sql = "INSERT INTO client(tel,nom,prenom,wilaya,adresse,motpasse) VALUES(?,?,?,?,?,?)";
            try (Connection conn = Main.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, tel.getText());
                pstmt.setString(2,nom.getText() );
                pstmt.setString(3, prenom.getText());
                pstmt.setString(4, wilaya.getText());
                pstmt.setString(5, adresse.getText());
                pstmt.setString(6, hash);
                pstmt.executeUpdate();

                Main.client = new Client(
                        nom.getText(),
                        prenom.getText(),
                        hash,
                        wilaya.getText(),
                        adresse.getText(),
                        tel.getText()
                );
                Main.stage.close();

                Parent parent = FXMLLoader.load(getClass().getResource("./client/accueil.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                scene.setFill(Color.TRANSPARENT);
                stage.setResizable(false);
                Main.stage = stage;

            } catch (SQLException | IOException e) {
                System.out.println(e.getMessage());
            }

        }else {
         snackbar.show("les mots de passes n'ont pas compatible !!",3000);
        }

        }else {
            snackbar.show("rempli touts les champs svp !!!",3000);
        }

    }

    private void inscrirevendeur() throws NoSuchAlgorithmException {

        if (!nom.getText().isEmpty()
                && !prenom.getText().isEmpty()
                && !password2.getText().isEmpty()
                && !password3.getText().isEmpty()
                && !wilaya.getText().isEmpty()

                && !adresse.getText().isEmpty()
                && !tel2.getText().isEmpty()
        ) {
            if (password2.getText().equals(password3.getText())) {
                String hash = new HashPassword().hashPassword(password3.getText());
                String sql = "INSERT INTO vendeur(tel,nom,prenom,wilaya,adresse,motpasse) VALUES(?,?,?,?,?,?)";
                try (Connection conn = Main.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, tel2.getText());
                    pstmt.setString(2, nom.getText());
                    pstmt.setString(3, prenom.getText());
                    pstmt.setString(4, wilaya.getText());
                    pstmt.setString(5, adresse.getText());
                    pstmt.setString(6, hash);
                    pstmt.executeUpdate();

                    MainVendeur.vendeur = new Vendeur(
                            nom.getText(),
                            prenom.getText(),
                            hash,
                            wilaya.getText(),
                            adresse.getText(),
                            tel2.getText()
                    );
                    Main.stage.close();
                    Parent parent = FXMLLoader.load(getClass().getResource("./vendeur/accueil.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    scene.setFill(Color.TRANSPARENT);
                    stage.setResizable(false);
                    Main.stage = stage;

                } catch (SQLException | IOException e) {
                    System.out.println(e.getMessage());
                }

            } else {
                snackbar.show("les mots de passes n'ont pas compatible !!", 3000);
            }

        } else {
            snackbar.show("rempli touts les champs svp !!!", 3000);
        }
    }




    private void seconnecterVendeur() throws IOException {
        if (!tel.getText().isEmpty() && !password.getText().isEmpty()){
            String sql = "SELECT * FROM vendeur where tel='"+tel.getText()+"';";
            try (Connection conn = MainVendeur.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                if (rs.next()) {
                    String mtp=new HashPassword().hashPassword(password.getText());

                    if (rs.getString("motpasse").equals(mtp)) {
                        MainVendeur.vendeur = new Vendeur(
                                rs.getString("nom"),
                                rs.getString("prenom"),
                                rs.getString("motpasse"),
                                rs.getString("wilaya"),
                                rs.getString("adresse"),
                                rs.getString("tel")
                        );
                        Main.stage.close();
                        Parent parent = FXMLLoader.load(getClass().getResource("./vendeur/accueil.fxml"));
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        scene.setFill(Color.TRANSPARENT);
                        stage.setResizable(false);
                        Main.stage = stage;
                    }else {
                        snackbar.show("mot de passe erroné !!",3000);
                    }
                }else {
                    snackbar.show("ce numero n'existe pas",3000);
                }
            } catch (SQLException | NoSuchAlgorithmException e) {
                System.out.println(e.getMessage());
            }
        }else {

            snackbar.show("veuillez remplir tout les champs !!",3000);
        }

    }



   private void seConnecterclient() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

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
                        Main.stage.close();
                        Parent parent = FXMLLoader.load(getClass().getResource("./client/accueil.fxml"));
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        scene.setFill(Color.TRANSPARENT);
                        stage.setResizable(false);
                        Main.stage = stage;

                       // Main.stage.initStyle(StageStyle.TRANSPARENT);
                    }else {

                        snackbar.show("Mot de passe erroné !!",3000);
                    }
                }else {

                    snackbar.show("ce numero de téléphone n'existe pas",3000);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }else {

            snackbar.show("veuillez remplir tout les champs !!",3000);

        }

    }


    public void erreur() {
        if (tel.getText().equals("") || password.getText().equals("") || type1.getValue().equals("") ){
            tel.setText("");
            password.clear();



            snackbar.show("Veuillez remplir les champs",2000);


        }else{
            snackbar.show("Identifient ou mot de passe incorrect",2000);
            tel.setText("");
            password.clear();

        }
    }
    @FXML
    public void inscriree(ActionEvent event) throws IOException, SQLException {
        if (!vide2()) {
            if (true) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Confirmation de l'inscription");
                alert.setContentText("Voulez vraiment confirmer l'inscription ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        Connection cnx = Main.connection;
                        //execute sql query
                        java.sql.Statement myStmt = cnx.createStatement();
                        myStmt.execute("INSERT INTO `Client`(`Username`, `Password`, `type`) VALUES  ('"
                                + tel2.getText()
                                + "','" + pass2.getText()
                                + "','" + type2.getValue()
                                + "');");
                    } catch (SQLIntegrityConstraintViolationException e) {
                        snackbar.show("Deja existant",2000);
                    }
                } else { }
            } else { }
            clear();
        } else {
            snackbar.show("Veuillez remplir les champs",2000);
        }
    }

    public void clear() throws IOException {
        tel2.clear();
        pass2.clear();
    }

    public boolean vide() {
        if (tel.getText().isEmpty()   || password.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean vide2() {
        if (tel2.getText().isEmpty()   || pass2.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type1.getItems().addAll("Vendeur","Client");
        type2.getItems().addAll("Vendeur","Client");
        type1.setValue("Client");
        type2.setValue("Vendeur");
        snackbar =new JFXSnackbar(LoginForme);
    }

    public void passerAconnexion(ActionEvent event) {
        ConnexionForm.toFront();
        double a,b;
        ConnexionForm.setDisable(false);
        InscriptionForm.setDisable(true);
        a=ConnexionForm.getLayoutX();
        b=ConnexionForm.getLayoutY();
        ConnexionForm.setLayoutX(InscriptionForm.getLayoutX());
        ConnexionForm.setLayoutY(InscriptionForm.getLayoutY());
        InscriptionForm.setLayoutX(a);
        InscriptionForm.setLayoutY(b);
        new SlideInRight(InscriptionForm).play();
        new SlideInLeft(ConnexionForm).play();
    }

    public void passerAinscrire(ActionEvent event) {
        InscriptionForm.toFront();
        double a,b;
        InscriptionForm.setDisable(false);
        ConnexionForm.setDisable(true);
        a=InscriptionForm.getLayoutX();
        b=InscriptionForm.getLayoutY();
        InscriptionForm.setLayoutX(ConnexionForm.getLayoutX());
        InscriptionForm.setLayoutY(ConnexionForm.getLayoutY());
        ConnexionForm.setLayoutX(a);
        ConnexionForm.setLayoutY(b);
        new SlideInRight(ConnexionForm).play();


        new SlideInLeft(InscriptionForm).play();

    }

    public void select(ActionEvent actionEvent) {
        if(type1.getValue().equals("Client"))
        {texttype.setText("Client");
            ConnexionForm.setStyle("-fx-background-color: radial-gradient(radius 180%, #2AF598, derive(#009EFD, -30%), derive(#2AF598, 30%))");

            new RollIn(texttype).play();
            new Pulse(ConnexionForm).play();

        }
        else if(type1.getValue().equals("Vendeur"))
        {texttype.setText("Vendeur");
            ConnexionForm.setStyle("-fx-background-color: radial-gradient(radius 180%, #E5B2CA, derive(#7028E4, -30%), derive(#FFE29F, 30%))");
            new RollIn(texttype).play();
            new Pulse(ConnexionForm).play();

        }
        else {
            texttype.setText(" ");
            ConnexionForm.setStyle("-fx-background-color: radial-gradient(radius 180%, #2AF598, derive(#009EFD, -30%), derive(#2AF598, 30%))");

        }
    }
}
