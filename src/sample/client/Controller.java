package sample.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Label nomPrenom;

    @FXML
    private BorderPane containerPane;


    @FXML
    private Pane centerPane;

    @FXML
    void dashboard(ActionEvent event) {
        containerPane.setCenter(centerPane);
    }

    @FXML
    void deconnecter(ActionEvent event) throws IOException {
        Main.client = null;
        Main.stage.close();
        Parent parent = FXMLLoader.load(getClass().getResource("../sample1.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        Main.stage = stage;

    }

    @FXML
    void requet(ActionEvent event) {
        loadUI("request");
    }

    @FXML
    void parametre(ActionEvent event) {
        loadUI("parametre");
    }

    @FXML
    void produits(ActionEvent event) {
        loadUI("produits");

    }

    @FXML
    void profil(ActionEvent event) {
        loadUI("profil");
    }


    private void loadUI(String ui) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/client/"+ui+".fxml"));
            if (ui.equals("profil")) loader.setController(new ControllerProfil());
            if (ui.equals("produits")) loader.setController(new ControllerProduit());
            if (ui.equals("parametre")) loader.setController(new ControllerParametre());
            if (ui.equals("request")) loader.setController(new ControllerRequest());
            root = loader.load();

            //root = FXMLLoader.load(getClass().getResource(ui+".fxml"));

        }catch (IOException ioe){

            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null , ioe);

        }
        containerPane.setCenter(root);
    }

}
