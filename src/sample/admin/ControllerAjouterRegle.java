package sample.admin;

import com.jfoenix.controls.*;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.outils.logic.Regle;



import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControllerAjouterRegle implements Initializable {
    JFXSnackbar snackbar;
    @FXML
    Pane APane;
    class RegleTable extends RecursiveTreeObject<RegleTable>{
        StringProperty regle;
        String regleText;

        public RegleTable(String regle) {
            this.regleText = regle;
            this.regle = new SimpleStringProperty(regle);
        }

        public String getRegleText() {
            return regleText;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialisation de produits
         snackbar = new JFXSnackbar(APane);
        String sql = "SELECT nom FROM objet";
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





        reglecolumn = new JFXTreeTableColumn<>("Règle");
        reglecolumn.setPrefWidth(180);
        reglecolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RegleTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RegleTable, String> param) {
                return param.getValue().getValue().regle;
            }
        });

        regles = FXCollections.observableArrayList();
        //regles.add(new RegleTable("etiq : IF vehicleType=cycle AND num_wheels=2 AND motor=no THEN vehicle=Bicycle"));
        final TreeItem<RegleTable> root = new RecursiveTreeItem<RegleTable>(regles,RecursiveTreeObject::getChildren);
        tbView.getColumns().setAll(reglecolumn);
        tbView.setRoot(root);
        tbView.setShowRoot(false);
    }
    @FXML
    private JFXComboBox<String> cbProduit;

    @FXML
    private JFXTextField tRegle;

    @FXML
    private JFXTreeTableView<RegleTable> tbView;

    JFXTreeTableColumn<RegleTable,String> reglecolumn;

    ObservableList<RegleTable> regles;

    @FXML
    void supprimerRegle(ActionEvent event) {
        regles.remove(tbView.getSelectionModel().getSelectedItem().getValue());
    }


    @FXML
    void change(ActionEvent event) {

    }


    @FXML
    void Enregistrer(ActionEvent event) {
        if (!cbProduit.getSelectionModel().isEmpty()) {
            String sql = "INSERT INTO baseConnaissance(regle,fk_objet) VALUES(?,?)";

            try (Connection conn = MainAdmin.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (RegleTable regleTablele : regles) {
                    pstmt.setString(1, regleTablele.getRegleText());
                    pstmt.setString(2, cbProduit.getSelectionModel().getSelectedItem());
                    pstmt.executeUpdate();
                    System.out.println("la rège a été ajouter");
                    snackbar.show("la Regle a bien etait ajoutee !!!",3000);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void ajouterRegle(ActionEvent event) {
        String textRegle = tRegle.getText();
        try {
            Regle regle = new Regle(textRegle);
            System.out.println(regle);
            regles.add(new RegleTable(textRegle));

        }catch (Exception e){
            System.out.println(e.getMessage());
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
