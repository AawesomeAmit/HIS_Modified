package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.AttributeList;

import java.util.List;

public class ProbAttribResp {
    @SerializedName("attributeList")
    @Expose
    public List<AttributeList> attributeList = null;

    public List<AttributeList> getAttributeList() {
        return attributeList;
    }
}
