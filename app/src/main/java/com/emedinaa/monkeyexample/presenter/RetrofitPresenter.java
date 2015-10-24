package com.emedinaa.monkeyexample.presenter;

import android.content.Context;
import android.util.Log;

import com.emedinaa.monkeyexample.model.entity.PokemonEntity;
import com.emedinaa.monkeyexample.model.response.PokemonAddResponse;
import com.emedinaa.monkeyexample.model.response.PokemonResponse;
import com.emedinaa.monkeyexample.request.MyApiClient;
import com.emedinaa.monkeyexample.view.core.BaseView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.Callback;

/**
 * Created by emedinaa on 21/09/15.
 */
public class RetrofitPresenter {

    private static final String TAG ="RetrofitPresenter";
    private List<PokemonEntity> pokemonSpeaker;
    private Context context;
    private BaseView view;

    public RetrofitPresenter(BaseView view, Context context) {
        this.view = view;
        this.context = context;
    }
    public void loadPokemons()
    {
        pokemonSpeaker = new ArrayList<PokemonEntity>();
        MyApiClient.getPokemonApiClient().loadPokemons(new Callback<PokemonResponse>() {
            @Override
            public void success(PokemonResponse pokemonResponse, Response response) {
                Log.v(TAG, "speaker success " + pokemonResponse);
                Log.v(TAG, "response " + response.getHeaders().toString());

                pokemonSpeaker = pokemonResponse.getResults();
                view.completeSuccess(pokemonSpeaker, 101);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v(TAG, "speaker error " + error);
                view.completeError(pokemonSpeaker, 101);
            }
        });
    }
    public void addPokemon(PokemonEntity pokemonEntity)
    {
        MyApiClient.getPokemonApiClient().addPokemon(pokemonEntity, new Callback<PokemonAddResponse>() {
            @Override
            public void success(PokemonAddResponse pokemonAddResponse, Response response) {
                Log.v(TAG, "pokemon add success " + pokemonAddResponse);
                Log.v(TAG, "response " + response.getHeaders().toString());

                view.completeSuccess(pokemonAddResponse, 101);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v(TAG, "speaker add error " + error);
                view.completeError(error, 101);
            }
        });
    }

    private JSONObject toJSONObject(PokemonEntity pokemonEntity)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("name", pokemonEntity.getName());
            jsonObject.put("type1", pokemonEntity.getType1());
            jsonObject.put("type2", pokemonEntity.getType2());
        }catch (JSONException e)
        {
        }
        return jsonObject;
    }

}
