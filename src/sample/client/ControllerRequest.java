package sample.client;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerRequest implements Initializable {

    int i;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.request = new HashMap<>();
        i = 0;
        String sql = "SELECT distinct nom FROM objet";
        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                if (!objet.getItems().contains(rs.getString("nom"))) {
                    objet.getItems().add(rs.getString("nom"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    @FXML
    private BorderPane containerBorderPane;

    @FXML
    private JFXComboBox<String> objet;

    @FXML
    void suivant(ActionEvent event) {
        if (i==0) {
            if (!objet.getSelectionModel().isEmpty()) {
                Main.request.put("category",objet.getSelectionModel().getSelectedItem());
                loadUI("request_caracteristique");
                i++;
            }
        }else if (i==1){
            //load base de fait
            for (String key : Main.hashMap.keySet()){
                if (!Main.hashMap.get(key).getSelectionModel().isEmpty()) {
                    Main.request.put(key, Main.hashMap.get(key).getSelectionModel().getSelectedItem());
                }
            }

            loadUI("request_result");
            i++;
        }
    }

    private void loadUI(String ui) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/client/"+ui+".fxml"));
            if (ui.equals("request_caracteristique")){
                loader.setController(new ControllerRequestCaracteristique());
            }
            if (ui.equals("request_result")){
                loader.setController(new ControllerRequestResult());
            }
            root = loader.load();
        }catch (IOException ioe){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null , ioe);
        }
        containerBorderPane.setCenter(root);
    }

}
