package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("subjectName")
    @Expose
    public String subjectName;

    @Override
    public String toString() {
        return subjectName;
    }

    public SubjectList(Integer id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }

    public Integer getId() {
        return id;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
