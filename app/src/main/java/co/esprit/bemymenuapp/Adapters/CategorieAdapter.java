package co.esprit.bemymenuapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.esprit.bemymenuapp.DB.MenuBDD;
import co.esprit.bemymenuapp.MainActivity;
import co.esprit.bemymenuapp.Model.Category;
import co.esprit.bemymenuapp.Model.Menu;
import co.esprit.bemymenuapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bof on 07/11/2016.
 */


public class CategorieAdapter extends ArrayAdapter<Category> {


    CategoryHolder  viewHolder;
    Context context ;
    SharedPreferences pf;
    List<Category> cats;

    public CategorieAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        cats=objects;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MenuBDD menuBDD = new MenuBDD(context);
        menuBDD.open();
        Menu m = menuBDD.getMenu();
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rotate_list_view_item,parent, false);
        }
        //   pf=this.getContext().getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        pf = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        viewHolder = (CategoryHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new CategoryHolder();
            viewHolder.nom = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.backgroundCell = (RelativeLayout) convertView.findViewById(R.id.backgroundCategroie);
            convertView.setTag(viewHolder);
         }

        final    Category category = getItem(position);
        System.out.println("+++++++"+pf.getString(category.getNom().toString(),"").toString());
      //  viewHolder.quantite.setText(pf.getString(produit.getNom().toString(),"").toString());

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets

        //il ne reste plus qu'à remplir notre vue
        //la position commence avec 0 et les image de a1 --> a9
       // int pos=position+1;
//String a="a"+pos;
        Picasso.with(context).load("http://"+ MainActivity.ip+"/uploads/brochures/brochures/"+category.getImage()).into(viewHolder.image);
       //viewHolder.image.setImageResource(getContext().getResources().getIdentifier(a, "drawable", getContext().getPackageName()));
        //viewHolder.image.setImageResource(R.drawable.a1);
      viewHolder.nom.setText(category.getNom().toString());
       // Picasso.with(context).load("http://"+ MainActivity.ip+"/BMMA/var/uploads/brochures/"+category.getImage()).into(viewHolder.image);
        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println("ksjdksdjdksjdkjskdjs");
            }
        });*/
        if(m!=null) {

            if (m.getTemplate().equals("template1")) {



                if (m.getTheme().equals("1")) {

                    viewHolder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.template1_theme1_drawerColor));


                } else if (m.getTheme().equals("2")) {
                    viewHolder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.template1_theme2_drawerColor));


                } else if (m.getTheme().equals("3")) {

                    viewHolder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.template1_theme3_drawerColor));

                }
                //viewHolder.backgroundCell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_blur));



                viewHolder.nom.setTextColor(ContextCompat.getColor(context, R.color.md_white_1000));


            } else if (m.getTemplate().equals("template2")) {

                if (m.getTheme().equals("1")) {

                    viewHolder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.theme1_drawerColor));


                } else if (m.getTheme().equals("2")) {
                    viewHolder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.theme2_drawerColor));


                } else if (m.getTheme().equals("3")) {

                    viewHolder.backgroundCell.setBackgroundColor(ContextCompat.getColor(context, R.color.theme3_drawerColor));

                }

            }
        }
        return convertView;}
}
