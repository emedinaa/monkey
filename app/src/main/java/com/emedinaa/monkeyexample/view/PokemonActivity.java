package com.emedinaa.monkeyexample.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.emedinaa.monkeyexample.R;
import com.emedinaa.monkeyexample.model.entity.PokemonEntity;
import com.emedinaa.monkeyexample.model.entity.TypeEntity;
import com.emedinaa.monkeyexample.presenter.MonkeyPresenter;
import com.emedinaa.monkeyexample.presenter.PokemonPresenter;
import com.emedinaa.monkeyexample.storage.TypePokemonCrud;
import com.emedinaa.monkeyexample.utils.CircleTransform;
import com.emedinaa.monkeyexample.view.core.BaseView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PokemonActivity extends Activity implements BaseView {

    private static final String TAG = "PokemonActivity";
    @Bind(R.id.eTxtName) EditText eTxtName;
    @Bind(R.id.eTxtType1) EditText eTxtType1;
    @Bind(R.id.eTxtType2) EditText eTxtType2;
    @Bind(R.id.iviPokemon) ImageView iviPokemon;
    @Bind(R.id.butUpdate) View butUpdate;
    @Bind(R.id.rlayLoading) View rlayLoading;


    private PokemonEntity pokemonEntity;
    private TypePokemonCrud typePokemonCrud;
    private PokemonPresenter pokemonPresenter;
    private MonkeyPresenter monkeyPresenter;

    private int type1;
    private int type2;
    private String name;

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
        //presenter= new PokemonPresenter(this,this);
        monkeyPresenter= new MonkeyPresenter(this,this);
        typePokemonCrud= new TypePokemonCrud(this);

        if(pokemonEntity!=null)
        {
            String name = pokemonEntity.getName();
            int type1 = pokemonEntity.getType1();
            int type2 = pokemonEntity.getType2();
            TypeEntity typeEntity1=typePokemonCrud.getTypeById(type1);
            TypeEntity typeEntity2=typePokemonCrud.getTypeById(type2);

            String mType1= (typeEntity1==null)?(""):(typeEntity1.getName());
            String mType2= (typeEntity2==null)?(""):(typeEntity2.getName());
            String url= pokemonEntity.getPhotoPath();

            eTxtName.setText(name);
            eTxtType1.setText(mType1);
            eTxtType2.setText(mType2);

            if(url!=null)
            {
                if(!url.isEmpty())
                {
                    Picasso.with(iviPokemon.getContext())
                            .load(url)
                            .transform(new CircleTransform())
                            .into(iviPokemon);
                }else
                {
                    Picasso.with(iviPokemon.getContext())
                            .load(R.drawable.default_pokemon)
                            .resize(200, 200)
                            .transform(new CircleTransform())
                            .into(iviPokemon);
                }
            }
        }

        butUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading(true);
                if (validate())
                {

                    JSONObject params= new JSONObject();
                    try {
                        params.put("name",name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    monkeyPresenter.updatePokemon(pokemonEntity,params);
                    //pokemonPresenter.updatePokemon(pokemonEntity);
                }
            }
        });

    }

    private boolean validate() {
        name= eTxtName.getText().toString();
        //type1= eTxtType1.getText().toString();
        //type2= eTxtType2.getText().toString();
        if(name.isEmpty())return false;
        return true;
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

    @Override
    public void showLoading(boolean b)
    {
        int visibility= (b)?(View.VISIBLE):(View.GONE);
        rlayLoading.setVisibility(visibility);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void completeSuccess(Object object, int type) {
        Log.v(TAG, "success " + object + " type " + type);
        showLoading(false);
        gotoMain();
    }

    private void gotoMain() {
        finish();
    }

    @Override
    public void completeError(Object object, int type) {
        Log.v(TAG, "error " + object + " type " + type);
        showLoading(false);
    }
}
