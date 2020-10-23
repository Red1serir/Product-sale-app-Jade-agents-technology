package sample.vendeur;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerCommandes implements Initializable {

    class CommandesTable extends RecursiveTreeObject<CommandesTable> {

        StringProperty produit;
        StringProperty date;
        StringProperty nom;
        StringProperty prenom;
        StringProperty tel;

        String produitText;
        String dateText;
        String nomText;
        String prenomText;
        String telText;


        public CommandesTable(String produitText, String dateText, String nomText, String prenomText,String telText) {
            this.produit = new SimpleStringProperty(produitText);
            this.date = new SimpleStringProperty(dateText);
            this.nom = new SimpleStringProperty(nomText);
            this.prenom = new SimpleStringProperty(prenomText);
            this.tel = new SimpleStringProperty(telText);

            this.produitText = produitText;
            this.dateText = dateText;
            this.nomText = nomText;
            this.prenomText = prenomText;
            this.telText = telText;
        }

        public String getProduitText() {
            return produitText;
        }

        public String getDateText() {
            return dateText;
        }

        public String getNomText() {
            return nomText;
        }

        public String getPrenomText() {
            return prenomText;
        }

        @Override
        public String toString() {
            return "CommandesTable{" +
                    "produitText='" + produitText + '\'' +
                    ", dateText='" + dateText + '\'' +
                    ", nomText='" + nomText + '\'' +
                    ", prenomText='" + prenomText + '\'' +
                    ", telText='" + telText + '\'' +
                    '}';
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produitcolumn = new JFXTreeTableColumn<>("Produit");
        produitcolumn.setPrefWidth(180);
        produitcolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CommandesTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CommandesTable, String> param) {
                return param.getValue().getValue().produit;
            }
        });

        datecolumn = new JFXTreeTableColumn<>("Date");
        datecolumn.setPrefWidth(180);
        datecolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CommandesTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CommandesTable, String> param) {
                return param.getValue().getValue().date;
            }
        });

        nomcolumn = new JFXTreeTableColumn<>("Nom");
        nomcolumn.setPrefWidth(180);
        nomcolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CommandesTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CommandesTable, String> param) {
                return param.getValue().getValue().nom;
            }
        });

        prenomcolumn = new JFXTreeTableColumn<>("Prenom");
        prenomcolumn.setPrefWidth(180);
        prenomcolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CommandesTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CommandesTable, String> param) {
                return param.getValue().getValue().prenom;
            }
        });

        telcolumn = new JFXTreeTableColumn<>("N° Tel");
        telcolumn.setPrefWidth(180);
        telcolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CommandesTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CommandesTable, String> param) {
                return param.getValue().getValue().tel;
            }
        });



        dataCommandes = FXCollections.observableArrayList(); // liste de latable d'objet

        final TreeItem<CommandesTable> root = new RecursiveTreeItem<>(dataCommandes, RecursiveTreeObject::getChildren);
        tableCommandes.getColumns().setAll(produitcolumn,datecolumn,nomcolumn,prenomcolumn,telcolumn);
        tableCommandes.setRoot(root);
        tableCommandes.setShowRoot(false);



        //init data table

        String sql = "SELECT commande.produit,commande.date,client.nom,client.prenom,client.tel FROM produit,commande,client where client.tel=commande.client and commande.produit=produit.nom and produit.proprietaire='"+ MainVendeur.vendeur.getTel()+"';";
        try (Connection conn = MainVendeur.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                CommandesTable commandesTable = new CommandesTable(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                );
                System.out.println(commandesTable);
                dataCommandes.add(commandesTable);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }

    @FXML
    private JFXTreeTableView<CommandesTable> tableCommandes;
    JFXTreeTableColumn<CommandesTable,String> produitcolumn;
    JFXTreeTableColumn<CommandesTable,String> datecolumn;
    JFXTreeTableColumn<CommandesTable,String> nomcolumn;
    JFXTreeTableColumn<CommandesTable,String> prenomcolumn;
    JFXTreeTableColumn<CommandesTable,String> telcolumn;
    ObservableList<CommandesTable> dataCommandes;





    @FXML
    void accueil(ActionEvent event) throws IOException {
        Parent HomePageParent = FXMLLoader.load(getClass().getResource("accueil.fxml"));
        Scene HomePageScene = new Scene(HomePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(HomePageScene);
        appStage.show();
    }
}
