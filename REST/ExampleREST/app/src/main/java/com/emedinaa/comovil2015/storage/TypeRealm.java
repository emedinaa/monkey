package com.emedinaa.comovil2015.storage;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emedinaa on 18/10/15.
 */
public class TypeRealm extends RealmObject {

    @PrimaryKey
    private String objectId;
    private String name;
    private int type_id;

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

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}
