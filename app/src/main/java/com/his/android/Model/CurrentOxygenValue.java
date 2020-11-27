package com.his.android.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentOxygenValue {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("oxygen")
    @Expose
    public String oxygen;

    public Integer getId() {
        return id;
    }

    public String getOxygen() {
        return oxygen;
    }
}
