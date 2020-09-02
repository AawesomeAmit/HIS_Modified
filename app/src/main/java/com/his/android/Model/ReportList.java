package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportList {
    @SerializedName("mainID")
    @Expose
    public Integer mainID;
    @SerializedName("subID")
    @Expose
    public Integer subID;
    @SerializedName("questionText")
    @Expose
    public String questionText;
    @SerializedName("optionText")
    @Expose
    public String optionText;
    @SerializedName("questionnaireDateTime")
    @Expose
    public String questionnaireDateTime;
    @SerializedName("inputTime")
    @Expose
    public String inputTime;

    public Integer getMainID() {
        return mainID;
    }

    public Integer getSubID() {
        return subID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionText() {
        return optionText;
    }

    public String getQuestionnaireDateTime() {
        return questionnaireDateTime;
    }

    public String getInputTime() {
        return inputTime;
    }
}
