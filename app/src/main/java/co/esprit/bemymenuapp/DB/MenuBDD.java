package co.esprit.bemymenuapp.DB;

/**
 * Created by bof on 12/10/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.esprit.bemymenuapp.Model.Menu;

public class MenuBDD {

    protected static final int VERSION_BDD = 1;
    protected static final String NOM_BDD = "menus.db";

    protected static final String TABLE_MENU = "table_menus";
    protected static final String COL_ID = "ID";
    protected static final int NUM_COL_ID = 0;
    protected static final String COL_LOGIMG= "logimg";
    protected static final int NUM_COL_LOGIMG = 1;
    protected static final String COL_LOGOEMP = "logemp";
    protected static final int NUM_COL_LOGOEMP = 2;
    protected static final String COL_TITRETXT = "titretxt";
    protected static final int NUM_COL_TITRETXT = 3;
    protected static final String COL_TITREEMP = "titreemp";
    protected static final int NUM_COL_TITREMP = 4;
    protected static final String COL_CELLTYPE = "celltype";
    protected static final int NUM_COL_CELLTYPE = 5;
    protected static final String COL_TEMPLATE = "template";
    protected static final int NUM_COL_TEMPLATE = 7;
    protected static final String COL_THEME = "theme";
    protected static final int NUM_COL_THEME = 6;
    private SQLiteDatabase bdd;

    private MySqlLiteDatabase msld;

    public MenuBDD(Context context){
        //On crée la BDD et sa table
        msld = new MySqlLiteDatabase(context, NOM_BDD, null, VERSION_BDD);
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
    public void upgradeBDD(){
        msld.onUpgrade(bdd,0,1);
    }
    public long insertMenu(Menu menu){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_LOGIMG, menu.getLogimg());
        values.put(COL_LOGOEMP, menu.getLogoemp());
        values.put(COL_CELLTYPE, menu.getCelltype());
        values.put(COL_TEMPLATE, menu.getTemplate());
        values.put(COL_TITRETXT, menu.getTitretxt());
        values.put(COL_THEME, menu.getTheme());
        values.put(COL_TITREEMP, menu.getTitreemp());


        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_MENU, null, values);
    }


    public int updateMenu(int id,Menu menu){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_LOGIMG, menu.getLogimg());
        values.put(COL_LOGOEMP, menu.getLogoemp());
        values.put(COL_CELLTYPE, menu.getCelltype());
        values.put(COL_TEMPLATE, menu.getTemplate());
        values.put(COL_TITRETXT, menu.getTitretxt());
        values.put(COL_THEME, menu.getTheme());
        values.put(COL_TITREEMP, menu.getTitreemp());
        return bdd.update(TABLE_MENU, values, COL_ID + " = " +id, null);
    }

    public int removeMenuWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_MENU, COL_ID + " = " +id, null);
    }

    public Menu getMenuWithNom(String titre){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_MENU, new String[] {COL_ID, COL_LOGIMG, COL_LOGOEMP,COL_TEMPLATE,COL_THEME,COL_TITREEMP,COL_TITRETXT}, COL_TITRETXT + " LIKE \"" + titre +"\"", null, null, null, null);
        return cursorToMenu(c);
    }
   public Menu getMenu(){

       Menu menu=new Menu();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MENU;


        Cursor cursor = bdd.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

       if( cursor.moveToFirst())



                // Adding contact to list
          // Menu(String id, String logimg, String logoemp, String titreemp, String titretxt, String celltype, String theme, String template) {

           return new Menu(cursor.getString(NUM_COL_ID),cursor.getString(NUM_COL_LOGIMG),cursor.getString(NUM_COL_LOGOEMP),
                    cursor.getString(NUM_COL_TITREMP),
                    cursor.getString(NUM_COL_TITRETXT),cursor.getString(NUM_COL_CELLTYPE),cursor.getString(NUM_COL_THEME),
                    cursor.getString(NUM_COL_TEMPLATE));
       return null;
    }
    //Cette méthode permet de convertir un cursor en un livre
    private Menu cursorToMenu(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        Menu menu = new Menu();
        menu.setId(c.getString(NUM_COL_ID));
        menu.setCelltype(c.getString(NUM_COL_CELLTYPE));
        menu.setLogimg(c.getString(NUM_COL_LOGIMG));
        menu.setTitretxt(c.getString(NUM_COL_TITRETXT));

        c.close();
        return menu;
    }
}
