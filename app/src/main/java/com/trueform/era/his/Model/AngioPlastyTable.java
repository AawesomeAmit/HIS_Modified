package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AngioPlastyTable {
    @SerializedName("subTestName")
    @Expose
    public String subTestName;
    @SerializedName("template")
    @Expose
    public String template;
    @SerializedName("itemName")
    @Expose
    public String itemName;

    public String getSubTestName() {
        return subTestName;
    }

    public String getTemplate() {
        return template;
    }

    public String getItemName() {
        return itemName;
    }
}
