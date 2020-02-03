package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.OptionList;
import com.trueform.era.his.Model.QuestionList;

import java.util.List;

public class QuestionnaireResp {
    @SerializedName("questionList")
    @Expose
    public List<QuestionList> questionList = null;
    @SerializedName("optionList")
    @Expose
    public List<OptionList> optionList = null;
    public List<QuestionList> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionList> questionList) {
        this.questionList = questionList;
    }

    public List<OptionList> getOptionList() {
        return optionList;
    }
}
