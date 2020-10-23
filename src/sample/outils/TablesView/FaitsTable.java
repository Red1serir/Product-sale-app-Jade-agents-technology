package sample.outils.TablesView;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class FaitsTable extends RecursiveTreeObject<FaitsTable> {
    public StringProperty variable;
    public StringProperty valeur;

    public String variableText;
    public String valeurText;
    public FaitsTable(String variable, String valeur) {
        this.variableText = variable;
        this.valeurText = valeur;
        this.variable = new SimpleStringProperty(variable);
        this.valeur = new SimpleStringProperty(valeur);
    }

    public String getVariableText() {
        return variableText;
    }

    public String getValeurText() {
        return valeurText;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FaitsTable) {
            return this.variableText.equals(((FaitsTable) obj).getVariableText());
        }
        return false;
    }
}
