package co.esprit.bemymenuapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.esprit.bemymenuapp.Model.Category;

public class CategorieBDD {

    private static final int VERSION_BDD = 1;
    protected static final String NOM_BDDCategorie = "categorie.db";
    protected static final String TABLE_Categorie = "table_categorie";
    protected static final String COL_IDC = "ID_Cat";
    private static final int NUM_COL_IDC = 0;
    protected static final String COL_NOMCAT= "nom_cat";
    private static final int NUM_COL_NOMCAT = 1;
    protected static final String COL_CATIMAGE = "image";
    private static final int NUM_COL_CATIMAGE = 2;
    /*
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
    @SerializedName("produits")
 */
    private SQLiteDatabase bdd;

    private MySqlLiteDatabase msld;

    public CategorieBDD(Context context){
        //On crée la BDD et sa table
        msld = new MySqlLiteDatabase(context, NOM_BDDCategorie, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = msld.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }
    public void upgradeBDD(){ msld.onUpgrade(bdd,0,1);}
    public long insertCategorie(Category category){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_IDC,category.getId());
        values.put(COL_NOMCAT, category.getNom());
        values.put(COL_CATIMAGE, category.getImage());


        //System.out.println("hahhahahahahah");

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_Categorie, null, values);
    }


    public int updateContact(int id,Category category){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_NOMCAT, category.getNom());
        values.put(COL_CATIMAGE, category.getImage());


        return bdd.update(TABLE_Categorie, values, COL_IDC + " = " +id, null);
    }


    public List<Category> getAllCategorie(){

        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Categorie;


        Cursor cursor = bdd.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId((cursor.getString(NUM_COL_IDC)));
                category.setNom(cursor.getString(NUM_COL_NOMCAT));
                category.setImage(cursor.getString(NUM_COL_CATIMAGE));


                System.out.println("hihihihihihihih");



                categoryList.add(category);
            } while (cursor.moveToNext());
        }

        // return contact list
        return categoryList;
    }


      /*
      int i=0;
        String[] produitId={};

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                produitId[i]=cursor.getString(0);
     */






    //Cette méthode permet de convertir un cursor en un livre
    private Category cursorToCategory(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        Category category = new Category();
        category.setId(c.getString(NUM_COL_IDC));
        category.setNom(c.getString(NUM_COL_NOMCAT));
        category.setImage(c.getString(NUM_COL_CATIMAGE));


        c.close();
        return category;
    }
}