package co.esprit.bemymenuapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.esprit.bemymenuapp.DB.MenuBDD;
import co.esprit.bemymenuapp.DetailActivity;
import co.esprit.bemymenuapp.MainActivity;
import co.esprit.bemymenuapp.Model.Menu;
import co.esprit.bemymenuapp.Model.Produit;
import co.esprit.bemymenuapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bof on 07/11/2016.
 */


public class ProduitsAdapter extends RecyclerView.Adapter<ProduitsAdapter.ProduitHolder> {

    private List<Produit> lp;
    Context context ;
    SharedPreferences pf;
    public ViewGroup vc;
    public static class ProduitHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView pseudo;
        public TextView text;
        public ImageView image;
        public RelativeLayout backgroundCell ;
        public RelativeLayout productCell;
        public Button btnPanierAdd;

        public ProduitHolder(View v ,TextView ps,TextView tex, ImageView imag , RelativeLayout backgroundCel , RelativeLayout productCel,Button btnPanierAd) {
            super(v);
            pseudo = ps;
            text = tex;
            image = imag;
            productCell = productCel;
            backgroundCell = backgroundCel;
            btnPanierAdd = btnPanierAd;
            System.out.println("endholder");
        }
    }

    public ProduitsAdapter(List<Produit> lp) {
        this.lp = lp;
    }


    @Override
    public ProduitsAdapter.ProduitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.vc=parent;
        context = parent.getContext();
        System.out.println("ghjk");

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false); ;



//        if(convertView == null){
//            if(m.getCelltype().equals("CellMenu1")) {
//                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
//            }else if (m.getCelltype().equals("CellMenu2")){
//                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
//            }
//        }
        System.out.println("ghjk");
        TextView pseudo = (TextView) v.findViewById(R.id.pseudo);
        System.out.println("ghjk");
        TextView text = (TextView) v.findViewById(R.id.text);
        System.out.println("ghjk");
        ImageView image = (ImageView) v.findViewById(R.id.avatar);
        System.out.println("ghjk");
        RelativeLayout backgroundCell=(RelativeLayout) v.findViewById(R.id.cellBackground);
        System.out.println("ghjk");
        RelativeLayout productCell = (RelativeLayout) v.findViewById(R.id.secondCellColor);
        System.out.println("ghjk");
        Button btnPanierAdd=(Button) v.findViewById(R.id.addPanierBtn);
        System.out.println("ghjk");


        ProduitHolder viewHolder = new ProduitHolder(v,pseudo,text,image,backgroundCell,productCell,btnPanierAdd);
        System.out.println("ghjk");

        return viewHolder;
//
    }

    @Override
    public void onBindViewHolder(ProduitHolder holder, int position) {
        MenuBDD menuBDD=new MenuBDD(context);
        menuBDD.open();

        Menu m = menuBDD.getMenu();
        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
        System.out.println("ghjk");


        final Produit produit = lp.get(position);
        System.out.println("ghjk");

        //il ne reste plus qu'à remplir notre vue
        holder.pseudo.setText(produit.getNom().toString());
        System.out.println("ghjk");
        holder.text.setText(produit.getDescription().toString());
        System.out.println("ghjk");
        holder.btnPanierAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pf = context.getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor e = pf.edit();
                if(pf.getString(produit.getNom().toString(),"").equals("") ) {
                    e.putString(produit.getNom().toString(),"1");

                }
                else
                {
                    int quantite=Integer.parseInt(pf.getString(produit.getNom().toString(),""));
                    quantite++;
                    e.putString(produit.getNom().toString(),quantite+"");
                }
                Toast.makeText(context, "item added", Toast.LENGTH_SHORT).show();
                e.commit();

                Intent i2 =new Intent(vc.getContext(),DetailActivity.class);
                i2.putExtra("produit",  produit);
                context.startActivity(i2);
            }
        });
        Picasso.with(context).load("http://"+ MainActivity.ip+"/uploads/brochures/brochures/"+produit.getImage()).into(holder.image);

        if (m.getTemplate().equals("template1")) {


            //viewHolder.backgroundCell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_blur));
            holder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            holder.backgroundCell.setAlpha(0.9f);
            holder.productCell.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));


        } else if (m.getTemplate().equals("template2")) {

            if (m.getTheme().equals("1")) {

                holder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.theme1_cellColor));
                holder.productCell.setBackgroundColor(ContextCompat.getColor(context, R.color.theme1_drawerColor));

            } else if (m.getTheme().equals("2")) {
                holder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.theme2_cellColor));
                holder.productCell.setBackgroundColor(ContextCompat.getColor(context, R.color.theme2_drawerColor));

            } else if (m.getTheme().equals("3")) {

                holder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.theme3_cellColor));
                holder.productCell.setBackgroundColor(ContextCompat.getColor(context, R.color.theme3_drawerColor));
            }

        }
        System.out.println("end");

    }




    @Override
    public int getItemCount() {
        return lp.size();

    }
}
