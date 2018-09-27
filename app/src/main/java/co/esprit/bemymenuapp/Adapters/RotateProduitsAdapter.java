package co.esprit.bemymenuapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import co.esprit.bemymenuapp.RotateActivity;

import static android.content.Context.MODE_PRIVATE;


public class RotateProduitsAdapter extends ArrayAdapter<Produit> {
    RotateProduitHolder  holder;
    Context context ;
    SharedPreferences pf;
    List<Produit> prods;
    public RotateProduitsAdapter(Context context, int resource, List<Produit> objects) {
        super(context, resource, objects);
        this.context = context;
        prods=objects;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MenuBDD menuBDD = new MenuBDD(context);
        menuBDD.open();
        Menu m = menuBDD.getMenu();
        if(convertView == null){
                if(m.getCelltype().equals("CellMenu1"))
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rotate_produit_list_item,parent, false);
            else
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item1,parent, false);
        }
        final Produit produit = getItem(position);
       holder = (RotateProduitHolder) convertView.getTag();
        if(holder == null){
            holder = new RotateProduitHolder();
            holder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            holder.description = (TextView) convertView.findViewById(R.id.text);
            holder.text = (TextView) convertView.findViewById(R.id.prixTextView);
            System.out.println("ghjk");
            holder.image = (ImageView)convertView.findViewById(R.id.avatar);

            holder.backgroundCell=(RelativeLayout) convertView.findViewById(R.id.cellBackground);
            System.out.println("sljfdljfldjldfj");
            holder.productCell = (ConstraintLayout) convertView.findViewById(R.id.secondCellColor);
            System.out.println("ghjkf");
            holder.btnPanierAdd=(Button) convertView.findViewById(R.id.addPanierBtn);
            System.out.println("ghjk");
            convertView.setTag(holder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("skjfskjfkjfsjfskjfsk");

                            Intent i2 =new Intent(getContext(),DetailActivity.class);
                            i2.putExtra("produit",  produit);
                            context.startActivity(i2);
                        }
                    });


        //   pf=this.getContext().getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        pf = getContext().getSharedPreferences("pref", MODE_PRIVATE);


           holder.image.setScaleType(ImageView.ScaleType.FIT_XY);

            System.out.println("ghjk");
            holder.description.setText(produit.getDescription());

            System.out.println("ghjk");

            //il ne reste plus qu'à remplir notre vue
            holder.pseudo.setText(produit.getNom());
            System.out.println("ghjk");
            holder.text.setText(produit.getPrix().toString()+"$");
            System.out.println("ghjk");
            holder.btnPanierAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int coutPan=Integer.parseInt(RotateActivity.paniercount.getText().toString())+1;
                    RotateActivity.paniercount.setText(coutPan+"");
                    System.out.println("mamamamamam"+RotateActivity.paniercount.getText().toString());
                    pf = context.getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor e = pf.edit();
                    if (pf.getString(produit.getNom().toString(), "").equals("")) {
                        e.putString(produit.getNom().toString(), "1");

                    } else {
                        int quantite = Integer.parseInt(pf.getString(produit.getNom().toString(), ""));
                        quantite++;
                        e.putString(produit.getNom().toString(), quantite + "");
                    }
                    Toast.makeText(context, "item added", Toast.LENGTH_SHORT).show();
                    e.commit();
                }
            });
            Picasso.with(context).load("http://" + MainActivity.ip + "/uploads/brochures/brochures/" + produit.getImage()).into(holder.image);
            if (m.getTemplate().equals("template1")) {
                if (m.getTheme().equals("1")) {

                    holder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.template1_theme1_drawerColor));
                    holder.productCell.setBackgroundColor(ContextCompat.getColor(context, R.color.template1_theme1_drawerColor));

                } else if (m.getTheme().equals("2")) {
                    holder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.template1_theme2_drawerColor));
                    holder.productCell.setBackgroundColor(ContextCompat.getColor(context, R.color.template1_theme2_drawerColor));

                } else if (m.getTheme().equals("3")) {

                    holder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.template1_theme3_drawerColor));
                    holder.productCell.setBackgroundColor(ContextCompat.getColor(context, R.color.template1_theme3_drawerColor));
                }

                //viewHolder.backgroundCell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_blur));

                holder.text.setTextColor(ContextCompat.getColor(context, R.color.md_white_1000));
                holder.pseudo.setTextColor(ContextCompat.getColor(context, R.color.md_white_1000));
                holder.description.setTextColor(ContextCompat.getColor(context,R.color.md_white_1000));


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
        return convertView;}
}
