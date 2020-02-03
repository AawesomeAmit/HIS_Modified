package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPatientListModel {
    public Integer getId() {
        return id;
    }

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("pid")
    @Expose
    public Object pid;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("gender")
    @Expose
    public String gender;

    public Object getPid() {
        return pid;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getGender() {
        return gender;
    }

    public String getProblem() {
        return problem;
    }

    public String getPatientImage() {
        return patientImage;
    }

    @SerializedName("problem")
    @Expose
    public String problem;
    @SerializedName("patientImage")
    @Expose
    public String patientImage;
}
