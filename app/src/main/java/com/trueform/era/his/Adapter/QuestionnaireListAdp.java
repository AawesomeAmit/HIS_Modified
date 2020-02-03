package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.trueform.era.his.Model.OptionList;
import com.trueform.era.his.Model.QuestionList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.QuestionnaireResp;

public class QuestionnaireListAdp extends RecyclerView.Adapter<QuestionnaireListAdp.RecyclerViewHolder> {
    private Context mCtx;
    private QuestionnaireResp questionnaireResp;
    QuestionList questionList;
    OptionList optionList;

    public QuestionnaireListAdp(Context mCtx, QuestionnaireResp questionnaireResp) {
        this.mCtx = mCtx;
        this.questionnaireResp = questionnaireResp;
        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public QuestionnaireListAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_questioner, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionnaireListAdp.RecyclerViewHolder holder, int i) {
        questionList = questionnaireResp.getQuestionList().get(i);
        optionList = questionnaireResp.getOptionList().get(i);
        holder.txtQues.setText(questionList.getQuestionText());
        if (questionList.getAnswerDataType().equalsIgnoreCase("text")) {
            holder.edtLayout.setVisibility(View.VISIBLE);
            holder.radioLayout.setVisibility(View.GONE);
            holder.checkLayout.setVisibility(View.GONE);
            //holder.edtOption.setText(optionList.getOptionText());
        } else if (questionList.getAnswerDataType().equalsIgnoreCase("radio")) {
            holder.edtLayout.setVisibility(View.GONE);
            holder.radioLayout.setVisibility(View.VISIBLE);
            holder.checkLayout.setVisibility(View.GONE);
            RadioGroup rdGrp = new RadioGroup(mCtx);
            for (int k = 0; k < questionnaireResp.getOptionList().size(); k++) {
                optionList = questionnaireResp.getOptionList().get(k);
                if (questionList.getId().equals(optionList.getQuestionMasterID())) {
                    RadioButton rButton = new RadioButton(mCtx);
                    rButton.setTextColor(mCtx.getResources().getColor(R.color.black));
                    rButton.setButtonDrawable(null);
                    rButton.setButtonDrawable(mCtx.getResources().getDrawable(R.drawable.radio_selector));
                    rButton.setText(optionList.getOptionText());
                    rdGrp.addView(rButton);
                }
            }
            holder.gridRadio.addView(rdGrp);
        } else if (questionList.getAnswerDataType().equalsIgnoreCase("check")) {
            holder.edtLayout.setVisibility(View.GONE);
            holder.radioLayout.setVisibility(View.GONE);
            holder.checkLayout.setVisibility(View.VISIBLE);
            for (int k = 0; k < questionnaireResp.getOptionList().size(); k++) {
                optionList = questionnaireResp.getOptionList().get(k);
                if (questionList.getId().equals(optionList.getQuestionMasterID())) {
                    CheckBox chk = new CheckBox(mCtx);
                    chk.setText(optionList.getOptionText());
                    chk.setButtonDrawable(null);
                    chk.setTextColor(mCtx.getResources().getColor(R.color.black));
                    chk.setButtonDrawable(mCtx.getResources().getDrawable(R.drawable.radio_selector));
                    holder.gridChk.addView(chk);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return questionnaireResp.getQuestionList().size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtQues;
        EditText edtOption;
        GridLayout gridRadio, gridChk;
        LinearLayout edtLayout, radioLayout, checkLayout;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQues = itemView.findViewById(R.id.txtQues);
            gridRadio = itemView.findViewById(R.id.gridRadio);
            gridChk = itemView.findViewById(R.id.gridChk);
            edtOption = itemView.findViewById(R.id.edtOption);
            edtLayout = itemView.findViewById(R.id.editLayout);
            radioLayout = itemView.findViewById(R.id.radioLayout);
            checkLayout = itemView.findViewById(R.id.checkLayout);
        }
    }
}