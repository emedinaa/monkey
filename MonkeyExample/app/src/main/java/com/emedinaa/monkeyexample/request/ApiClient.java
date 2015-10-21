package com.emedinaa.monkeyexample.request;

import com.emedinaa.monkeyandroid.Callback;
import com.emedinaa.monkeyandroid.Monkey;
import com.emedinaa.monkeyandroid.http.Body;
import com.emedinaa.monkeyandroid.http.GET;
import com.emedinaa.monkeyandroid.http.POST;
import com.emedinaa.monkeyandroid.http.Query;

/**
 * Created by emedinaa on 20/10/15.
 */
public class ApiClient {

    private static MyApiInterface myApiInterface;
    public static MyApiInterface getApiInterface()
    {
        if(myApiInterface==null)
        {
            Monkey monkey = new Monkey.Builder()
                    .setEndpoint("https://api.parse.com/1/classes/Speaker")
                    .build();
            myApiInterface= monkey.create(MyApiInterface.class);
        }
        return myApiInterface;
    }

    public interface MyApiInterface
    {
        @GET("/user_rest/login")
        void login(@Query("email") String email, Callback<Object> callback);

        @POST("/user-rest/register")
        void register(@Body Object raw,Callback<Object> callback);
    }
}
