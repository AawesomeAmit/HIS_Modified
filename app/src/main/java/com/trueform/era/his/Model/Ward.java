package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Ward {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("wardName")
    @Expose
    public String wardName;

    public Integer getId() {
        return id;
    }

    public String getWardName() {
        return wardName;
    }
}
