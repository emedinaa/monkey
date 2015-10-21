package com.emedinaa.comovil2015.request;

import com.emedinaa.comovil2015.model.response.PokemonResponse;
import com.emedinaa.comovil2015.model.response.TypePokemonResponse;
import com.emedinaa.monkeyandroid.Callback;
import com.emedinaa.monkeyandroid.Monkey;
import com.emedinaa.monkeyandroid.http.Body;
import com.emedinaa.monkeyandroid.http.GET;
import com.emedinaa.monkeyandroid.http.Headers;
import com.emedinaa.monkeyandroid.http.POST;

import retrofit.RestAdapter;

/**
 * Created by emedinaa on 20/10/15.
 */
public class MonkeyApiClient {

    private static final String PATH="https://api.parse.com";
    private static PokemonApiInterface pokemonApiInterface;

    public static PokemonApiInterface getPokemonApiClient() {
        if (pokemonApiInterface == null) {
            Monkey monkey = new Monkey.Builder()
                    .setEndpoint("https://api.parse.com/1/classes/Speaker")
                    .build();
            pokemonApiInterface= monkey.create(PokemonApiInterface.class);
        }
        return pokemonApiInterface;
    }
    public interface PokemonApiInterface {

        @Headers({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
        @GET("/1/classes/Pokemon")
        void loadPokemons(Callback<PokemonResponse> callback);

        @Headers({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
        @GET("/1/classes/Type")
        void loadTypesPokemon(Callback<TypePokemonResponse> callback);

        @Headers({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW",
                "Content-Type: application/json"})
        @POST("/1/classes/Pokemon")
        void addPokemon(@Body Object json,Callback<Object> callback);
    }
}
