package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAttributeList {
    @SerializedName("problemID")
    @Expose
    public Integer problemID;
    @SerializedName("attributeID")
    @Expose
    public Integer attributeID;
    @SerializedName("attributeName")
    @Expose
    public String attributeName;
    @SerializedName("attribute")
    @Expose
    public List<Attribute> attribute = null;

    public Integer getProblemID() {
        return problemID;
    }

    public Integer getAttributeID() {
        return attributeID;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public List<Attribute> getAttribute() {
        return attribute;
    }
}
