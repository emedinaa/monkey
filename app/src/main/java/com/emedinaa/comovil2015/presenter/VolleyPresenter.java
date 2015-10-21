package com.emedinaa.comovil2015.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.emedinaa.comovil2015.R;
import com.emedinaa.comovil2015.model.entity.SpeakerEntity;
import com.emedinaa.comovil2015.model.entity.SpeakerResponse;
import com.emedinaa.comovil2015.view.core.BaseView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by emedinaa on 21/09/15.
 */
public class VolleyPresenter {

    private static final String TAG ="VolleyPresenter";
    private RequestQueue queue;
    private List<SpeakerEntity> dataSpeaker;
    private Context context;
    private BaseView view;

    public VolleyPresenter(BaseView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void loadSpeakers()
    {
        dataSpeaker= new ArrayList<SpeakerEntity>();
        queue = Volley.newRequestQueue(context);

        String url = context.getString(R.string.url_speaker_get);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        SpeakerResponse objects = gson.fromJson(response.toString(), SpeakerResponse.class);

                        dataSpeaker= objects.getResults();
                        view.completeSuccess(dataSpeaker,100);
                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Speaker", "Error: " + error.getMessage());
                // hide the progress dialog
                view.completeError(dataSpeaker,100);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Parse-Application-Id", context.getString(R.string.application_id));
                params.put("X-Parse-REST-API-Key", context.getString(R.string.rest_api_key));

                return params;
            }
        };
        queue.add(jsonObjReq);

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

    public void addSpeaker(String name, String lastName,String skyll)
    {
        queue = Volley.newRequestQueue(context);

        String url = context.getString(R.string.url_speaker_get);
        SpeakerEntity request= new SpeakerEntity();
        request.setName(name);
        request.setLastname(lastName);
        request.setSkill(skyll);
        JSONObject params= toJSONObject(request);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG,"add speaker response "+ response.toString());
                        view.completeSuccess(response, 100);

                    }
                }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "add speaker Error: " + error.getMessage());
                // hide the progress dialog
                view.completeSuccess(error, 100);

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-Parse-Application-Id", context.getString(R.string.application_id));
                params.put("X-Parse-REST-API-Key", context.getString(R.string.rest_api_key));

                return params;
            }
        };
        queue.add(jsonObjReq);
    }

}
