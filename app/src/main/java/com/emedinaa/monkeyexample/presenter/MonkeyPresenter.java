package com.emedinaa.monkeyexample.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonStringRequest;
import com.android.volley.toolbox.Volley;
import com.emedinaa.monkeyandroid.MCallback;
import com.emedinaa.monkeyexample.R;
import com.emedinaa.monkeyexample.model.entity.PokemonEntity;
import com.emedinaa.monkeyexample.model.entity.TypeEntity;
import com.emedinaa.monkeyexample.model.response.PokemonResponse;
import com.emedinaa.monkeyexample.model.response.TypePokemonResponse;
import com.emedinaa.monkeyexample.request.MonkeyApiClient;
import com.emedinaa.monkeyexample.view.core.BaseView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by emedinaa on 21/09/15.
 */
public class MonkeyPresenter {

    private static final String TAG ="MonkeyPresenter";
    private List<PokemonEntity> dataPokemon;
    private List<TypeEntity> typePokemon;
    private Context context;
    private BaseView view;
    private RequestQueue queue;

    public MonkeyPresenter(BaseView view, Context context) {
        this.view = view;
        this.context = context;
    }


    public void loadPokemons()
    {
        dataPokemon = new ArrayList<PokemonEntity>();
        MonkeyApiClient.getPokemonApiClient(this.context).loadPokemons(new MCallback<String>() {

            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                PokemonResponse objects = gson.fromJson(response, PokemonResponse.class);

                Log.v(TAG, "Response : " + objects);
                dataPokemon = objects.getResults();
                view.completeSuccess(dataPokemon, 100);
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.v(TAG, "Error: " + volleyError);
                view.completeError(dataPokemon, 100);
            }
        });

    }

    public void loadTypesPokemon()
    {
        typePokemon = new ArrayList<TypeEntity>();

        MonkeyApiClient.getPokemonApiClient(this.context).loadTypesPokemon(new MCallback<String>() {

            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                TypePokemonResponse objects = gson.fromJson(response, TypePokemonResponse.class);
                Log.v(TAG, objects.toString());
                typePokemon = objects.getResults();
                view.completeSuccess(typePokemon, 102);
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.v(TAG, "Error: " + volleyError);
                view.completeError(dataPokemon, 102);
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

    public void addPokemon(String name, int type1,int type2)
    {

        PokemonEntity pokemonEntity= new PokemonEntity();
        pokemonEntity.setName(name);
        pokemonEntity.setType1(type1);
        pokemonEntity.setType1(type2);

        JSONObject params= toJSONObject(pokemonEntity);
        MonkeyApiClient.getPokemonApiClient(this.context).addPokemon(params, new MCallback<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, "add pokemon response " + response);

                Log.v(TAG, "add pokemon response " + response);
                view.completeSuccess(response, 100);
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.v(TAG, "add pokemon onFailure " + volleyError);
                view.completeError(volleyError, 100);
            }
        });
        /*queue = Volley.newRequestQueue(context);

        String url = context.getString(R.string.url_pokemon_get);
        PokemonEntity request= new PokemonEntity();
        request.setName(name);
        request.setType1(type1);
        request.setType2(type2);
        JSONObject params= toJSONObject(request);

        JsonStringRequest jsonStringRequest= new JsonStringRequest(Request.Method.POST,
                url,params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "add pokemon response " + response);
                        view.completeSuccess(response, 100);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.completeError(error, 100);

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Parse-Application-Id", context.getString(R.string.application_id));
                params.put("X-Parse-REST-API-Key", context.getString(R.string.rest_api_key));

                return params;
            }
        };
        queue.add(jsonStringRequest);
        */
    }

    public void updatePokemon(PokemonEntity pokemonEntity, JSONObject params)
    {
        String objectId = pokemonEntity.getObjectId();

        MonkeyApiClient.getPokemonApiClient(this.context).updatePokemon(objectId, params, new MCallback<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, "update pokemon response " + response);
                view.completeSuccess(response, 100);
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.v(TAG, "update pokemon error " + volleyError);
                view.completeError(volleyError, 100);
            }
        });
        /*queue = Volley.newRequestQueue(context);

        String url = context.getString(R.string.url_pokemon_get)+"/"+pokemonEntity.getObjectId();

        JsonStringRequest jsonStringRequest= new JsonStringRequest(Request.Method.PUT,
                url,params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "update pokemon response " + response);
                        view.completeSuccess(response, 100);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.completeError(error, 100);

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Parse-Application-Id", context.getString(R.string.application_id));
                params.put("X-Parse-REST-API-Key", context.getString(R.string.rest_api_key));

                return params;
            }
        };
        queue.add(jsonStringRequest);*/
    }

}
