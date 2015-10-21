package com.emedinaa.comovil2015.model.entity;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by emedinaa on 21/09/15.
 */
public class PokemonEntity implements Serializable {

    private static final String TAG ="PokemonEntity" ;
    private String objectId;
    private String name;
    private int type1;
    private int type2;
    private String desc;
    private PhotoPokemon photo;


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType1() {
        return type1;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public int getType2() {
        return type2;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public PhotoPokemon getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoPokemon photo) {
        this.photo = photo;
    }

    public String getPhotoPath()
    {
        String path="";
        if(this.photo!=null)
        {
            path=this.photo.getUrl();
        }
        return path;
    }

    @Override
    public String toString() {
        return "PokemonEntity{" +
                "objectId='" + objectId + '\'' +
                ", name='" + name + '\'' +
                ", type1=" + type1 +
                ", type2=" + type2 +
                ", desc='" + desc + '\'' +
                ", photo=" + photo +
                '}';
    }

    public class PhotoPokemon implements Serializable
    {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "PhotoPokemon{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
