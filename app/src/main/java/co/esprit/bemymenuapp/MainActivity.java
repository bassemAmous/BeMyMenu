package co.esprit.bemymenuapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.ImageView;



import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import co.esprit.bemymenuapp.DB.MenuBDD;

import co.esprit.bemymenuapp.DB.ProduitBDD;
import co.esprit.bemymenuapp.Fragments.MainFragment;
import co.esprit.bemymenuapp.Model.Menu;
import co.esprit.bemymenuapp.Model.Produit;
import co.esprit.bemymenuapp.Service.APIService;



public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_tab_favourite,
            R.drawable.ic_tab_call,
            R.drawable.ic_tab_contacts
    };
    public static String ip="bmma.detect.tn";
    public static String idUser="3";

    private String[] mPlanetTitles = {"dessert","cold starters","food","soft drinks"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_main);
        MenuBDD menuBDD=new MenuBDD(getApplicationContext());
        menuBDD.open();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Menu m = menuBDD.getMenu();

        if(m != null){
            ImageView imgV = (ImageView) findViewById(R.id.imageV);
            System.out.println("HHHHHHHHHHhttp://" + MainActivity.ip + "/BMMA/var/uploads/brochures/" + m.getLogimg());
            Picasso.with(getApplicationContext()).load("http://" + MainActivity.ip + "/uploads/brochures/brochures/" + m.getLogimg()).into(imgV);
            System.out.println("DRAWAABLE"+imgV.getDrawable());
            toolbar.setTitle(m.getTitreemp());
        }






        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[1]);
        tabLayout.getTabAt(4).setIcon(tabIcons[0]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundl = new Bundle();
        bundl.putString("elist", "food");
        MainFragment food = new MainFragment();
        food.setArguments(bundl);

        Bundle bundl1 = new Bundle();
        bundl1.putString("elist", "soft drinks");
        MainFragment drinks = new MainFragment();
        drinks.setArguments(bundl1);

        Bundle bundl2 = new Bundle();
        bundl2.putString("elist", "cold starters");
        MainFragment coldStarters = new MainFragment();
        coldStarters.setArguments(bundl2);

        Bundle bundl3 = new Bundle();
        bundl3.putString("elist", "dessert");
        MainFragment desserts = new MainFragment();
        desserts.setArguments(bundl3);

        Bundle bundl4 = new Bundle();
        bundl4.putString("elist", "smoothies");
        MainFragment  smoothies = new MainFragment();
        smoothies.setArguments(bundl4);


        adapter.addFrag(food, "food");
        adapter.addFrag(drinks, "soft drinks");
        adapter.addFrag(desserts, "dessert");
        adapter.addFrag(smoothies, "smoothies");
        adapter.addFrag(coldStarters, "cold starters");
        viewPager.setAdapter(adapter);
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



        switch (item.getItemId()) {

            case R.id.action_refresh:
                this.getMenu();
                ProduitBDD produitBDD2=new ProduitBDD(getApplicationContext());
                produitBDD2.open();
                List<Produit> prodss = produitBDD2.getAllProduit();
                for (Produit p:
                        prodss) {


                    e.remove(p.getNom());

                    e.commit();
                }
                return true;
            case R.id.panier:
                Intent i=new Intent(this,PanierActivity.class);

                startActivity(i);
                return true;
            case R.id.logout:

                sp.edit().clear().commit();
                Intent i2=new Intent(this,LoginActivity.class);
                startActivity(i2);
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }

    }
    private void getMenu() {



        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://"+ip+"/").
                            addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);

            Call<List<Menu>> call = service.getMenu(idUser);

            call.enqueue(new Callback<List<Menu>>() {
                @Override
                public void onResponse(Response<List<Menu>> response, Retrofit retrofit) {
                    List<Menu> menu = response.body();
                    String details = "";

                    MenuBDD menuBDD=new MenuBDD(getApplicationContext());
                    menuBDD.open();
                    ProduitBDD produitBDD=new ProduitBDD(getApplicationContext());
                    produitBDD.open();
                    menuBDD.upgradeBDD();
                    for (int i = 0; i < menu.size(); i++) {

                        String theme = menu.get(i).getTheme();
                        String template = menu.get(i).getTemplate();

                        System.out.println("azhrehrehrrejroejr" + template + "   " + theme);

                        List<Produit> lp = menu.get(i).getListProduits();
                        for (int j = 0; j < lp.size(); j++) {
                            System.out.println(lp.get(j).getPrix());
                        }



                        menuBDD.insertMenu(menu.get(i));
                        ProduitBDD produitBdd = new ProduitBDD(getBaseContext());
                        produitBdd.open();
                        produitBdd.upgradeBDD();
                        for (int j = 0; j < lp.size(); j++) {
                            produitBdd.insertProduit(lp.get(j));

                        }

                    }

                    finish();
                    startActivity(getIntent());
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



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
