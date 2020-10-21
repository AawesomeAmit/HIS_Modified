package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectNameListTab {
    @SerializedName("chatMasterId")
    @Expose
    public Integer chatMasterId;
    @SerializedName("subjectName")
    @Expose
    public String subjectName;
    @SerializedName("pid")
    @Expose
    public Integer pid;

    public Integer getChatMasterId() {
        return chatMasterId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Integer getPid() {
        return pid;
    }
}
