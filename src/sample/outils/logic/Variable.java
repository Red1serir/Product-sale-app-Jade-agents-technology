package sample.outils.logic;

public class Variable implements Comparable<Variable>{
    private String variable;
    private String nom;
    private String val;
    private String condition;


    public Variable(String variable) {
        this.variable = variable;
        this.condition = getCond(variable);

        String[] spliteAttr = variable.split(condition);
        if (spliteAttr.length == 2){
            this.nom = spliteAttr[0];
            this.val = spliteAttr[1];
        }else{
            System.out.println("error length != 2");
        }

    }
    public Variable(String nom,String valeur) {
        this.variable = nom+"="+valeur;
        this.condition ="=";
        this.nom = nom;
        this.val = valeur;
    }

    public String getNom() {
        return nom;
    }

    public String getVal() { return val; }

    public String getCondition() { return condition; }


    public void setVal(String val) {
        this.val = val;
    }

    private String getCond(String attr){
        String condition = "";
        String[] spliteAttr = new String[2];
        if (attr.contains("="))
            condition = "=";
        else if (attr.contains("!="))
            condition = "!=";
        else if (attr.contains("<="))
            condition = "<=";
        else if (attr.contains(">="))
            condition = ">=";
        else if (attr.contains("<"))
            condition = "<";
        else if (attr.contains(">"))
            condition = "!=";

        return condition;
    }

    public String getVariable() {
        return variable;
    }

    public boolean isVerifier(BaseFait baseFait){
        Variable variable = baseFait.search(this.nom);
        System.out.println(this.nom+" "+this.val);
        if (this.nom.equals("processeur") && this.val.equals("core i5"))
            System.out.println(variable.getVal().equals(this.val));
        if (this.nom.equals("marque") && this.val.equals("asus"))
            System.out.println(variable.getVal().equals(this.val));

        if (variable != null) {
            switch (this.condition) {
                case "=":
                    return variable.getVal().replace(" ","").equals(this.val.replace(" ",""));
                case "!=":
                    return !variable.getVal().replace(" ","").equals(this.val);
                case ">":
                    return Float.parseFloat(variable.getVal()) > Float.parseFloat(this.val);
                case ">=":
                    return Float.parseFloat(variable.getVal()) >=  Float.parseFloat(this.val);
                case "<":
                    return Float.parseFloat(variable.getVal()) < Float.parseFloat(this.val);
                case "<=":
                    return Float.parseFloat(variable.getVal()) <=  Float.parseFloat(this.val);
                default: return false;
            }
        }
        return false;
    }


    @Override
    public int compareTo(Variable variable) {
        return this.nom.compareTo(variable.getNom());
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Variable var = (Variable) obj;
            return this.nom.equals(var.getNom()) && this.condition.equals(var.getCondition()) && this.val.equals(var.getVal());
        }catch (Exception e){
            return false;
        }
    }

    public boolean in(BaseFait baseFait){
        for (Variable variable : baseFait.getBaseFaits()) {
            if (this.equals(variable)){
                return true;
            }
        }
        return false;
    }




    @Override
    public String toString() {
        return "Variable{" +
                "nom='" + nom + '\'' +
                ", val=" + val +
                '}';
    }
}
