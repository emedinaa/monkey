package com.emedinaa.monkeyandroid;



import android.content.Context;
import android.util.Log;

import com.emedinaa.monkeyandroid.http.MGET;
import com.emedinaa.monkeyandroid.http.MHeader;
import com.emedinaa.monkeyandroid.http.MHeaders;
import com.emedinaa.monkeyandroid.http.MPOST;
import com.emedinaa.monkeyandroid.http.MPUT;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by emedinaa on 12/09/15.
 */
public class Monkey {

    private static final String TAG = "Monkey";
    private String path;
    private Context context;
    public Monkey(Builder builder)
    {
        this.path= builder.path;
        this.context= builder.context;
    }

    public <T> T create(final Class<T> service)
    {
        MonkeyUtils.validateInterface(service);

        return (T)Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
            {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                return MethodHandler.create(context,method,getPath()).invoke(args);
            }
        });
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static class Builder
    {
        private String path;
        private Context context;
        public Builder setEndpoint(String path)
        {
            this.path=path;
            return this;
        }
        public Builder setContext(Context context)
        {
            this.context= context;
            return this;
        }
        public Monkey build()
        {
            return  new Monkey(this);
        }
    }
}
