package sample.outils.logic;

import sample.outils.TablesView.FaitsTable;

import java.util.ArrayList;

public class BaseFait {

    private ArrayList<Variable> baseFaits;

    public BaseFait(ArrayList<Variable> baseFaits) {
        this.baseFaits = baseFaits;
    }

    public BaseFait(javafx.collections.ObservableList<FaitsTable> baseFaits) {
        this.baseFaits = new ArrayList<>();
        for (FaitsTable faitsTable : baseFaits){
            this.baseFaits.add(new Variable(faitsTable.getVariableText()+"="+faitsTable.getValeurText()));
        }

    }
    public BaseFait() {
        this.baseFaits = new ArrayList<>();
    }

    public void setBaseFaits(ArrayList<Variable> baseFaits) {
        this.baseFaits = baseFaits;
    }

    public ArrayList<Variable> getBaseFaits() {
        return baseFaits;
    }


    public Variable search(String nom){
        for (Variable variable : this.baseFaits){
            if (variable.getNom().equals(nom)){
                return variable;
            }
        }
        return null;
    }

    public String getQuery(){
        String query = "";
        int i = 0;
        for (Variable variable : this.baseFaits){
            if (i==0 && i==this.baseFaits.size()-1) {
                query += "SELECT produit FROM caracteristique_produit where nom='" + variable.getNom() + "' AND valeur='" + variable.getVal() + "'";
            }else if (i==0 && i<this.baseFaits.size()-1) {
                 query = query += "SELECT produit FROM caracteristique_produit where nom='" + variable.getNom() + "' AND valeur='" + variable.getVal() + "' AND produit in ()";
            }else if (i>0 && i<this.baseFaits.size()-1){
                query = query.replace("()","(SELECT produit FROM caracteristique_produit where nom='"+variable.getNom()+"' AND valeur='"+variable.getVal()+"' AND produit in () )");
            }else if (i==this.baseFaits.size()-1){
                query = query.replace("()","(SELECT produit FROM caracteristique_produit where nom='"+variable.getNom()+"' AND valeur='"+variable.getVal()+"')");
            }
            i++;
        }
        return query;
    }

    @Override
    public String toString() {
        String str = "Found solution : \n";
        for(Variable var : baseFaits){
            str += var.getNom() +" = "+var.getVal()+"\n";
        }
        return str;
    }

    public void add(Variable variable) {
        if (!baseFaits.contains(variable)){
            baseFaits.add(variable);
        }
    }
}
