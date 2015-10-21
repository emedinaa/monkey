package com.emedinaa.comovil2015.request;

import com.emedinaa.comovil2015.model.entity.SpeakerAddResponse;
import com.emedinaa.comovil2015.model.entity.SpeakerResponse;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

public class ApiClient {

    private static final String PATH="https://api.parse.com";
    private static SpeakerApiInterface speakerApiInterface;

    public static SpeakerApiInterface getSpeakerApiClient() {
        if (speakerApiInterface == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(PATH)
                    .build();
            speakerApiInterface = restAdapter.create(SpeakerApiInterface.class);
        }
        return speakerApiInterface;
    }

    public interface SpeakerApiInterface {

        @Headers({"X-Parse-Application-Id: UPM4jj613e1bShpWh0AD5vstZvVSWC19S0lrcz82","X-Parse-REST-API-Key: r1hdn8GDfsyurkSh2YFQD8D2WlmoCT1QPM45g3XQ"})
        @GET("/1/classes/Speaker")
        void loadSpeakers(Callback<SpeakerResponse> callback);

        @Headers({"X-Parse-Application-Id: UPM4jj613e1bShpWh0AD5vstZvVSWC19S0lrcz82","X-Parse-REST-API-Key: r1hdn8GDfsyurkSh2YFQD8D2WlmoCT1QPM45g3XQ",
        "Content-Type: application/json"})
        @POST("/1/classes/Speaker")
        void addSpeaker(@Body Object json,Callback<SpeakerAddResponse> callback);
    }
}
