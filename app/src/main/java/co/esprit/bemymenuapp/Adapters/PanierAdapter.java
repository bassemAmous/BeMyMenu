package co.esprit.bemymenuapp.Adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.esprit.bemymenuapp.MainActivity;
import co.esprit.bemymenuapp.Model.Produit;
import co.esprit.bemymenuapp.PanierActivity;
import co.esprit.bemymenuapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bof on 07/11/2016.
 */


public class PanierAdapter extends ArrayAdapter<Produit> implements View.OnClickListener{

    PanierHolder  viewHolder;
    Context context ;
    SharedPreferences pf;
    List<Produit> prods;
    ImageButton imMoin;
    ImageButton imPlus;
    ImageButton imTrash;
    TextView tv;
    TextView tvNom;
    TextView tvPrix;
    Produit produit ;
    AlertDialog alert;
    int pos ;

    public PanierAdapter(Context context, int resource, List<Produit> objects) {
        super(context, resource, objects);
        this.context = context;
        prods=objects;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent, false);
        }
        //   pf=this.getContext().getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        pf = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        viewHolder = (PanierHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PanierHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.prix = (TextView) convertView.findViewById(R.id.prix);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.avatar.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.prixTotal=(TextView) convertView.findViewById(R.id.prixTotal);
            viewHolder.quantite=(TextView) convertView.findViewById(R.id.quantite);

            convertView.setTag(viewHolder);

        }

        produit = getItem(position);

        System.out.println("+++++++"+pf.getString(produit.getNom().toString(),"").toString());
        viewHolder.quantite.setText(pf.getString(produit.getNom().toString(),"").toString());

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(produit.getNom().toString());
        viewHolder.prix.setText(produit.getPrix().toString());



        int prixt=Integer.parseInt( pf.getString(produit.getNom().toString(),""))* Integer.parseInt(viewHolder.prix.getText().toString());
        viewHolder.prixTotal.setText( prixt+"");


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pos = position;
                System.out.println("positioon"+pos);
                showInputDialog();

            }
        });

        Picasso.with(context).load("http://"+ MainActivity.ip+"/uploads/brochures/brochures/"+produit.getImage()).into(viewHolder.avatar);
        return convertView;}

    protected void showInputDialog() {
        Produit pp = getItem(pos);
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.panier_pop_up, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);

        tv = (TextView) promptView.findViewById(R.id.qteTV);
        imPlus = (ImageButton) promptView.findViewById(R.id.plusss);
        imMoin = (ImageButton) promptView.findViewById(R.id.moinnn);
        imTrash = (ImageButton)promptView.findViewById(R.id.deleteee);
        tvNom = (TextView) promptView.findViewById(R.id.product);
        tvPrix =(TextView) promptView.findViewById(R.id.pricee);

        tvNom.setText(pp.getNom());
        int prixt=Integer.parseInt( pf.getString(pp.getNom().toString(),""))* Integer.parseInt(pp.getPrix());
        tvPrix.setText( prixt+"$");
        tv.setText( pf.getString(pp.getNom().toString(),""));

        imMoin.setOnClickListener(this);
        imPlus.setOnClickListener(this);
        imTrash.setOnClickListener(this);








        // setup a dialog window
        alertDialogBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });


        // create an alert dialog
        alert = alertDialogBuilder.create();

        alert.show();
    }
    @Override
    public void onClick(View view) {



        Produit pp = getItem(pos);
        if(view==imMoin)
        {
            SharedPreferences.Editor e = pf.edit();
            System.out.println("positioon"+pos);
            int quantite=Integer.parseInt( pf.getString(pp.getNom().toString(),""));
            if(quantite > 1) {
                quantite--;

                e.putString(pp.getNom().toString(), quantite + "");
                e.commit();

                float total=Float.parseFloat(PanierActivity.menuItemTotal.getTitle().toString())-(Integer.parseInt(pp.getPrix()));
                PanierActivity.menuItemTotal.setTitle(total+"");
                tv.setText(pf.getString(pp.getNom().toString(), ""));
                viewHolder.quantite.setText(pf.getString(pp.getNom().toString(), ""));
                int prixt=Integer.parseInt( pf.getString(pp.getNom().toString(),""))* Integer.parseInt(viewHolder.prix.getText().toString());
                tvPrix.setText( prixt+"$");
                notifyDataSetChanged();
            }
        }
        else if(view == imPlus){
            System.out.println("positioon"+pos);
            SharedPreferences.Editor e = pf.edit();
            int quantite=Integer.parseInt( pf.getString(pp.getNom().toString(),""));
            quantite++;
            e.putString(pp.getNom().toString(),quantite+"");
            e.commit();

            float total=Float.parseFloat(PanierActivity.menuItemTotal.getTitle().toString())+(Integer.parseInt(pp.getPrix()));
            PanierActivity.menuItemTotal.setTitle(total+"");
            tv.setText( pf.getString(pp.getNom().toString(),""));
            viewHolder.quantite.setText(pf.getString(pp.getNom().toString(), ""));
            int prixt=Integer.parseInt( pf.getString(pp.getNom().toString(),""))* Integer.parseInt(pp.getPrix());
            tvPrix.setText( prixt+"$");


            notifyDataSetChanged();

        }
        else if(view == imTrash){
            SharedPreferences.Editor e = pf.edit();
            float total=Float.parseFloat(PanierActivity.menuItemTotal.getTitle().toString())-(Integer.parseInt(pp.getPrix())*Integer.parseInt(pf.getString(pp.getNom().toString(),"")));
            PanierActivity.menuItemTotal.setTitle(total+"");
            e.remove(pp.getNom().toString());
            e.commit();
            prods.remove(pos);
            notifyDataSetChanged();
            alert.hide();
        }

    }
}