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
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.outils.object.CaracteristiqueObjet;
import sample.outils.object.ObjetCommercial;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAjouterObjet implements Initializable {

    class CaracteristiqueTable extends RecursiveTreeObject<CaracteristiqueTable> {

        StringProperty caracteristique;
        String caracteristiqueText;


        public CaracteristiqueTable(String caracteristique) {
            this.caracteristiqueText = caracteristique;
            this.caracteristique = new SimpleStringProperty(caracteristique);
        }

        public String getCaracteristiqueText() { return caracteristiqueText; }
    }

    class ValueTable extends RecursiveTreeObject<ValueTable> {

        StringProperty value;
        String valueText;

        public ValueTable(String value) {
            this.valueText = value;
            this.value = new SimpleStringProperty(value);

        }

        public String getValueText() {
            return valueText;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        i = 1;
        if (cbCaracteristique != null) {
            for (CaracteristiqueObjet car : MainAdmin.objetCommercial.getListCaracteristique()) {
                cbCaracteristique.getItems().add(
                        new Label(car.getCaracteristique())
                );
            }
        }

        //la colone des caracteristique des objets
        if (tbView != null) {
            caracteristiquecolumn = new JFXTreeTableColumn<>("caracteristiques");
            caracteristiquecolumn.setPrefWidth(180);
            caracteristiquecolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CaracteristiqueTable, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CaracteristiqueTable, String> param) {
                    return param.getValue().getValue().caracteristique;
                }
            });
            caracteristiques = FXCollections.observableArrayList(); // liste de latable d'objet

            final TreeItem<CaracteristiqueTable> rootcara = new RecursiveTreeItem<>(caracteristiques, RecursiveTreeObject::getChildren);
            tbView.getColumns().setAll(caracteristiquecolumn);
            tbView.setRoot(rootcara);
            tbView.setShowRoot(false);

        }

        //la colone des valeurs possibles des caracteristique
        if (tbValues != null) {
            listValues = new HashMap<>();
            mapvaluecolumn = new HashMap<>();
            String initCar = "";
            for (CaracteristiqueObjet car : MainAdmin.objetCommercial.getListCaracteristique()) {
                 initCar = MainAdmin.objetCommercial.getListCaracteristique().get(0).getCaracteristique();
                 JFXTreeTableColumn<ValueTable,String> valuecolumn = new JFXTreeTableColumn<>(car.getCaracteristique());
                 valuecolumn.setPrefWidth(180);
                 valuecolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ValueTable, String>, ObservableValue<String>>() {
                     @Override
                     public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ValueTable, String> param) {
                         return param.getValue().getValue().value;
                     }
                 });

                ObservableList<ValueTable> values = FXCollections.observableArrayList();


                listValues.put(car.getCaracteristique(),values);
                mapvaluecolumn.put(car.getCaracteristique(),valuecolumn);
            }
            final TreeItem<ValueTable> rootValue = new RecursiveTreeItem<>(listValues.get(initCar), RecursiveTreeObject::getChildren);
            tbValues.getColumns().add(mapvaluecolumn.get(initCar));
            tbValues.setRoot(rootValue);
            tbValues.setShowRoot(false);

        }

        //init message
        if (nomObjetLabel != null){
            nomObjetLabel.setText(MainAdmin.objetCommercial.getNom());
            for (CaracteristiqueObjet caracteristiqueObjet : MainAdmin.objetCommercial.getListCaracteristique()) {
                Label label = new Label(caracteristiqueObjet.getCaracteristique()+" : "+caracteristiqueObjet.getListeValeurs());
                vbCaracteristique.getChildren().add(label);
            }
        }

    }

    int i;

    @FXML
    protected BorderPane container;

    @FXML
    private JFXTextField tNomObjet;

    @FXML
    private JFXTreeTableView<CaracteristiqueTable> tbView;
    JFXTreeTableColumn<CaracteristiqueTable,String> caracteristiquecolumn;
    ObservableList<CaracteristiqueTable> caracteristiques;


    @FXML
    private JFXTreeTableView<ValueTable> tbValues;
    HashMap<String,JFXTreeTableColumn<ValueTable,String>> mapvaluecolumn;
    HashMap<String,ObservableList<ValueTable>> listValues;



    @FXML
    private JFXTextField tCaracteristique;

    @FXML
    private JFXTextField tValeur;

    @FXML
    private JFXComboBox<Label> cbCaracteristique;

    @FXML
    private VBox vbCaracteristique;

    @FXML
    private Label nomObjetLabel;


    @FXML
    private JFXButton btnSuivant;

    @FXML
    void Enregistrer(ActionEvent event) {

    }

    @FXML
    void ajouterCaracteristique(ActionEvent event) {
        if (!tCaracteristique.getText().isEmpty()){
            MainAdmin.objetCommercial.add(new CaracteristiqueObjet(tCaracteristique.getText()));
            CaracteristiqueTable caracteristiqueTable = new CaracteristiqueTable(tCaracteristique.getText());
            caracteristiques.add(caracteristiqueTable);
        }else {
            System.out.println("le nom de l'objet n'est pas définie!!");
        }

    }

    @FXML
    void ajouterValeur(ActionEvent event) {
        if (!tValeur.getText().isEmpty() && !cbCaracteristique.getSelectionModel().isEmpty()){

            ValueTable valueTable = new ValueTable(tValeur.getText());

            listValues.get(cbCaracteristique.getSelectionModel().getSelectedItem().getText()).add(valueTable);

            MainAdmin.objetCommercial.searchCaracteristique(cbCaracteristique.getSelectionModel().getSelectedItem().getText()).add(tValeur.getText());

        } else {
            System.out.println("la valeur n'est pas définie!!");
        }
    }

    @FXML
    void change(ActionEvent event) {
        String initCar = cbCaracteristique.getValue().getText();
        System.out.println("llllllllllededededecdcfr : "+initCar);
        final TreeItem<ValueTable> rootValue = new RecursiveTreeItem<>(listValues.get(initCar), RecursiveTreeObject::getChildren);
        tbValues.getColumns().setAll(mapvaluecolumn.get(initCar));
        tbValues.setRoot(rootValue);
        tbValues.setShowRoot(false);
    }

    @FXML
    void retour(ActionEvent event) throws Exception{
        if(i==1) {
            Parent HomePageParent = FXMLLoader.load(getClass().getResource("accueil.fxml"));
            Scene HomePageScene = new Scene(HomePageParent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(HomePageScene);
            appStage.show();
        }else if (i==3){
            loadUI("caracteristiqueContainer");
            i=2;
        }else if (i==2){
            loadUI("nomObjet");
            i=1;
        }else if (i==4){
            loadUI("valuesContainer");
            i=3;
        }
    }

    @FXML
    void suivant(ActionEvent event) {
        if (i==1){
            if (!tNomObjet.getText().isEmpty()) {
                MainAdmin.objetCommercial = new ObjetCommercial(tNomObjet.getText());
                loadUI("caracteristiqueContainer");
                i = 2;
            }
        }else if (i==2){
            loadUI("valuesContainer");
            i=3;
        }else if (i==3){
            loadUI("message");
            btnSuivant.setText("Enregistrer");
            i = 4;
        }else if (i==4){    //enregestrer dans la base de données

            //inserssion de l'objet

            String sql = "INSERT INTO objet(nom,caracteristique,valeur) VALUES(?,?,?)";

            try (Connection conn = MainAdmin.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (CaracteristiqueObjet caracteristiqueObjet : MainAdmin.objetCommercial.getListCaracteristique()) {
                    for (String valeur : caracteristiqueObjet.getListeValeurs()) {
                        pstmt.setString(1, MainAdmin.objetCommercial.getNom());
                        pstmt.setString(2, caracteristiqueObjet.getCaracteristique());
                        pstmt.setString(3, valeur);
                        pstmt.executeUpdate();
                        System.out.println("l'objet a été ajouter");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private void loadUI(String ui){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
        }catch (IOException ioe){
            Logger.getLogger(ControllerAjouterObjet.class.getName()).log(Level.SEVERE, null , ioe);
        }
        container.setCenter(root);
    }
}
