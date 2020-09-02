package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ResultList;
import com.his.android.Model.ResultListForAndroid;

import java.util.List;

public class CalciumDynamicReportResp {
    @SerializedName("resultList")
    @Expose
    public List<ResultList> resultList = null;
    @SerializedName("resultListForAndroid")
    @Expose
    //public String resultListForAndroid = null;
    public List<ResultListForAndroid> resultListForAndroid = null;

    public List<ResultList> getResultList() {
        return resultList;
    }

    public List<ResultListForAndroid> getResultListForAndroid() {
        return resultListForAndroid;
    }
}
