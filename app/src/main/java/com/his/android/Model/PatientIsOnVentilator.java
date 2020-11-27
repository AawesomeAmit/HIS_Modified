package com.his.android.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class PatientIsOnVentilator {
    @SerializedName("noVentilator")
    @Expose
    public Integer noVentilator;

    public Integer getNoVentilator() {
        return noVentilator;
    }
}
