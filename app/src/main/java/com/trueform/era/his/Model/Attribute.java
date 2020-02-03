package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attribute {
    @SerializedName("problemId")
    @Expose
    public Integer problemId;
    @SerializedName("attributeValueId")
    @Expose
    public Integer attributeValueId;
    @SerializedName("attributeID")
    @Expose
    public Integer attributeID;
    @SerializedName("attributeValue")
    @Expose
    public String attributeValue;

    public Integer getProblemId() {
        return problemId;
    }

    public Integer getAttributeValueId() {
        return attributeValueId;
    }

    public Integer getAttributeID() {
        return attributeID;
    }

    public String getAttributeValue() {
        return attributeValue;
    }
}
