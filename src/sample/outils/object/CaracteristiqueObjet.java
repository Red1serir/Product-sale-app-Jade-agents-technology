package sample.outils.object;

import java.util.ArrayList;

public class CaracteristiqueObjet {

    private String caracteristique;
    private ArrayList<String> listeValeurs;
    private String valeur;


    public CaracteristiqueObjet(String caracteristique, ArrayList<String> listeValeurs) {
        this.caracteristique = caracteristique;
        this.listeValeurs = listeValeurs;
    }

    public CaracteristiqueObjet(String caracteristique,String valeur) {
        this.caracteristique = caracteristique;
        this.valeur = valeur;
    }

    public CaracteristiqueObjet(String caracteristique) {
        this.caracteristique = caracteristique;
        this.listeValeurs = new ArrayList<>();
    }
    public CaracteristiqueObjet() {
        this.caracteristique = "";
        this.listeValeurs = new ArrayList<>();
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getCaracteristique() {
        return caracteristique;
    }

    public ArrayList<String> getListeValeurs() {
        return listeValeurs;
    }

    public void setCaracteristique(String caracteristique) {
        this.caracteristique = caracteristique;
    }

    public void setListeValeurs(ArrayList<String> listeValeurs) {
        this.listeValeurs = listeValeurs;
    }

    public void add(String value){
        if (!listeValeurs.contains(value)){
            listeValeurs.add(value);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  CaracteristiqueObjet) {
            CaracteristiqueObjet caracteristiqueObjet = (CaracteristiqueObjet) obj;
            return this.caracteristique.equals(caracteristiqueObjet.getCaracteristique());
        }
        return false;
    }

    @Override
    public String toString() {
        return "CaracteristiqueObjet{" +
                "caracteristique='" + caracteristique + '\'' +
                ", listeValeurs=" + listeValeurs +
                '}';
    }
}
