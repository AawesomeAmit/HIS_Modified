package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.AttributeValueList;

import java.util.List;

public class AttribValueResp {
    @SerializedName("attributeValueList")
    @Expose
    public List<AttributeValueList> attributeValueList = null;

    public List<AttributeValueList> getAttributeValueList() {
        return attributeValueList;
    }
}
