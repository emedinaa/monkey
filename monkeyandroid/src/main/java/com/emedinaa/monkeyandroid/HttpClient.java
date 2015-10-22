package com.emedinaa.monkeyandroid;

import android.content.Context;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonStringRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by emedinaa on 21/10/15.
 */
public class HttpClient {

    private static final String TAG = "HttpClient";

    public static  final  int GET=0;
    public static  final  int POST=1;
    public static  final  int PUT=2;
    public static  final  int DELETE=3;

    private Context context;
    private String baseURL;
    private RequestQueue queue;

    public HttpClient(Context context, String baseURL) {
        this.context = context;
        this.baseURL = baseURL;
        queue = Volley.newRequestQueue(context);
    }

    public Object execute(int method,String relativeUrl,JSONObject params, String[] paramsHeaders,final Callback<String> callback)
    {
        final Map<String, String>  headers= (paramsHeaders!=null)?(buildHeaders(paramsHeaders)):(new HashMap<String, String>());
        String url = baseURL+relativeUrl;
        int volleyMethod= Request.Method.GET;
        switch (method)
        {
            case GET:
                volleyMethod=Request.Method.GET;//0
                break;
            case POST:
                volleyMethod=Request.Method.POST;//1
                break;
            case PUT:
                volleyMethod=Request.Method.PUT;//2
                break;
            case DELETE:
                volleyMethod=Request.Method.DELETE;//3
                break;
        }
        Log.v(TAG,"-----------------------------------------------------------------------------");
        Log.v(TAG,"method "+method+" relativeUrl "+relativeUrl+" params "+params+" paramsHeaders "+
                paramsHeaders+" callback "+callback);
        Log.v(TAG, "url "+url+" volleyMethod "+volleyMethod);
        Log.v(TAG, "headers "+headers);
        Log.v(TAG,"-----------------------------------------------------------------------------");


        Request<String> request= null;
        if(method==GET)
        {
            request= buildStringRequest(volleyMethod,url,headers,callback);
        }else
        {
            request= buildJsonStringRequest(volleyMethod,url,params,headers,callback);
        }

        if(request!=null)queue.add(request);

        return queue;
    }

    private Request<String> buildStringRequest(int volleyMethod,String url, final Map<String, String> headers,final Callback<String> callback) {
        return new StringRequest(volleyMethod,
                url,new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.v(TAG,"onResponse: "+ response);
                if(callback!=null)callback.onResponse(response);
            }
        },new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(TAG, "Error: " + volleyError);
                if(callback!=null)callback.onFailure(volleyError);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
    }

    private Request<String> buildJsonStringRequest(int volleyMethod,String url, JSONObject params,final Map<String, String> headers,final Callback<String> callback) {
        return new JsonStringRequest(volleyMethod,
                url,params,new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.v(TAG,"onResponse: "+ response);
                callback.onResponse(response);
            }
        },new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(TAG, "Error: " + volleyError);
                callback.onFailure(volleyError);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
    }

    private Map<String, String> buildHeaders(String[] paramsHeaders) {

        Log.v(TAG, "buildHeaders paramsHeaders "+paramsHeaders);
        Map<String, String> aux = new HashMap<String, String>();
        String key;
        String value;
        String[] keyValue;
        for (String string:paramsHeaders) {
            Log.v(TAG, "string "+string);
            keyValue=string.split(":");
            Log.v(TAG, "keyValue "+keyValue);

            if(keyValue.length>1)
            {
                key=keyValue[0].trim();
                value=keyValue[1].trim();
                aux.put(key,value);
                Log.v(TAG,"buildHeaders key "+key+" value "+value);
            }
        }
        return aux;
    }

    public void stop()
    {
        if(queue!=null)
        {
            queue.stop();
        }
    }

    public class HeaderMap
    {
        private String httpHeader;
        private String httpValue;

        public HeaderMap(String httpHeader, String httpValue) {
            this.httpHeader = httpHeader;
            this.httpValue = httpValue;
        }

        public String getHttpHeader() {
            return httpHeader;
        }

        public void setHttpHeader(String httpHeader) {
            this.httpHeader = httpHeader;
        }

        public String getHttpValue() {
            return httpValue;
        }

        public void setHttpValue(String httpValue) {
            this.httpValue = httpValue;
        }
    };
}
