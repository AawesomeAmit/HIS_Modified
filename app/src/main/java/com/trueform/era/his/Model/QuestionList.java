package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("subCategoryID")
    @Expose
    public Integer subCategoryID;
    @SerializedName("questionText")
    @Expose
    public String questionText;
    @SerializedName("optionTypeID")
    @Expose
    public Integer optionTypeID;
    @SerializedName("relatedtoID")
    @Expose
    public Integer relatedtoID;
    @SerializedName("relatedContextID")
    @Expose
    public Integer relatedContextID;
    @SerializedName("answerDataType")
    @Expose
    public String answerDataType;
    @SerializedName("noOfOption")
    @Expose
    public Integer noOfOption;
    @SerializedName("isMendatory")
    @Expose
    public Boolean isMendatory;
    @SerializedName("userID")
    @Expose
    public Integer userID;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("d")
    @Expose
    public Integer d;
    @SerializedName("dependentQuestionID")
    @Expose
    public Integer dependentQuestionID;
    @SerializedName("indexNo")
    @Expose
    public Integer indexNo;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSubCategoryID(Integer subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setOptionTypeID(Integer optionTypeID) {
        this.optionTypeID = optionTypeID;
    }

    public void setRelatedtoID(Integer relatedtoID) {
        this.relatedtoID = relatedtoID;
    }

    public void setRelatedContextID(Integer relatedContextID) {
        this.relatedContextID = relatedContextID;
    }

    public void setAnswerDataType(String answerDataType) {
        this.answerDataType = answerDataType;
    }

    public void setNoOfOption(Integer noOfOption) {
        this.noOfOption = noOfOption;
    }

    public void setMendatory(Boolean mendatory) {
        isMendatory = mendatory;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public void setDependentQuestionID(Integer dependentQuestionID) {
        this.dependentQuestionID = dependentQuestionID;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSubCategoryID() {
        return subCategoryID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Integer getOptionTypeID() {
        return optionTypeID;
    }

    public Integer getRelatedtoID() {
        return relatedtoID;
    }

    public Integer getRelatedContextID() {
        return relatedContextID;
    }

    public String getAnswerDataType() {
        return answerDataType;
    }

    public Integer getNoOfOption() {
        return noOfOption;
    }

    public Boolean getMendatory() {
        return isMendatory;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getD() {
        return d;
    }

    public Integer getDependentQuestionID() {
        return dependentQuestionID;
    }

    public Integer getIndexNo() {
        return indexNo;
    }
}
