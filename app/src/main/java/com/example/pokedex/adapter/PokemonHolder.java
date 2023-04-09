package com.example.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.activities.InfoPokemon;

import org.jetbrains.annotations.NotNull;

public class PokemonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView iconPokemon;
    TextView idPokemon;
    TextView namePokemon;
    CardView cards;
    Context context;

    public PokemonHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        iconPokemon = (ImageView) itemView.findViewById(R.id.item_pokemon_icon);
        idPokemon = (TextView) itemView.findViewById(R.id.item_pokemon_id);
        namePokemon = (TextView) itemView.findViewById(R.id.item_pokemon_name);
        cards = (CardView) itemView.findViewById(R.id.carts);

    }

    public void setOnClickListener() {
        iconPokemon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), InfoPokemon.class);
        intent.putExtra("number",idPokemon.getText());
        v.getContext().startActivity(intent);
    }
}
