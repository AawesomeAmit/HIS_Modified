package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.GetBodyRegionModel;

import java.util.List;

public class GetBodyOrganRegionRes {
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseValue")
    @Expose
    private List<GetBodyRegionModel> responseValue = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<GetBodyRegionModel> getResponseValue() {
        return responseValue;
    }
}
