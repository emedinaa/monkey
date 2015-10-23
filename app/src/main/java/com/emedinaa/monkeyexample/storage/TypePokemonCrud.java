package com.emedinaa.monkeyexample.storage;

import android.content.Context;

import com.emedinaa.monkeyexample.model.entity.TypeEntity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by emedinaa on 18/10/15.
 */
public class TypePokemonCrud {

    private Realm realm;
    private Context context;

    public TypePokemonCrud(Context context,Realm realm) {
        this.context= context;
        this.realm = realm;
    }

    public TypePokemonCrud(Context context) {
        this.context= context;
        this.realm = Realm.getInstance(this.context);
    }

    public void add(TypeEntity typeEntity)
    {
        realm.beginTransaction();

        // Add a type
        TypeRealm typeRealm = realm.createObject(TypeRealm.class);
        typeRealm.setObjectId(typeEntity.getObjectId());
        typeRealm.setName(typeEntity.getName());
        typeRealm.setType_id(typeEntity.getType_id());

        realm.commitTransaction();
    }
    public void remove(TypeEntity typeEntity)
    {
        /*
         realm.beginTransaction();
                realm.where(TimeStamp.class).equalTo("timeStamp", timestamp).findAll().clear();
                realm.commitTransaction();
         */
        //realm.beginTransaction();
        //typeEntity.removeFromRealm();
        //realm.commitTransaction();
    }

    /*public void update(TypeEntity typeEntity)
    {
        realm.beginTransaction();
        TypeEntity realmType = realm.copyToRealm(typeEntity);
        realm.commitTransaction();
    }*/

    public void saveList(List<TypeEntity> typeEntityList)
    {
        //realm.beginTransaction();
        //List<TypeEntity> realmRepos = realm.copyToRealmOrUpdate(typeEntityList);
        //realm.copyToRealmOrUpdate(typeEntityList);
        //realm.commitTransaction();
        realm.beginTransaction();
        TypeRealm typeRealm;
        for (TypeEntity typeEntity:typeEntityList
             ) {
            typeRealm= new TypeRealm();
            typeRealm.setObjectId(typeEntity.getObjectId());
            typeRealm.setName(typeEntity.getName());
            typeRealm.setType_id(typeEntity.getType_id());
            realm.copyToRealmOrUpdate(typeRealm);
        }
        realm.commitTransaction();
    }

    public List<TypeEntity> all()
    {
        List<TypeRealm> data=realm.where(TypeRealm.class).findAll();
        List<TypeEntity> typeEntities = new ArrayList<>();
        TypeEntity typeEntity;
        for (TypeRealm typeRealm:data
             ) {
            typeEntity= new TypeEntity();
            typeEntity.setObjectId(typeRealm.getObjectId());
            typeEntity.setName(typeRealm.getName());
            typeEntity.setType_id(typeRealm.getType_id());
            typeEntities.add(typeEntity);
        }
        return typeEntities;
    }

    public TypeEntity getTypeById(int type_id)
    {
        RealmResults<TypeRealm> typeEntities = realm.where(TypeRealm.class).equalTo("type_id", type_id).findAll();
        if(typeEntities.size()>0)
        {
            TypeRealm typeRealm=typeEntities.get(0);
            TypeEntity typeEntity= new TypeEntity();
            typeEntity.setObjectId(typeRealm.getObjectId());
            typeEntity.setName(typeRealm.getName());
            typeEntity.setType_id(typeRealm.getType_id());
            return typeEntity;
        }
        //User firstJohn = teenagers.where().equalTo("name", "John").findFirst();
        //TypeEntity typeEntity = teenagers.where().equalTo("name", "John").findFirst();
        return null;
    }


    public void close()
    {
        realm.close();
    }
}
