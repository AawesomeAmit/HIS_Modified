package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.VoiceData;

import java.util.List;

public class VoiceDataResp {
    @SerializedName("voiceDataList")
    @Expose
    public List<VoiceData> voiceDataList = null;

    public List<VoiceData> getVoiceDataList() {
        return voiceDataList;
    }
}
