package sample.outils.logic;

public class Conclusion {
    private String conclusion;
    private Variable variable;

    public Conclusion(String conclusion) {
        this.conclusion = conclusion;
        this.variable = new Variable(conclusion);
    }

    public String getConclusion() {
        return conclusion;
    }

    public Variable getVariable() {
        return variable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Conclusion){
            if (
                    this.variable.getNom().equals(((Conclusion)obj).getVariable().getNom()) &&
                            this.variable.getCondition().equals(((Conclusion)obj).getVariable().getCondition()) &&
                            this.variable.getVal().equals(((Conclusion)obj).getVariable().getVal())
            ){
                return true;
            }
        }
        return false;
    }
}
