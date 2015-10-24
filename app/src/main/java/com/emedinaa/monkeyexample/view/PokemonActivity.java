package com.emedinaa.monkeyexample.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.emedinaa.monkeyexample.storage.TypePokemonCrud;
import com.emedinaa.monkeyexample.utils.CircleTransform;
import com.emedinaa.monkeyexample.view.adapters.TypeAdapter;
import com.emedinaa.monkeyexample.view.core.BaseView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    @Bind(R.id.spType1)Spinner spType1;
    @Bind(R.id.spType2)Spinner spType2;

    private PokemonEntity pokemonEntity;
    private PokemonPresenter pokemonPresenter;
    private MonkeyPresenter monkeyPresenter;
    private TypePokemonCrud typePokemonCrud;

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
        populate();
    }

    private void populate() {
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
                        params.put("type1",type1);
                        params.put("type2",type2);
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
        if(name.isEmpty())return false;
        if(type1<0)return false;
        if(type2<0)return false;
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
