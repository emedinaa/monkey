package com.emedinaa.monkeyexample.view;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.emedinaa.monkeyexample.R;
import com.emedinaa.monkeyexample.model.entity.PokemonEntity;
import com.emedinaa.monkeyexample.model.entity.TypeEntity;
import com.emedinaa.monkeyexample.presenter.MonkeyPresenter;
import com.emedinaa.monkeyexample.presenter.PokemonPresenter;
import com.emedinaa.monkeyexample.presenter.RetrofitPresenter;
import com.emedinaa.monkeyexample.presenter.VolleyPresenter;
import com.emedinaa.monkeyexample.storage.TypePokemonCrud;
import com.emedinaa.monkeyexample.utils.CircleTransform;
import com.emedinaa.monkeyexample.view.adapters.TypeAdapter;
import com.emedinaa.monkeyexample.view.core.BaseView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddPokemonActivity extends ActionBarActivity implements BaseView {

    private static final String TAG ="AddPokemonActivity" ;
    @Bind(R.id.iviPokemon)ImageView iviPokemon;
    @Bind(R.id.eTxtName)EditText eTxtName;
    @Bind(R.id.eTxtType1)EditText eTxtType1;
    @Bind(R.id.eTxtType2)EditText eTxtType2;
    @Bind(R.id.butAddPokemon)View butAddPokemon;
    @Bind(R.id.rlayLoading)View rlayLoading;
    @Bind(R.id.spType1)Spinner spType1;
    @Bind(R.id.spType2)Spinner spType2;

    private TypePokemonCrud typePokemonCrud;
    private String name;
    private int type1;
    private int type2;

    private VolleyPresenter volleyPresenter;
    private RetrofitPresenter retrofitPresenter;
    private PokemonPresenter pokemonPresenter;
    private MonkeyPresenter monkeyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pokemon);
        ButterKnife.bind(this);
        volleyPresenter= new VolleyPresenter(this,this);
        retrofitPresenter= new RetrofitPresenter(this,this);
        pokemonPresenter= new PokemonPresenter(this,this);
        monkeyPresenter= new MonkeyPresenter(this,this);

        Picasso.with(iviPokemon.getContext())
                .load(R.drawable.default_pokemon)
                .resize(200, 200)
                .transform(new CircleTransform())
                .into(iviPokemon);
        events();
        populate();
    }

    private void populate() {
        typePokemonCrud= new TypePokemonCrud(this);
        List<TypeEntity> data= typePokemonCrud.all();

        spType1.setAdapter(new TypeAdapter(this,data));
        spType1.setTag(true);

        spType2.setAdapter(new TypeAdapter(this, data));
        spType2.setTag(true);

        spType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                boolean tag = (boolean) spType1.getTag();
                if (tag) {
                    tag = false;
                    spType1.setTag(tag);
                } else {
                    if (position > 0) {
                        Log.v(TAG, "onItemSelected " + position);
                        TypeEntity typeEntity = (TypeEntity) adapterView.getAdapter().getItem(position);
                        eTxtType1.setText(typeEntity.getName());
                        type1 = typeEntity.getType_id();
                    } else {
                        type1 = -1;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.v(TAG, "onNothingSelected");
            }
        });

        spType2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                boolean tag = (boolean) spType2.getTag();
                if (tag) {
                    tag = false;
                    spType2.setTag(tag);
                } else {
                    if (position > 0) {
                        Log.v(TAG, "onItemSelected " + position);
                        TypeEntity typeEntity = (TypeEntity) adapterView.getAdapter().getItem(position);
                        eTxtType2.setText(typeEntity.getName());
                        type2 = typeEntity.getType_id();
                    } else {
                        type2 = -1;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.v(TAG, "onNothingSelected");
            }
        });
    }

    private void events() {
        butAddPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    showLoading(true);
                    PokemonEntity pokemonEntity= new PokemonEntity();
                    pokemonEntity.setName(name);
                    pokemonEntity.setType1(type1);
                    pokemonEntity.setType2(type2);

                    //retrofitPresenter.addPokemon(pokemonEntity);
                    //volleyPresenter.addPokemon(name,type1,type2);
                    //pokemonPresenter.addPokemon(name,type1,type2);
                    Log.v(TAG, "butAddPokemon add pokemon "+name+" "+type1+" "+type2);
                    monkeyPresenter.addPokemon(name, type1, type2);

                }
            }
        });
    }

    private boolean validate() {
        name= eTxtName.getText().toString().trim();
        if(name.isEmpty())return false ;
        if(type1<0)return false;
        if(type2<0)return false;
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }

    @Override
    public void showLoading(boolean b) {
        int visibility= (b)?(View.VISIBLE):(View.GONE);
        rlayLoading.setVisibility(visibility);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void completeSuccess(Object object, int type) {
        showLoading(false);
        finish();
    }

    @Override
    public void completeError(Object object, int type) {
        showLoading(false);
    }
}
