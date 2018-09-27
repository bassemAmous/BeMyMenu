package co.esprit.bemymenuapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import co.esprit.bemymenuapp.DB.ProduitBDD;
import co.esprit.bemymenuapp.Model.Produit;

public class QR extends AppCompatActivity implements ZXingScannerView.ResultHandler  {
    private  ZXingScannerView mScannerView ;
    ProduitBDD produitBDD;
    SharedPreferences pf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        Button b = (Button) findViewById(R.id.buttoon);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    public void onClick (View v){


    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();


    }
    public ArrayList<Produit> getProductPanier() {
        produitBDD = new ProduitBDD(getBaseContext());
        produitBDD.open();
        List<Produit> lp = produitBDD.getAllProduit();
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
    public void handleResult(final Result result) {



        SharedPreferences sp = this.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        e.putInt("numTable",Integer.parseInt(result.getText()));

        RotateActivity.numTable =Integer.parseInt(result.getText());


        mScannerView.resumeCameraPreview(this);

        finish();


    }


}
