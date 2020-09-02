package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.his.android.Model.OptionList;
import com.his.android.Model.QuestionList;
import com.his.android.R;
import com.his.android.Response.QuestionnaireResp;

public class QuestionnaireListAdp1 extends BaseAdapter {
    private Context mCtx;
    private QuestionnaireResp questionnaireResp;
    QuestionList questionList;
    OptionList optionList;

    public QuestionnaireListAdp1(Context mCtx, QuestionnaireResp questionnaireResp) {
        this.mCtx = mCtx;
        this.questionnaireResp = questionnaireResp;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return questionnaireResp.getQuestionList().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView txtQues;
        EditText edtOption;
        GridLayout gridRadio, gridChk;
        LinearLayout edtLayout, radioLayout, checkLayout;
        view = LayoutInflater.from(mCtx).inflate(R.layout.inner_questioner, null);
        txtQues = view.findViewById(R.id.txtQues);
        gridRadio = view.findViewById(R.id.gridRadio);
        gridChk = view.findViewById(R.id.gridChk);
        edtOption = view.findViewById(R.id.edtOption);
        edtLayout = view.findViewById(R.id.editLayout);
        radioLayout = view.findViewById(R.id.radioLayout);
        checkLayout = view.findViewById(R.id.checkLayout);
        questionList = questionnaireResp.getQuestionList().get(i);
        //optionList = questionnaireResp.getOptionList().get(i);
        txtQues.setText(questionList.getQuestionText());
        if (questionList.getAnswerDataType().equalsIgnoreCase("text")) {
            edtLayout.setVisibility(View.VISIBLE);
            radioLayout.setVisibility(View.GONE);
            checkLayout.setVisibility(View.GONE);
            //holder.edtOption.setText(optionList.getOptionText());
        } else if (questionList.getAnswerDataType().equalsIgnoreCase("radio")) {
            edtLayout.setVisibility(View.GONE);
            radioLayout.setVisibility(View.VISIBLE);
            checkLayout.setVisibility(View.GONE);
            RadioGroup rdGrp = new RadioGroup(mCtx);
            for (int k = 0; k < questionnaireResp.getOptionList().size(); k++) {
                optionList = questionnaireResp.getOptionList().get(k);
                if (questionList.getId().equals(optionList.getQuestionMasterID())) {
                    RadioButton rButton = new RadioButton(mCtx);
                    rButton.setTextColor(mCtx.getResources().getColor(R.color.black));
                    rButton.setButtonDrawable(null);
                    rButton.setButtonDrawable(mCtx.getResources().getDrawable(R.drawable.radio_selector));
                    rButton.setText(optionList.getOptionText());
                    rButton.setPadding(0, 15,0,15);
                    rdGrp.addView(rButton);
                }
            }
            gridRadio.addView(rdGrp);
        } else if (questionList.getAnswerDataType().equalsIgnoreCase("check")) {
            edtLayout.setVisibility(View.GONE);
            radioLayout.setVisibility(View.GONE);
            checkLayout.setVisibility(View.VISIBLE);
            for (int k = 0; k < questionnaireResp.getOptionList().size(); k++) {
                optionList = questionnaireResp.getOptionList().get(k);
                if (questionList.getId().equals(optionList.getQuestionMasterID())) {
                    CheckBox chk = new CheckBox(mCtx);
                    chk.setText(optionList.getOptionText());
                    chk.setButtonDrawable(null);
                    chk.setTextColor(mCtx.getResources().getColor(R.color.black));
                    chk.setButtonDrawable(mCtx.getResources().getDrawable(R.drawable.radio_selector));
                    chk.setPadding(0, 15,0,15);
                    gridChk.addView(chk);
                }
            }
        }
        return view;
    }
}