package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugUnit {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("drugunitname")
    @Expose
    public String drugunitname;

    public Integer getId() {
        return id;
    }

    public String getDrugunitname() {
        return drugunitname;
    }
}
