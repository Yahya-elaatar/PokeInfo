package com.example.pokedex.api;

import com.example.pokedex.Service.ListPokemon;
import com.example.pokedex.model.PokemonInfo;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonApi {
    @GET("pokemon")
    Call<ListPokemon> getListPokemon(@Query("limit") int limit, @Query("offset") int offset);
    @GET("pokemon/{id}")
    Call<PokemonInfo> getPokemonInfo(@Path("id") int id);
}
