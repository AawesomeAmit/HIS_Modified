package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttributeValueList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("attributeValue")
    @Expose
    public String attributeValue;

    @Override
    public String toString() {
        return attributeValue;
    }

    public AttributeValueList(Integer id, String attributeValue) {
        this.id = id;
        this.attributeValue = attributeValue;
    }

    public Integer getId() {
        return id;
    }

    public String getAttributeValue() {
        return attributeValue;
    }
}
