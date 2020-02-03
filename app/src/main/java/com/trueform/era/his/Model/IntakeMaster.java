package com.trueform.era.his.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntakeMaster {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("typeName")
    @Expose
    public String typeName;
    @SerializedName("status")
    @Expose
    public Integer status;

    public IntakeMaster(Integer id, String typeName, Integer status) {
        this.id = id;
        this.typeName = typeName;
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return typeName;
    }

    public Integer getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public Integer getStatus() {
        return status;
    }
}
