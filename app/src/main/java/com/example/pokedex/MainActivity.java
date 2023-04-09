package com.example.pokedex;

import static android.content.ContentValues.TAG;


import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pokedex.Service.ListPokemon;
import com.example.pokedex.adapter.PokemonListAdapter;
import com.example.pokedex.api.PokemonApi;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.RetrofitSingleTon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PokeInfo";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private PokemonListAdapter pokemonListAdapter;

    private int offset;

    private boolean load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pokemonListAdapter = new PokemonListAdapter(this);
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (load) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            load = false;
                            offset += 20;
                            getPokemons(offset);
                        }
                    }
                }
            }
        });


        retrofit = RetrofitSingleTon.getRetrofitInstance();
        load = true;
        offset = 0;
        getPokemons(offset);
    }

    private void getPokemons(int offset){

        PokemonApi pokemonApi = retrofit.create(PokemonApi.class);
        Call<ListPokemon> call = pokemonApi.getListPokemon(20,offset);

        call.enqueue(new Callback<ListPokemon>() {
            @Override
            public void onResponse(Call<ListPokemon> call, Response<ListPokemon> response) {
                load = true;
                if(!response.isSuccessful()){
                    Log.e(TAG, " onResponse: "+response.errorBody());
                    return;
                }
                ListPokemon poke = response.body();
                List<Pokemon> list = poke.getResults();

                pokemonListAdapter.addListPokemons(list);
            }

            @Override
            public void onFailure(Call<ListPokemon> call, Throwable t) {
                load = true;
                Log.e(TAG, " onFailure: "+t.getMessage());
            }
        });

    }
}