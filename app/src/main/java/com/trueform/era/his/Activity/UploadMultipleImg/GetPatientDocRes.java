package com.trueform.era.his.Activity.UploadMultipleImg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPatientDocRes {
    @SerializedName("getPatientAllDocumentList")
    @Expose
    public List<GetPatientAllDocumentList> getPatientAllDocumentList = null;

    public List<GetPatientAllDocumentList> getGetPatientAllDocumentList() {
        return getPatientAllDocumentList;
    }
}

