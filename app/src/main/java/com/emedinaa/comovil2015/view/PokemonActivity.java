package com.emedinaa.comovil2015.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.emedinaa.comovil2015.R;
import com.emedinaa.comovil2015.model.entity.PokemonEntity;
import com.emedinaa.comovil2015.storage.TypePokemonCrud;
import com.emedinaa.comovil2015.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PokemonActivity extends Activity {

    @Bind(R.id.eTxtName) EditText eTxtName;
    @Bind(R.id.eTxtType1) EditText eTxtType1;
    @Bind(R.id.eTxtType2) EditText eTxtType2;
    @Bind(R.id.iviPokemon) ImageView iviPokemon;

    private PokemonEntity pokemonEntity;
    private TypePokemonCrud typePokemonCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        ButterKnife.bind(this);
        extras();
        init();
    }

    private void init() {

        typePokemonCrud= new TypePokemonCrud(this);
        if(pokemonEntity!=null)
        {
            String name = pokemonEntity.getName();
            int type1 = pokemonEntity.getType1();
            int type2 = pokemonEntity.getType2();
            String mType1= typePokemonCrud.getTypeById(type1).getName();
            String mType2= typePokemonCrud.getTypeById(type2).getName();

            String url= pokemonEntity.getPhotoPath();

            eTxtName.setText(name);
            eTxtType1.setText(mType1);
            eTxtType2.setText(mType2);

            if(!url.isEmpty())
            {
                Picasso.with(iviPokemon.getContext())
                        .load(url)
                        .transform(new CircleTransform())
                        .into(iviPokemon);
            }else
            {
                Picasso.with(iviPokemon.getContext())
                        .load(R.drawable.default_user)
                        .resize(200, 200)
                        .transform(new CircleTransform())
                        .into(iviPokemon);
            }
        }

    }

    private void extras() {
        if(getIntent()!=null)
        {
            Bundle bundle =getIntent().getExtras();
            if(bundle!=null)
            {
                pokemonEntity=(PokemonEntity)bundle.getSerializable("ENTITY");
            }

        }
    }
}
