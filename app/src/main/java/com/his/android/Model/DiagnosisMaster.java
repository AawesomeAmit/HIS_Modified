package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiagnosisMaster {
    @SerializedName("diagID")
    @Expose
    public Integer diagID;
    @SerializedName("diagnosis")
    @Expose
    public String diagnosis;

    public DiagnosisMaster(Integer diagID, String diagnosis) {
        this.diagID = diagID;
        this.diagnosis = diagnosis;
    }

    public Integer getDiagID() {
        return diagID;
    }

    @Override
    public String toString() {
        return diagnosis;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
}
