package co.esprit.bemymenuapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import co.esprit.bemymenuapp.DB.MenuBDD;
import co.esprit.bemymenuapp.Model.Menu;
import co.esprit.bemymenuapp.Model.Produit;

public class DetailActivity extends AppCompatActivity {
    ImageView img;
    TextView pseudo,description,prix;

    ConstraintLayout relativeLayout;
    Produit produit;
    ImageButton soundButton;
    SharedPreferences pf;
    TextToSpeech t1;
    ImageButton share;
    public void shareItem(String url) {
        Picasso.with(getApplicationContext()).load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                startActivity(Intent.createChooser(i, "Share Image"));
            }
            @Override public void onBitmapFailed(Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }
    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img=(ImageView)findViewById(R.id.image);
        pseudo=(TextView)findViewById(R.id.pseudo);
        description=(TextView)findViewById(R.id.description);
        prix=(TextView)findViewById(R.id.prix);
        soundButton=(ImageButton)findViewById(R.id.soundButton);
        share=(ImageButton)findViewById(R.id.imageButton3);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.FRANCE);
                }
            }
        });

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSpeak = pseudo.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);





            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               shareItem("http://" + MainActivity.ip + "/uploads/brochures/brochures/" + produit.getImage());
            }
        });
        relativeLayout = (ConstraintLayout) findViewById(R.id.activity_detail);
         produit =(Produit) getIntent().getSerializableExtra("produit");
        System.out.println("kfjkfjdkjfd");


        MenuBDD menuBDD=new MenuBDD(getApplicationContext());
        menuBDD.open();

        Menu m =  menuBDD.getMenu();

        if(m!=null)
        {
         if (m.getTemplate().equals("template1")) {


                relativeLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_blur));
                prix.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_white_1000));
                pseudo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_white_1000));
                description.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_white_1000));


            } else if (m.getTemplate().equals("template2")) {

                if (m.getTheme().equals("1")) {

                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.theme1_background));



                } else if (m.getTheme().equals("2")) {
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.theme2_background));


                } else if (m.getTheme().equals("3")) {

                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.theme3_background));

                }

            }






        }
        else{
            System.out.println("mdrrrr");
        }
        Picasso.with(getApplicationContext()).load("http://" + MainActivity.ip + "/uploads/brochures/brochures/" + produit.getImage()).into(img);
        pseudo.setText(produit.getNom());
        description.setText(produit.getPrix()+"$");
       // prix.setText(produit.getDescription());
        prix.setText("Une préparation autour du haddock, il y a du haddock fumé, une mousseline de haddock, également quelques pommes de terre cuites dans une soupe de poisson légèrement safranée et l’on vous a servi une crème de haddock qui est chaude");
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_panier_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


       /* if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }*/
        // Handle your other action bar items...

        switch (item.getItemId()) {


            case R.id.addpanierMenu:
                pf = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor e = pf.edit();
                if (pf.getString(produit.getNom().toString(), "").equals("")) {
                    e.putString(produit.getNom().toString(), "1");

                } else {
                    int quantite = Integer.parseInt(pf.getString(produit.getNom().toString(), ""));
                    quantite++;
                    e.putString(produit.getNom().toString(), quantite + "");
                }
                Toast.makeText(getApplicationContext(), "item added", Toast.LENGTH_SHORT).show();
                e.commit();

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
