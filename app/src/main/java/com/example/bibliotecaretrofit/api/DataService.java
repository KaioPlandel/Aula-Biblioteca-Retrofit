package com.example.bibliotecaretrofit.api;

import com.example.bibliotecaretrofit.model.Fotos;
import com.example.bibliotecaretrofit.model.Post;
import com.google.android.material.snackbar.BaseTransientBottomBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DataService {
    @GET("/photos")
    Call<List<Fotos>> recuperarFotos();

    @GET("/posts")
    Call<List<Post>> recuperarPostagens();

    @POST("/posts")
    Call<Post> salvarPostagem(@Body Post post);

    @FormUrlEncoded
    @POST("/posts")
    Call<Post>salvarPostagem(
            @Field("userId") String userId,
            @Field("title") String title,
            @Field("body") String body
    );

    @PUT("/posts/{id}")
    Call<Post> atualizarPostagem(@Path("id") int id, @Body Post post);

    @PATCH("/posts/{id}")
    Call<Post> atualizarPostagemPath(@Path("id") int id, @Body Post post);

    @DELETE("/posts/{id}")
    Call<Void> deletarPostagem(@Path("id") int id);



}
