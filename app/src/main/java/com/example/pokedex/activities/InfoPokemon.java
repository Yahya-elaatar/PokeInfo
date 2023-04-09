package com.example.pokedex.activities;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import androidx.appcompat.app.AppCompatActivity;
import com.example.pokedex.R;
import com.example.pokedex.api.PokemonApi;
import com.example.pokedex.model.PokemonInfo;
import com.example.pokedex.model.RetrofitSingleTon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoPokemon extends AppCompatActivity {

    private static final String TAG = "InfoPoke";

    private Retrofit retrofit;

    private ImageView image;
    private TextView name;
    private TextView height;
    private TextView weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        image = (ImageView) findViewById(R.id.item_info_pokemon_icon);
        name = (TextView) findViewById(R.id.item_info_pokemon_name);
        height = (TextView) findViewById(R.id.item_info_pokemon_height);
        weight = (TextView) findViewById(R.id.item_info_pokemon_weight);

        retrofit = RetrofitSingleTon.getRetrofitInstance();

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            String number = extra.getString("number");
            getPokeInfo(Integer.parseInt(number),this);
        }
    }

    public void getPokeInfo(int id, Context context){
        PokemonApi apiPokemon = retrofit.create(PokemonApi.class);
        Call<PokemonInfo> call = apiPokemon.getPokemonInfo(id);

        call.enqueue(new Callback<PokemonInfo>() {
            @Override
            public void onResponse(Call<PokemonInfo> call, Response<PokemonInfo> response) {
                if(!response.isSuccessful()){
                    Log.e(TAG, " onResponse: "+response.errorBody());
                    return;
                }

                PokemonInfo p = response.body();

                Glide.with(context)
                        .load(p.sprites.getFront_default())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image);
                name.setText(p.getName());
                height.setText(String.valueOf(p.getHeight()));
                weight.setText(String.valueOf(p.getWeight()));
            }

            @Override
            public void onFailure(Call<PokemonInfo> call, Throwable t) {
                Log.e(TAG, " onFailure: "+t.getMessage());
            }
        });
    }
}
