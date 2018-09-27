package co.esprit.bemymenuapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.esprit.bemymenuapp.Adapters.PanierAdapter;
import co.esprit.bemymenuapp.DB.MenuBDD;
import co.esprit.bemymenuapp.DB.ProduitBDD;
import co.esprit.bemymenuapp.Model.Menu;
import co.esprit.bemymenuapp.Model.Produit;

public class PanierActivity extends AppCompatActivity  {
    SharedPreferences pf;
    GridView lv ;
    ImageView iv ;
    ProduitBDD produitBDD;
    RelativeLayout relativeLayout;
    public static   MenuItem menuItemTotal;
    private TextView resultText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);


        //create the drawer and remember the `Drawer` result object
        //getMenu();

        lv=  (GridView) findViewById(R.id.listView);
        iv = (ImageView) findViewById(R.id.avatar);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_panier);
        MenuBDD menuBDD=new MenuBDD(getApplicationContext());
        menuBDD.open();

        Menu m =  menuBDD.getMenu();

        if(m!=null)
        {



            if (m.getTemplate().equals("template1")) {


                relativeLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_blur));



            } else if (m.getTemplate().equals("template2")) {

                if (m.getTheme().equals("1")) {

                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.theme1_background));

                } else if (m.getTheme().equals("2")) {
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.theme2_background));


                } else if (m.getTheme().equals("3")) {

                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.theme3_background));
                }

            }






        }
        else{
            System.out.println("mdrrrr");
        }
        produitBDD = new ProduitBDD(this);

        List<Produit> p = getProductPanier();

        PanierAdapter adapter = new PanierAdapter(this,R.layout.list_item,p);


        lv.setAdapter(adapter);

        //Toast.makeText(this, p.toString(), Toast.LENGTH_SHORT).show();



    }


    public ArrayList<Produit> getProductPanier() {
        produitBDD = new ProduitBDD(getBaseContext());
        produitBDD.open();
        List<Produit>  lp = produitBDD.getAllProduit();
        pf = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor e = pf.edit();
        ArrayList<Produit> lpPanier = new ArrayList<Produit>();
        for (Produit produit : lp) {


            if (!pf.getString(produit.getNom().toString(), "").equals("")) {

                lpPanier.add(produit);
            }

        }
        return lpPanier;
    }

    public Float getProductPanierTotal() {
        produitBDD = new ProduitBDD(getBaseContext());
        produitBDD.open();
        List<Produit>  lp = produitBDD.getAllProduit();
        pf = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);

        Float total=0f;
        for (Produit produit : lp) {
            if (!pf.getString(produit.getNom().toString(), "").equals("")) {
                total+=Float.parseFloat(produit.getPrix())*Integer.parseInt(pf.getString(produit.getNom(),""));
                System.out.println("ti haw el produit "+produit);
            }
        }
        return total;
    }



    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.panier_menu, menu);
        TextView total;
        menuItemTotal   = (MenuItem) menu.findItem(R.id.total);
        menuItemTotal.setTitle(""+getProductPanierTotal());
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sp = this.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        switch (item.getItemId()) {

            case R.id.done:

                Intent i=new Intent(this,QR.class);
                startActivity(i);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


}