package sample.outils.TablesView;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class RegleTable extends RecursiveTreeObject<RegleTable> {
    public StringProperty regle;
    public String regleText;
    public int id;
    public RegleTable(int id,String regle) {
        this.id = id;
        this.regleText = regle;
        this.regle = new SimpleStringProperty(regle);
    }

    public String getRegleText() {
        return regleText;
    }

    public int getId() {
        return id;
    }
}
