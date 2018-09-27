package co.esprit.bemymenuapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.esprit.bemymenuapp.DB.PanierBDD;
import co.esprit.bemymenuapp.Model.Panier;
import co.esprit.bemymenuapp.PanierCommandDisplayActivity;
import co.esprit.bemymenuapp.R;

/**
 * Created by bof on 07/11/2016.
 */


public class PanierCommandAdapter extends ArrayAdapter<Panier> {
    PanierCommandHolder  holder;
    Context context ;
    SharedPreferences pf;
    List<Panier> panier;

    public PanierCommandAdapter(Context context, int resource, List<Panier> objects) {
        super(context, resource, objects);
        this.context = context;
        panier=objects;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PanierBDD panierBDD = new PanierBDD(context);
        panierBDD.open();

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.panier_command_grid,parent, false);

        }

        final Panier panier = getItem(position);
       holder = (PanierCommandHolder) convertView.getTag();
        if(holder == null){
            holder = new PanierCommandHolder();
            holder.numTable = (TextView) convertView.findViewById(R.id.numTable);

           convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("skjfskjfkjfsjfskjfsk");
                            Intent i2 =new Intent(getContext(),PanierCommandDisplayActivity.class);
                            i2.putExtra("panier",  panier.getTable());
                            i2.putExtra("qte",panier.getQte());
                            i2.putExtra("produitt",panier.getProduit());
                    System.out.println("produiiiiiiiiiiiiiiiiits"+panier.getProduit());
                            context.startActivity(i2);
                        }
                    });


        //   pf=this.getContext().getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
       // pf = getContext().getSharedPreferences("pref", MODE_PRIVATE);



            }
        holder.numTable.setText(" N°\n"+panier.getTable());
            System.out.println("end");

        return convertView;}
}
