package com.emedinaa.monkeyandroid;

import com.android.volley.VolleyError;

/**
 * Created by emedinaa on 12/09/15.
 */
public interface MCallback<T>
{
    void onResponse(String response);

    void onFailure(VolleyError volleyError);
}
