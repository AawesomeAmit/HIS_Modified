package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttributeList {
    @SerializedName("attributeID")
    @Expose
    public Integer attributeID;
    @SerializedName("attributeName")
    @Expose
    public String attributeName;

    @Override
    public String toString() {
        return attributeName;
    }

    public AttributeList(Integer attributeID, String attributeName) {
        this.attributeID = attributeID;
        this.attributeName = attributeName;
    }

    public Integer getAttributeID() {
        return attributeID;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
