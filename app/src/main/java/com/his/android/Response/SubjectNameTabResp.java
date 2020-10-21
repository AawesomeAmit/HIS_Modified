package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SubjectNameListTab;

import java.util.List;

public class SubjectNameTabResp {
    @SerializedName("subjectNameListTabs")
    @Expose
    public List<SubjectNameListTab> subjectNameListTabs = null;

    public List<SubjectNameListTab> getSubjectNameListTabs() {
        return subjectNameListTabs;
    }
}
