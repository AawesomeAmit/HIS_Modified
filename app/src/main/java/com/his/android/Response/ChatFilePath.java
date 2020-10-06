package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatFilePath {
    @SerializedName("fileType")
    @Expose
    public String fileType;
    @SerializedName("filePath")
    @Expose
    public String filePath;

    public String getFileType() {
        return fileType;
    }

    public String getFilePath() {
        return filePath;
    }
}
