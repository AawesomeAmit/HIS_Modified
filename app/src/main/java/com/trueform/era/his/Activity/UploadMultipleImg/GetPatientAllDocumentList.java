package com.trueform.era.his.Activity.UploadMultipleImg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPatientAllDocumentList {
    @SerializedName("fileType")
    @Expose
    public String fileType;
    @SerializedName("fileDateTime")
    @Expose
    public String fileDateTime;
    @SerializedName("fileName")
    @Expose
    public String fileName;
    @SerializedName("filePath")
    @Expose
    public String filePath;

    public String getFileType() {
        return fileType;
    }

    public String getFileDateTime() {
        return fileDateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }
}
