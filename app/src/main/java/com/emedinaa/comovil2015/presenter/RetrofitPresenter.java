package com.emedinaa.comovil2015.presenter;

import android.content.Context;
import android.util.Log;

import com.emedinaa.comovil2015.model.entity.SpeakerAddResponse;
import com.emedinaa.comovil2015.model.entity.SpeakerEntity;
import com.emedinaa.comovil2015.model.entity.SpeakerResponse;
import com.emedinaa.comovil2015.request.ApiClient;
import com.emedinaa.comovil2015.view.core.BaseView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by emedinaa on 21/09/15.
 */
public class RetrofitPresenter {

    private static final String TAG ="RetrofitPresenter";
    private List<SpeakerEntity> dataSpeaker;
    private Context context;
    private BaseView view;

    public RetrofitPresenter(BaseView view, Context context) {
        this.view = view;
        this.context = context;
    }
    public void loadSpeakers()
    {
        dataSpeaker= new ArrayList<SpeakerEntity>();
        ApiClient.getSpeakerApiClient().loadSpeakers(new Callback<SpeakerResponse>() {
            @Override
            public void success(SpeakerResponse speakerResponse, Response response) {
                Log.v(TAG, "speaker success " + speakerResponse);
                Log.v(TAG, "response "+response.getHeaders().toString());

                dataSpeaker= speakerResponse.getResults();
                view.completeSuccess(dataSpeaker, 101);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v(TAG, "speaker error " + error);
                view.completeError(dataSpeaker,101);
            }
        });
    }
    public void addSpeaker(SpeakerEntity speakerEntity)
    {

        ApiClient.getSpeakerApiClient().addSpeaker(speakerEntity, new Callback<SpeakerAddResponse>() {
            @Override
            public void success(SpeakerAddResponse speakerAddResponse, Response response) {
                Log.v(TAG, "speaker add success " + speakerAddResponse);
                Log.v(TAG, "response " + response.getHeaders().toString());

                view.completeSuccess(speakerAddResponse, 101);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v(TAG, "speaker add error " + error);
                view.completeError(error, 101);
            }
        });
    }

    private JSONObject toJSONObject(SpeakerEntity speakerEntity)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("name",speakerEntity.getName());
            jsonObject.put("lastname",speakerEntity.getLastname());
            jsonObject.put("skill",speakerEntity.getSkill());
        }catch (JSONException e)
        {

        }
        return jsonObject;
    }

}
