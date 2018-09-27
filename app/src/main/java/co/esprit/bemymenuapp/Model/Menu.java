package co.esprit.bemymenuapp.Model;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Menu {

    public Menu(String id, String logimg, String logoemp, String titreemp, String titretxt, String celltype, String theme, String template) {
        this.id = id;
        this.logimg = logimg;
        this.logoemp = logoemp;
        this.titreemp = titreemp;
        this.titretxt = titretxt;
        this.celltype = celltype;
        this.theme = theme;
        this.template = template;
    }
    public Menu(){

    }
    @SerializedName("id")
    private String id;

    @SerializedName("logimg")
    private String logimg;

    @SerializedName("logoemp")
    private String logoemp;

    @SerializedName("titreemp")
    private String titreemp;
    @SerializedName("titretxt")
    private String titretxt;

    @SerializedName("celltype")
    private String celltype;
    @SerializedName("theme")
    private String theme;
    @SerializedName("template")
    private String template;
    @SerializedName("produits")
    private List<Produit> listProduits;
    @SerializedName("categories")
    private List<Category> listCategories;
    @SerializedName("version")
    private int version;

    public List<Category> getListCategories() {
        return listCategories;
    }

    public void setListCategories(List<Category> listCategories) {
        this.listCategories = listCategories;
    }

    public List<Produit> getListProduits() {
        return listProduits;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setListProduits(List<Produit> listProduits) {
        this.listProduits = listProduits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogimg() {
        return logimg;
    }

    public void setLogimg(String logimg) {
        this.logimg = logimg;
    }

    public String getLogoemp() {
        return logoemp;
    }

    public void setLogoemp(String logoemp) {
        this.logoemp = logoemp;
    }


    public String getTitreemp() {
        return titreemp;
    }

    public void setTitreemp(String titreemp) {
        this.titreemp = titreemp;
    }

    public String getTitretxt() {
        return titretxt;
    }

    public void setTitretxt(String titretxt) {
        this.titretxt = titretxt;
    }

    public String getCelltype() {
        return celltype;
    }

    public void setCelltype(String celltype) {
        this.celltype = celltype;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", logimg='" + logimg + '\'' +
                ", logoemp='" + logoemp + '\'' +
                ", titreemp='" + titreemp + '\'' +
                ", titretxt='" + titretxt + '\'' +
                ", celltype='" + celltype + '\'' +
                ", theme='" + theme + '\'' +
                ", template='" + template + '\'' +
                ", listProduits=" + listProduits +
                '}';
    }
}