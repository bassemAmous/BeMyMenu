package co.esprit.bemymenuapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bassem on 30/11/2016.
 */
public class Category {
    @SerializedName("id")
    private   String id;
    @SerializedName("Nom")
    private   String nom;
    @SerializedName("Image")
    private   String image;

    public Category() {

    }
    public Category(String id, String nom, String image) {
        this.id = id;
        this.nom = nom;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
