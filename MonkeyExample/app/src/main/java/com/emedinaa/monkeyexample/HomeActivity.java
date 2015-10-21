package com.emedinaa.monkeyexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.emedinaa.monkeyandroid.Callback;
import com.emedinaa.monkeyandroid.Response;
import com.emedinaa.monkeyandroid.http.GET;
import com.emedinaa.monkeyandroid.http.POST;
import com.emedinaa.monkeyexample.request.ApiClient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class HomeActivity extends ActionBarActivity {

    private static final String TAG ="HomeActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {

        Class api= ApiClient.MyApiInterface.class;
        for (Method method : api.getDeclaredMethods())
        {
            System.out.println("method "+method.getName());
            Annotation[] nAnnotations = method.getDeclaredAnnotations();
            for(Annotation annotation : nAnnotations){
                if(annotation instanceof GET)
                {
                    GET myGET = (GET) annotation;
                    System.out.println("value "+myGET.value());
                    Log.v(TAG, "GET value " + myGET.value());

                }else if (annotation instanceof POST)
                {
                    POST myPOST = (POST) annotation;
                    System.out.println("value "+myPOST.value());
                    Log.v(TAG, "POST value " + myPOST.value());
                }
            }
        }

        ApiClient.getApiInterface().login("user001", new Callback<Object>() {
            @Override
            public void onResponse(Response<Object> response) {

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
