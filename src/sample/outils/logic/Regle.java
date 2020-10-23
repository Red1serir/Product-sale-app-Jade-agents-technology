package sample.outils.logic;

public class Regle {
    private String regle;
    private String etiq;
    private Antecedent antecedent;
    private Conclusion conclusion;
    private String fc;
    private boolean marque;

    public Regle(String regle) throws Exception{
        this.regle = regle;
        this.marque = false;
        String[] spliteRegle = regle.replace("IF","").replace(" ","").split("THEN");
        if (spliteRegle.length == 2){
            String[] splitAntecedent = spliteRegle[0].split(":");
            if (splitAntecedent.length==2) {
                this.etiq = splitAntecedent[0];
                this.antecedent = new Antecedent(splitAntecedent[1]);
            }
            if (spliteRegle[1].contains("WITH")) {
                String[] splitConclusion = spliteRegle[1].split("WITH");
                this.conclusion = new Conclusion(splitConclusion[0]);
                this.fc = splitConclusion[1].replace("CF:","");
            } else {
                this.conclusion = new Conclusion(spliteRegle[1]);
            }
        }else{
            throw new Exception("error Regel : length != 2");
        }
    }

    public String getRegle() {
        return regle;
    }

    public Antecedent getAntecedent() {
        return antecedent;
    }

    public Conclusion getConclusion() {
        return conclusion;
    }

    public String getEtiq() {
        return etiq;
    }

    public boolean isMarque() { return marque; }

    public void setMarque(boolean marque) { this.marque = marque; }

    public boolean conclu(Conclusion conclusion){
        return this.conclusion.equals(conclusion);
    }

    public boolean applicable(BaseFait baseFait){
        for (Clause clause : this.antecedent.getClauses()){
            for (Variable variable : clause.getVariables()){
                if(!variable.isVerifier(baseFait)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Regle{" +
                "regle='" + regle + '\'' +
                '}';
    }
}
