package co.esprit.bemymenuapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.InputStream;

/**
 * Created by bof on 06/11/2016.
 */
 class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ProgressDialog mDialog;
    private ImageView bmImage;
    private RelativeLayout rl ;
    private int flag ;

    public DownloadImageTask(ImageView bmImage , RelativeLayout rl , int flag ) {
        this.bmImage = bmImage;
        this.rl = rl ;
        this.flag = flag;

    }

    protected void onPreExecute() {


    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", "image download error");
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        //set image of your imageview
        bmImage.setImageBitmap(result);
        if(flag == 0){
            rl.setBackground(bmImage.getDrawable());
        }


        //close

    }
}
