package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectWiseChatList {
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
    @SerializedName("chatMessagesId")
    @Expose
    public Integer chatMessagesId;
    @SerializedName("chatMessage")
    @Expose
    public String chatMessage;
    @SerializedName("side")
    @Expose
    public String side;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("chatReadStatus")
    @Expose
    public Integer chatReadStatus;
    @SerializedName("userId")
    @Expose
    public Integer userId;
    @SerializedName("filePath")
    @Expose
    public String filePath;
    @SerializedName("userList")
    @Expose
    public String userList;

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

    public Integer getChatMessagesId() {
        return chatMessagesId;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public String getSide() {
        return side;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public Integer getChatReadStatus() {
        return chatReadStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getUserList() {
        return userList;
    }
}
