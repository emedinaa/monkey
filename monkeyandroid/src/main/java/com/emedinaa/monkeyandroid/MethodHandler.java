package com.emedinaa.monkeyandroid;

import android.content.Context;

import com.emedinaa.monkeyandroid.http.MBody;
import com.emedinaa.monkeyandroid.http.MDELETE;
import com.emedinaa.monkeyandroid.http.MGET;
import com.emedinaa.monkeyandroid.http.MHeaders;
import com.emedinaa.monkeyandroid.http.MPOST;
import com.emedinaa.monkeyandroid.http.MPUT;
import com.emedinaa.monkeyandroid.http.MPath;
import com.emedinaa.monkeyandroid.http.MQuery;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

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
        HeaderAnnotationParam headerAnnotationParam =null;

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
                    headerAnnotationParam =parseParameters(method);
                    continue;

                } else if (annotation instanceof MPOST) {
                    MPOST myPOST = (MPOST) annotation;
                    httpMethod=HttpClient.POST;
                    relativeUrl= myPOST.value();
                    System.out.println("MethodHandler MPOST value " + myPOST.value());
                    headerAnnotationParam =parseParameters(method);
                    primaryAnnotation=true;
                    continue;

                } else if (annotation instanceof MPUT) {
                    MPUT myPUT = (MPUT) annotation;
                    httpMethod=HttpClient.PUT;
                    relativeUrl= myPUT.value();
                    System.out.println("MethodHandler MPUT value " + myPUT.value());
                    headerAnnotationParam =parseParameters(method);
                    primaryAnnotation=true;
                    continue;
                }else if(annotation instanceof MDELETE)
                {
                    MDELETE myDELETE = (MDELETE) annotation;
                    httpMethod=HttpClient.DELETE;
                    relativeUrl= myDELETE.value();
                    System.out.println("MethodHandler MDELETE value " + myDELETE.value());
                    headerAnnotationParam =parseParameters(method);
                    primaryAnnotation=true;
                    continue;
                }
            }
            if(annotation instanceof MHeaders)
            {
                MHeaders myHeaders = (MHeaders) annotation;
                paramsHeaders=myHeaders.value();
                System.out.println("MethodHandler MHeaders value " + myHeaders.value());
            }


        }
        return  new MethodHandler(context,httpMethod,baseURL,relativeUrl,paramsHeaders,headerAnnotationParam);
    }

    private static HeaderAnnotationParam parseParameters(Method method) {

        HeaderAnnotationParam headerAnnotationParam =new HeaderAnnotationParam();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class[] parameterTypes = method.getParameterTypes();

        int i=0;
        for(Annotation[] annotations : parameterAnnotations){
            Class parameterType = parameterTypes[i++];

            for(Annotation annotation : annotations){
                if(annotation instanceof MBody)
                {
                    headerAnnotationParam.setGotBody(true);
                    MBody myAnnotation = (MBody) annotation;
                    System.out.println("Body param: " + parameterType.getName());
                    System.out.println("Body annotation : " + myAnnotation.toString());
                }else if(annotation instanceof MPath)
                {
                    headerAnnotationParam.setGotPath(true);
                    MPath myAnnotation = (MPath) annotation;
                    System.out.println("MPath param: " + parameterType.getName());
                    System.out.println("MPath annotation : " + myAnnotation.toString());
                    headerAnnotationParam.setPathValue(myAnnotation.value());
                }else if(annotation instanceof MQuery)
                {
                    headerAnnotationParam.setGotQuery(true);
                    MQuery myAnnotation = (MQuery) annotation;
                    System.out.println("MQuery param: " + parameterType.getName());
                    System.out.println("MQuery annotation : " + myAnnotation.toString());
                    headerAnnotationParam.setQueryValue(myAnnotation.value());

                }
            }
        }

        System.out.println("headerParameters " + headerAnnotationParam.toString());
        return headerAnnotationParam;
    }

    private HttpClient httpClient;
    private Context context;
    private String baseURL;
    private String relativeUrl;
    private Callback<String> callback;
    private String[] paramsHeaders;
    private JSONObject bodyJSON;
    private int httpMethod=HttpClient.GET;
    private HeaderAnnotationParam headerAnnotationParam;

    private MethodHandler(Context context,int httpMethod,String baseURL,String relativeUrl,String[] paramsHeaders, HeaderAnnotationParam headerAnnotationParam)
    {
        this.context= context;
        this.baseURL= baseURL;
        this.httpMethod=httpMethod;
        this.relativeUrl= relativeUrl;
        this.paramsHeaders= paramsHeaders;
        this.headerAnnotationParam= headerAnnotationParam;
        this.httpClient= new HttpClient(context,baseURL);
        //httpClient.execute();
    }

    public Object invoke(Object... args)
    {
        RelativeUrlParse relativeUrlParse= new RelativeUrlParse(this.relativeUrl,this.headerAnnotationParam,this.httpMethod,args);
        relativeUrlParse.parse();
        this.callback= relativeUrlParse.getCallback();
        this.bodyJSON= relativeUrlParse.getJsonObject();
        this.relativeUrl= relativeUrlParse.getRelativeUrl();

        System.out.println("invoke relativeUrlParse "+relativeUrlParse);

        //return httpClient.execute(httpMethod, relativeUrlParse.getRelativeUrl(), relativeUrlParse.getJsonObject(), paramsHeaders, relativeUrlParse.getCallback());
        return httpClient.execute(httpMethod, relativeUrl, bodyJSON, paramsHeaders, callback);
    }

}
