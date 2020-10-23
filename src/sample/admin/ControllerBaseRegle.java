package sample.admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.outils.TablesView.RegleTable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerBaseRegle implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id = new ArrayList<>();
        //initialisation de produits
        String sql = "SELECT distinct nom FROM objet";
        try (Connection conn = MainAdmin.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                if (!cbProduit.getItems().contains(rs.getString("nom"))) {
                    cbProduit.getItems().add(rs.getString("nom"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //initialisation La table view de la base de conaissances
        reglecolumn = new JFXTreeTableColumn<>("Règle");
        reglecolumn.setPrefWidth(800);
        reglecolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RegleTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RegleTable, String> param) {
                return param.getValue().getValue().regle;
            }
        });

        regles = FXCollections.observableArrayList();

        final TreeItem<RegleTable> root = new RecursiveTreeItem<RegleTable>(regles, RecursiveTreeObject::getChildren);
        tvBaseConnaissance.getColumns().setAll(reglecolumn);
        tvBaseConnaissance.setRoot(root);
        tvBaseConnaissance.setShowRoot(false);

    }

    @FXML
    private JFXTreeTableView<RegleTable> tvBaseConnaissance;
    JFXTreeTableColumn<RegleTable,String> reglecolumn;
    ObservableList<RegleTable> regles;

    @FXML
    private JFXComboBox<String> cbProduit;

    private ArrayList<Integer> id;
    @FXML
    void Enregistrer(ActionEvent event) throws IOException {
        for (int idRegle : id){
            String sql = "delete FROM baseConnaissance where id = '" + idRegle +"' ;";
            try (Connection conn = MainAdmin.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("messageAction.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

    @FXML
    void changeProduit(ActionEvent event) {
        if (!cbProduit.getSelectionModel().getSelectedItem().isEmpty()) {
            //initialisation de la table des règles
            String sql = "SELECT * FROM baseConnaissance where fk_objet = '" + cbProduit.getSelectionModel().getSelectedItem()+"' ;";
            try (Connection conn = MainAdmin.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // loop through the result set
                regles.clear();
                id.clear();
                while (rs.next()) {
                    regles.add(new RegleTable(rs.getInt("id"),rs.getString("regle")));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void supprimer(ActionEvent event) {

        id.add(tvBaseConnaissance.getSelectionModel().getSelectedItem().getValue().getId());
        System.out.println(id);
        regles.remove(tvBaseConnaissance.getSelectionModel().getSelectedItem().getValue());
    }

    @FXML
    void Annuler(ActionEvent event) throws IOException {
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("accueil.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }
}
