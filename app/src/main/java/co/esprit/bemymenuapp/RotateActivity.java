package co.esprit.bemymenuapp;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import co.esprit.bemymenuapp.Adapters.CategorieAdapter;
import co.esprit.bemymenuapp.Adapters.RotateProduitsAdapter;
import co.esprit.bemymenuapp.DB.CategorieBDD;
import co.esprit.bemymenuapp.DB.MenuBDD;
import co.esprit.bemymenuapp.DB.PanierBDD;
import co.esprit.bemymenuapp.DB.ProduitBDD;
import co.esprit.bemymenuapp.Model.Category;
import co.esprit.bemymenuapp.Model.Produit;
import co.esprit.bemymenuapp.Model.Table;
import co.esprit.bemymenuapp.Service.APIService;
import co.esprit.bemymenuapp.Model.Menu;
public class RotateActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    GridView gv;
    ListView lv;
    ImageView logo;
    Context context;
    ArrayList prgmName;
    SharedPreferences pf;
    ProduitBDD produitBdd;
    RelativeLayout relativeLayout;
    Menu m;
    TextView nomRestaut;
    SearchView searchView;
    EditText kd;
    boolean recursif=false;
    static int numTable = -1 ;
 public static    TextView  paniercount;

    boolean updated = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rotate);
        lv=(ListView)findViewById(R.id.listView);
        nomRestaut=(TextView)findViewById(R.id.nomRestaut);
        relativeLayout = (RelativeLayout) findViewById(R.id.rlRotate);
        gv = (GridView) findViewById(R.id.gridView1);
        logo=(ImageView)findViewById(R.id.logo);
        searchView = (SearchView) findViewById(R.id.recheche);

        System.out.println("hahahaahahahhihih");
        //System.out.println(m);

      //  gv.setAdapter(new CustomAdapter(this, prgmNameList, prgmImages));
        CategorieBDD categorieBDD=new CategorieBDD(getApplicationContext());
        categorieBDD.open();
        List<Category>categories =categorieBDD.getAllCategorie();


        MenuBDD menuBDD=new MenuBDD(getApplicationContext());
        menuBDD.open();

      this.m=  menuBDD.getMenu();
        if(m!=null)
        {
            System.out.println("looool");
            System.out.println("http://" + MainActivity.ip + "/uploads/brochures/brochures/" +m.getLogimg());
            Picasso.with(this).load("http://" + MainActivity.ip + "/uploads/brochures/brochures/" +m.getLogimg()).into(logo);

                // Toast.makeText(this, lp.toString(), Toast.LENGTH_LONG).show();
                System.out.println("kejrkejkrjkejr");

                System.out.println(m.toString());
                //System.out.println("http://+"+ip+"/BMMA/var/uploads/brochures/" + m.getTitretxt());

                //details += "ID: " + id + "\n logo "+ imgLog ;
                Picasso.with(getApplicationContext()).load("http://"+MainActivity.ip+"/uploads/brochures/brochures/" + m.getLogimg()).into(logo);

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

                if (m.getLogoemp().equals("Left")) {


                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(logo.getLayoutParams());


                    layoutParams.leftMargin = 70;



                    logo.setLayoutParams(layoutParams);
                    getWindow().getDecorView().findViewById(R.id.logo).invalidate();
                } else if (m.getLogoemp().equals("Right")) {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(logo.getLayoutParams());
                    layoutParams.leftMargin = 800;


                    logo.setLayoutParams(layoutParams);
                    getWindow().getDecorView().findViewById(R.id.logo).invalidate();
                }else if (m.getLogoemp().equals("Center")) {

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(logo.getLayoutParams());


                    layoutParams.leftMargin = 430;


                    logo.setLayoutParams(layoutParams);
                    getWindow().getDecorView().findViewById(R.id.logo).invalidate();


                }


            nomRestaut.setText(m.getTitreemp());

            System.out.println(m.getTitreemp()+"jjjjjjjjjj");
            if (m.getTitretxt().equals("Center")) {

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(nomRestaut.getLayoutParams());


                layoutParams.leftMargin = 370;
                layoutParams.topMargin=120;

                nomRestaut.setLayoutParams(layoutParams);
                getWindow().getDecorView().findViewById(R.id.nomRestaut).invalidate();


            }
            if (m.getTitretxt().equals("Left")) {

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(nomRestaut.getLayoutParams());


                layoutParams.leftMargin = 10;
                layoutParams.topMargin=120;

                nomRestaut.setLayoutParams(layoutParams);
                getWindow().getDecorView().findViewById(R.id.nomRestaut).invalidate();


            }
            if (m.getTitretxt().equals("Right")) {

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(nomRestaut.getLayoutParams());


                layoutParams.leftMargin = 740;
                layoutParams.topMargin=120;

                nomRestaut.setLayoutParams(layoutParams);
                getWindow().getDecorView().findViewById(R.id.nomRestaut).invalidate();


            }


        }
        else{
            System.out.println("mdrrrr");
        }
        CategorieAdapter listAdapter = new CategorieAdapter(this,R.layout.rotate_list_item,categories);
//kif tekliki 3ala categiry fel list view treloadi
        produitBdd = new ProduitBDD(getBaseContext());
        produitBdd.open();
        System.out.println("siiiiiiize"+getProductPanier().size());
        paniercount = (TextView)findViewById(R.id.ddddlfp);
        paniercount.setText(""+getProductPanier().size());


            lv.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();


        lv.setItemsCanFocus(true);
        lv.setOnItemClickListener(this);
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                List<Produit> produits;
                produits=produitBdd.getAllProduitLike(query);
                RotateProduitsAdapter adapter = new RotateProduitsAdapter(RotateActivity.this,R.layout.rotate_list_item,produits);
                adapter.notifyDataSetChanged();
                gv.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                callSearch(newText);
                List<Produit> produits;
                produits=produitBdd.getAllProduitLike(newText);
                RotateProduitsAdapter adapter = new RotateProduitsAdapter(RotateActivity.this,R.layout.rotate_list_item,produits);
                adapter.notifyDataSetChanged();
                gv.setAdapter(adapter);
                System.out.println("ggg");
//              }
                return true;
            }

            public void callSearch(String query) {
                //Do searching
            }

        });
        System.out.println("aaaaaaa");
        List<Produit> p;
        p = produitBdd.getAllProduit();
        System.out.println("produiiissseeeoi"+p.toString());
        if(p!=null) {

            List<Produit>     lp = produitBdd.getAllProduit();
            List<Produit> lp2 = new ArrayList<Produit>();
            for (Produit p3 : lp) {
                if(categories.get(0).getNom().equals(p3.getCategorie())){
                    lp2.add(p3);
                }
            }
            RotateProduitsAdapter adapter = new RotateProduitsAdapter(this, R.layout.rotate_list_item, lp2);
            gv.setAdapter(adapter);
        }
    }
    public ArrayList<Produit> getProductPanier() {
        produitBdd = new ProduitBDD(getBaseContext());
        produitBdd.open();
        List<Produit>  lp = produitBdd.getAllProduit();
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
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
    System.out.println("kjdskjdksdjskjdk");
    produitBdd = new ProduitBDD(getBaseContext());
    produitBdd.open();
        CategorieBDD categorieBDD=new CategorieBDD(getBaseContext());
        categorieBDD.open();
        List<Category>categories=categorieBDD.getAllCategorie();
    List<Produit>     lp = produitBdd.getAllProduit();
    List<Produit> lp2 = new ArrayList<Produit>();
    for (Produit p : lp) {
       if(categories.get(position).getNom().equals(p.getCategorie())){
           lp2.add(p);
       }
    }
    System.out.println("hahahahahahahahalklklklkdjslkfjskjdklklkl");
    RotateProduitsAdapter adapter = new RotateProduitsAdapter(RotateActivity.this,R.layout.rotate_list_item,lp2);
    adapter.notifyDataSetChanged();
    gv.setAdapter(adapter);

}

    @Override
    protected void onResume() {
        super.onResume();

        paniercount.setText(getProductPanier().size()+"");

        if(numTable != -1){
            List<Produit> prodss = getProductPanier();
            final SharedPreferences.Editor e = pf.edit();
            System.out.println("pproooooodsssss "+prodss);
            for (final Produit p:
                    prodss) {

                try {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://"+MainActivity.ip+"/").
                                    addConverterFactory(GsonConverterFactory.create())
                            .build();

                    APIService service = retrofit.create(APIService.class);

                    Call<List<Table>> call = service.setCommand(Integer.parseInt(p.getId()),Integer.parseInt( pf.getString(p.getNom().toString(),"")),numTable);
                    System.out.println("http://"+MainActivity.ip+"/panier/add/"+Integer.parseInt(p.getId())+"/"+Integer.parseInt( pf.getString(p.getNom().toString(),""))+"/"+numTable);
                    call.enqueue(new Callback<List<Table>>() {
                        @Override
                        public void onResponse(Response<List<Table>> response, Retrofit retrofit) {
                            System.out.println("booodyyy = "+response.message());
                            List<Table> respon = response.body();
                            for (int i = 0; i < respon.size(); i++) {
                                Toast.makeText(RotateActivity.this, respon.get(i).getNum(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(RotateActivity.this, RotateActivity.numTable+"", Toast.LENGTH_LONG).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(RotateActivity.this);
                                builder.setTitle("Merci pour votre confiance ");
                                builder.setMessage(RotateActivity.numTable + " = " + respon.get(i).getNum());
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                e.remove(p.getNom());
                                e.commit();
                                RotateActivity.numTable = -1;
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d("onFailure", t.toString());

                        }
                    });
                } catch (Exception ee) {
                    Log.d("onResponse", "There is an error");
                    ee.printStackTrace();

                }




            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sp = this.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();


       /* if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }*/
        // Handle your other action bar items...

        switch (item.getItemId()) {


            case R.id.panier:
             Intent   i=new Intent(this,PanierActivity.class);
                startActivity(i);

                return true;
            case R.id.about:
             Intent    i3=new Intent(this,About.class);
                startActivity(i3);

                return true;
            case R.id.logout:
                PanierBDD panierBDD=new PanierBDD(this);
                panierBDD.open();
                panierBDD.upgradeBDD();
                MenuBDD menuBDD=new MenuBDD(this);
                menuBDD.open();
                menuBDD.upgradeBDD();
                ProduitBDD produitBDD=new ProduitBDD(this);
                produitBDD.open();
                produitBDD.upgradeBDD();
                System.out.println("waaaaaaaw"+panierBDD.getAllPanier()+"waaaw2"+produitBDD.getAllProduit()+"waaaw3"+menuBDD.getMenu());
                sp.edit().clear().commit();
                Intent i2=new Intent(this,LoginActivity.class);

                startActivity(i2);
                finish();
                return true;

          /*  case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
*/
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }
    public void rafrechir(View v){
            SharedPreferences sp = this.getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            System.out.println("updateeeeed :" + isUpdated());
            if (!isUpdated()) {
                System.out.println("getmenuuuuuu");
                this.getMenu();
                ProduitBDD produitBDD2 = new ProduitBDD(getApplicationContext());
                produitBDD2.open();
                List<Produit> prodss = produitBDD2.getAllProduit();
                for (Produit p :
                        prodss) {
                    e.remove(p.getNom());
                    e.commit();
                }
             } else {

                System.out.println("veeeeriosssjeojfeojfoejf" + sp.getInt("version", -1));


            }



}
    public void panieer(View v){
        Intent i=new Intent(this,PanierActivity.class);
        startActivity(i);
    }
    protected void updateMenu(String idMenu){
        SharedPreferences sp = this.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+MainActivity.ip+"/").
                        addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<List<Menu>> call = service.updated(sp.getString("id",""));
        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Response<List<Menu>> response, Retrofit retrofit) {
                List<Menu> lm = response.body();
                System.out.println("RESPPOOONSE "+response.body());


            }
            @Override
            public void onFailure(Throwable t) {

                System.out.println("FAILUREEE");
            }



        });



    }
//
    protected boolean isUpdated (){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+MainActivity.ip+"/").
                        addConverterFactory(GsonConverterFactory.create())
                .build();
        SharedPreferences sp = this.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        APIService service = retrofit.create(APIService.class);

        Call<Integer> call = service.versioning(sp.getString("id",""));
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Response<Integer> response, Retrofit retrofit) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
                Integer version = response.body();
                System.out.println("bodyyyy"+response.body());
                if(version != null) {
                    if (version > sp.getInt("version", 0)) {
                        System.out.println("need update");
                        System.out.println(version + "versfjkhfehfefh locale" + sp.getInt("version", 0));
                        System.out.println("getmenuuuuuu");
                        getMenu();
                        ProduitBDD produitBDD2 = new ProduitBDD(getApplicationContext());
                        produitBDD2.open();
                        SharedPreferences spp = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor e = spp.edit();
                        List<Produit> prodss = produitBDD2.getAllProduit();
                        for (Produit p :
                                prodss) {
                            e.remove(p.getNom());
                            e.commit();
                        }

                        updated = true;
                    } else {
                        System.out.println("ELSE");
                        Toast.makeText(getApplicationContext(), "UP TO DATE", Toast.LENGTH_SHORT).show();

                    }
                }



            }
            @Override
            public void onFailure(Throwable t) {


                System.out.println( "FAILUUREEE YOYUO FEFJEFE");
            }



        });
        return updated;


    }

   protected void getMenu() {
       try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://"+MainActivity.ip+"/").
                            addConverterFactory(GsonConverterFactory.create())
                    .build();
           SharedPreferences sp = this.getSharedPreferences("pref", MODE_PRIVATE);
            APIService service = retrofit.create(APIService.class);

            Call<List<co.esprit.bemymenuapp.Model.Menu>> call = service.getMenu(sp.getString("id",""));

            call.enqueue(new Callback<List<co.esprit.bemymenuapp.Model.Menu>>() {
                @Override
                public void onResponse(Response<List<co.esprit.bemymenuapp.Model.Menu>> response, Retrofit retrofit) {
                    List<co.esprit.bemymenuapp.Model.Menu> menu = response.body();
                    String details = "";
                    MenuBDD menuBDD = new MenuBDD(getApplicationContext());
                    menuBDD.open();
                    ProduitBDD produitBDD = new ProduitBDD(getApplicationContext());
                    produitBDD.open();
                    menuBDD.upgradeBDD();
                    CategorieBDD categorieBDD=new CategorieBDD(getApplicationContext());
                    categorieBDD.open();
                    categorieBDD.upgradeBDD();
                    for (int i = 0; i < menu.size(); i++) {
                        String id = menu.get(i).getId();
                        String imgLog = menu.get(i).getLogimg();
                        //String backgroundImage = menu.get(i).getBackgroundimage();
                        String emp = menu.get(i).getLogoemp();
                        String theme = menu.get(i).getTheme();
                        String template = menu.get(i).getTemplate();
                        String titreText = menu.get(i).getTitretxt();
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor e = sp.edit();
                        e.putInt("version",menu.get(i).getVersion());
                        e.commit();
                        System.out.println("azhrehrehrrejroejr" + template + "   " + theme);
                        List<Produit> lp = menu.get(i).getListProduits();
                        List<Category> lc = menu.get(i).getListCategories();
                        for (int j = 0; j < lp.size(); j++) {
                            System.out.println(lp.get(j).getPrix());
                        }
                            System.out.println("sldksdlksdlkdslsklsdklsd");
                            System.out.println(m);
                            menuBDD.insertMenu(menu.get(i));
                            ProduitBDD produitBdd = new ProduitBDD(getBaseContext());
                            produitBdd.open();
                            produitBdd.upgradeBDD();
                            for (int j = 0; j < lp.size(); j++) {
                                produitBdd.insertProduit(lp.get(j));

                            }
                        CategorieBDD categoriebdd = new CategorieBDD(getBaseContext());
                        categoriebdd.open();
                        for (int j = 0; j < lc.size(); j++) {
                            categorieBDD.insertCategorie(lc.get(j));
                        }
                          finish();
                        startActivity(getIntent());
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.d("onFailure", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
        }
    }


}
