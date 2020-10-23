package sample.outils.object;

import java.util.ArrayList;

public class ObjetCommercial {
    private float prixProduit;
    private String nomProduit;
    private String nom;
    private ArrayList<CaracteristiqueObjet> listCaracteristique;

    public ObjetCommercial(String nom) {
        this.nom = nom;
        this.listCaracteristique = new ArrayList<>();
    }

    public ObjetCommercial(String nom, ArrayList<CaracteristiqueObjet> listCaracteristique) {
        this.nom = nom;
        this.listCaracteristique = listCaracteristique;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<CaracteristiqueObjet> getListCaracteristique() {
        return listCaracteristique;
    }

    public CaracteristiqueObjet searchCaracteristique(String caracteristique){
        for (CaracteristiqueObjet caracteristiqueObjet : listCaracteristique){
            if (caracteristiqueObjet.getCaracteristique().equals(caracteristique)){
                return caracteristiqueObjet;
            }
        }
        return null;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setListCaracteristique(ArrayList<CaracteristiqueObjet> listCaracteristique) {
        this.listCaracteristique = listCaracteristique;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ObjetCommercial) {
            ObjetCommercial objetCommercial = (ObjetCommercial) obj;
            return this.nom.equals(objetCommercial.getNom());
        }
        return false;
    }

    public void add(CaracteristiqueObjet caracteristiqueObjet){
        if (!listCaracteristique.contains(caracteristiqueObjet)){
            listCaracteristique.add(caracteristiqueObjet);
        }
    }

    @Override
    public String toString() {
        return "ObjetCommercial{" +
                "nom='" + nom + '\'' +
                ", listCaracteristique=" + listCaracteristique +
                '}';
    }

    public float getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(float prixProduit) {
        this.prixProduit = prixProduit;
    }
}

