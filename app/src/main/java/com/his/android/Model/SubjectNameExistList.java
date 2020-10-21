package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectNameExistList {
    @SerializedName("chatId")
    @Expose
    public Integer chatId;
    @SerializedName("chatSubjectName")
    @Expose
    public String chatSubjectName;
    @SerializedName("pid")
    @Expose
    public Integer pid;

    public Integer getChatId() {
        return chatId;
    }

    public String getChatSubjectName() {
        return chatSubjectName;
    }

    public Integer getPid() {
        return pid;
    }
}
