package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.AttributeList;

import java.util.List;

public class ProbAttribResp {
    @SerializedName("attributeList")
    @Expose
    public List<AttributeList> attributeList = null;

    public List<AttributeList> getAttributeList() {
        return attributeList;
    }
}
