package sample.admin;

import com.jfoenix.controls.*;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.outils.engine.MoteurInference;
import sample.outils.logic.BaseConnaissance;
import sample.outils.logic.BaseFait;
import sample.outils.logic.Conclusion;
import sample.outils.TablesView.FaitsTable;
import sample.outils.TablesView.RegleTable;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        reglecolumn.setPrefWidth(180);
        reglecolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RegleTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RegleTable, String> param) {
                return param.getValue().getValue().regle;
            }
        });

        regles = FXCollections.observableArrayList();

        final TreeItem<RegleTable> root = new RecursiveTreeItem<RegleTable>(regles,RecursiveTreeObject::getChildren);
        tvBaseConnaissance.getColumns().setAll(reglecolumn);
        tvBaseConnaissance.setRoot(root);
        tvBaseConnaissance.setShowRoot(false);


        //initialisation La table view de la base de fait
        variableColumn = new JFXTreeTableColumn<>("variables");
        variableColumn.setPrefWidth(180);
        variableColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FaitsTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FaitsTable, String> param) {
                return param.getValue().getValue().variable;
            }
        });
        valeurColumn = new JFXTreeTableColumn<>("valeur");
        valeurColumn.setPrefWidth(180);
        valeurColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FaitsTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FaitsTable, String> param) {
                return param.getValue().getValue().valeur;
            }
        });

        faits = FXCollections.observableArrayList();

        final TreeItem<FaitsTable> rootFait = new RecursiveTreeItem<FaitsTable>(faits,RecursiveTreeObject::getChildren);
        tvBaseFait.getColumns().setAll(variableColumn,valeurColumn);
        tvBaseFait.setRoot(rootFait);
        tvBaseFait.setShowRoot(false);
    }


    @FXML
    private JFXRadioButton tgForwardChain;

    @FXML
    private JFXRadioButton tgBackwardChain;

    @FXML
    private JFXComboBox<String> cbCaracteristique;

    @FXML
    private JFXComboBox<String> cbValeur;

    @FXML
    private JFXComboBox<String> cbProduit;

    @FXML
    private ToggleGroup method;

    @FXML
    private JFXTextField tGoal;

    @FXML
    private JFXTextArea taTraceLog;

    @FXML
    private JFXTreeTableView<RegleTable> tvBaseConnaissance;

    JFXTreeTableColumn<RegleTable,String> reglecolumn;

    ObservableList<RegleTable> regles;

    @FXML
    private JFXTreeTableView<FaitsTable> tvBaseFait;

    JFXTreeTableColumn<FaitsTable,String> variableColumn;
    JFXTreeTableColumn<FaitsTable,String> valeurColumn;
    ObservableList<FaitsTable> faits;

    @FXML
    void changeProduit(ActionEvent event) {

        if (!cbProduit.getSelectionModel().getSelectedItem().isEmpty()) {
            //initialisation de la table des règles
            faits.clear();
            String sql = "SELECT * FROM baseConnaissance where fk_objet = '" + cbProduit.getSelectionModel().getSelectedItem()+"' ;";
            try (Connection conn = MainAdmin.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // loop through the result set
                regles.clear();
                while (rs.next()) {
                    regles.add(new RegleTable(rs.getInt("id"),rs.getString("regle")));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            //initialisation des caracteristique
            sql = "SELECT caracteristique FROM objet where nom = '" + cbProduit.getSelectionModel().getSelectedItem()+"';";
            try (Connection conn = MainAdmin.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                cbCaracteristique.getItems().clear();
                while (rs.next()) {
                    if (!cbCaracteristique.getItems().contains(rs.getString("caracteristique"))) {
                        cbCaracteristique.getItems().add(rs.getString("caracteristique"));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void changeCaracteristique(ActionEvent event) {
        //initialisation des valeurs
        cbValeur.getItems().clear();
        if (!cbProduit.getSelectionModel().isEmpty() && !cbCaracteristique.getSelectionModel().isEmpty()) {
            String sql = "SELECT valeur FROM objet where nom = '" + cbProduit.getSelectionModel().getSelectedItem() +"' AND caracteristique = '"+cbCaracteristique.getSelectionModel().getSelectedItem()+"' ;";
            try (Connection conn = MainAdmin.connect();
                 Statement stmt = conn.createStatement();


                 ResultSet rs = stmt.executeQuery(sql)) {
                cbValeur.getItems().clear();
                while (rs.next()) {
                    if (!cbValeur.getItems().contains(rs.getString("valeur"))) {
                        cbValeur.getItems().add(rs.getString("valeur"));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void ajouter(ActionEvent event) {
        if (!cbProduit.getSelectionModel().getSelectedItem().isEmpty() && !cbCaracteristique.getSelectionModel().getSelectedItem().isEmpty() && !cbValeur.getSelectionModel().getSelectedItem().isEmpty()){
            FaitsTable faitsTable = new FaitsTable(cbCaracteristique.getSelectionModel().getSelectedItem(), cbValeur.getSelectionModel().getSelectedItem());
            if (faits.contains(faitsTable)){
                faits.remove(faitsTable);
                faits.add(faitsTable);
            }else {
                faits.add(faitsTable);
            }
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        if (!tvBaseFait.getSelectionModel().isEmpty()) {
            faits.remove(tvBaseFait.getSelectionModel().getSelectedItem().getValue());
        }
    }

    @FXML
    void backwardChainSelected(ActionEvent event) {
        tGoal.setVisible(true);
    }

    @FXML
    void forwardChainSelected(ActionEvent event) {
        tGoal.setVisible(false);
    }


    @FXML
    void findGoal(ActionEvent event) {
        if (!faits.isEmpty()) {
            MoteurInference moteurInference = new MoteurInference(new BaseConnaissance(regles),new BaseFait(faits));
            BaseFait baseFait = null;
            if (tgForwardChain.isSelected()){

                baseFait = moteurInference.forwardChain();

            }
            taTraceLog.setText(baseFait.toString());
        }
    }

    @FXML
    void reset(ActionEvent event) {
        faits.clear();
        tGoal.setText("");
    }

    @FXML
    void runDemo(ActionEvent event) {
        if (!faits.isEmpty()) {
            MoteurInference moteurInference = new MoteurInference(new BaseConnaissance(regles),new BaseFait(faits));
            BaseFait baseFait = null;
            if (tgForwardChain.isSelected()){
                baseFait = moteurInference.forwardChain();
            }
            taTraceLog.setText(baseFait.toString());
        }
    }

    @FXML
    void retour(ActionEvent event) throws Exception{
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("accueil.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }

}
