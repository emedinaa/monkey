package com.emedinaa.comovil2015.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.JsonStringRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.emedinaa.comovil2015.R;
import com.emedinaa.comovil2015.model.entity.PokemonEntity;
import com.emedinaa.comovil2015.model.entity.SpeakerEntity;
import com.emedinaa.comovil2015.model.entity.SpeakerResponse;
import com.emedinaa.comovil2015.model.entity.TypeEntity;
import com.emedinaa.comovil2015.model.response.PokemonResponse;
import com.emedinaa.comovil2015.model.response.TypePokemonResponse;
import com.emedinaa.comovil2015.view.core.BaseView;
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
public class PokemonPresenter {

    private static final String TAG ="PokemonPresenter";
    private RequestQueue queue;
    private List<PokemonEntity> dataPokemon;
    private List<TypeEntity> typePokemon;
    private Context context;
    private BaseView view;

    public PokemonPresenter(BaseView view, Context context) {
        this.view = view;
        this.context = context;
    }

    /*public void load()
    {
        dataPokemon = new ArrayList<PokemonEntity>();
        queue = Volley.newRequestQueue(context);

        String url = context.getString(R.string.url_pokemon_get);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        PokemonResponse objects = gson.fromJson(response.toString(), PokemonResponse.class);

                        dataPokemon = objects.getResults();
                        view.completeSuccess(dataPokemon,100);
                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Speaker", "Error: " + error.getMessage());
                // hide the progress dialog
                view.completeError(dataPokemon,100);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Parse-Application-Id", context.getString(R.string.application_id));
                params.put("X-Parse-REST-API-Key", context.getString(R.string.rest_api_key));

                return params;
            }
        };
        queue.add(jsonObjReq);

    }}*/
    public void loadPokemon()
    {
        dataPokemon = new ArrayList<PokemonEntity>();
        queue = Volley.newRequestQueue(context);

        String url = context.getString(R.string.url_pokemon_get);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                PokemonResponse objects = gson.fromJson(response, PokemonResponse.class);

                dataPokemon = objects.getResults();
                view.completeSuccess(dataPokemon,100);
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(TAG, "Error: " + volleyError.getMessage());
                view.completeError(dataPokemon,100);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Parse-Application-Id", context.getString(R.string.application_id));
                params.put("X-Parse-REST-API-Key", context.getString(R.string.rest_api_key));

                return params;
            }
        };

        queue.add(stringRequest);

    }

    public void loadTypesPokemon()
    {
        typePokemon = new ArrayList<TypeEntity>();
        queue = Volley.newRequestQueue(context);

        String url = context.getString(R.string.url_type_get);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                TypePokemonResponse objects = gson.fromJson(response, TypePokemonResponse.class);
                Log.v(TAG, objects.toString());
                typePokemon = objects.getResults();
                view.completeSuccess(typePokemon,102);
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(TAG, "Error: " + volleyError.getMessage());
                view.completeError(dataPokemon,102);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Parse-Application-Id", context.getString(R.string.application_id));
                params.put("X-Parse-REST-API-Key", context.getString(R.string.rest_api_key));

                return params;
            }
        };

        queue.add(stringRequest);

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
        queue = Volley.newRequestQueue(context);

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

        /*JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "add pokemon response " + response.toString());
                        view.completeSuccess(response, 100);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "add pokemon Error: " + error.getMessage());
                // hide the progress dialog
                view.completeError(error, 100);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Parse-Application-Id", context.getString(R.string.application_id));
                params.put("X-Parse-REST-API-Key", context.getString(R.string.rest_api_key));

                return params;
            }
        };
        queue.add(jsonObjReq);*/
    }

    public void updatePokemon(PokemonEntity pokemonEntity)
    {
        queue = Volley.newRequestQueue(context);

        String url = context.getString(R.string.url_pokemon_get)+"/"+pokemonEntity.getObjectId();
        JSONObject params= new JSONObject();
        try {
            params.put("name","Prueba");
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        queue.add(jsonStringRequest);
    }

}
