package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.AttributeValueList;

import java.util.List;

public class AttribValueResp {
    @SerializedName("attributeValueList")
    @Expose
    public List<AttributeValueList> attributeValueList = null;

    public List<AttributeValueList> getAttributeValueList() {
        return attributeValueList;
    }
}
