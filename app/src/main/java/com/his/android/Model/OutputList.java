package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutputList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("typeName")
    @Expose
    public String typeName;
    @SerializedName("status")
    @Expose
    public Integer status;

    public OutputList(Integer id, String typeName, Integer status) {
        this.id = id;
        this.typeName = typeName;
        this.status = status;
    }

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
