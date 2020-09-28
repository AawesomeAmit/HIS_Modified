package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SubjectList;

import java.util.List;

public class SubjectListResp {
    @SerializedName("subjectList")
    @Expose
    public List<SubjectList> subjectList = null;

    public List<SubjectList> getSubjectList() {
        return subjectList;
    }
}
