package com.emedinaa.comovil2015;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.emedinaa.comovil2015.R;
import com.emedinaa.comovil2015.model.response.PokemonResponse;
import com.emedinaa.comovil2015.request.MonkeyApiClient;
import com.emedinaa.monkeyandroid.Callback;
import com.emedinaa.monkeyandroid.Response;
import com.emedinaa.monkeyandroid.http.Body;
import com.emedinaa.monkeyandroid.http.GET;
import com.emedinaa.monkeyandroid.http.POST;
import com.emedinaa.monkeyandroid.http.Headers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class MainExampleActivity extends AppCompatActivity {

    private static final String TAG ="MainExampleActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_example);
        init();
    }

    private void init() {

        Class api= MonkeyApiClient.PokemonApiInterface.class;
        for (Method method : api.getDeclaredMethods())
        {
            System.out.println("method "+method.getName());
            Log.v(TAG, "method "+method.getName());

            Annotation[] nAnnotations = method.getDeclaredAnnotations();
            for(Annotation annotation : nAnnotations){
                if(annotation instanceof GET)
                {
                    GET myGET = (GET) annotation;
                    System.out.println("GET value "+myGET.value());
                    Log.v(TAG, "GET value " + myGET.value());

                }else if (annotation instanceof POST)
                {
                    POST myPOST = (POST) annotation;
                    System.out.println("POST value "+myPOST.value());
                    Log.v(TAG, "POST value " + myPOST.value());

                }else if(annotation instanceof  Headers)
                {
                    Headers myHeaders = (Headers) annotation;
                    System.out.println("Headers value "+myHeaders.value());
                    Log.v(TAG, "Headers value " + myHeaders.value());

                }else if(annotation instanceof Body)
                {
                    Body myBody = (Body) annotation;
                    System.out.println("Body value "+myBody.toString());
                    Log.v(TAG, "Body value " + myBody.toString());
                }
            }
        }

        MonkeyApiClient.getPokemonApiClient().loadPokemons(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Response<PokemonResponse> response) {

            }

            @Override
            public void onFailure(Throwable t) {

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
