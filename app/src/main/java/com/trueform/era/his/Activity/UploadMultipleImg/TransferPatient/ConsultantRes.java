package com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConsultantRes {
    @SerializedName("doctors")
    @Expose
    public List<ConsultantList> doctors = null;
    @SerializedName("wards")
    @Expose
    public List<WardList> wards = null;

    public List<ConsultantList> getDoctors() {
        return doctors;
    }

    public List<WardList> getWards() {
        return wards;
    }
}
