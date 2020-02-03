package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllSuggestedProblemModel {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
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
