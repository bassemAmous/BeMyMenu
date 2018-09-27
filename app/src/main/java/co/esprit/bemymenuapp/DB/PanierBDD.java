package co.esprit.bemymenuapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import co.esprit.bemymenuapp.Model.Panier;

/**
 * Created by Bassem on 13/11/2016.
 */

public class PanierBDD {

    private static final int VERSION_BDD = 1;
    protected static final String NOM_BDDPanier = "panier.db";

    protected static final String TABLE_PANIER = "table_panier";
    protected static final String COL_IDP = "ID";
    private static final int NUM_COL_IDP = 0;
    protected static final String COL_NUMTABLE= "tablep";
    private static final int NUM_COL_NUMTABLE = 1;
    protected static final String COL_PRODUITP = "produit";
    private static final int NUM_COL_PRODUITP = 2;
    protected static final String COL_QUANTITE = "quantite";
    private static final int NUM_COL_QUANTITE = 3;

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

    public PanierBDD(Context context){
        //On crée la BDD et sa table
        msld = new MySqlLiteDatabase(context, NOM_BDDPanier, null, VERSION_BDD);
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
    public long insertPanier(Panier panier){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_IDP,panier.getId());
        values.put(COL_NUMTABLE, panier.getTable());
        values.put(COL_PRODUITP, panier.getProduit());
        values.put(COL_QUANTITE, panier.getQte());

        System.out.println("hahhahahahahah");

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_PANIER, null, values);
    }


    public int updateContact(int id,Panier panier){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_NUMTABLE, panier.getTable());
        values.put(COL_PRODUITP, panier.getProduit());
        values.put(COL_QUANTITE, panier.getQte());

        return bdd.update(TABLE_PANIER, values, COL_IDP + " = " +id, null);
    }

    public int removeProductsWithTable(String numTable){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_PANIER, COL_NUMTABLE + " = " +numTable, null);
    }

    public Panier getPaniertWithProduit(String nom){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_PANIER, new String[] {COL_IDP, COL_NUMTABLE, COL_PRODUITP,COL_QUANTITE}, COL_PRODUITP + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToPanier(c);
    }

    public List<Panier> getAllPanier(){

        List<Panier> paniersList = new ArrayList<Panier>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PANIER;


        Cursor cursor = bdd.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Panier panier = new Panier();
                panier.setId((cursor.getString(NUM_COL_IDP)));
                panier.setTable(cursor.getString(NUM_COL_NUMTABLE));
                panier.setProduit(cursor.getString(NUM_COL_PRODUITP));
                panier.setQte(cursor.getString(NUM_COL_QUANTITE));

                System.out.println("hihihihihihihih");



                paniersList.add(panier);
            } while (cursor.moveToNext());
        }

        // return contact list
        return paniersList;


    }


      /*
      int i=0;
        String[] produitId={};

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                produitId[i]=cursor.getString(0);
     */



    public List<Integer> getAllPanierWithTable(String nomtab){

        List<Integer> paniersList = new ArrayList<Integer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PANIER+" WHERE "+COL_NUMTABLE+" = "+nomtab;


        Cursor cursor = bdd.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Panier panier = new Panier();
                panier.setId((cursor.getString(NUM_COL_IDP)));
                panier.setTable(cursor.getString(NUM_COL_NUMTABLE));
                panier.setProduit(cursor.getString(NUM_COL_PRODUITP));
                panier.setQte(cursor.getString(NUM_COL_QUANTITE));

                System.out.println("hihihihihihihih");



                paniersList.add(Integer.parseInt(cursor.getString(NUM_COL_PRODUITP)));
            } while (cursor.moveToNext());
        }

        // return contact list
        return paniersList;
    }
    public List<Panier> getAllDistinctTable(){
        List<Panier> paniersList = new ArrayList<Panier>();
        // Select All Query
     //   String selectQuery = "SELECT  Distinct "+COL_NUMTABLE+" , "+COL_QUANTITE +" FROM " + TABLE_PANIER;
       // Cursor cursor = bdd.rawQuery(selectQuery, null);
        Cursor cursor = bdd.query(true, TABLE_PANIER,new String[] {COL_IDP, COL_NUMTABLE, COL_PRODUITP,COL_QUANTITE}, null, null, COL_NUMTABLE, null, null,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Panier panier = new Panier();
                panier.setId(cursor.getString(NUM_COL_IDP));
                panier.setTable(cursor.getString(NUM_COL_NUMTABLE));
                panier.setQte(cursor.getString(NUM_COL_QUANTITE));


                paniersList.add(panier);
            } while (cursor.moveToNext());
        }
        System.out.println("kakakaakd"+paniersList);
        // return contact list
        return paniersList;


    }


    //Cette méthode permet de convertir un cursor en un livre
    private Panier cursorToPanier(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        Panier panier = new Panier();
        panier.setId(c.getString(NUM_COL_IDP));
        panier.setTable(c.getString(NUM_COL_NUMTABLE));
        panier.setProduit(c.getString(NUM_COL_PRODUITP));
        panier.setQte(c.getString(NUM_COL_QUANTITE));

        c.close();
        return panier;
    }
}