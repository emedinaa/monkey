package com.emedinaa.monkeyandroid;


import com.emedinaa.monkeyandroid.http.GET;
import com.emedinaa.monkeyandroid.http.POST;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by emedinaa on 12/09/15.
 */
public class Monkey {

    private String path;
    public Monkey(Builder builder)
    {
        this.path= builder.path;
    }

    public <T> T create(final Class<T> service)
    {
        Annotation[] annotations = service.getAnnotations();
        System.out.println("monkey annotations "+annotations);
        /*for(Annotation annotation : annotations)
        {
            if(annotation instanceof GET) {
                GET myAnnotation = (GET) annotation;
                System.out.println("monkey value "+myAnnotation.value());;
            }
        }*/
        for (Method method : service.getDeclaredMethods())
        {
            System.out.println("method "+method.getName());
            Annotation[] nAnnotations = method.getDeclaredAnnotations();
            for(Annotation annotation : nAnnotations){
                if(annotation instanceof GET)
                {
                    GET myGET = (GET) annotation;
                    System.out.println("monkey value "+myGET.value());
                }else if (annotation instanceof POST)
                {
                    POST myPOST = (POST) annotation;
                    System.out.println("monkey value "+myPOST.value());
                }
            }
        }

        return (T)Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
            {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                return null;
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
        public Builder setEndpoint(String path)
        {
            this.path=path;
            return this;
        }
        public Monkey build()
        {
            return  new Monkey(this);
        }
    }
}
