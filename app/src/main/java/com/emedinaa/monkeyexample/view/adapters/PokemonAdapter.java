package com.emedinaa.monkeyexample.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emedinaa.monkeyexample.R;
import com.emedinaa.monkeyexample.model.entity.PokemonEntity;
import com.emedinaa.monkeyexample.model.entity.TypeEntity;
import com.emedinaa.monkeyexample.storage.TypePokemonCrud;
import com.emedinaa.monkeyexample.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by emedinaa on 21/09/15.
 */
public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder>  {

    private List<PokemonEntity> pokemonEntityList;
    private Context context;
    private TypePokemonCrud typePokemonCrud;

    public PokemonAdapter(Context context, List<PokemonEntity> pokemonEntityList) {
        this.context=context;
        this.pokemonEntityList = pokemonEntityList;
        typePokemonCrud= new TypePokemonCrud(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pokemon, parent, false);
        ViewHolder itemHolder = new ViewHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final PokemonEntity pokemonEntity= pokemonEntityList.get(position);
        int type1=pokemonEntity.getType1();
        int type2=pokemonEntity.getType2();
        TypeEntity typeEntity1=  typePokemonCrud.getTypeById(type1);
        TypeEntity typeEntity2=  typePokemonCrud.getTypeById(type2);
        String sType1=(typeEntity1!=null)?(typeEntity1.getName()):("");//Integer.toString(pokemonEntity.getType1());
        String sType2 =(typeEntity2!=null)?(typeEntity2.getName()):("");// Integer.toString(pokemonEntity.getType2());
        holder.tviName.setText(pokemonEntity.getName());
        holder.tviType1.setText(sType1);
        holder.tviType2.setText(sType2);

        String url= pokemonEntity.getPhotoPath();
        if(!url.isEmpty())
        {
            Picasso.with(holder.iviPokemon.getContext())
                    .load(url)
                    .transform(new CircleTransform())
                    .into(holder.iviPokemon);
            /*
             Picasso.with(holder.iviPokemon.getContext())
                    .load(url)
                    .resize(200, 200)
                    .transform(new CircleTransform())
                    .into(holder.iviPokemon);
             */
        }else
        {
            Picasso.with(holder.iviPokemon.getContext())
                    .load(R.drawable.default_pokemon)
                    .resize(200, 200)
                    .transform(new CircleTransform())
                    .into(holder.iviPokemon);
        }

    }

    @Override
    public int getItemCount() {
        return pokemonEntityList.size();
    }

    public void clear()
    {
        pokemonEntityList.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iviPokemon;
        TextView tviName;
        TextView tviType1;
        TextView tviType2;

        public ViewHolder(View view) {
            super(view);

            iviPokemon = (ImageView)view.findViewById(R.id.iviPokemon);
            tviName = (TextView)view.findViewById(R.id.tviName);
            tviType1 = (TextView)view.findViewById(R.id.tviType1);
            tviType2 = (TextView)view.findViewById(R.id.tviType2);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

