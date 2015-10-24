package com.emedinaa.monkeyexample.request;

import android.content.Context;

import com.emedinaa.monkeyandroid.MCallback;
import com.emedinaa.monkeyandroid.Monkey;
import com.emedinaa.monkeyandroid.http.MBody;
import com.emedinaa.monkeyandroid.http.MDELETE;
import com.emedinaa.monkeyandroid.http.MGET;
import com.emedinaa.monkeyandroid.http.MHeaders;
import com.emedinaa.monkeyandroid.http.MPOST;
import com.emedinaa.monkeyandroid.http.MPUT;
import com.emedinaa.monkeyandroid.http.MPath;

/**
 * Created by emedinaa on 20/10/15.
 */
public class MonkeyApiClient {

    private static final String PATH="https://api.parse.com";
    private static PokemonApiInterface pokemonApiInterface;

    public static PokemonApiInterface getPokemonApiClient(Context context) {
        if (pokemonApiInterface == null) {
            Monkey monkey = new Monkey.Builder()
                    .setContext(context)
                    .setEndpoint(PATH)
                    .build();
            pokemonApiInterface= monkey.create(PokemonApiInterface.class);
        }
        return pokemonApiInterface;
    }

    public interface PokemonApiInterface {

        @MHeaders({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
        @MGET("/1/classes/Pokemon")
        void loadPokemons(MCallback<String> callback);

        @MHeaders({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
        @MGET("/1/classes/Type")
        void loadTypesPokemon(MCallback<String> callback);

        @MHeaders({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW",
                "Content-Type: application/json"})
        @MPOST("/1/classes/Pokemon")
        void addPokemon(@MBody Object json,MCallback<String> callback);

        @MHeaders({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW",
                "Content-Type: application/json"})
        @MPUT("/1/classes/Pokemon/{objectId}")
        void updatePokemon(@MPath("objectId") String objectId,@MBody Object json,MCallback<String> callback);

        @MHeaders({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
        @MDELETE("/1/classes/Pokemon/{objectId}")
        void deletePokemon(@MPath("objectId") String objectId,MCallback<String> callback);
    }
}
