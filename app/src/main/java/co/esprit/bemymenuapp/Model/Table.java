package co.esprit.bemymenuapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bof on 27/11/2016.
 */

public class Table {
        @SerializedName("Num")
        private String Num;

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }
}
