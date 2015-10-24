package com.emedinaa.monkeyandroid;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by emedinaa on 23/10/15.
 */
public class RelativeUrlParse {

    private Object[] args;
    private HeaderAnnotationParam headerAnnotationParam;

    private MCallback<String> MCallback;
    private String relativeUrl;
    private int httpMethod=HttpClient.GET;
    private JSONObject jsonObject;

    public RelativeUrlParse(String relativeUrl,HeaderAnnotationParam headerAnnotationParam,int httpMethod,Object[] args)
    {
        this.relativeUrl=relativeUrl;
        this.headerAnnotationParam = headerAnnotationParam;
        this.httpMethod= httpMethod;
        this.args=args;

    }

    public void parse()
    {
        jsonObject = null;
        this.MCallback = (MCallback<String>) (this.args[this.args.length - 1]);

        if (this.httpMethod == HttpClient.POST || this.httpMethod==HttpClient.PUT )
        {
            if (args.length < 2) {
                throw new IllegalArgumentException("Debes tener como mínimo 2 parámetros , 1 Objeto y un Callback.");
            }
            try {
                if (args.length == 2) {
                    jsonObject = (JSONObject) (args[0]);
                } else {
                    jsonObject = (JSONObject) (args[1]);
                }

            } catch (ClassCastException e) {
            }
        }

        if(this.httpMethod==HttpClient.DELETE)
        {
            jsonObject=null;
            if (args.length < 2) {
                throw new IllegalArgumentException("Debes tener como mínimo 2 parámetros , 1 Objeto y un Callback.");
            }

            try {
                if (args.length == 2) {
                    jsonObject = (JSONObject) (args[0]);
                } else
                {
                    if(args[1] instanceof MCallback)
                    {
                        jsonObject = null;
                    }
                    if (args[1] instanceof JSONObject)
                    {
                        jsonObject=(JSONObject)(args[1]);
                    }
                }

            } catch (ClassCastException e) {
            }

        }

        if(headerAnnotationParam.isGotPath())
        {
            String originalURL= this.relativeUrl;
            Object object= args[0];
            String annotation= headerAnnotationParam.getPathValue();

            if(originalURL.contains("{"+annotation+"}"))
            {
                this.relativeUrl= originalURL.replace("{"+annotation+"}",object.toString());
            }else
            {
                throw new IllegalArgumentException("Debes agregar el valor a reemplazar entre las llaves {} ");
            }
        }

        System.out.println("relative url parse this.relativeUrl= "+this.relativeUrl+" this.callback= "+this.MCallback +" jsonObject= "+this.jsonObject);
    }

    public MCallback<String> getMCallback() {
        return this.MCallback;
    }


    public String getRelativeUrl() {
        return relativeUrl;
    }


    public int getHttpMethod() {
        return httpMethod;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String toString() {
        return "RelativeUrlParse{" +
                "args=" + Arrays.toString(args) +
                ", headerAnnotationParam=" + headerAnnotationParam +
                ", callback=" + MCallback +
                ", relativeUrl='" + relativeUrl + '\'' +
                ", httpMethod=" + httpMethod +
                ", jsonObject=" + jsonObject +
                '}';
    }
}
