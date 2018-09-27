/*
 * Created by Shaon on 8/15/16 8:06 PM
 * Copyright (c) 2016. This is free to use in any software.
 * You must provide developer name on your project.
 */

package co.esprit.bemymenuapp.Service;



import java.util.List;
import retrofit.Call;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import co.esprit.bemymenuapp.Model.Menu;
import co.esprit.bemymenuapp.Model.Panier;
import co.esprit.bemymenuapp.Model.Table;
import co.esprit.bemymenuapp.Model.User;

/**
 * Created by Shaon on 8/15/2016.
 */
public interface APIService {
    @GET("menu/webservices/{user_id}")
    Call<List<Menu>>  getMenu(@Path("user_id") String userID);
    @GET("user/webservices/{login}")
    Call<User>  getUser(@Path("login") String login);
    @GET("panier/webservices/all/{idUser}")
    Call <List<Panier>>  getPanier(@Path("idUser") String idUser);
    @POST("panier/add/{id_produit}/{qte}/{tableNum}")
    Call<List<Table>> setCommand(@Path("id_produit") int id_produit, @Path("qte") int qte, @Path("tableNum") int tableNum);
    @POST("panier/delete/{numTable}/{produit}")
    Call<String> deletePanier(@Path("numTable") String numTable,@Path("produit") String idProduit);
    @GET("menu/updated/{id}")
    Call<List<Menu>> updated(@Path("id") String idMenu);

    @GET("menu/webservices/version/{id}")
    Call<Integer> versioning(@Path("id") String idMenu);
}
