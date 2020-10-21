package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SubjectNameExistList;

import java.util.List;

public class SubjectNameExistResp {
    @SerializedName("subjectNameExistList")
    @Expose
    public List<SubjectNameExistList> subjectNameExistList = null;

    public List<SubjectNameExistList> getSubjectNameExistList() {
        return subjectNameExistList;
    }
}
