package co.esprit.bemymenuapp.DB;

/**
 * Created by bof on 12/10/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import static co.esprit.bemymenuapp.DB.MenuBDD.COL_CELLTYPE;
import static co.esprit.bemymenuapp.DB.MenuBDD.COL_LOGIMG;
import static co.esprit.bemymenuapp.DB.MenuBDD.COL_LOGOEMP;
import static co.esprit.bemymenuapp.DB.MenuBDD.COL_TEMPLATE;
import static co.esprit.bemymenuapp.DB.MenuBDD.COL_THEME;
import static co.esprit.bemymenuapp.DB.MenuBDD.COL_TITREEMP;
import static co.esprit.bemymenuapp.DB.MenuBDD.COL_TITRETXT;
import static co.esprit.bemymenuapp.DB.MenuBDD.TABLE_MENU;
import static co.esprit.bemymenuapp.DB.PanierBDD.COL_NUMTABLE;

import static co.esprit.bemymenuapp.DB.PanierBDD.COL_PRODUITP;
import static co.esprit.bemymenuapp.DB.PanierBDD.COL_QUANTITE;
import static co.esprit.bemymenuapp.DB.PanierBDD.TABLE_PANIER;
import static co.esprit.bemymenuapp.DB.ProduitBDD.COL_DESCRIPTION;
import static co.esprit.bemymenuapp.DB.ProduitBDD.COL_IMAGE;
import static co.esprit.bemymenuapp.DB.ProduitBDD.COL_NOM;
import static co.esprit.bemymenuapp.DB.ProduitBDD.COL_PRIX;
import static co.esprit.bemymenuapp.DB.ProduitBDD.TABLE_PRODUIT;
import static co.esprit.bemymenuapp.DB.ProduitBDD.COL_CATEGORIE;
import static co.esprit.bemymenuapp.DB.CategorieBDD.*;


 public class MySqlLiteDatabase extends SQLiteOpenHelper {
    private static final String CREATE_BDD_MENU = "CREATE TABLE " + TABLE_MENU + " ("
            + MenuBDD.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_LOGIMG + " TEXT NOT NULL, "
            + COL_LOGOEMP + " TEXT NOT NULL," + COL_TITREEMP + " TEXT NOT NULL," + COL_TITRETXT +
            " TEXT NOT NULL,"+ COL_CELLTYPE + " TEXT NOT NULL,"+COL_THEME +" TEXT NOT NULL,"+COL_TEMPLATE+" TEXT NOT NULL);";

    private static final String CREATE_BDD_PRODUIT = "CREATE TABLE " + TABLE_PRODUIT + " ("
            + ProduitBDD.COL_ID + " TEXT NOT NULL , " + COL_NOM + " TEXT NOT NULL, "
            + COL_IMAGE + " TEXT NOT NULL, " + COL_PRIX + " TEXT NOT NULL, " + COL_DESCRIPTION +
     " TEXT NOT NULL, "+COL_CATEGORIE+" TEXT NOT NULL);";
     private static final String CREATE_BDD_Category = "CREATE TABLE " + TABLE_Categorie + " ("
             + CategorieBDD.COL_IDC + " TEXT NOT NULL , " + CategorieBDD.COL_NOMCAT +" , "+CategorieBDD.COL_CATIMAGE+" TEXT NOT NULL);";
     private static final String CREATE_BDD_PANIER = "CREATE TABLE " + TABLE_PANIER + " ("
             + PanierBDD.COL_IDP + " TEXT NOT NULL , " + COL_NUMTABLE + " TEXT NOT NULL, "
             + COL_PRODUITP + " TEXT NOT NULL, " + COL_QUANTITE+" TEXT NOT NULL);";
     /*
    protected static final String TABLE_PANIER = "table_panier";
    protected static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    protected static final String COL_NUMTABLE= "table";
    private static final int NUM_COL_NUMTABLE = 1;
    protected static final String COL_PRODUIT = "produit";
    private static final int NUM_COL_PRODUIT = 2;
    protected static final String COL_QUANTITE = "quantite";
    private static final int NUM_COL_QUANTITE = 3;
 */



    public MySqlLiteDatabase(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        System.out.println(CREATE_BDD_Category+" categoryokokok ");
        System.out.println("versssiiiiiiioon"+version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_BDD_MENU);
        db.execSQL(CREATE_BDD_PRODUIT);
        db.execSQL(CREATE_BDD_PANIER);
        db.execSQL(CREATE_BDD_Category);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + TABLE_PRODUIT + ";");
        db.execSQL("DROP TABLE " + TABLE_MENU + ";");
        db.execSQL("DROP TABLE " + TABLE_PANIER + ";");
        db.execSQL("DROP TABLE " + TABLE_Categorie + ";");
        System.out.println("loooool");
        System.out.println(oldVersion+" new "+newVersion);
        onCreate(db);

    }
}
