package com.emedinaa.monkeyexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.emedinaa.monkeyexample.model.entity.PokemonEntity;
import com.emedinaa.monkeyexample.model.entity.SpeakerEntity;
import com.emedinaa.monkeyexample.presenter.MonkeyPresenter;
import com.emedinaa.monkeyexample.presenter.PokemonPresenter;
import com.emedinaa.monkeyexample.utils.DividerItemDecorator;
import com.emedinaa.monkeyexample.utils.RecyclerItemClickListener;
import com.emedinaa.monkeyexample.view.AddPokemonActivity;
import com.emedinaa.monkeyexample.view.PokemonActivity;
import com.emedinaa.monkeyexample.view.adapters.PokemonAdapter;
import com.emedinaa.monkeyexample.view.core.BaseView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BaseView {

    private static final String TAG = "MainActivity";
    @Bind(R.id.rviPokemon)
    RecyclerView rviPokemon;

    @Bind(R.id.rlayLoading)
    View rlayLoading;

    @Bind(R.id.iviAdd)
    View iviAdd;

    @Bind(R.id.iviRefresh)
    View iviRefresh;

    private RecyclerView.LayoutManager mLayoutManager;
    private PokemonPresenter pokemonPresenter;
    private MonkeyPresenter monkeyPresenter;
    private PokemonAdapter pokemonAdapter;
    private List<SpeakerEntity> data;
    private List<PokemonEntity> dataPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ui();
        pokemonPresenter= new PokemonPresenter(this,this);
        monkeyPresenter= new MonkeyPresenter(this,this);

        //events
        iviAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAdd();
            }
        });

        iviRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading(true);
                if (pokemonAdapter != null) pokemonAdapter.clear();
                //pokemonPresenter.loadPokemon();
                monkeyPresenter.loadPokemon();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //cargar expositores
        showLoading(true);
        //pokemonPresenter.loadPokemon();
        monkeyPresenter.loadPokemon();
    }

    private void gotoAdd() {
        Intent intent= new Intent(this, AddPokemonActivity.class);
        startActivity(intent);
    }

    private void ui() {
        rviPokemon.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rviPokemon.setLayoutManager(mLayoutManager);
        rviPokemon.addItemDecoration(new DividerItemDecorator(this, DividerItemDecorator.VERTICAL_LIST));

        rviPokemon.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        PokemonEntity pokemonEntity= dataPokemon.get(position);
                        gotoPokemon(pokemonEntity);
                    }
                })
        );
    }

    private void gotoPokemon(PokemonEntity pokemonEntity) {

        Bundle bundle= new Bundle();
        bundle.putSerializable("ENTITY",pokemonEntity);

        Intent intent= new Intent(this, PokemonActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void populate(List<PokemonEntity> lsPokemonEntities)
    {
        for (PokemonEntity entity:lsPokemonEntities
             ) {
            Log.v(TAG, "pokemon "+entity.getPhotoPath()+" "+entity.getPhoto());
        }
        pokemonAdapter= new PokemonAdapter(this, lsPokemonEntities);
        rviPokemon.setAdapter(pokemonAdapter);


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
        dataPokemon= (List<PokemonEntity>)(object);
        switch (type)
        {
            case 100:

                    if(dataPokemon!=null)populate(dataPokemon);
                break;
            case 101:
                    if(dataPokemon!=null)populate(dataPokemon);
                break;
        }
        showLoading(false);
    }

    @Override
    public void completeError(Object object, int type) {
        switch (type)
        {
            case 100:
                    Log.v(TAG, "volley complete error " + object);
                break;
            case 101:
                    Log.v(TAG, "retrofit complete error " + object);
                break;
        }
        showLoading(false);
    }
}
