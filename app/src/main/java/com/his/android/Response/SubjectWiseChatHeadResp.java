package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectWiseChatHeadResp {
    @SerializedName("chatMasterId")
    @Expose
    public Integer chatMasterId;
    @SerializedName("subjectId")
    @Expose
    public Integer subjectId;
    @SerializedName("subjectName")
    @Expose
    public String subjectName;
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("userId")
    @Expose
    public Integer userId;
    @SerializedName("chatMessage")
    @Expose
    public String chatMessage;
    @SerializedName("recipientName")
    @Expose
    public String recipientName;

    public Integer getChatMasterId() {
        return chatMasterId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Integer getPid() {
        return pid;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public String getRecipientName() {
        return recipientName;
    }
}
