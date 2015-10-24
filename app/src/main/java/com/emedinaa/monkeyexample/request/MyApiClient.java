package com.emedinaa.monkeyexample.request;

import com.emedinaa.monkeyandroid.MCallback;
import com.emedinaa.monkeyexample.model.response.PokemonAddResponse;
import com.emedinaa.monkeyexample.model.response.PokemonResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by emedinaa on 20/10/15.
 */
public class MyApiClient {

    private static final String PATH="https://api.parse.com";
    private static PokemonApiInterface pokemonApiInterface;

    public static PokemonApiInterface getPokemonApiClient() {
        if (pokemonApiInterface == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(PATH)
                    .build();
            pokemonApiInterface= restAdapter.create(PokemonApiInterface.class);
        }
        return pokemonApiInterface;
    }


    public interface PokemonApiInterface {

        @Headers({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
        @GET("/1/classes/Pokemon")
        void loadPokemons(Callback<PokemonResponse> callback);

        @Headers({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
        @GET("/1/classes/Type")
        void loadTypesPokemon(Callback<Object> callback);

        @Headers({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW",
                "Content-Type: application/json"})
        @POST("/1/classes/Pokemon")
        void addPokemon(@Body Object json, Callback<PokemonAddResponse> callback);

        @Headers({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW",
                "Content-Type: application/json"})
        @PUT("/1/classes/Pokemon/{objectId}")
        void updatePokemon(@Path("objectId") String objectId, @Body Object json, Callback<Object> callback);

        @Headers({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
        @DELETE("/1/classes/Pokemon/{objectId}")
        void deletePokemon(@Path("objectId") String objectId, Callback<Object> callback);
    }
}
