package com.emedinaa.comovil2015;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.emedinaa.comovil2015.R;
import com.emedinaa.comovil2015.model.response.PokemonResponse;
import com.emedinaa.comovil2015.request.MonkeyApiClient;
import com.emedinaa.monkeyandroid.Callback;
import com.emedinaa.monkeyandroid.Response;

public class MainExample1Activity extends ActionBarActivity {

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
        MonkeyApiClient.getPokemonApiClient(this).loadPokemons(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Response<PokemonResponse> response) {

            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onFailure(VolleyError volleyError) {

            }
        });
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
