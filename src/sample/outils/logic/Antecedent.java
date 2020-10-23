package sample.outils.logic;

import java.util.ArrayList;

public class Antecedent {
    private String antecedent;
    private ArrayList<Clause> clauses;

    public Antecedent(String antecedent) {
        this.antecedent = antecedent;                               //antecedent full text
        this.clauses = new ArrayList<>();                           //list de clause

        //Initialisation clauses
        String[] spliteAntecedent = antecedent.split("AND"); //decompose l'antecedent en un ensemble de clauses
        for (String element : spliteAntecedent){
                clauses.add(new Clause(element));
        }

    }

    public String getAntecedent() {
        return antecedent;
    }

    public ArrayList<Clause> getClauses() {
        return clauses;
    }

    public ArrayList<Variable> getVariables(){
        ArrayList<Variable> temp = new ArrayList<>();
        for (Clause clause : this.clauses) {
            for (Variable variable: clause.getVariables()) {
                if (!temp.contains(variable)){
                 temp.add(variable);
                }
            }
        }
        return temp;
    }
}
