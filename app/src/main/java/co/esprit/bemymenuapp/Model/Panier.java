package co.esprit.bemymenuapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by bassem on 09/12/2016.
 */

public class Panier implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("numTable")
    private String table;
    @SerializedName("produit")
    private String produit;
    @SerializedName("qte")
    private String qte;
public Panier(){

}
    public Panier(String id, String table, String produit, String qte) {
        this.id = id;
        this.table = table;
        this.produit = produit;
        this.qte = qte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id='" + id + '\'' +
                ", table='" + table + '\'' +
                ", produit='" + produit + '\'' +
                ", qte='" + qte + '\'' +
                '}';
    }
}
