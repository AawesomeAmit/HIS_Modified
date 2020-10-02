package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatFilesUploaderResp {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("fileType")
    @Expose
    public String fileType;
    @SerializedName("fileExtn")
    @Expose
    public String fileExtn;
    @SerializedName("fileSize")
    @Expose
    public String fileSize;
    @SerializedName("filePath")
    @Expose
    public String filePath;

    public String getId() {
        return id;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileExtn() {
        return fileExtn;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getFilePath() {
        return filePath;
    }
}
