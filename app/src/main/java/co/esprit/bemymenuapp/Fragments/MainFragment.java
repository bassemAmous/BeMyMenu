package co.esprit.bemymenuapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.esprit.bemymenuapp.Adapters.ProduitsAdapter;
import co.esprit.bemymenuapp.DB.MenuBDD;
import co.esprit.bemymenuapp.DB.ProduitBDD;
import co.esprit.bemymenuapp.MainActivity;
import co.esprit.bemymenuapp.Model.Menu;
import co.esprit.bemymenuapp.Model.Produit;
import co.esprit.bemymenuapp.R;

/**
 * Created by bof on 30/11/2016.
 */

public class MainFragment extends android.support.v4.app.Fragment {

    private ProgressDialog pDialog;
    TextView idTV ;
    ImageView imv ;
    RelativeLayout rl ;
    ImageView imvLogo ;
    static RecyclerView lv ;
    static ProduitsAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout relativeLayout;
    RelativeLayout cellRelativeLayout;
    RelativeLayout productTitleRelativeLayout;
    static List<Produit> lp ;
    ProduitBDD produitBdd;

    public MainFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);
        // Inflate the layout for this fragment
        imv = (ImageView) view.findViewById(R.id.imageView);
        rl = (RelativeLayout) view.findViewById(R.id.activity_main);
        imvLogo = (ImageView) view.findViewById(R.id.imageView2);
        imvLogo.setScaleType(ImageView.ScaleType.FIT_START);
        lv = (RecyclerView) view.findViewById(R.id.recyclerView);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.activity_main);
        cellRelativeLayout = (RelativeLayout) view.findViewById(R.id.cellBackground);
        productTitleRelativeLayout = (RelativeLayout) view.findViewById(R.id.secondCellColor);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        mLayoutManager = new LinearLayoutManager(getActivity());

//create the drawer and remember the `Drawer` result object
        //getMenu();

        MenuBDD menuBDD = new MenuBDD(getActivity().getApplicationContext());
        menuBDD.open();

        Menu m = menuBDD.getMenu();

        produitBdd = new ProduitBDD(getActivity().getBaseContext());
        produitBdd.open();


        Bundle b = getArguments();
        String categorie = b.getString("elist");
        System.out.println("CATEGORRRIEEE "+categorie);
        lp = produitBdd.getProductsWithCategory(categorie);


        if (m != null) {


            // Toast.makeText(this, lp.toString(), Toast.LENGTH_LONG).show();
            System.out.println("kejrkejkrjkejr");
            System.out.println(lp.toString());
            System.out.println(m.toString());
            //System.out.println("http://+"+ip+"/BMMA/var/uploads/brochures/" + m.getTitretxt());

            //details += "ID: " + id + "\n logo "+ imgLog ;
            Picasso.with(getActivity().getApplicationContext()).load("http://" + MainActivity.ip + "/uploads/brochures/brochures" + m.getLogimg()).into(imvLogo);
            if (m.getTemplate().equals("template1")) {


                // relativeLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_blur));


            } else if (m.getTemplate().equals("template2")) {

                if (m.getTheme().equals("1")) {

                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.theme1_background));

                } else if (m.getTheme().equals("2")) {
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.theme2_background));


                } else if (m.getTheme().equals("3")) {

                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.theme3_background));
                }

            }

            if (m.getLogoemp().equals("Left")) {


                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imvLogo.getLayoutParams());


                layoutParams.leftMargin = 20;
                layoutParams.topMargin = 80;


                imvLogo.setLayoutParams(layoutParams);
                imvLogo.invalidate();
            } else if (m.getLogoemp().equals("Right")) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imvLogo.getLayoutParams());


                layoutParams.leftMargin = 500;
                layoutParams.topMargin = 80;


                imvLogo.setLayoutParams(layoutParams);
                imvLogo.invalidate();
            } else if (m.getLogoemp().equals("Center")) {

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imvLogo.getLayoutParams());


                layoutParams.leftMargin = 230;


                imvLogo.setLayoutParams(layoutParams);
                imvLogo.invalidate();


            }


        }
        adapter = new ProduitsAdapter(lp);
        System.out.println("looolkl");
        lv.setAdapter(adapter);
        System.out.println("looolkl");
        lv.setLayoutManager(mLayoutManager);
        System.out.println("looolkl");

        return view;
    }






}
