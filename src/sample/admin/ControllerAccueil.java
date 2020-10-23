package sample.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;

import java.io.IOException;

public class ControllerAccueil {

    @FXML
    void ajouterObjet(ActionEvent event) throws Exception{
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("ajouterObjer.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    @FXML
    void ajouterRegle(ActionEvent event) throws Exception{
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("AjouterRegle.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    @FXML
    void moteurInference(ActionEvent event) throws Exception{
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    @FXML
    void baseRegle(ActionEvent event) throws IOException {
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("baseDeRegles.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    @FXML
    void objets(ActionEvent event) throws IOException {
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("objets.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    @FXML
    void deconnecter(ActionEvent event) throws IOException {
        Main.stage.close();
        Parent parent = FXMLLoader.load(getClass().getResource("Log_in.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        Main.stage = stage;
    }

}
