package com.emedinaa.comovil2015;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.emedinaa.comovil2015.R;
import com.emedinaa.comovil2015.model.entity.PokemonEntity;
import com.emedinaa.comovil2015.model.response.PokemonResponse;
import com.emedinaa.comovil2015.request.MonkeyApiClient;
import com.emedinaa.monkeyandroid.Callback;
import com.emedinaa.monkeyandroid.Response;
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

        /*
            @MHeaders({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
            @MGET("/1/classes/Pokemon")
            void loadPokemons(Callback<PokemonResponse> callback);
         */
        /*MonkeyApiClient.getPokemonApiClient(this).loadPokemons(new Callback<String>() {

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
        });*/
        PokemonEntity pokemonEntity= new PokemonEntity();
        pokemonEntity.setName("test");
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
