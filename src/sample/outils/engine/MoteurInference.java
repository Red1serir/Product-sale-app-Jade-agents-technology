package sample.outils.engine;

import javafx.scene.transform.Translate;
import sample.outils.logic.*;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

public class MoteurInference {
    private String nom;
    private BaseConnaissance baseConnaissance;
    private BaseFait baseFait;
    private Conclusion but;

    private String logs;
    public MoteurInference(String nom, BaseConnaissance baseConnaissance, BaseFait baseFait) {
        this.nom = nom;
        this.baseConnaissance = baseConnaissance;
        this.baseFait = baseFait;
        this.logs = "";
    }
    public MoteurInference(BaseConnaissance baseConnaissance, BaseFait baseFait) {
        this.baseConnaissance = baseConnaissance;
        this.baseFait = baseFait;
        this.logs = "";
    }
    public MoteurInference(HashMap<String,String> query, BaseConnaissance baseConnaissance) {
        HashMap<String,String> queryClone = (HashMap<String, String>) query.clone();

        this.nom = queryClone.get("category");

        this.baseFait = new BaseFait();
        for (String key : queryClone.keySet()){
            if (!key.equals("category")) this.baseFait.add(new Variable(key,queryClone.get(key)));
        }

        System.out.println(baseFait);
        this.baseConnaissance = baseConnaissance;
        this.logs = "";

    }
    public MoteurInference() {
        this.baseConnaissance = new BaseConnaissance();
        this.baseFait = new BaseFait();
        this.logs = "";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BaseConnaissance getBaseConnaissance() {
        return baseConnaissance;
    }

    public BaseFait getBaseFait() {
        return baseFait;
    }

    public Conclusion getBut() {
        return but;
    }

    public String getLogs() { return logs; }

    public void setBaseConnaissance(BaseConnaissance baseConnaissance) {
        this.baseConnaissance = baseConnaissance;
    }

    public void setBaseFait(BaseFait baseFait) {
        this.baseFait = baseFait;
    }

    public void setLogs(String logs) { this.logs = logs; }

    public void setBut(Conclusion but) { this.but = but; }

    private ArrayList<Regle> selectRegleApplicable(){
        ArrayList<Regle> regles = new ArrayList<>();
        System.out.println(this.baseConnaissance);
        for (Regle regle : this.baseConnaissance.getBased()) {
            if (!regle.isMarque()){
               if (regle.applicable(this.baseFait)){
                   regle.setMarque(true);
                   regles.add(regle);
               }
            }
        }
        return regles;
    }


    private ArrayList<Regle> selectAllRegleConclu(Conclusion conclusion){
        ArrayList<Regle> regles = new ArrayList<>();

        for (Regle regle : this.baseConnaissance.getBased()){
            if (!regle.isMarque()){
                if (regle.conclu(conclusion)){
                    regle.setMarque(true);
                    regles.add(regle);
                }
            }
        }
        return regles;
    }

    public BaseFait forwardChain(){
        do {
            ArrayList<Regle> regleApplicable = selectRegleApplicable();
            if (!regleApplicable.isEmpty()){
                System.out.println(regleApplicable);
                for (Regle regle:regleApplicable) {
                    this.baseFait.getBaseFaits().add(regle.getConclusion().getVariable());
                    System.out.println("base fait added : " + this.baseFait);
                }
            }else {
                break;
            }
        }while (true);

        this.baseConnaissance.reSet(); // marque les regle à faux

        return this.baseFait;
    }

    public BaseFait backwardChain(Conclusion but) {

        ArrayList<Variable> goals = new ArrayList<>();
        goals.add(but.getVariable());

        while (!goals.isEmpty()) {

            Variable buAPreuver = goals.remove(goals.size()-1);

            if (!buAPreuver.in(this.baseFait)) {
                ArrayList<Regle> regles = this.selectAllRegleConclu(but);           //regles selectionnner
                if (regles != null){// regles non selectionner
                    for (Regle regle : regles) {
                        if (regle.applicable(this.baseFait)){
                            baseFait.getBaseFaits().add(buAPreuver);                    //la regle est applicable meter le but à prever dans la base de fait
                            System.out.println("base fait added : "+this.baseFait);
                        }else {
                                 goals.addAll(regle.getAntecedent().getVariables());           //la regle n'est pas applicable meter les antecedant non preuver dan l'enssemble de but
                        }
                    }
                }
            }


        }

        return this.baseFait;
    }

    public String backward_Chain(Conclusion but) {
        String string = "Voici notre but : \n\t" + but + "\n";
        string += "En partant des faits de base suivant : \n";
        for(Variable var : this.baseFait.getBaseFaits()) {
            string += "\n\t" + var.getNom() + " - " + var.getVal()+"\n";
        }
        string += "\n";
        ArrayList<Regle> regleApplicable = selectRegleApplicable();
        boolean modification = true;
        while (modification && !regleApplicable.isEmpty() && (but == null || but.getVariable().in(this.baseFait))){
            modification = false;
            for (Regle regle : regleApplicable) {
                string += "\nAvons nous appliqué la règle " + regle + " ?";
                if (regle.getConclusion().getVariable().in(this.baseFait)){
                    string += "\n\tOui !";
                    modification = true;
                    regle.setMarque(false);
                    for (Variable variable : regle.getAntecedent().getVariables()){
                        string += "\n\tAjout de " + variable + " à la liste de faits\n";
                        baseFait.add(variable);
                    }
                }else {
                    string += "\n\tNon!";
                }
                if (modification){
                    break;
                }
            }
        }
        string += "\n\nfin du chainage arriere";
        if(but.getVariable().in(this.baseFait)){
            string += "\nLe but est atteint!";
        }

        return string;
    }
}
