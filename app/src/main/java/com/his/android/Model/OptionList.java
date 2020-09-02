package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptionList {
    @SerializedName("setId")
    @Expose
    public Integer setId;
    @SerializedName("answerDataType")
    @Expose
    public String answerDataType;
    @SerializedName("subCategoryID")
    @Expose
    public Integer subCategoryID;
    @SerializedName("questionMasterID")
    @Expose
    public Integer questionMasterID;
    @SerializedName("optionText")
    @Expose
    public String optionText;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("dependentQuestionID")
    @Expose
    public Integer dependentQuestionID;
    @SerializedName("categoryID")
    @Expose
    public Integer categoryID;
    @SerializedName("answer")
    @Expose
    public String answer;
    @SerializedName("isShow")
    @Expose
    public Boolean isShow;

    public Integer getSetId() {
        return setId;
    }

    public String getAnswerDataType() {
        return answerDataType;
    }

    public Integer getSubCategoryID() {
        return subCategoryID;
    }

    public Integer getQuestionMasterID() {
        return questionMasterID;
    }

    public String getOptionText() {
        return optionText;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDependentQuestionID() {
        return dependentQuestionID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public String getAnswer() {
        return answer;
    }

    public Boolean getShow() {
        return isShow;
    }
}
