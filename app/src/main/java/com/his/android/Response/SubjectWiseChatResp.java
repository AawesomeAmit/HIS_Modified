package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SubjectWiseChatList;

import java.util.List;

public class SubjectWiseChatResp {
    @SerializedName("subjectWiseChatList")
    @Expose
    public List<SubjectWiseChatList> subjectWiseChatList = null;

    public List<SubjectWiseChatList> getSubjectWiseChatList() {
        return subjectWiseChatList;
    }
}
