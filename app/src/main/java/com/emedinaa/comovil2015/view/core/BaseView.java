package com.emedinaa.comovil2015.view.core;

import android.content.Context;

/**
 * Created by emedinaa on 21/09/15.
 */
public interface BaseView {

    void showLoading(boolean b);
    Context getContext();
    void completeSuccess(Object object,int type);
    void completeError(Object object,int type);

}
