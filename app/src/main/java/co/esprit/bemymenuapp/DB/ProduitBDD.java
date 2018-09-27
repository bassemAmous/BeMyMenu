package co.esprit.bemymenuapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.esprit.bemymenuapp.Model.Produit;

/**
 * Created by Bassem on 13/11/2016.
 */

public class ProduitBDD {

    private static final int VERSION_BDD = 1;
    protected static final String NOM_BDDProd = "produits.db";

    protected static final String TABLE_PRODUIT = "table_produits";
    protected static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    protected static final String COL_NOM= "nom";
    private static final int NUM_COL_NOM = 1;
    protected static final String COL_IMAGE = "image";
    private static final int NUM_COL_IMAGE = 2;
    protected static final String COL_PRIX = "prix";
    private static final int NUM_COL_PRIX = 3;
    protected static final String COL_DESCRIPTION = "description";
    private static final int NUM_COL_DESCRIPTION = 4;
    protected static final String COL_CATEGORIE = "categorie";
    private static final int NUM_COL_CATEGORIE= 5;
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

    public ProduitBDD(Context context){
        //On crée la BDD et sa table
        msld = new MySqlLiteDatabase(context, NOM_BDDProd, null, VERSION_BDD);
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
    public long insertProduit(Produit produit){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_ID,produit.getId());
        values.put(COL_DESCRIPTION, produit.getDescription());
        values.put(COL_IMAGE, produit.getImage());
        values.put(COL_NOM, produit.getNom());
        values.put(COL_PRIX, produit.getPrix());
        values.put(COL_CATEGORIE, produit.getCategorie());
        System.out.println("hahhahahahahah");

        System.out.println(COL_IMAGE+"  "+produit.getImage()+"  Prix :"+COL_PRIX+"  "+produit.getPrix());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_PRODUIT, null, values);
    }


    public int updateContact(int id,Produit produit){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_DESCRIPTION, produit.getDescription());
        values.put(COL_IMAGE, produit.getImage());
        values.put(COL_NOM, produit.getNom());
        values.put(COL_PRIX, produit.getPrix());
        values.put(COL_CATEGORIE, produit.getCategorie());
        return bdd.update(TABLE_PRODUIT, values, COL_ID + " = " +id, null);
    }
    public List<Produit> getProductsWithCategory(String category){

        List<Produit> produitsList = new ArrayList<Produit>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUIT + " WHERE "+COL_CATEGORIE+"='"+category+"'";

        Cursor cursor = bdd.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Produit produit = new Produit();
                produit.setId((cursor.getString(NUM_COL_ID)));
                produit.setNom(cursor.getString(NUM_COL_NOM));
                produit.setDescription(cursor.getString(NUM_COL_DESCRIPTION));
                produit.setImage(cursor.getString(NUM_COL_IMAGE));
                produit.setPrix(cursor.getString(NUM_COL_PRIX));
                produit.setCategorie(cursor.getString(NUM_COL_CATEGORIE));
                System.out.println("hihihihihihihih");

                System.out.println(NUM_COL_IMAGE+"  "+cursor.getString(NUM_COL_IMAGE)+"  Prix :"+NUM_COL_PRIX+"  "+cursor.getString(NUM_COL_PRIX));

                produitsList.add(produit);
            } while (cursor.moveToNext());
        }

        return produitsList;

    }

    public int removeContactWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_PRODUIT, COL_ID + " = " +id, null);
    }

    public Produit getProduittWithTitre(String nom){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_PRODUIT, new String[] {COL_ID, COL_DESCRIPTION, COL_IMAGE,COL_NOM,COL_PRIX}, COL_NOM + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToProduit(c);
    }

    public Produit getProduittWithId(String id){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUIT+" WHERE id ='"+id+"'";
        System.out.println(selectQuery+"seleeeeeect");

        Cursor cursor = bdd.rawQuery(selectQuery, null);
        //System.out.println("produiiiiiiireeeeeeee"+cursorToProduit(c)+id);

        return cursorToProduit(cursor);
    }




      public List<Produit> getAllProduit(){

          List<Produit> produitsList = new ArrayList<Produit>();
          // Select All Query
          String selectQuery = "SELECT  * FROM " + TABLE_PRODUIT;


          Cursor cursor = bdd.rawQuery(selectQuery, null);

          // looping through all rows and adding to list
          if (cursor.moveToFirst()) {
              do {
                  Produit produit = new Produit();

                  produit.setId((cursor.getString(NUM_COL_ID)));
                  produit.setNom(cursor.getString(NUM_COL_NOM));
                  produit.setDescription(cursor.getString(NUM_COL_DESCRIPTION));
                  produit.setImage(cursor.getString(NUM_COL_IMAGE));
                  produit.setPrix(cursor.getString(NUM_COL_PRIX));
                  produit.setCategorie(cursor.getString(NUM_COL_CATEGORIE));
                  System.out.println("hihihihihihihih");

                  System.out.println(NUM_COL_IMAGE+"  "+cursor.getString(NUM_COL_IMAGE)+"  Prix :"+NUM_COL_PRIX+"  "+cursor.getString(NUM_COL_PRIX));

                  produitsList.add(produit);
              } while (cursor.moveToNext());
          }

          // return contact list
          return produitsList;


      }

    public List<Produit> getAllProduitLike(String nom){

        List<Produit> produitsList = new ArrayList<Produit>();
        // Select All Query
        String selectQuery = "SELECT  * FROM "+ TABLE_PRODUIT+" where "+COL_NOM+" LIKE '%"+nom+"%'" ;


        Cursor cursor = bdd.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Produit produit = new Produit();
                produit.setId((cursor.getString(NUM_COL_ID)));
                produit.setNom(cursor.getString(NUM_COL_NOM));
                produit.setDescription(cursor.getString(NUM_COL_DESCRIPTION));
                produit.setImage(cursor.getString(NUM_COL_IMAGE));
                produit.setPrix(cursor.getString(NUM_COL_PRIX));
                produit.setCategorie(cursor.getString(NUM_COL_CATEGORIE));
                System.out.println("hihihihihihihih");

                System.out.println(NUM_COL_IMAGE+"  "+cursor.getString(NUM_COL_IMAGE)+"  Prix :"+NUM_COL_PRIX+"  "+cursor.getString(NUM_COL_PRIX));

                produitsList.add(produit);
            } while (cursor.moveToNext());
        }
        System.out.println("ti haw el produit"+produitsList);
        // return contact list
        return produitsList;


    }

    public Produit getAllProduitByIds(int id){

        List<Produit> produitsList = new ArrayList<Produit>();
        // Select All Query
        String selectQuery = "SELECT  *  FROM " + TABLE_PRODUIT+" where "+COL_ID+" = '"+id+"'";
        System.out.println("mmmmmqmqmqmqm");
        System.out.println(selectQuery);

        Cursor cursor = bdd.rawQuery(selectQuery, null);
        Produit produit = new Produit();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                produit.setId((cursor.getString(NUM_COL_ID)));
                produit.setId((cursor.getString(NUM_COL_ID)));
                produit.setNom(cursor.getString(NUM_COL_NOM));
                produit.setDescription(cursor.getString(NUM_COL_DESCRIPTION));
                produit.setImage(cursor.getString(NUM_COL_IMAGE));
                produit.setPrix(cursor.getString(NUM_COL_PRIX));
                produit.setCategorie(cursor.getString(NUM_COL_CATEGORIE));
                System.out.println("hihihihihihihih");

                System.out.println(NUM_COL_IMAGE+"  "+cursor.getString(NUM_COL_IMAGE)+"  Prix :"+NUM_COL_PRIX+"  "+cursor.getString(NUM_COL_PRIX));


            } while (cursor.moveToNext());
        }

        // return contact list
        return produit;


    }





    //Cette méthode permet de convertir un cursor en un livre
    private Produit cursorToProduit(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
        {
            System.out.println("cursorhahahaha");     return null;}

        c.moveToFirst();
        Produit produit = new Produit();
        produit.setId(c.getString(NUM_COL_ID));
        produit.setNom(c.getString(NUM_COL_NOM));
        produit.setPrix(c.getString(NUM_COL_PRIX));
        produit.setDescription(c.getString(NUM_COL_DESCRIPTION));
        produit.setImage(c.getString(NUM_COL_IMAGE));
        produit.setCategorie(c.getString(NUM_COL_CATEGORIE));
        c.close();
        return produit;
    }
}