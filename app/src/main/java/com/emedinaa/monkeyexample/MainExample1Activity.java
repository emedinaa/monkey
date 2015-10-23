package com.emedinaa.monkeyexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.emedinaa.monkeyexample.R;
import com.emedinaa.monkeyexample.model.entity.PokemonEntity;
import com.emedinaa.monkeyexample.model.response.PokemonResponse;
import com.emedinaa.monkeyexample.request.MonkeyApiClient;
import com.emedinaa.monkeyandroid.Callback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class MainExample1Activity extends ActionBarActivity {

    private static final String TAG = "MExample1Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_example1);
        init();
    }

    private void init() {

        //loadPokemons();
        //addPokemon();
        //updatePokemon();
        //deletePokemon();
    }

    private void deletePokemon() {
        PokemonEntity pokemonEntity= new PokemonEntity();
        //pokemonEntity.setObjectId("VXLwx9dJKi");
        pokemonEntity.setObjectId("FgaczGUZSA");
        pokemonEntity.setName("test");
        pokemonEntity.setType1(2);
        pokemonEntity.setType2(0);

        String objectId = pokemonEntity.getObjectId();

        MonkeyApiClient.getPokemonApiClient(this).deletePokemon(objectId, new Callback<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, "delete pokemon response " + response);
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.v(TAG, "delete pokemon error " + volleyError);
            }
        });
    }

    private void updatePokemon()
    {
        PokemonEntity pokemonEntity= new PokemonEntity();
        pokemonEntity.setObjectId("ovX3oKxWLB");
        pokemonEntity.setName("test");
        pokemonEntity.setType1(2);
        pokemonEntity.setType2(0);

        String objectId = pokemonEntity.getObjectId();
        JSONObject params= new JSONObject();
        try {
            params.put("name","Prueba desde Monkey");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MonkeyApiClient.getPokemonApiClient(this).updatePokemon(objectId, params, new Callback<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, "update pokemon response " + response);
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.v(TAG, "update pokemon error " + volleyError);
            }
        });
    }

    private void addPokemon() {

        PokemonEntity pokemonEntity= new PokemonEntity();
        pokemonEntity.setName("test 3");
        pokemonEntity.setType1(1);
        pokemonEntity.setType1(2);

        JSONObject params= toJSONObject(pokemonEntity);
        MonkeyApiClient.getPokemonApiClient(this).addPokemon(params, new Callback<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, "add pokemon response " + response);
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.v(TAG, "add pokemon onFailure " + volleyError);
            }
        });
    }

    private void loadPokemons() {

        MonkeyApiClient.getPokemonApiClient(this).loadPokemons(new Callback<String>() {

            @Override
            public void onResponse(String response)
            {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                PokemonResponse objects = gson.fromJson(response, PokemonResponse.class);

                Log.v(TAG, "Response : " + objects);
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.v(TAG, "Error: " + volleyError.getMessage());
            }
        });
    }

    private JSONObject toJSONObject(PokemonEntity pokemonEntity)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("name",pokemonEntity.getName());
            jsonObject.put("type1",pokemonEntity.getType1());
            jsonObject.put("type2",pokemonEntity.getType2());
        }catch (JSONException e)
        {

        }
        return jsonObject;
    }

    public static JSONObject generateJSONObject(Object obj)
    {
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(obj));//JsonObject(gson.toJson(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
