package co.esprit.bemymenuapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by bof on 06/11/2016.
 */

public class Produit implements Serializable {
    public Produit()
    {

    }
    @SerializedName("id")
    private String id;

    @SerializedName("Nom")
    private String Nom;
    @SerializedName("Image")
    private String Image;
    @SerializedName("Prix")
    private String Prix;
    @SerializedName("Description")
    private String Description;
    @SerializedName("Categorie")
    private String Categorie;
    private String qte;
    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id='" + id + '\'' +
                ", Nom='" + Nom + '\'' +
                ", Image='" + Image + '\'' +
                ", Prix='" + Prix + '\'' +
                ", Description='" + Description + '\'' +
                ", Categorie='" + Categorie + '\'' +
                ", qte='" + qte + '\'' +
                '}';
    }
}
