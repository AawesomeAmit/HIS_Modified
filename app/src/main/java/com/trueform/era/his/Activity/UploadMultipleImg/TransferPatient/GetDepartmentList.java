package com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDepartmentList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("subDepartmentName")
    @Expose
    public String subDepartmentName;

    public Integer getId() {
        return id;
    }

    public String getSubDepartmentName() {
        return subDepartmentName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSubDepartmentName(String subDepartmentName) {
        this.subDepartmentName = subDepartmentName;
    }

    @Override
    public String toString() {
        return subDepartmentName;
    }
}
