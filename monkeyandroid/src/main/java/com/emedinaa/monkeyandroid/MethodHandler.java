package com.emedinaa.monkeyandroid;

import android.content.Context;
import android.util.Log;

import com.emedinaa.monkeyandroid.http.MDELETE;
import com.emedinaa.monkeyandroid.http.MGET;
import com.emedinaa.monkeyandroid.http.MHeaders;
import com.emedinaa.monkeyandroid.http.MPOST;
import com.emedinaa.monkeyandroid.http.MPUT;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import retrofit.http.DELETE;

/**
 * Created by emedinaa on 21/10/15.
 */
final public class MethodHandler {

    public static MethodHandler create(Context context,Method method,String baseURL)
    {
        /*
            @MHeaders({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
            @MGET("/1/classes/Pokemon")
            void loadPokemons(Callback<PokemonResponse> callback);

            @MHeaders({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW"})
            @MGET("/1/classes/Type")
            void loadTypesPokemon(Callback<TypePokemonResponse> callback);

            @MHeaders({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugTKGdo","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6qofXsAnmimDVRbeW",
                "Content-Type: application/json"})
            @MPOST("/1/classes/Pokemon")
            void addPokemon(@MBody Object json,Callback<Object> callback);
         */
        int httpMethod=HttpClient.GET;
        String relativeUrl="";
        String[] paramsHeaders= {};

        Annotation[] nAnnotations = method.getDeclaredAnnotations();
        boolean primaryAnnotation=false;

        for(Annotation annotation : nAnnotations)
        {
            if(!primaryAnnotation)
            {
                if (annotation instanceof MGET)
                {
                    MGET myGET = (MGET) annotation;
                    httpMethod=HttpClient.GET;
                    relativeUrl= myGET.value();
                    System.out.println("MethodHandler MGET value " + myGET.value());
                    primaryAnnotation=true;
                    continue;

                } else if (annotation instanceof MPOST) {
                    MPOST myPOST = (MPOST) annotation;
                    httpMethod=HttpClient.POST;
                    relativeUrl= myPOST.value();
                    System.out.println("MethodHandler MPOST value " + myPOST.value());
                    primaryAnnotation=true;
                    continue;

                } else if (annotation instanceof MPUT) {
                    MPUT myPUT = (MPUT) annotation;
                    httpMethod=HttpClient.PUT;
                    relativeUrl= myPUT.value();
                    System.out.println("MethodHandler MPUT value " + myPUT.value());
                    primaryAnnotation=true;
                    continue;
                }else if(annotation instanceof MDELETE)
                {
                    MDELETE myDELETE = (MDELETE) annotation;
                    httpMethod=HttpClient.DELETE;
                    relativeUrl= myDELETE.value();
                    System.out.println("MethodHandler MDELETE value " + myDELETE.value());
                    primaryAnnotation=true;
                    continue;
                }
            }else
            {
                if(annotation instanceof MHeaders)
                {
                    MHeaders myHeaders = (MHeaders) annotation;
                    paramsHeaders=myHeaders.value();
                    System.out.println("MethodHandler MHeaders value " + myHeaders.value());
                }
            }

        }
        return  new MethodHandler(context,httpMethod,baseURL,relativeUrl,paramsHeaders);
    }

    private HttpClient httpClient;
    private Context context;
    private String baseURL;
    private String relativeUrl;
    private Callback<String> callback;
    private String[] paramsHeaders;
    private int httpMethod=HttpClient.GET;

    private MethodHandler(Context context,int httpMethod,String baseURL,String relativeUrl,String[] paramsHeaders)
    {
        this.context= context;
        this.baseURL= baseURL;
        this.httpMethod=httpMethod;
        this.relativeUrl= relativeUrl;
        this.paramsHeaders= paramsHeaders;

        this.httpClient= new HttpClient(context,baseURL);
        //httpClient.execute();
    }

    Object invoke(Object... args)
    {
        return httpClient.execute(httpMethod,relativeUrl,null,paramsHeaders,null);
    }
}
