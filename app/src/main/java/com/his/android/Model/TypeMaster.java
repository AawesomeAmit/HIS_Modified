package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypeMaster {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("typeName")
    @Expose
    public String typeName;

    public Integer getId() {
        return id;
    }

    public TypeMaster(Integer id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
