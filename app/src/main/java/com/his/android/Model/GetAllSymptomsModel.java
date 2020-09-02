package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllSymptomsModel {
    private boolean isSelected = false;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("problemName")
    @Expose
    private String problemName;

    public Integer getId() {
        return id;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
