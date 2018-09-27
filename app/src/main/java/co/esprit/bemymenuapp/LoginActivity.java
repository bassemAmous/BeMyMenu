package co.esprit.bemymenuapp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import co.esprit.bemymenuapp.DB.CategorieBDD;
import co.esprit.bemymenuapp.DB.MenuBDD;
import co.esprit.bemymenuapp.DB.PanierBDD;
import co.esprit.bemymenuapp.DB.ProduitBDD;
import co.esprit.bemymenuapp.Model.Category;
import co.esprit.bemymenuapp.Model.Panier;
import co.esprit.bemymenuapp.Model.Produit;
import co.esprit.bemymenuapp.Model.User;
import co.esprit.bemymenuapp.Service.APIService;
public class LoginActivity extends AppCompatActivity {
    EditText login, password;
    Button btnLog;
    SharedPreferences pf;
    AlertDialog alert;
    static int version ;
    Boolean panierFinished=false;
    Boolean menuFinished=false;
    Boolean userFinished=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!LoginActivity.isTablet(getBaseContext())){
            showInputDialog2();
        }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            login = (EditText) findViewById(R.id.login);
            password = (EditText) findViewById(R.id.password);
            btnLog = (Button) findViewById(R.id.loginBtm);
            pf = this.getSharedPreferences("pref", MODE_PRIVATE);
            if (pf.getString("login", "").equals("") && pf.getString("password", "").equals("")) {

            } else {
                if (pf.getString("role", "").equals("10")) {
                    showInputDialog();
                } else {
                    Intent i;
                    if (pf.getString("password", "").contains("Server**"))
                        i = new Intent(this, PanierCommandActivity.class);

                    else
                        i = new Intent(this, RotateActivity.class);

                    startActivity(i);
                    finish();
                }
            }
        }

    protected void getPanier() {
        System.out.println("hshsfhjsfjhfsjhsfhjssf");
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://"+MainActivity.ip+"/").
                            addConverterFactory(GsonConverterFactory.create())
                    .build();
            pf = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
            APIService service = retrofit.create(APIService.class);
            Call<List<Panier>> call = service.getPanier(pf.getString("id", ""));
            call.enqueue(new Callback<List<Panier>>() {
                @Override
                public void onResponse(Response<List<Panier>> response, Retrofit retrofit) {
                    System.out.println("panieeeeeer d5al");
                    List<Panier> panier = response.body();
                    String details = "";

                    PanierBDD panierBDD=new PanierBDD(getApplicationContext());
                    panierBDD.open();

                    panierBDD.upgradeBDD();
                    if(panier != null) {
                        for (int i = 0; i < panier.size(); i++) {


                            panierBDD.insertPanier(panier.get(i));
                            System.out.println("insereeeeer");
                            System.out.println(panier.get(i));
                        }
                        System.out.println("avaaaaantLog");
                        System.out.println(panierBDD.getAllPanier());
                        panierBDD.close();
                    }else{
                        System.out.println("NULLLLLLLLPANIER");
                    }
                    panierFinished=true;
                    getMenu();
         }
                @Override
                public void onFailure(Throwable t) {
                    Log.d("onFailure", t.toString());
                    System.out.println("failure fel panier");
                }
            });
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
        }
    }
    protected void getMenu() {
        try {
            System.out.println("menuuuuuu");
            pf = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);

            System.out.println("looool"+ pf.getString("id", ""));
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://"+MainActivity.ip+"/").
                            addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);

            Call<List<co.esprit.bemymenuapp.Model.Menu>> call = service.getMenu(pf.getString("id", ""));

            call.enqueue(new Callback<List<co.esprit.bemymenuapp.Model.Menu>>() {
                @Override
                public void onResponse(Response<List<co.esprit.bemymenuapp.Model.Menu>> response, Retrofit retrofit) {
                    System.out.println("menu d5al");
                    List<co.esprit.bemymenuapp.Model.Menu> menu = response.body();
                    System.out.println(response.body());
                    SharedPreferences.Editor e = pf.edit();
                    String details = "";
                    MenuBDD menuBDD=new MenuBDD(getApplicationContext());
                    menuBDD.open();
                    menuBDD.upgradeBDD();
                    ProduitBDD produitBDD=new ProduitBDD(getApplicationContext());
                    produitBDD.open();
                    produitBDD.upgradeBDD();
                    CategorieBDD categorieBDD=new CategorieBDD(getApplicationContext());
                    categorieBDD.open();
                    categorieBDD.upgradeBDD();
                    if(menu != null) {
                        for (int i = 0; i < menu.size(); i++) {
                            String id = menu.get(i).getId();
                            String imgLog = menu.get(i).getLogimg();
                            //String backgroundImage = menu.get(i).getBackgroundimage();
                            String emp = menu.get(i).getLogoemp();
                            String theme = menu.get(i).getTheme();
                            String template = menu.get(i).getTemplate();
                            System.out.println("azhrehrehrrejroejr" + template + "   " + theme);
                            List<Produit> lp = menu.get(i).getListProduits();
                            List<Category> lc = menu.get(i).getListCategories();
                            System.out.println("veeersonneekenkejhrkehrkehrje" + menu.get(i).getVersion());
                            e.putInt("version", menu.get(i).getVersion());
                            e.commit();
                            for (int j = 0; j < lp.size(); j++) {
                                System.out.println(lp.get(j).getPrix());
                            }
                            menuBDD.insertMenu(menu.get(i));
                            ProduitBDD produitBdd = new ProduitBDD(getBaseContext());
                            produitBdd.open();

                            for (int j = 0; j < lp.size(); j++) {
                                produitBdd.insertProduit(lp.get(j));
                            }
                            CategorieBDD categoriebdd = new CategorieBDD(getBaseContext());
                            categoriebdd.open();
                            for (int j = 0; j < lc.size(); j++) {
                                categorieBDD.insertCategorie(lc.get(j));
                            }
                            System.out.println("aaaalllll" + categorieBDD.getAllCategorie());
                        }
                    }

                   // finish();
                    //startActivity(getIntent());

                    Intent i;
                    if (pf.getString("password", "").contains("Server**")) {

                        i = new Intent(getBaseContext(), PanierCommandActivity.class);

                        startActivity(i);
                        finish();
                    } else if (LoginActivity.isTablet(getBaseContext())) {
                        i = new Intent(getBaseContext(), RotateActivity.class);

                        startActivity(i);
                        finish();
                    }
                    //smart phone
                    /*
                    else {
                        Toast.makeText(LoginActivity.this, "this app works only in  tablets", Toast.LENGTH_SHORT).show();
                    }*/


                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("onFailure", t.toString());
                    System.out.println("failure fel menu");

                }

            });
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();

        }
    }

    private void getUser() {

        try {
            System.out.println("useeeeeeer");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://"+ MainActivity.ip+"/").
                            addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);
            Call<User> call = service.getUser(login.getText().toString());


            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    System.out.println("user d5al");
                  User user  = response.body();
                    System.out.println("lskdlskdlskdslkdddd"+user);
                    String pass = user.getPassword()+"Server**";
                    if(user.getLogin().equals(login.getText().toString())&&(user.getPassword().equals(password.getText().toString()) || pass.equals(password.getText().toString()))){
                        //Toast.makeText(LoginActivity.this, "ahahahahahah", Toast.LENGTH_LONG).show();
                       pf = getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor e = pf.edit();
                        System.out.println("jjijijijiji"+user.getId());

                        if(user.getFlagRole().equals("10")){
                            showInputDialog();
                        }else {
                            e.putString("login", login.getText().toString());
                            e.putString("password", password.getText().toString());
                            e.putString("role", user.getFlagRole());
                            e.putString("id", user.getId());

                            e.commit();
                            getPanier();
                        }

                    }
                    else
                    {  Toast.makeText(LoginActivity.this, "verify your account", Toast.LENGTH_LONG).show();

                    }


                }
                @Override
                public void onFailure(Throwable t) {
                    System.out.println("useeeer failure");
                }

            });
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            System.out.println("failure fel user");
            e.printStackTrace();
        }
    }

    public final static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
    public void loginBtn(View v) throws ExecutionException, InterruptedException {
        if(isNetworkAvailable()) {
            WebServiceUser wsu = new WebServiceUser();
            wsu.execute();
        }
        else
            Toast.makeText(this, "please verify your network connexion", Toast.LENGTH_LONG).show();
        /*while(wsu .getStatus() !=AsyncTask.Status.FINISHED){
            System.out.println("1111111111111111");
        }



        WebServicePanier wsp=new WebServicePanier();
        wsp.execute();
        while(wsp .getStatus() != AsyncTask.Status.FINISHED){
            System.out.println("222222222222222222");
        }
          WebServiceMenu wsm=new WebServiceMenu();
        wsm.execute();
        while(wsm .getStatus() != AsyncTask.Status.FINISHED){
            System.out.println("3333333333333333333");
        }*/



    }

    public void attente(){
        if(!userFinished)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 3000);
            System.out.println("attente user");
            attente();
        }
        else if(!panierFinished)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 3000);
            System.out.println("attente panier");
            attente();
        }
        else if(!menuFinished){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 3000);
            System.out.println("attente menu");
            attente();
        }

    }

    protected void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.user_pop_up, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false).setPositiveButton("Understand", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
// create an alert dialog
        alert = alertDialogBuilder.create();
        alert.show();
    }
    protected void showInputDialog2() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.smart_phone_error, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        // setup a dialog window
// create an alert dialog
        alert = alertDialogBuilder.create();
        alert.show();
    }


    private class WebServiceUser extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Waiting for server ..", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);


        }

        @Override
        protected Void doInBackground(Void... arg0) {

            getUser();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(getApplicationContext(), "Connecting..!", Toast.LENGTH_SHORT).show();
          /*  WebServicePanier wsp=new WebServicePanier();
            wsp.execute();*/
        }
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
