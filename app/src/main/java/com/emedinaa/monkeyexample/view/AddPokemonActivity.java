package com.emedinaa.monkeyexample.view;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.emedinaa.monkeyexample.R;
import com.emedinaa.monkeyexample.model.entity.PokemonEntity;
import com.emedinaa.monkeyexample.presenter.MonkeyPresenter;
import com.emedinaa.monkeyexample.presenter.PokemonPresenter;
import com.emedinaa.monkeyexample.presenter.RetrofitPresenter;
import com.emedinaa.monkeyexample.presenter.VolleyPresenter;
import com.emedinaa.monkeyexample.storage.TypePokemonCrud;
import com.emedinaa.monkeyexample.view.core.BaseView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddPokemonActivity extends ActionBarActivity implements BaseView {

    @Bind(R.id.iviPokemon)ImageView iviPokemon;
    @Bind(R.id.eTxtName)EditText eTxtName;
    @Bind(R.id.eTxtType1)EditText eTxtType1;
    @Bind(R.id.eTxtType2)EditText eTxtType2;
    @Bind(R.id.butAddPokemon)View butAddPokemon;
    @Bind(R.id.rlayLoading)View rlayLoading;
    @Bind(R.id.spType1)Spinner spType1;
    @Bind(R.id.spType2)Spinner spType2;

    private VolleyPresenter volleyPresenter;
    private RetrofitPresenter retrofitPresenter;
    private PokemonPresenter pokemonPresenter;
    private MonkeyPresenter monkeyPresenter;
    private TypePokemonCrud typePokemonCrud;

    private String name;
    private String type1;
    private String type2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pokemon);
        ButterKnife.bind(this);
        volleyPresenter= new VolleyPresenter(this,this);
        retrofitPresenter= new RetrofitPresenter(this,this);
        pokemonPresenter= new PokemonPresenter(this,this);
        monkeyPresenter= new MonkeyPresenter(this,this);

        events();
    }

    private void events() {
        butAddPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    showLoading(true);

                    PokemonEntity pokemonEntity= new PokemonEntity();
                    pokemonEntity.setName(name);
                    pokemonEntity.setType1(1);
                    pokemonEntity.setType2(2);

                    //retrofitPresenter.addSpeaker(speakerEntity);
                    //volleyPresenter.addSpeaker(name,lastName,skill);
                    //pokemonPresenter.addPokemon(name,1,2);
                    monkeyPresenter.addPokemon(name,1,2);
                }
            }
        });
    }

    private boolean validate() {
        name= eTxtName.getText().toString().trim();
        //lastName= eTxtLastName.getText().toString().trim();
        //skill= eTxtSkill.getText().toString().trim();
        if(name.isEmpty())return false ;
        //if(lastName.isEmpty())return false ;
        //if(skill.isEmpty())return false;

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
