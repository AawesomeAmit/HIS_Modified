package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SubjectWiseChatList;
import com.his.android.Model.SubjectWiseRecipientList;

import java.util.List;

public class SubjectWiseChatResp {
    @SerializedName("subjectWiseChatMessagesList")
    @Expose
    public List<SubjectWiseChatList> subjectWiseChatList = null;
    @SerializedName("subjectWiseRecipientList")
    @Expose
    public List<SubjectWiseRecipientList> subjectWiseRecipientList = null;

    public List<SubjectWiseChatList> getSubjectWiseChatList() {
        return subjectWiseChatList;
    }

    public List<SubjectWiseRecipientList> getSubjectWiseRecipientList() {
        return subjectWiseRecipientList;
    }
}
