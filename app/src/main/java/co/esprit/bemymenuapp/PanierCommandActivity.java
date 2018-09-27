package co.esprit.bemymenuapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.List;

import co.esprit.bemymenuapp.Adapters.PanierCommandAdapter;
import co.esprit.bemymenuapp.DB.MenuBDD;
import co.esprit.bemymenuapp.DB.PanierBDD;
import co.esprit.bemymenuapp.DB.ProduitBDD;
import co.esprit.bemymenuapp.Model.Panier;

public class PanierCommandActivity extends AppCompatActivity {
    GridView gv;
    static PanierCommandAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier_command);
        gv= (GridView) findViewById(R.id.gridViewCommand);
        PanierBDD p=new PanierBDD(this);
        p.open();
        PanierBDD panierBDD=new PanierBDD(this);
        panierBDD.open();
        ProduitBDD produitBDD=new ProduitBDD(this);
        produitBDD.open();
        List<Panier> panierList=panierBDD.getAllDistinctTable();
      System.out.println("dsofjdkjfdljfdlujuuoujfdljkf");
         adapter = new PanierCommandAdapter(this,R.layout.panier_command_grid, panierList);
        /*if(LoginActivity.isTablet(this)){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                gv.setNumColumns(4);

            else
                gv.setNumColumns(3);
        }
        else{
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                gv.setNumColumns(3);
            else
            gv.setNumColumns(2);
        }*/
        // pr optimiser
        if(LoginActivity.isTablet(this) && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            gv.setNumColumns(4);
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)//landscape et pas tablet
            gv.setNumColumns(3);
        else
            gv.setNumColumns(2);
        gv.setAdapter(adapter);
        //System.out.println("panieeeeer"+p.getAllPanier());
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.command, menu);
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



            case R.id.logout:

                sp.edit().clear().commit();
                Intent i2=new Intent(this,LoginActivity.class);
                PanierBDD panierBDD=new PanierBDD(this);
                panierBDD.open();
                panierBDD.upgradeBDD();
                MenuBDD menuBDD=new MenuBDD(this);
                menuBDD.open();
                menuBDD.upgradeBDD();
                ProduitBDD produitBDD=new ProduitBDD(this);
                produitBDD.open();
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
}
