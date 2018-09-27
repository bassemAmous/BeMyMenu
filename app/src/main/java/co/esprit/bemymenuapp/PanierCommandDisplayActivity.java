package co.esprit.bemymenuapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import co.esprit.bemymenuapp.Adapters.CommandDetailAdapter;
import co.esprit.bemymenuapp.DB.MenuBDD;
import co.esprit.bemymenuapp.DB.PanierBDD;
import co.esprit.bemymenuapp.DB.ProduitBDD;
import co.esprit.bemymenuapp.Model.Menu;
import co.esprit.bemymenuapp.Model.Produit;
import co.esprit.bemymenuapp.Service.APIService;

public class PanierCommandDisplayActivity extends AppCompatActivity {
    SharedPreferences pf;
    GridView lv ;
    ImageView iv ;
    ProduitBDD produitBDD;
    RelativeLayout relativeLayout;
    Intent i ;

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
        PanierBDD panierBDD=new PanierBDD(this);
        panierBDD.open();
        System.out.println("allllll"+panierBDD.getAllPanier()+"fin all");
        i=getIntent();

        List<Integer> produitIds=panierBDD.getAllPanierWithTable(i.getStringExtra("panier"));
        String qtee = i.getStringExtra("qte");
        produitBDD = new ProduitBDD(this);
        produitBDD.open();
        System.out.println("sldkslksdlkllkslkhahahah"+produitIds);
       String idProduitsPanierInString=produitIds.toString();
        idProduitsPanierInString= idProduitsPanierInString.replace("[","(");
        idProduitsPanierInString=idProduitsPanierInString.replace("]",")");
        idProduitsPanierInString=idProduitsPanierInString.replace(" ","");
        System.out.println("okkkkk");
        System.out.println(idProduitsPanierInString);
        List<Produit> p=new ArrayList<Produit>();
        for (int k:produitIds
             ) {
            Produit pp = produitBDD.getProduittWithId(k+"");
            pp.setQte(qtee);
            p.add(pp);
        }
        System.out.println("bjbjbjbjbjbjb");
        System.out.println(p);
        CommandDetailAdapter adapter = new CommandDetailAdapter(this,R.layout.list_item,p);
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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.panier_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sp = this.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        switch (item.getItemId()) {

            case R.id.done:
                PanierBDD panierBDD=new PanierBDD(this);
                panierBDD.open();

                panierBDD.removeProductsWithTable(i.getStringExtra("panier"));
                System.out.println("paniiier table : "+i.getStringExtra("panier"));
                PanierCommandActivity.adapter.notifyDataSetChanged();
                deletePanier(i.getStringExtra("panier"),sp.getString("id",""));
                finish();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void deletePanier(String numTable,String produit){


        try {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://"+MainActivity.ip+"/").
                            addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);

            Call<String> call = service.deletePanier(numTable+"",produit+"");
            System.out.println("USSEEEEEER "+produit);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    System.out.println("Responseee"+response.code());
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println("failuuuuure");
                }
            });
            System.out.println("numTabelll"+numTable);





            System.out.println("xwwwww:http://"+MainActivity.ip+"/panier/delete/"+numTable);

        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();

        }


    }

}
