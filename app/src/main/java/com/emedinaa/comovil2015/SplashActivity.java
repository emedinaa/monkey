package com.emedinaa.comovil2015;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.emedinaa.comovil2015.R;
import com.emedinaa.comovil2015.model.entity.PokemonEntity;
import com.emedinaa.comovil2015.model.entity.TypeEntity;
import com.emedinaa.comovil2015.presenter.PokemonPresenter;
import com.emedinaa.comovil2015.storage.TypePokemonCrud;
import com.emedinaa.comovil2015.view.core.BaseView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements BaseView {

    private static final String TAG = "SplashActivity";
    @Bind(R.id.rlayLoading) View rlayLoading;

    private PokemonPresenter pokemonPresenter;
    private List<TypeEntity> lsTypeEntities;
    private TypePokemonCrud typePokemonCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        typePokemonCrud= new TypePokemonCrud(this);
        loadTypes();
    }

    private void loadTypes() {
        pokemonPresenter= new PokemonPresenter(this,this);
        showLoading(true);
        pokemonPresenter.loadTypesPokemon();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(typePokemonCrud!=null)
        {
            typePokemonCrud.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_splash, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);*/
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
        Log.v(TAG, "completeSuccess " + object + " type " + type);
        switch (type) {
            case 102:
                lsTypeEntities= (List<TypeEntity>)(object);
                if (lsTypeEntities != null) {
                    typePokemonCrud.saveList(lsTypeEntities);
                }
                gotoMain();
                break;
        }
    }

    private void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void completeError(Object object, int type) {
        showLoading(false);
        Log.v(TAG, "completeError " + object + " type " + type);
    }
}
