package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignContol {
    @SerializedName("controlName")
    @Expose
    public String controlName;
    @SerializedName("isAssign")
    @Expose
    public Boolean isAssign;
}
