package com.his.android.Activity.UploadMultipleImg.TransferPatient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDepartmentRes {
    @SerializedName("subDept")
    @Expose
    public List<GetDepartmentList> subDept = null;

    public List<GetDepartmentList> getSubDept() {
        return subDept;
    }
}
