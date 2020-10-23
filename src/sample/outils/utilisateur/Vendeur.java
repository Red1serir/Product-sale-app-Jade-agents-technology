package sample.outils.utilisateur;

public class Vendeur {
    private String nom;
    private String prenom;
    private String password;
    private String wilaya;
    private String adresse;
    private String tel;

    public Vendeur(String nom, String prenom, String password, String wilaya, String adresse, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.wilaya = wilaya;
        this.adresse = adresse;
        this.tel = tel;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPassword() {
        return password;
    }

    public String getWilaya() {
        return wilaya;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
