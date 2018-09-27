package co.esprit.bemymenuapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.esprit.bemymenuapp.MainActivity;
import co.esprit.bemymenuapp.Model.Produit;
import co.esprit.bemymenuapp.R;

/**
 * Created by bof on 07/11/2016.
 */


public class CommandDetailAdapter extends ArrayAdapter<Produit> {

    PanierHolder  viewHolder;
    Context context ;

    List<Produit> prods;

    public CommandDetailAdapter(Context context, int resource, List<Produit> objects) {
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

        final    Produit produit = getItem(position);
        viewHolder.quantite.setText(produit.getQte());

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(produit.getNom());
        viewHolder.prix.setText(produit.getPrix());





        int prixt=Integer.parseInt(produit.getQte())* Integer.parseInt(viewHolder.prix.getText().toString());
        viewHolder.prixTotal.setText( prixt+"");


        Picasso.with(context).load("http://"+ MainActivity.ip+"/uploads/brochures/brochures/"+produit.getImage()).into(viewHolder.avatar);
        return convertView;}
}
