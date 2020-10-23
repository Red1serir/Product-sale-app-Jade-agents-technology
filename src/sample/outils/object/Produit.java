package sample.outils.object;

public class Produit {

    String nom;
    String typeObjet;
    String date;
    String prix;

    public Produit(String nom, String typeObjet, String date, String prix) {
        this.nom = nom;
        this.typeObjet = typeObjet;
        this.date = date;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public String getTypeObjet() {
        return typeObjet;
    }

    public String getDate() {
        return date;
    }

    public String getPrix() {
        return prix;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTypeObjet(String typeObjet) {
        this.typeObjet = typeObjet;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}
