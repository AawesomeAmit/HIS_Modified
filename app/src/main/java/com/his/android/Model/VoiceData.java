package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoiceData {
    @SerializedName("voiceDataId")
    @Expose
    public Integer voiceDataId;
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("formName")
    @Expose
    public String formName;
    @SerializedName("voiceData")
    @Expose
    public String voiceData;
    @SerializedName("voiceDate")
    @Expose
    public String voiceDate;
    @SerializedName("isChecked")
    @Expose
    public Integer isChecked;

    public Integer getVoiceDataId() {
        return voiceDataId;
    }

    public Integer getPmID() {
        return pmID;
    }

    public String getFormName() {
        return formName;
    }

    public String getVoiceData() {
        return voiceData;
    }

    public String getVoiceDate() {
        return voiceDate;
    }

    public Integer getIsChecked() {
        return isChecked;
    }
}
