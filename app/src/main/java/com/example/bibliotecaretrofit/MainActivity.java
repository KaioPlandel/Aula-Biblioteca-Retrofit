package com.example.bibliotecaretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bibliotecaretrofit.api.CEPService;
import com.example.bibliotecaretrofit.api.DataService;
import com.example.bibliotecaretrofit.model.CEP;
import com.example.bibliotecaretrofit.model.Fotos;
import com.example.bibliotecaretrofit.model.Post;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

   private Button button;
   private TextView textResp;
   private Retrofit retrofit;
   private List<Fotos> listaFotos = new ArrayList<>();
   private List<Post> listaPostagem = new ArrayList<>();
   private DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textResp = findViewById(R.id.textResp);

        retrofit = new Retrofit.Builder()
                //.baseUrl("https://viacep.com.br/ws/")
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        button.setOnClickListener(v -> {

            //recuperarCepRetrofit();
            //recuperarListaRetrofit();
            //salvarPostagem();
            //atualizarPostagem();
            removerPostagem();
        });



    }

    private void removerPostagem(){
        dataService = retrofit.create(DataService.class);
        Call<Void> call = dataService.deletarPostagem(2);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    textResp.setText("Status: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }



    private void atualizarPostagem(){
        dataService = retrofit.create(DataService.class);

        //Criar objeto postagem
         //Post post = new Post("1234",null,"Corpo postagem");
        Post post = new Post();
        post.setBody("Corpo alterado");

         Call<Post> callAtulizar = dataService.atualizarPostagemPath(2,post);
         callAtulizar.enqueue(new Callback<Post>() {

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Post postResposta = response.body();
                    textResp.setText(" Status: " + response.code() +
                                     " Id: " + postResposta.getId() +
                                     " userId " + postResposta.getUserId() +
                                     " Título: " + postResposta.getTitle() +
                                     " Body: " +postResposta.getBody());

                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }


    private void salvarPostagem(){

        //Criar objeto postagem
       // Post post = new Post("1234","Postagem","Corpo postagem");

        //recupera o serviço e salva a postagem
        DataService dataService = retrofit.create(DataService.class);
        Call<Post> call = dataService.salvarPostagem("1234","Postagem","Corpo postagem");

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Post postResposta = response.body();
                    textResp.setText("Código: " + response.code() + " Id: " + postResposta.getId() + " Título: " + postResposta.getTitle());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void recuperarListaRetrofit(){

        DataService dataService = retrofit.create(DataService.class);
        Call<List<Fotos>> callListaFotos = dataService.recuperarFotos();
        Call<List<Post>> callListaPostagem = dataService.recuperarPostagens();

        callListaPostagem.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    listaPostagem = response.body();

                    for(int i = 0; i < listaPostagem.size(); i++){
                        Post post = listaPostagem.get(i);
                        Log.i("TAG", "onResponse: " + post.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });


//        callListaFotos.enqueue(new Callback<List<Fotos>>() {
//            @Override
//            public void onResponse(Call<List<Fotos>> call, Response<List<Fotos>> response) {
//                if(response.isSuccessful()){
//                    listaFotos = response.body();
//
//                    for(int i = 0; i < listaFotos.size(); i++){
//                        Fotos fotos = listaFotos.get(i);
//                        Log.i("resultado", "resultado: " + fotos.getId() +"/ " + fotos.getTitle());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Fotos>> call, Throwable t) {
//
//            }
//        });


    }

    private void recuperarCepRetrofit(){

        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEP> call = cepService.recuperarCep("01001000");

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if(response.isSuccessful()){
                    CEP cep = response.body();
                    textResp.setText( cep.getcep() +" / "+cep.getBairro()+" / "+cep.getComplemento()+" / "+cep.getLocalidade()+" / "+cep.getLogradouro()+" / "+cep.getUf());
                }

            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });

    }
}