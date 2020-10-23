package sample.outils.logic;


import java.util.ArrayList;
import sample.outils.TablesView.RegleTable;

public class BaseConnaissance {

    private ArrayList<Regle> based;

    public BaseConnaissance(ArrayList<Regle> based) {
        this.based = based;
    }


    public BaseConnaissance(javafx.collections.ObservableList<RegleTable> based)  {
        this.based = new ArrayList<>();
        try {
            for (RegleTable regleTable : based) {
                this.based.add(new Regle(regleTable.getRegleText()));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public BaseConnaissance() {
        this.based = new ArrayList<>();
    }

    public void add(Regle regle){
        if (!based.contains(regle)){
            based.add(regle);
        }
    }
    public ArrayList<Regle> getBased() {
        return based;
    }

    public void reSet(){
        for (Regle regle : based){
            regle.setMarque(false);
        }
    }

    @Override
    public String toString() {
        return "BaseConnaissance{" +
                "based=" + based +
                '}';
    }
}
