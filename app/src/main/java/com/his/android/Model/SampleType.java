package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SampleType {
    @SerializedName("sampleID")
    @Expose
    public Integer sampleID;
    @SerializedName("sampleName")
    @Expose
    public String sampleName;

    public Integer getSampleID() {
        return sampleID;
    }

    public String getSampleName() {
        return sampleName;
    }
}
