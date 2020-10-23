package sample.outils.logic;

import java.util.ArrayList;

public class Clause {

    private String clause;
    private ArrayList<Variable> variables;


    public Clause(String clause) {
        this.clause = clause;
        String[] splitClaus = clause.split("OR");
        this.variables = new ArrayList<>();
        for (String attr : splitClaus){
                variables.add(new Variable(attr));
        }
    }

    public String getClause() { return clause; }

    public ArrayList<Variable> getVariables() { return variables; }

    @Override
    public String toString() {
        return "Clause{\n" +
                "clause='" + clause + '\'' +'\n'+
                ", variables=" + variables + '\n' +
                '}';
    }
}
